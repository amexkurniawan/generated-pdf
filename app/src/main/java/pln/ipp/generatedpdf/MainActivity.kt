package pln.ipp.generatedpdf

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.os.Environment
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.text.TextPaint
import android.util.Log
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
    var btnCreate: Button? = null
    var editText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCreate = findViewById<View>(R.id.create) as Button
        editText = findViewById<View>(R.id.edittext) as EditText
        btnCreate!!.setOnClickListener {
            createPdf()
        }
    }

    private fun createPdf() {
        // create a new document
        val document = PdfDocument()

        // Create Page 1
        var pageInfo = PageInfo.Builder(300, 600, 1).create()
        var page = document.startPage(pageInfo)
        var canvas: Canvas = page.canvas
        var paint = TextPaint()
        paint.setColor(Color.BLACK)
        //canvas.drawMultilineText("Proyek Name : ", paint, 60, 24F, 50F)
        canvas.drawMultilineText("Proyek Name : ${getString(R.string.long_text)}", paint, 300, 24F, 50F)
        document.finishPage(page)

        // Create Page 2
        pageInfo = PageInfo.Builder(300, 600, 2).create()
        page = document.startPage(pageInfo)
        canvas = page.canvas
        paint = TextPaint()
        paint.setColor(Color.BLACK)
        canvas.drawText("Text 3", 24F, 50F, paint)
        document.finishPage(page)

        // write the document content
        val directory_path = Environment.getExternalStorageDirectory().path + "/mypdf/"
        val file = File(directory_path)

        if (!file.exists()) {
            file.mkdirs()
        }

        val targetPdf = directory_path + "IPP-Generated-Pdf.pdf"
        val filePath = File(targetPdf)

        try {
            document.writeTo(FileOutputStream(filePath))
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            Log.e("main", "error " + e.toString())
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show()
        }

        // close the document
        document.close()
    }
}