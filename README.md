#  Lost & Found Mobile App 

##  Overview

This project is an Android mobile application developed for SIT708 Task 7.1. The app allows users to report lost or found items and helps connect them with their rightful owners.



##  Features

*  Create Lost/Found Advert
*  SQLite Database for local storage
*  Image Upload (Mandatory for each post)
*  Category-based filtering (Electronics, Pets, Wallets, etc.)
*  Automatic Date & Time Stamp
*  View all items in a list
*  Remove advert once item is found



## Technologies Used

* Kotlin
* Android Studio
* SQLite Database
* XML (UI Design)



##  App Screens

1. **Main Screen**

   * Create New Advert
   * View Lost & Found Items

2. **Add Item Screen**

   * Enter item details
   * Upload image
   * Save advert

3. **View Items Screen**

   * Display all items
   * Filter by category
   * Remove advert



## Project Structure

* `MainActivity.kt` → Home screen
* `AddItemActivity.kt` → Add new item
* `ViewItemsActivity.kt` → Display and filter items
* `DatabaseHelper.kt` → SQLite database operations
* `ItemAdapter.kt` → ListView adapter
* `ItemModel.kt` → Data model

