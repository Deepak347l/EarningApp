package com.deepakdev.earnpocketmoney

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView


class UPIAdapter(notesRVModalArrayList: ArrayList<UPIModel>,
                 context: Context,
) :
    RecyclerView.Adapter<UPIAdapter.ViewHolder>() {
    // creating variables for our list, context, interface and position.
    private var notesRVModalArrayList: ArrayList<UPIModel>
    private val context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating our layout file on below line.
        return try {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.upi_transation, parent, false)
            ViewHolder(view)
        } catch (e: Exception) {
            Log.e("adapterError", e.message.toString())
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.upi_transation, parent, false)
            ViewHolder(view)
        }

    }
    //
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notesRVModal: UPIModel = notesRVModalArrayList[position]
        holder.headText.text = "UPI ID->"
        if(notesRVModal.status == true){
            holder.descText.text =  "sucess"
        }else {
            holder.descText.text =  "pending"
        }
        holder.view.text = notesRVModal.Amount
    }


    override fun getItemCount(): Int {
        return notesRVModalArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val headText: TextView
        val descText: TextView
        val view: TextView

        init {
            headText = itemView.findViewById(R.id.ascvaxxxkUPI)//upi text id
            descText = itemView.findViewById(R.id.ascvkUPI)//suscess or panding
            view = itemView.findViewById(R.id.ascvakUPI)//amount
        }
    }
    // creating a constructor.
    init {
        this.notesRVModalArrayList = notesRVModalArrayList
        this.context = context
    }

}