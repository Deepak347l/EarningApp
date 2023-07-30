package com.deepakdev.earnpocketmoney

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.FragmentTransaction
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment(), MaxAdListener {
    private lateinit var interstitialAd: MaxInterstitialAd
    private var retryAttempt = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_home, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        interstitialAd = MaxInterstitialAd( "11e5844be866c6da", context as Activity?)
        interstitialAd.setListener( this )

        // Load the first ad
        interstitialAd.loadAd()
        bn1.setOnClickListener {
            FirebaseDatabase.getInstance().getReference("user")
                .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val bounous =  snapshot.child("bounous").value
                      if(bounous == false){
                          val intent = Intent(context,Rewardfromads::class.java)
                          intent.putExtra("key","ads")
                          startActivity(intent)
                          val hashMap = HashMap<String, Any>()
                          hashMap.put("bounous",true)
                          FirebaseDatabase.getInstance().getReference("user")
                              .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).updateChildren(hashMap)
                      }else{
                          Toast.makeText(context,"You already claimed",Toast.LENGTH_SHORT).show()
                      }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.e("mainActivityError", error.message)
                    }
                })
        }
        bn2.setOnClickListener {
            if ( interstitialAd.isReady() )
            {
                interstitialAd.showAd();
            }else{
            val intent = Intent(context,Rewardfromads::class.java)
            intent.putExtra("key1","ads1")
            startActivity(intent)
        }
        }
        bn3.setOnClickListener {
            val intent = Intent(context,Spin::class.java)
            startActivity(intent)
        }
        bn4.setOnClickListener {
            val intent = Intent(context,Skratch::class.java)
            startActivity(intent)
        }
        bn5.setOnClickListener {
           /* val intent = Intent(context,Rewardfromads::class.java)
            intent.putExtra("key1111","ads1111")
            startActivity(intent)
            */
            Toast.makeText(context,"Not Available", Toast.LENGTH_SHORT).show()
        }
        bn6.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            transaction?.replace(R.id.frameLayout, Categories())
            transaction?.commit()
        }
        bn7.setOnClickListener {
            Toast.makeText(context,"Not Available", Toast.LENGTH_SHORT).show()
        }
        bn8.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            transaction?.replace(R.id.frameLayout, Settings())
            transaction?.commit()
        }
        bn8x.setOnClickListener {
            val builder = CustomTabsIntent.Builder()

            // to set the toolbar color use CustomTabColorSchemeParams
            // since CustomTabsIntent.Builder().setToolBarColor() is deprecated


            // shows the title of web-page in toolbar

            // setShareState(CustomTabsIntent.SHARE_STATE_ON) will add a menu to share the web-page

            // To modify the close button, use
            // builder.setCloseButtonIcon(bitmap)

            // to set weather instant apps is enabled for the custom tab or not, use
            //  To use animations use -
            //  builder.setStartAnimations(this, android.R.anim.start_in_anim, android.R.anim.start_out_anim)
            //  builder.setExitAnimations(this, android.R.anim.exit_in_anim, android.R.anim.exit_out_anim)
            val customBuilder = builder.build()
            customBuilder.launchUrl(context!!, Uri.parse("http://1090.set.qureka.com"))
        }
    }

    override fun onAdLoaded(ad: MaxAd?) { retryAttempt = 0.0}
    override fun onAdDisplayed(ad: MaxAd?) {}
    override fun onAdHidden(ad: MaxAd?) {
        val intent = Intent(context,Rewardfromads::class.java)
        intent.putExtra("key1","ads1")
        startActivity(intent)
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