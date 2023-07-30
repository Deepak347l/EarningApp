package com.deepakdev.earnpocketmoney.CPALeadclaim

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.bumptech.glide.Glide
import com.contactdevloperdk.notesshareing.Main_file.installAdapter
import com.deepakdev.earnpocketmoney.CPALead.modelofferClaimItem
import com.deepakdev.earnpocketmoney.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_offerclaims_page.*
import org.json.JSONArray
import org.json.JSONObject

class offerclaimsPage : AppCompatActivity(), offerAdapter.DownloadClickInterface {
    private var notesRV: RecyclerView? = null
    private lateinit var loadingPB: ProgressBar
    private lateinit var  notesRVModalArrayList: ArrayList<modelofferClaimItem>
    private lateinit var notesRVAdapter: offerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offerclaims_page)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.color5a)
        }
        notesRV = findViewById(R.id.rcb)
        notesRVModalArrayList = ArrayList()
        notesRVAdapter = offerAdapter(notesRVModalArrayList, this, this)
        notesRV!!.layoutManager = LinearLayoutManager(this)
        notesRV!!.adapter = notesRVAdapter
        AndroidNetworking.initialize(this)
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val url = "https://cpalead.com/dashboard/reports/conversion_api.php?id=2379391&subid="+uid.toString()
        AndroidNetworking.get(url)
            .setPriority(Priority.HIGH)
            .build().getAsJSONArray(object: JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {
                    if(response != null){
                        for (i in 0 until response.length()){
                            val jsonObject: JSONObject = response.getJSONObject(i)
                            val campid = jsonObject.getString("campid")
                            val country = jsonObject.getString("country")
                            val status = jsonObject.getBoolean("status")
                            val subid = jsonObject.getString("subid")
                            val timestamp = jsonObject.getString("timestamp")
                            notesRVModalArrayList
                                .add(modelofferClaimItem(campid.toInt(),country,status,subid,timestamp.toInt()))
                        }
                        progress_small1XXX.visibility = View.GONE
                        notesRVAdapter.notifyDataSetChanged()
                    }else{
                        progress_small1XXX.visibility = View.GONE
                        XXXtextview1W.visibility = View.VISIBLE
                    }
                }
                override fun onError(anError: ANError?) {
                    progress_small1XXX.visibility = View.GONE
                    Log.e("error", anError?.message.toString())
                    Toast.makeText(this@offerclaimsPage,anError?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onDownloadClick(
        position: Int,
        notesRVModal: modelofferClaimItem,
        holder: offerAdapter.ViewHolder
    ) {
        try{
            FirebaseDatabase.getInstance().getReference("instalTracking")
            .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child(notesRVModal.campid.toString()).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val conversion =  snapshot.child("conversion").value
                    if (conversion == false){
                        val amount =  snapshot.child("amount").value.toString().toFloat()
                        FirebaseDatabase.getInstance().getReference("user")
                            .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addListenerForSingleValueEvent(object :
                                ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val income =  snapshot.child("income").value.toString().toFloat()
                                    val fincome =  income + amount
                                    val hashMap = HashMap<String, Any>()
                                    hashMap.put("income", fincome.toString())
                                    FirebaseDatabase.getInstance().getReference("user")
                                        .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).updateChildren(hashMap)
                                }
                                override fun onCancelled(error: DatabaseError) {
                                    Log.e("mainActivityError", error.message)
                                }
                            })
                        val hashMapx = HashMap<String, Any>()
                        hashMapx.put("conversion", true)
                        FirebaseDatabase.getInstance().getReference("instalTracking")
                            .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child(notesRVModal.campid.toString()).updateChildren(hashMapx)
                        Toast.makeText(this@offerclaimsPage,"Done!",Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this@offerclaimsPage,"You allradey claimed",Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("mainActivityError", error.message)
                }
            })
    }catch (e:Exception){
            Log.e("finderrorvvv",e.message.toString())
            Toast.makeText(this@offerclaimsPage,e.message.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}