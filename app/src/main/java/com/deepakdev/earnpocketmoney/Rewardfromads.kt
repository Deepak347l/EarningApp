package com.deepakdev.earnpocketmoney

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_rewardfromads.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class Rewardfromads : AppCompatActivity(), MaxAdListener {
    private lateinit var interstitialAd: MaxInterstitialAd
    private var retryAttempt = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewardfromads)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.color5a)
        }
        interstitialAd = MaxInterstitialAd( "11e5844be866c6da", this)
        interstitialAd.setListener( this )

        // Load the first ad
        interstitialAd.loadAd()

        val key = intent.getStringExtra("key")
        val key1 = intent.getStringExtra("key1")
        val key11 = intent.getStringExtra("key11")
        val key11x = intent.getStringExtra("key11x")
        val key111x = intent.getStringExtra("key111x")
        val key111 = intent.getStringExtra("key111")
        if(key == "ads"){
        val randomwin = (1..100).random()
        val win = randomwin/100.toFloat()//total earn uptp 1 by bonous
            BalanceWr.text = win.toString()
                button2WWRRRr.setOnClickListener {
                if ( interstitialAd.isReady() )
                {
                    FirebaseDatabase.getInstance().getReference("user")
                        .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val income =  snapshot.child("income").value.toString().toFloat()
                                val fincome =  income + win
                                val hashMap = HashMap<String, Any>()
                                hashMap.put("income", fincome.toString())
                                FirebaseDatabase.getInstance().getReference("user")
                                    .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).updateChildren(hashMap)
                            }
                            override fun onCancelled(error: DatabaseError) {
                                Log.e("mainActivityError", error.message)
                            }
                        })
                    interstitialAd.showAd();
                }else{
                    Toast.makeText(this,"Please click again",Toast.LENGTH_SHORT).show()
                }
            }
        }
        else if(key1 == "ads1"){

            val randomwin = (1..10).random()
            val win = randomwin/100.toFloat()//total earn uptp 0.1 by bonous
            BalanceWr.text = win.toString()

            button2WWRRRr.setOnClickListener {
                if ( interstitialAd.isReady() )
                {
                    FirebaseDatabase.getInstance().getReference("user")
                        .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val income =  snapshot.child("income").value.toString().toFloat()
                                val fincome =  income + win
                                val hashMap = HashMap<String, Any>()
                                hashMap.put("income", fincome.toString())
                                FirebaseDatabase.getInstance().getReference("user")
                                    .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).updateChildren(hashMap)
                            }
                            override fun onCancelled(error: DatabaseError) {
                                Log.e("mainActivityError", error.message)
                            }
                        })
                    interstitialAd.showAd();
                }else{
                    Toast.makeText(this,"Please click again",Toast.LENGTH_SHORT).show()
                }
            }

        }
        //here we do our other codes
        else if(key11x == "ads11"){
            BalanceWr.text = key11.toString()
            val win = key11.toString().toFloat()//total earn uptp 0.1 by bonous
            button2WWRRRr.setOnClickListener {
                if ( interstitialAd.isReady() )
                {
                    FirebaseDatabase.getInstance().getReference("user")
                        .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val income =  snapshot.child("income").value.toString().toFloat()
                                val fincome =  income + win
                                val hashMap = HashMap<String, Any>()
                                hashMap.put("income", fincome.toString())
                                FirebaseDatabase.getInstance().getReference("user")
                                    .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).updateChildren(hashMap)
                            }
                            override fun onCancelled(error: DatabaseError) {
                                Log.e("mainActivityError", error.message)
                            }
                        })
                    interstitialAd.showAd();
                }else{
                    Toast.makeText(this,"Please click again",Toast.LENGTH_SHORT).show()
                }
            }

        }
        else if(key111x == "ads111"){

            BalanceWr.text = key111.toString()
            val win = key111.toString().toFloat()//total earn uptp 0.1 by bonous
            button2WWRRRr.setOnClickListener {
                if ( interstitialAd.isReady() )
                {
                    FirebaseDatabase.getInstance().getReference("user")
                        .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val income =  snapshot.child("income").value.toString().toFloat()
                                val fincome =  income + win
                                val hashMap = HashMap<String, Any>()
                                hashMap.put("income", fincome.toString())
                                FirebaseDatabase.getInstance().getReference("user")
                                    .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).updateChildren(hashMap)
                            }
                            override fun onCancelled(error: DatabaseError) {
                                Log.e("mainActivityError", error.message)
                            }
                        })
                    interstitialAd.showAd();
                }else{
                    Toast.makeText(this,"Please click again",Toast.LENGTH_SHORT).show()
                }

        }

    }
    }

    override fun onAdLoaded(ad: MaxAd?) {
        // Interstitial ad is ready to be shown. interstitialAd.isReady() will now return 'true'

        // Reset retry attempt
        retryAttempt = 0.0
    }

    override fun onAdDisplayed(ad: MaxAd?) {}

    override fun onAdHidden(ad: MaxAd?) {
        val intentx = Intent(this,MainActivity::class.java)
        startActivity(intentx)
        Toast.makeText(this,"Susessfully credited",Toast.LENGTH_SHORT).show()
       finish()
    }

    override fun onAdClicked(ad: MaxAd?) {}

    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
        // Interstitial ad failed to load
        // AppLovin recommends that you retry with exponentially higher delays up to a maximum delay (in this case 64 seconds)
        retryAttempt++
        val delayMillis = TimeUnit.SECONDS.toMillis( Math.pow( 2.0, Math.min( 6.0, retryAttempt ) ).toLong() )

        Handler().postDelayed( { interstitialAd.loadAd() }, delayMillis )
    }

    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
        interstitialAd.loadAd()
    }


}