package com.example.lostandfoundapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ViewItemsActivity : AppCompatActivity() {

    lateinit var spinnerFilter: Spinner
    lateinit var listViewItems: ListView
    lateinit var db: DatabaseHelper
    lateinit var itemList: ArrayList<ItemModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_items)

        db = DatabaseHelper(this)
        spinnerFilter = findViewById(R.id.spinnerFilter)
        listViewItems = findViewById(R.id.listViewItems)

        val categories = arrayOf("All", "Electronics", "Pets", "Wallets", "Keys", "Bags", "Documents", "Other")
        spinnerFilter.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)

        loadItems("All")

        spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                loadItems(categories[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun loadItems(category: String) {
        itemList = if (category == "All") {
            db.getAllItems()
        } else {
            db.getItemsByCategory(category)
        }

        val adapter = ItemAdapter(this, itemList, db)
        listViewItems.adapter = adapter
    }
}