package com.example.productivity_pocket

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.productivity_pocket.R
import java.text.SimpleDateFormat
import java.util.*

class DailyJournalActivity : AppCompatActivity() {

    private lateinit var imgPreview: ImageView
    private lateinit var edtJournal: EditText
    private lateinit var btnSave: Button
    private lateinit var btnSelectImage: Button
    private lateinit var journalList: LinearLayout

    private var imageUri: Uri? = null
    private val PICK_IMAGE = 100

    private val dateKey: String
        get() = "journal_" + SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_journal)

        imgPreview = findViewById(R.id.imgPreview)
        edtJournal = findViewById(R.id.edtJournal)
        btnSave = findViewById(R.id.btnSaveJournal)
        btnSelectImage = findViewById(R.id.btnSelectImage)
        journalList = findViewById(R.id.journalList)

        loadJournalEntries()

        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            startActivityForResult(intent, PICK_IMAGE)
        }

        btnSave.setOnClickListener {
            saveJournalEntry()
        }
    }

    private fun saveJournalEntry() {
        val prefs = getSharedPreferences("daily_journal", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val journalText = edtJournal.text.toString()
        val image = imageUri?.toString() ?: ""

        val entry = "$journalText||$image"
        editor.putString(dateKey, entry).apply()

        Toast.makeText(this, "Journal saved!", Toast.LENGTH_SHORT).show()
        edtJournal.text.clear()
        imgPreview.setImageResource(0)
        imageUri = null

        loadJournalEntries()
    }

    private fun loadJournalEntries() {
        journalList.removeAllViews()
        val prefs = getSharedPreferences("daily_journal", Context.MODE_PRIVATE)

        prefs.all.toSortedMap().forEach { (key, value) ->
            val entryLayout = layoutInflater.inflate(R.layout.item_journal_entry, journalList, false)
            val parts = (value as String).split("||")
            val text = parts.getOrElse(0) { "" }
            val uri = parts.getOrElse(1) { "" }

            entryLayout.findViewById<TextView>(R.id.journalText).text = "$key:\n$text"

            val imageView = entryLayout.findViewById<ImageView>(R.id.journalImage)
            if (uri.isNotEmpty()) {
                imageView.setImageURI(Uri.parse(uri))
            }

            journalList.addView(entryLayout)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                imageUri = it
                contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                imgPreview.setImageURI(it)
            }
        }
    }
}
