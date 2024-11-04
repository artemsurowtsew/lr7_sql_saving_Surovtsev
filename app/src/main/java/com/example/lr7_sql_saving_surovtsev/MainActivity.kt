package com.example.lr7_sql_saving_surovtsev

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomerAdapter
    private lateinit var customers: List<Customer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ініціалізуємо базу даних
        databaseHelper = DatabaseHelper(this)
        // Додаємо початкові записи, якщо база даних порожня

        if (databaseHelper.getAllCustomers().isEmpty()) {
            addInitialCustomers()
        }

        // Завантажуємо список клієнтів з бази даних
        customers = databaseHelper.getAllCustomers()

        // Налаштовуємо RecyclerView
        recyclerView = findViewById(R.id.recyclerViewCustomers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CustomerAdapter(customers, ::openCustomerDetail)
        recyclerView.adapter = adapter

        // Додаємо обробник для кнопки сортування за кількістю замовлень
        findViewById<Button>(R.id.buttonSortByOrders).setOnClickListener {
            sortCustomersByOrders()
        }
    }

    private fun addInitialCustomers() {
        databaseHelper.clearCustomers()
        databaseHelper.addCustomer(Customer(0, "Олександр", "Шевченко", "oleksandr.shevchenko@example.com", "Замовлення 1, Замовлення 2", 3, 15.0))
        databaseHelper.addCustomer(Customer(1, "Марія", "Коваленко", "maria.kovalenko@example.com", "Замовлення 3, Замовлення 4", 5, 20.0))
        databaseHelper.addCustomer(Customer(2, "Сергій", "Іваненко", "serhiy.ivanenko@example.com", "Замовлення 5", 2, 12.0))
        databaseHelper.addCustomer(Customer(3, "Тетяна", "Петренко", "tetyana.petrenko@example.com", "Замовлення 6, Замовлення 7", 7, 25.5))
        databaseHelper.addCustomer(Customer(4, "Іван", "Сидоренко", "ivan.sydorenko@example.com", "Замовлення 8, Замовлення 9", 1, 5.0))

        // Оновлюємо список клієнтів
        customers = databaseHelper.getAllCustomers()
        adapter.updateData(customers)
    }

    private fun openCustomerDetail(customer: Customer) {
        val intent = Intent(this, CustomerDetailActivity::class.java)
        intent.putExtra("customer", customer)
        startActivity(intent)
    }

    // Сортування за кількістю замовлень
    private fun sortCustomersByOrders() {
        customers = customers.sortedByDescending { it.orderCount }
        adapter.updateData(customers)
        Log.d("MainActivity", "Customers sorted by order count: ${customers.joinToString { it.firstName }}")
    }

}
