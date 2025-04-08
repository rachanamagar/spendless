package com.myapp.spendless.feature.HomeScreen.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.spendless.feature.HomeScreen.model.Transaction
import com.myapp.spendless.feature.HomeScreen.model.TransactionRepository
import com.myapp.spendless.feature.HomeScreen.presentation.state.TransactionEvent
import com.myapp.spendless.util.DataStoreManager
import com.myapp.spendless.util.SessionManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionViewModelTest {

    @get: Rule
    val instantTaskExecutorRole = InstantTaskExecutorRule()

    val mockkUUID = UUID.randomUUID()
    private lateinit var viewModel: TransactionViewModel
    private val repository: TransactionRepository = mockk(relaxed = true)
    private val sessionManager: SessionManager = mockk(relaxed = true)
    private val dataStoreManager: DataStoreManager = mockk(relaxed = true)

    val mockTransactions = listOf(
        Transaction(
            title = "Transaction 1",
            amount = 100.0,
            category = "Expense",
            date = System.currentTimeMillis(),
            userId = UUID.randomUUID(),
            id = 1,
            note = "note",
            icon = 1
        ),
        Transaction(
            title = "Transaction 2",
            amount = 200.0,
            category = "Income",
            date = System.currentTimeMillis(),
            userId = UUID.randomUUID(),
            id = 2,
            note = "note2",
            icon = 2
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }


    @Test
    fun `given viewmodel,when initialized, should call getTransaction once`() = runTest {
        //When
        every { sessionManager.getUserName() } returns flowOf("TestUser")
        every { sessionManager.getUserSession() } returns flowOf(mockkUUID)
        coEvery { repository.getTransaction(mockkUUID) } returns flowOf(mockTransactions)
        coEvery { dataStoreManager.saveTotalAmount(any()) } just runs
        viewModel = TransactionViewModel(repository, sessionManager, dataStoreManager)
        runCurrent()
        //Then
        coVerify(exactly = 1) { repository.getTransaction(mockkUUID) }
    }

    @Test
    fun `given the new transaction, insert the transaction in the list`() = runTest {

        val fakeTransaction =  Transaction(
            title = "Transaction 1",
            amount = 100.0,
            category = "Expense",
            date = System.currentTimeMillis(),
            userId = UUID.randomUUID(),
            id = 1,
            note = "note",
            icon = 1
        )
        every { sessionManager.getUserSession() } returns flowOf(mockkUUID)
        coEvery { repository.getTransaction(mockkUUID) } returns flowOf(listOf(fakeTransaction))
        coEvery { repository.insertTransaction(any(), any()) } just runs

        viewModel = TransactionViewModel(repository, sessionManager, dataStoreManager)

        viewModel.handleEvent(TransactionEvent.updateTitle(fakeTransaction.title))
        viewModel.handleEvent(TransactionEvent.updateAmount(fakeTransaction.amount))
        viewModel.handleEvent(TransactionEvent.updateCategory(fakeTransaction.category))
        viewModel.handleEvent(TransactionEvent.updateNote(fakeTransaction.note))
        viewModel.handleEvent(TransactionEvent.updateIcon(fakeTransaction.icon))
        viewModel.insertTransaction()
        runCurrent()

        // Assert: verify repo call
        coVerify { repository.insertTransaction(any(), mockkUUID) }

        // Assert: check if transaction is in the state list
        val updatedList = viewModel.uiState.value.transactionList
        assertEquals(1, updatedList.size)
        assertEquals(fakeTransaction.title, updatedList[0].title)
        assertEquals(fakeTransaction.amount, updatedList[0].amount, 0.0)
        assertEquals(fakeTransaction.category, updatedList[0].category)
        assertEquals(fakeTransaction.note, updatedList[0].note)
    }
}