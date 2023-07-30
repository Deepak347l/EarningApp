package com.deepakdev.earnpocketmoney

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anupkumarpanwar.scratchview.ScratchView
import com.anupkumarpanwar.scratchview.ScratchView.IRevealListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_skratch.*


class Skratch : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skratch)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.color5a)
        }
        val randomwin = (0..7).random()
        scratchViewwin.text = "₹" + randomwin.toString()
        val scratchView = findViewById<ScratchView>(R.id.scratchView)
        scratchView.setRevealListener(object : IRevealListener {
            override fun onRevealed(scratchView: ScratchView) {
                FirebaseDatabase.getInstance().getReference("user")
                    .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val income =  snapshot.child("income").value.toString().toFloat()
                            if(income >= 4.0){
                                val fincome =  income - 4.0
                                val hashMap = HashMap<String, Any>()
                                hashMap.put("income", fincome.toString())
                                FirebaseDatabase.getInstance().getReference("user")
                                    .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).updateChildren(hashMap)
                                val intent = Intent(this@Skratch, Rewardfromads::class.java)
                                intent.putExtra("key111x", "ads111")
                                intent.putExtra("key111", randomwin.toString())
                                startActivity(intent)
                                finish()
                            }
                            else{
                                Toast.makeText(this@Skratch,"Low balance",Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            Log.e("mainActivityError", error.message)
                        }
                    })

            }

            override fun onRevealPercentChangedListener(scratchView: ScratchView, percent: Float) {}
        })
    }
}
