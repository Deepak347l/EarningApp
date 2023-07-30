package com.contactdevloperdk.notesshareing.Main_file

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deepakdev.earnpocketmoney.CPALead.Offer
import com.deepakdev.earnpocketmoney.R
import com.google.android.material.button.MaterialButton
import java.util.*
import kotlin.collections.ArrayList


class installAdapter(
    notesRVModalArrayList: ArrayList<Offer>,
    context: Context,
    downloadClickInterface: DownloadClickInterface,
) :
    RecyclerView.Adapter<installAdapter.ViewHolder>() {
    // creating variables for our list, context, interface and position.
    private var notesRVModalArrayList: ArrayList<Offer>
    private val context: Context
    private val downloadClickInterface: DownloadClickInterface
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating our layout file on below line.
        return try {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.install_items, parent, false)
            ViewHolder(view)
        } catch (e: Exception) {
            Log.e("adapterError", e.message.toString())
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.install_items, parent, false)
            ViewHolder(view)
        }

    }
    //
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notesRVModal:Offer = notesRVModalArrayList[position]
        holder.headText.text = notesRVModal.title
        val rewardamount =  (notesRVModal.amount.toFloat() * 80) / 2
        holder.descText.text =  "EARN:₹" + rewardamount.toString()
            Glide.with(holder.itemView.context).load(notesRVModal.creatives.get(0).url).into(holder.timeText)
        holder.view.setOnClickListener { downloadClickInterface.onDownloadClick(
            position,
            notesRVModal,holder,
        ) }
        val colorCode = getRandomColor()
        holder.viewx.setBackgroundColor(holder.itemView.resources.getColor(colorCode,null))
    }
    fun filterList(filteredNames: ArrayList<Offer>) {
        this.notesRVModalArrayList = filteredNames
        notifyDataSetChanged()
    }
    private fun getRandomColor(): Int {
        val colorcode: MutableList<Int> = ArrayList()
        colorcode.add(R.color.gray)
        colorcode.add(R.color.lightgreen)
        colorcode.add(R.color.skyblue)
        colorcode.add(R.color.color1)
        colorcode.add(R.color.color2)
        colorcode.add(R.color.color3)

        colorcode.add(R.color.color4)
        colorcode.add(R.color.color5)
        colorcode.add(R.color.green)

        val random = Random()
        val number: Int = random.nextInt(colorcode.size)
        return colorcode[number]
    }

    override fun getItemCount(): Int {
        return notesRVModalArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val headText: TextView
        val descText: TextView
        val timeText: ImageView
        val view: MaterialButton
        val viewx: View

        init {
            headText = itemView.findViewById(R.id.head1)
            descText = itemView.findViewById(R.id.desc1)
            timeText = itemView.findViewById(R.id.imageview2xxxmmm)
            view = itemView.findViewById(R.id.head1x)
            viewx = itemView.findViewById(R.id.view1)
        }
    }

    interface DownloadClickInterface {
        fun onDownloadClick(position: Int, notesRVModal: Offer,holder: ViewHolder)
    }
    // creating a constructor.
    init {
        this.notesRVModalArrayList = notesRVModalArrayList
        this.context = context
        this.downloadClickInterface = downloadClickInterface
    }
}