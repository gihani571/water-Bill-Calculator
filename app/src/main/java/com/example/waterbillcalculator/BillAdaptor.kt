package com.example.waterbillcalculator

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BillAdaptor : RecyclerView.Adapter<BillAdaptor.BillViewHolder>(){
    private var watList: ArrayList<WaterModel> = ArrayList()
    private var onClickItem:((WaterModel) -> Unit)? = null
    private var onClickDeleteItem:((WaterModel) -> Unit)? = null

    fun addItems(items: ArrayList<WaterModel>){
        this.watList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (WaterModel)-> Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (WaterModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BillViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.display_del, parent,false)
    )

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        val wat = watList[position]
        holder.bindView(wat)
        holder.itemView.setOnClickListener{onClickItem?.invoke(wat)}
        holder.btnDelete.setOnClickListener{onClickDeleteItem?.invoke(wat)}
    }

    override fun getItemCount(): Int {
        return watList.size
    }


    class BillViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.id)
        private var nd = view.findViewById<TextView>(R.id.nd)
        private var un = view.findViewById<TextView>(R.id.un)
        private var tot = view.findViewById<TextView>(R.id.tot)
        var btnDelete = view.findViewById<TextView>(R.id.btnDelete)

        fun bindView(wat: WaterModel){
            id.text = wat.id.toString()
            nd.text = wat.nd.toString()
            un.text = wat.un.toString()
            tot.text = wat.tot.toString()
        }
    }

}