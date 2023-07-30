package com.deepakdev.earnpocketmoney

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.contactdevloperdk.notesshareing.Main_file.installAdapter
import com.deepakdev.earnpocketmoney.CPALead.Creative
import com.deepakdev.earnpocketmoney.CPALead.Offer
import com.deepakdev.earnpocketmoney.CPALeadclaim.offerclaimsPage
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_categories.*
import org.json.JSONArray
import org.json.JSONObject


class Categories : Fragment(), installAdapter.DownloadClickInterface {
    private var notesRV: RecyclerView? = null
    private lateinit var loadingPB: ProgressBar
    private lateinit var  notesRVModalArrayList: ArrayList<Offer>
    private lateinit var notesRVAdapter: installAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_categories, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesRV = view.findViewById(R.id.recycler)
        loadingPB = view.findViewById(R.id.progress_small1)
        notesRVModalArrayList = ArrayList()
        notesRVAdapter = installAdapter(notesRVModalArrayList, context!!, this)
        val stagredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        notesRV!!.layoutManager = stagredGridLayoutManager
        notesRV!!.adapter = notesRVAdapter
        AndroidNetworking.initialize(view.context)
        val uid = FirebaseAuth.getInstance().currentUser?.uid
         val url = "https://cpalead.com/dashboard/reports/campaign_json.php?id=2379391&country=IN&show=30&subid="+uid.toString()
        AndroidNetworking.get(url)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val jsonArray: JSONArray = response!!.getJSONArray("offers")
                    try{
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                        val title = jsonObject.getString("title")
                        val amount = jsonObject.getString("amount")
                        val dating = jsonObject.getString("dating")
                        val description = jsonObject.getString("description")
                        val epc = jsonObject.getString("epc")
                        val link = jsonObject.getString("link")
                        val mobile_app = jsonObject.getString("mobile_app")
                        val mobile_app_icon_url = jsonObject.getString("mobile_app_icon_url")
                        val mobile_app_id = jsonObject.getString("mobile_app_id")
                        val mobile_app_minimum_version = jsonObject.getString("mobile_app_minimum_version")
                        val mobile_app_type = jsonObject.getString("mobile_app_type")
                        val offerwall_only = jsonObject.getString("offerwall_only")
                        val payout_currency = jsonObject.getString("payout_currency")
                        val payout_type = jsonObject.getString("payout_type")
                        val preview_url = jsonObject.getString("preview_url")
                        val rank = jsonObject.getString("rank")
                        val ratio = jsonObject.getString("ratio")
                        val traffic_type = jsonObject.getString("traffic_type")
                        val jsonArray1: JSONArray = jsonObject.getJSONArray("creatives")
                        val conversion = jsonObject.getString("conversion")
                        val campid = jsonObject.getString("campid")
                        for (j in 0 until jsonArray1.length()){
                        val jsonObject1: JSONObject = jsonArray1.getJSONObject(j)
                        val size = jsonObject1.getString("size")
                        val urls = jsonObject1.getString("url")
                            notesRVModalArrayList
                                .add(Offer(amount,dating.toBoolean(),description,epc,link,mobile_app.toInt()
                                    ,mobile_app_icon_url,mobile_app_id,mobile_app_minimum_version,mobile_app_type
                                    ,offerwall_only.toBoolean(),payout_currency,payout_type,preview_url,rank.toInt(),ratio,title,traffic_type,
                                    listOf(Creative(size,urls)),conversion,campid.toInt()
                                ))
                        }
                               loadingPB.visibility = View.GONE
                                notesRVAdapter.notifyDataSetChanged()
                    }
                }catch (e:Exception){
                    Log.e("finderror",e.message.toString())
                        Toast.makeText(context,e.message.toString(),Toast.LENGTH_SHORT).show()
                }
                }

                override fun onError(anError: ANError?) {
                    loadingPB.visibility = View.GONE
                    Log.e("error", anError?.message.toString())
                    Toast.makeText(context,anError?.message.toString(),Toast.LENGTH_SHORT).show()
                }

            })
        ascvaxxx.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do Nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do Nothing
            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }
        })
        ascva.setOnClickListener{
            val intent = Intent(context, offerclaimsPage::class.java)
            startActivity(intent)
        }
    }
    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filteredNames = ArrayList<Offer>()
        //looping through existing elements and adding the element to filtered list
        notesRVModalArrayList.filterTo(filteredNames) {
            //if the existing elements contains the search input
            it.title.toLowerCase().contains(text.toLowerCase())
        }
        //calling a method of the adapter class and passing the filtered list
        notesRVAdapter.filterList(filteredNames)
    }
    override fun onDownloadClick(
        position: Int,
        notesRVModal: Offer,
        holder: installAdapter.ViewHolder
    ) {

        //create custom dialog
        val dialog = Dialog(context!!)
        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.custom_dialog, null)
        val linkbtn = view.findViewById<MaterialButton>(R.id.head1xzzzz)
        val des = view.findViewById<TextView>(R.id.desc1zzzxxxxvvv)
        des.text = notesRVModal.conversion

            linkbtn.setOnClickListener {
                val campid = notesRVModal.campid.toString()
                val amount = ((notesRVModal.amount.toFloat() * 80) / 2).toString()
                val conversion = false
                val model = Model(campid,amount,conversion)
                FirebaseDatabase.getInstance().getReference("instalTracking")
                    .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child(notesRVModal.campid.toString()).setValue(model)
                val appLink = notesRVModal.link
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(appLink))
                startActivity(intent)
            // on below line we are calling a dismiss
            // method to close our dialog.
            dialog.dismiss()

        }
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }
}