package com.example.lr7_sql_saving_surovtsev

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class CustomerDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_detail)


        val customer = intent.getSerializableExtra("customer") as Customer
        val textViewDetail = findViewById<TextView>(R.id.textViewDetail)


        textViewDetail.text = "Name: ${customer.firstName} ${customer.lastName}\nEmail: ${customer.email}"
    }
}
