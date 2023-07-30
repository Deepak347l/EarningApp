package com.deepakdev.earnpocketmoney

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_my_profile.*

class myProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.color5a)
        }
        //Now retrive our hedrer data
        FirebaseDatabase.getInstance().getReference("user")
            .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val namedata =  snapshot.child("name").value.toString()
                    val picdata =  snapshot.child("pic").value.toString()
                    val emaildata =  snapshot.child("email").value.toString()
                    val uiddata =  snapshot.child("id").value.toString()
                    usernameff.text = namedata
                    following.text = emaildata
                    followers.text = uiddata
                    Glide.with(this@myProfile).load(picdata).circleCrop().into(mscxxxmscv)
                    c.setOnClickListener {
                        val c: ClipboardManager = it.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        val clipData = ClipData.newPlainText("TextView", followers.text.toString())
                        c.setPrimaryClip(clipData)
                        Toast.makeText(this@myProfile, "Copied", Toast.LENGTH_SHORT).show();
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("mainActivityError", error.message)
                }
            })
        material5.setOnClickListener {
            Toast.makeText(this,"Not Available", Toast.LENGTH_SHORT).show()
        }
        material55.setOnClickListener {
            Toast.makeText(this,"Not Available", Toast.LENGTH_SHORT).show()
        }
        material555.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://doc-hosting.flycricket.io/earn-pocket-money-privacy-policy/dab7e189-fc37-4d02-8caf-b8fc46118b31/privacy")
            )
            startActivity(intent)
        }
        material5555.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://wa.me/message/2UKJHOSWXFJ7G1")
            )
            startActivity(intent)
        }
        material55555.setOnClickListener {
            val intent = Intent(this, Earningdashbord::class.java)
            startActivity(intent)
        }
        material555555.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            Toast.makeText(
                this,
                "You susessfully logout",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
}