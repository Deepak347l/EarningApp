package com.deepakdev.earnpocketmoney

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentTransaction
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkConfiguration
import com.bumptech.glide.Glide
import com.deepakdev.earnpocketmoney.CPALead.Offer
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_background.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var view: View
    private lateinit var mAuth: FirebaseAuth
    //app update
    val UPDATE_CODE = 22
    lateinit var appUpdateManager: AppUpdateManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Make sure to set the mediation provider value to "max" to ensure proper functionality
        AppLovinSdk.getInstance( this ).setMediationProvider( "max" )
        AppLovinSdk.getInstance( this ).initializeSdk({ configuration: AppLovinSdkConfiguration ->
            // AppLovin SDK is initialized, start loading ads
        })
        tabLayout = findViewById(R.id.tabLayout);
        view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_background, null);
        //change the font family of text view in tabs(Make sure internet is on, for the first time to prevent crash)
        //if(internet is on)
        try {
            val typeface = ResourcesCompat.getFont(application, R.font.poppins_medium)
            tv1.setTypeface(typeface)
        } catch (e: Exception) {
            Log.e("mainActivityError", e.message.toString())
        }
        //else
        //..default font
        setCustomView(0, 1, 2, 3)
        setTextAndImageWithAnimation("HOME", R.drawable.ic_home);
        handleFragment(HomeFragment())
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    1 -> {
                        setCustomView(1, 0, 2, 3)
                        setTextAndImageWithAnimation("OFFERS", R.drawable.ic_baseline_local_offer_24)
                        //change to the fragment which you want to display
                        handleFragment_categories(Categories())
                    }
                    2 -> {
                        setCustomView(2, 1, 0, 3)
                        setTextAndImageWithAnimation("GAMES", R.drawable.ic_settings)
                        //change to the fragment which you want to display
                        handleFragment_settings(Settings())
                    }
                    3 -> {
                        setCustomView(3, 1, 2, 0)
                        setTextAndImageWithAnimation("WALLET", R.drawable.ic_person)
                        //change to the fragment which you want to display
                        handleFragment_person(Profile())
                    }
                    0 -> {
                        setCustomView(0, 1, 2, 3)
                        setTextAndImageWithAnimation("HOME", R.drawable.ic_home)
                        //change to the fragment which you want to display
                        handleFragment(HomeFragment())
                    }
                    else -> {
                        setCustomView(0, 1, 2, 3)
                        setTextAndImageWithAnimation("HOME", R.drawable.ic_home)
                        handleFragment(HomeFragment())
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        //data retrive for rcv
        mAuth = FirebaseAuth.getInstance()
        //compolsory app update methode
        inappupdate()
        //Now retrive our hedrer data
        FirebaseDatabase.getInstance().getReference("user")
            .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val namedata =  snapshot.child("name").value.toString()
                    val picdata =  snapshot.child("pic").value.toString()
                    val income =  snapshot.child("income").value.toString()
                    mscz.text = "₹" + income
                    userName.text = namedata
                    Glide.with(this@MainActivity).load(picdata).circleCrop().into(msc)
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("mainActivityError", error.message)
                }
            })

        msc.setOnClickListener {
            val intent = Intent(this, myProfile::class.java)
            startActivity(intent)
        }

    }

    private fun handleFragment_person(profile: Profile) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        transaction.replace(R.id.frameLayout, profile)
        transaction.commit()
    }

    private fun handleFragment_settings(settings: Settings) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        transaction.replace(R.id.frameLayout, settings)
        transaction.commit()
    }

    private fun handleFragment_categories(categories: Categories) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        transaction.replace(R.id.frameLayout, categories)
        transaction.commit()
    }

    private fun setCustomView(selectedtab: Int, non1: Int, non2: Int, non3: Int) {
        Objects.requireNonNull(tabLayout.getTabAt(selectedtab))?.setCustomView(view)
        Objects.requireNonNull(tabLayout.getTabAt(non1))?.setCustomView(null)
        Objects.requireNonNull(tabLayout.getTabAt(non2))?.setCustomView(null)
        Objects.requireNonNull(tabLayout.getTabAt(non3))?.setCustomView(null)
    }

    private fun setTextAndImageWithAnimation(text: String, images: Int) {
        val animation: Animation =
            AnimationUtils.loadAnimation(applicationContext, android.R.anim.slide_in_left)
        animation.setInterpolator(AccelerateDecelerateInterpolator())
        tv1.setText(text)
        iv1.setImageResource(images)
        tv1.startAnimation(animation)
        iv1.startAnimation(animation)
    }

    private fun handleFragment(fragment: HomeFragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }
    private fun inappupdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this)
        val task : com.google.android.play.core.tasks.Task<AppUpdateInfo> = appUpdateManager.appUpdateInfo
        task.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && it.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        it,
                        AppUpdateType.IMMEDIATE,
                        this,
                        UPDATE_CODE
                    )

                } catch (e: InternalError){ }
            }
        }
        appUpdateManager.registerListener(listner)
    }
    val listner: InstallStateUpdatedListener = InstallStateUpdatedListener{ state ->

        if (state.installStatus() == InstallStatus.DOWNLOADING){
            popup()
        }
    }

    private fun popup() {
        val sneekbar = Snackbar.make(
            findViewById(android.R.id.content), "App update done", Snackbar.LENGTH_INDEFINITE
        )
        sneekbar.setAction("Reloaded", object : View.OnClickListener {
            override fun onClick(v: View?) {
                appUpdateManager.completeUpdate()
            }

        })
        sneekbar.setTextColor(Color.parseColor("#FF0000"))
        sneekbar.show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == UPDATE_CODE){
            if (resultCode != RESULT_OK){
            }
        }
    }

    //CHEK USER LOGIN STATUS


    //when on back presed click app closed
    override fun onBackPressed() {
        // Create the object of AlertDialog Builder class
        val builder = AlertDialog.Builder(this@MainActivity)
        // Set the message show for the Alert time
        builder.setMessage("Do you want to exit the app")
        // Set Alert Title
        builder.setTitle("Are you sure ?")
        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false)
        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes") {
            // When the user click yes button then app will close
                dialog, which ->
           finish()
        }
        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No") {
            // If user click no then dialog box is canceled.
                dialog, which -> dialog.cancel()
        }
        // Create the Alert dialog
        val alertDialog = builder.create()
        alertDialog.setOnShowListener(object : DialogInterface.OnShowListener {
            @SuppressLint("ResourceAsColor")
            override fun onShow(dialog: DialogInterface?) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.black)
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.black)
            }
        })
        // Show the Alert Dialog box
        alertDialog.show()

    }

}