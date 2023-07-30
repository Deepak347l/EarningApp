package com.deepakdev.earnpocketmoney.CPALeadclaim

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.deepakdev.earnpocketmoney.CPALead.modelofferClaimItem
import com.deepakdev.earnpocketmoney.R
import com.google.android.material.button.MaterialButton

class offerAdapter (notesRVModalArrayList: ArrayList<modelofferClaimItem>,
context: Context,
downloadClickInterface: DownloadClickInterface,
) :
RecyclerView.Adapter<offerAdapter.ViewHolder>() {
    // creating variables for our list, context, interface and position.
    private var notesRVModalArrayList: ArrayList<modelofferClaimItem>
    private val context: Context
    private val downloadClickInterface: DownloadClickInterface
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating our layout file on below line.
        return try {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.offer_items, parent, false)
            ViewHolder(view)
        } catch (e: Exception) {
            Log.e("adapterError", e.message.toString())
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.offer_items, parent, false)
            ViewHolder(view)
        }

    }
    //
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notesRVModal: modelofferClaimItem = notesRVModalArrayList[position]
        holder.headText.text = "Offer ID->" + notesRVModal.campid.toString()
        if(notesRVModal.status == true){
            holder.descText.text =  "sucess"
        }else {
            holder.descText.text =  "failed"
        }
        holder.view.setOnClickListener { downloadClickInterface.onDownloadClick(
            position,
            notesRVModal,holder,
        ) }
    }


    override fun getItemCount(): Int {
        return notesRVModalArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val headText: TextView
        val descText: TextView
        val view: TextView

        init {
            headText = itemView.findViewById(R.id.ascvaxxxk)
            descText = itemView.findViewById(R.id.ascvak)
            view = itemView.findViewById(R.id.ascvk)
        }
    }

    interface DownloadClickInterface {
        fun onDownloadClick(position: Int, notesRVModal: modelofferClaimItem, holder: ViewHolder)
    }
    // creating a constructor.
    init {
        this.notesRVModalArrayList = notesRVModalArrayList
        this.context = context
        this.downloadClickInterface = downloadClickInterface
    }

}