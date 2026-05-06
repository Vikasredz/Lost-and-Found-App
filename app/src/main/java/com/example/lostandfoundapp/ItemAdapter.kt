package com.example.lostandfoundapp

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ItemAdapter(
    private val context: Context,
    private val items: ArrayList<ItemModel>,
    private val db: DatabaseHelper
) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = items[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_row, parent, false)

        val imageItem = view.findViewById<ImageView>(R.id.imageItem)
        val textTitle = view.findViewById<TextView>(R.id.textTitle)
        val textDetails = view.findViewById<TextView>(R.id.textDetails)
        val btnRemove = view.findViewById<Button>(R.id.btnRemove)

        val item = items[position]

        textTitle.text = "${item.type}: ${item.name}"
        textDetails.text = """
            Category: ${item.category}
            Phone: ${item.phone}
            Location: ${item.location}
            Date Lost/Found: ${item.date}
            Posted: ${item.createdAt}
            Description: ${item.description}
        """.trimIndent()

        imageItem.setImageURI(Uri.parse(item.imageUri))

        btnRemove.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Remove Advert")
                .setMessage("Are you sure you want to remove this item?")
                .setPositiveButton("Yes") { _, _ ->
                    db.deleteItem(item.id)
                    items.removeAt(position)
                    notifyDataSetChanged()
                    Toast.makeText(context, "Advert removed", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No", null)
                .show()
        }

        return view
    }
}