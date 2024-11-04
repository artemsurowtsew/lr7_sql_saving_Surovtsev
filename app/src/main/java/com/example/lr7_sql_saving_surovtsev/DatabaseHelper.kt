package com.example.lr7_sql_saving_surovtsev

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "customers.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_CUSTOMERS = "customers"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FIRST_NAME = "first_name"
        private const val COLUMN_LAST_NAME = "last_name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PURCHASE_HISTORY = "purchase_history"
        private const val COLUMN_ORDER_COUNT = "order_count"
        private const val COLUMN_DISCOUNTS = "discounts"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_CUSTOMERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_FIRST_NAME TEXT," +
                "$COLUMN_LAST_NAME TEXT," +
                "$COLUMN_EMAIL TEXT," +
                "$COLUMN_PURCHASE_HISTORY TEXT," +
                "$COLUMN_ORDER_COUNT INTEGER," +
                "$COLUMN_DISCOUNTS REAL)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CUSTOMERS")
        onCreate(db)
    }


    fun addCustomer(customer: Customer) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_FIRST_NAME, customer.firstName)
            put(COLUMN_LAST_NAME, customer.lastName)
            put(COLUMN_EMAIL, customer.email)
            put(COLUMN_PURCHASE_HISTORY, customer.purchaseHistory)
            put(COLUMN_ORDER_COUNT, customer.orderCount)
            put(COLUMN_DISCOUNTS, customer.discount)
        }
        db.insert(TABLE_CUSTOMERS, null, contentValues)
        db.close()
    }


    fun getAllCustomers(): List<Customer> {
        val customerList = mutableListOf<Customer>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CUSTOMERS", null)

        if (cursor.moveToFirst()) {
            do {
                val customer = Customer(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)).toInt(),
                    firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)),
                    lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    purchaseHistory = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PURCHASE_HISTORY)),
                    orderCount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_COUNT)),
                    discount = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_DISCOUNTS)).toDouble()
                )
                customerList.add(customer)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return customerList
    }

    // Update an existing customer
    fun updateCustomer(customer: Customer) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_FIRST_NAME, customer.firstName)
            put(COLUMN_LAST_NAME, customer.lastName)
            put(COLUMN_EMAIL, customer.email)
            put(COLUMN_PURCHASE_HISTORY, customer.purchaseHistory)
            put(COLUMN_ORDER_COUNT, customer.orderCount)
            put(COLUMN_DISCOUNTS, customer.discount)
        }
        db.update(TABLE_CUSTOMERS, contentValues, "$COLUMN_ID = ?", arrayOf(customer.id.toString()))
        db.close()
    }
    fun clearCustomers() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_CUSTOMERS")
        db.close()
    }

}
