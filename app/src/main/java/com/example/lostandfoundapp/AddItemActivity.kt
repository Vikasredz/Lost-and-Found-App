package com.example.lostandfoundapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class AddItemActivity : AppCompatActivity() {

    lateinit var radioLost: RadioButton
    lateinit var radioFound: RadioButton
    lateinit var editName: EditText
    lateinit var editPhone: EditText
    lateinit var editDescription: EditText
    lateinit var editDate: EditText
    lateinit var editLocation: EditText
    lateinit var spinnerCategory: Spinner
    lateinit var btnUploadImage: Button
    lateinit var imagePreview: ImageView
    lateinit var btnSave: Button

    lateinit var db: DatabaseHelper
    var selectedImageUri: Uri? = null

    private val PICK_IMAGE_REQUEST = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        db = DatabaseHelper(this)

        radioLost = findViewById(R.id.radioLost)
        radioFound = findViewById(R.id.radioFound)
        editName = findViewById(R.id.editName)
        editPhone = findViewById(R.id.editPhone)
        editDescription = findViewById(R.id.editDescription)
        editDate = findViewById(R.id.editDate)
        editLocation = findViewById(R.id.editLocation)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        btnUploadImage = findViewById(R.id.btnUploadImage)
        imagePreview = findViewById(R.id.imagePreview)
        btnSave = findViewById(R.id.btnSave)

        val categories = arrayOf("Electronics", "Pets", "Wallets", "Keys", "Bags", "Documents", "Other")
        spinnerCategory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)

        btnUploadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnSave.setOnClickListener {
            saveItem()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data

            selectedImageUri?.let {
                contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                imagePreview.setImageURI(it)
            }
        }
    }

    private fun saveItem() {
        val type = if (radioLost.isChecked) "Lost" else "Found"
        val name = editName.text.toString()
        val phone = editPhone.text.toString()
        val description = editDescription.text.toString()
        val date = editDate.text.toString()
        val location = editLocation.text.toString()
        val category = spinnerCategory.selectedItem.toString()

        if (name.isEmpty() || phone.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedImageUri == null) {
            Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show()
            return
        }

        val timestamp = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

        val item = ItemModel(
            id = 0,
            type = type,
            name = name,
            phone = phone,
            description = description,
            date = date,
            location = location,
            category = category,
            imageUri = selectedImageUri.toString(),
            createdAt = timestamp
        )

        val success = db.insertItem(item)

        if (success) {
            Toast.makeText(this, "Advert saved successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Failed to save advert", Toast.LENGTH_SHORT).show()
        }
    }
}