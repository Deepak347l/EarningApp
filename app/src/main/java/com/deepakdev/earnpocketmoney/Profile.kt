package com.deepakdev.earnpocketmoney

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONObject


class Profile : Fragment() {
    private val root = FirebaseDatabase.getInstance().getReference("Payment")
    private lateinit var radioBtn: RadioButton
    private lateinit var btnRadio5: RadioButton
    private lateinit var btnRadio55: RadioButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_profile, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try{
            FirebaseDatabase.getInstance().getReference("user")
                .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        try{
                        val income =  snapshot.child("income").value
                        msczmcz.text = "Available Ballance:₹" + income.toString()
                        }catch (e:Exception){
                            Log.e("Profile", e.message.toString())
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.e("mainActivityError", error.message)
                    }
                })
        }catch (e:Exception){
            Log.e("Profile", e.message.toString())
        }
        aaa555.setOnClickListener{
            val dialog = BottomSheetDialog(context!!)
            // on below line we are inflating a layout file which we have created.
            val view = layoutInflater.inflate(R.layout.bottomsheetlayout, null)
            // on below line we are creating a variable for our button
            // which we are using to dismiss our dialog.
            val btnClose = view.findViewById<Button>(R.id.buttonxxx)
            val btnRadio = view.findViewById<RadioGroup>(R.id.radio_group)
            btnRadio5 = view.findViewById<RadioButton>(R.id.red)
            btnRadio55 = view.findViewById<RadioButton>(R.id.green)
            // on below line we are adding on click listener
            // for our dismissing the dialog button.
            btnClose.setOnClickListener {
                val btnslc = btnRadio.checkedRadioButtonId
                radioBtn = view.findViewById<RadioButton>(btnslc)

                // on below line we are calling a dismiss
                // method to close our dialog.
                dialog.dismiss()

            }
            // below line is use to set cancelable to avoid
            // closing of dialog box when clicking on the screen.
            dialog.setCancelable(true)
            // on below line we are setting
            // content view to our view.
            dialog.setContentView(view)
            // on below line we are calling
            // a show method to display a dialog.
            dialog.show()
        }
        aaa5555.setOnClickListener{
            val title = aaa5.text.toString()
            val descripction = aaa55.text.toString()
            if (title.isEmpty()){
                aaa5.setError("required")
                aaa5.requestFocus()
            }
            else if(descripction.isEmpty()){
                aaa55.setError("required")
                aaa55.requestFocus()
            }
            else{
                FirebaseDatabase.getInstance().getReference("user")
                    .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val income =  snapshot.child("income").value.toString().toFloat()
                            if (income >= 50 ){
                                uploadToFirebase()
                            }
                            else{
                                Toast.makeText(context,"Low balance",Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            Log.e("mainActivityError", error.message)
                        }
                    })
           }
        }
    }
                fun uploadToFirebase() {

                            if (radioBtn == btnRadio5){
                                val Paytm = "&mobile=" + aaa5.text.toString().toLong()
                                val Amount = aaa55.text.toString().toInt() - 10
                                val tag = "&info=" + "EarnPocketMoney"
                                val url =  "https://full2sms.in/api/v1/disburse/paytm?mid=kDRK06u7NGBwbqWLVP4cteoTZ&mkey=evLwMZ0N4SfFx8RGrn3kop6mC&guid=KHeuJvILXU2z9RxDMfnwsVYo5&amount=" + Amount + Paytm + tag
                                        //api call for auto payment
                                AndroidNetworking.get(url)
                                    .setPriority(Priority.HIGH)
                                    .build().getAsJSONObject(object : JSONObjectRequestListener {
                                    override fun onResponse(response: JSONObject?) {
                                        val status = response?.getString("status")
                                        val message = response?.getString("message")
                                        val txn_id = response?.getString("txn_id")
                                        Toast.makeText(
                                            context,
                                            status.toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        if (status == "failed"){
                                            Toast.makeText(
                                                context,
                                                message.toString(),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }else{
                                            FirebaseDatabase.getInstance().getReference("user")
                                                .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addListenerForSingleValueEvent(object :
                                                    ValueEventListener {
                                                    override fun onDataChange(snapshot: DataSnapshot) {
                                                        val income =  snapshot.child("income").value.toString().toFloat()
                                                        val fincome =  income - aaa55.text.toString().toFloat()
                                                        if (income < aaa55.text.toString().toFloat()){
                                                            Toast.makeText(context,"Low balance",Toast.LENGTH_SHORT).show()
                                                        }else{
                                                            val hashMapx = HashMap<String, Any>()
                                                            hashMapx.put("income", fincome.toString())
                                                            FirebaseDatabase.getInstance().getReference("user")
                                                                .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).updateChildren(hashMapx)
                                                            val hashMap = HashMap<String,Any>()
                                                            hashMap.put("uid", FirebaseAuth.getInstance().currentUser?.uid.toString())
                                                            hashMap.put("Paytm",aaa5.text.toString())
                                                            hashMap.put("Amount",aaa55.text.toString())
                                                            hashMap.put("txn_id",txn_id.toString())
                                                            hashMap.put("status",true)
                                                            root.child("Paytm")
                                                                .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).push()
                                                                .setValue(hashMap)
                                                            Toast.makeText(
                                                                context,
                                                                "Sent Successfully",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }

                                                    }
                                                    override fun onCancelled(error: DatabaseError) {
                                                        Log.e("mainActivityError", error.message)
                                                    }
                                                })
                                        }
                                    }
                                    override fun onError(anError: ANError?) {
                                        Log.e("error", anError?.message.toString())
                                        Toast.makeText(context,anError?.message.toString(),Toast.LENGTH_SHORT).show()
                                    }

                                })

                            }
                            else {
                                FirebaseDatabase.getInstance().getReference("user")
                                    .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addListenerForSingleValueEvent(object :
                                        ValueEventListener {
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            val income =  snapshot.child("income").value.toString().toFloat()
                                            val fincome =  income - aaa55.text.toString().toFloat()
                                            if (income < aaa55.text.toString().toFloat()){
                                                Toast.makeText(context,"Low balance",Toast.LENGTH_SHORT).show()
                                            }else{
                                                val hashMap = HashMap<String,Any>()
                                                hashMap.put("uid", FirebaseAuth.getInstance().currentUser?.uid.toString())
                                                hashMap.put("UPI ID",aaa5.text.toString())
                                                hashMap.put("Amount",aaa55.text.toString())
                                                hashMap.put("status",false)
                                                root.child("UPI")
                                                    .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).push()
                                                    .setValue(hashMap)
                                                val hashMapx = HashMap<String, Any>()
                                                hashMapx.put("income", fincome.toString())
                                                FirebaseDatabase.getInstance().getReference("user")
                                                    .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).updateChildren(hashMapx)
                                                Toast.makeText(
                                                    context,
                                                    "Submit Successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                        override fun onCancelled(error: DatabaseError) {
                                            Log.e("mainActivityError", error.message)
                                        }
                                    })

                            }

                        }
    }



