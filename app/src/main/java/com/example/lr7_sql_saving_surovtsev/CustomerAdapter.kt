package com.example.lr7_sql_saving_surovtsev

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomerAdapter(
    private var customers: List<Customer>,
    private val onItemClick: (Customer) -> Unit
) : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    inner class CustomerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewEmail: TextView = view.findViewById(R.id.textViewEmail)
        val textViewPurchaseHistory: TextView = view.findViewById(R.id.textViewPurchaseHistory)
        val textViewOrderCount: TextView = view.findViewById(R.id.textViewOrderCount)
        val textViewDiscount: TextView = view.findViewById(R.id.textViewDiscount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_customer, parent, false)
        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = customers[position]
        holder.textViewName.text = "${customer.firstName} ${customer.lastName}"
        holder.textViewEmail.text = customer.email
        holder.textViewPurchaseHistory.text = "History: ${customer.purchaseHistory}"
        holder.textViewOrderCount.text = "Orders: ${customer.orderCount}"
        holder.textViewDiscount.text = "Discount: ${customer.discount}%"

        holder.itemView.setOnClickListener { onItemClick(customer) }
    }

    override fun getItemCount(): Int = customers.size


    fun updateData(newCustomers: List<Customer>) {
        customers = newCustomers
        notifyDataSetChanged()
    }
}
