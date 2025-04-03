package com.myapp.spendless.feature.HomeScreen.presentation.component

import android.content.ContentValues
import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.myapp.spendless.feature.HomeScreen.model.Transaction

fun exportTransactionToPDF(context: Context, transactions: List<Transaction>) {
    val fileName = "transactions_${System.currentTimeMillis()}.pdf"

    val pdfDocument = PdfDocument()
    val paint = Paint()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size (595x842)
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas

    var startX = 50f
    var startY = 50f
    val rowHeight = 30f
    val columnWidths = listOf(50f, 100f, 100f, 100f, 100f, 100f)

    paint.textSize = 14f
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

    // Draw Table Headers
    val headers = listOf("ID", "Title", "Amount", "Note", "Category", "Date")
    for (i in headers.indices) {
        canvas.drawText(headers[i], startX, startY, paint)
        startX += columnWidths[i]
    }

    // Draw Header Line
    startX = 50f
    startY += rowHeight
    canvas.drawLine(50f, startY - 10f, 550f, startY - 10f, paint)

    paint.typeface = Typeface.DEFAULT
    paint.textSize = 12f

    // Draw Transactions Data
    transactions.forEach { transaction ->
        val rowValues = listOf(
            transaction.id.toString(),
            transaction.title,
            transaction.amount.toString(),
            transaction.note,
            transaction.category,
            transaction.date.toString()
        )

        for (i in rowValues.indices) {
            canvas.drawText(rowValues[i], startX, startY, paint)
            startX += columnWidths[i]
        }

        // Draw row separator
        startX = 50f
        startY += rowHeight
        canvas.drawLine(50f, startY - 10f, 550f, startY - 10f, paint)
    }

    pdfDocument.finishPage(page)

    // Save the PDF file
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
    }

    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

    uri?.let {
        try {
            resolver.openOutputStream(it)?.use { outputStream ->
                pdfDocument.writeTo(outputStream)
            }
            Toast.makeText(context, "PDF Exported Successfully", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to Export PDF", Toast.LENGTH_LONG).show()
        }
    }

    pdfDocument.close()
}

