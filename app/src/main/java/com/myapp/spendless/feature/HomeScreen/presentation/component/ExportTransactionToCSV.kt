package com.myapp.spendless.feature.HomeScreen.presentation.component

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import com.myapp.spendless.feature.HomeScreen.model.Transaction
import java.io.OutputStream

fun exportTransactionToCSV(context: Context, transaction: List<Transaction>) {

    val fileName = "transactions_${System.currentTimeMillis()}.csv"
    val mimeType = "text/csv"

    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
    }

    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let {
        try {
            resolver.openOutputStream(it)?.use { outputStream ->
                writeCsv(outputStream, transaction)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}

fun writeCsv(outputStream: OutputStream, transaction: List<Transaction>){
    outputStream.bufferedWriter().use { writer ->
        writer.append("ID, Title, Amount, Note, Category, Date\n")
        transaction.forEach{transaction ->
            writer.append("${transaction.id},\"${transaction.title}\",${transaction.amount},\"${transaction.note}\",\"${transaction.category}\",${transaction.date.toDateFormat()}\n")
        }
    }
}