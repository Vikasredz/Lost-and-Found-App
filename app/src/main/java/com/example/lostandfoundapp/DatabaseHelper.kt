package com.example.lostandfoundapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "LostFoundDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = """
            CREATE TABLE items (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                type TEXT,
                name TEXT,
                phone TEXT,
                description TEXT,
                date TEXT,
                location TEXT,
                category TEXT,
                imageUri TEXT,
                createdAt TEXT
            )
        """
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS items")
        onCreate(db)
    }

    fun insertItem(item: ItemModel): Boolean {
        val db = writableDatabase
        val values = ContentValues()

        values.put("type", item.type)
        values.put("name", item.name)
        values.put("phone", item.phone)
        values.put("description", item.description)
        values.put("date", item.date)
        values.put("location", item.location)
        values.put("category", item.category)
        values.put("imageUri", item.imageUri)
        values.put("createdAt", item.createdAt)

        val result = db.insert("items", null, values)
        db.close()

        return result != -1L
    }

    fun getAllItems(): ArrayList<ItemModel> {
        val list = ArrayList<ItemModel>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM items ORDER BY id DESC", null)

        if (cursor.moveToFirst()) {
            do {
                list.add(
                    ItemModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9)
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return list
    }

    fun getItemsByCategory(category: String): ArrayList<ItemModel> {
        val list = ArrayList<ItemModel>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM items WHERE category = ? ORDER BY id DESC",
            arrayOf(category)
        )

        if (cursor.moveToFirst()) {
            do {
                list.add(
                    ItemModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9)
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return list
    }

    fun deleteItem(id: Int): Boolean {
        val db = writableDatabase
        val result = db.delete("items", "id=?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }
}