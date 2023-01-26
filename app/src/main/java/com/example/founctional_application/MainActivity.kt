package com.example.founctional_application
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    // timestamp
    lateinit var calendar: Calendar
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var date : String
    private lateinit var timeStamp : TextView
    private lateinit var TimeButton : Button

    // location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private  lateinit var locTv :TextView
    private lateinit var locButton: Button
    // internet
    private  lateinit var cld: ConnectionLiveData
    private lateinit var layout1: ConstraintLayout
    private lateinit var layout2: ConstraintLayout

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timeStamp = findViewById(R.id.timeTv)
        TimeButton = findViewById(R.id.TimeBtn)
        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        date = simpleDateFormat.format(calendar.time)
        TimeButton.setOnClickListener {
            timeStamp.text = date
        }
        //location
        locTv = findViewById(R.id.loctv)
        locButton = findViewById(R.id.locationBtn)
        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this)

        locButton.setOnClickListener {
            fetchLocation()

        }
        // internet
        checkNetworkConnection()
        layout1 = findViewById(R.id.layout1)
        layout2 = findViewById(R.id.layout2)





    }

    private fun checkNetworkConnection() {
        cld= ConnectionLiveData(application)
        cld.observe(this, { isConnected->
            if (isConnected){
                layout1.visibility = View.VISIBLE
                layout2.visibility = View.GONE

            }else{
                layout1.visibility = View.GONE
                layout2.visibility = View.VISIBLE

            }
        })
    }


    //location
    private fun fetchLocation() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
        }else{
            getLocattions()
        }


    }

    @SuppressLint("MissingPermission")
    private fun getLocattions() {
        fusedLocationProviderClient.lastLocation?.addOnSuccessListener {
            if (it == null){
                Toast.makeText(this,"Sorry Can't get Location", Toast.LENGTH_SHORT).show()
            }else it.apply {
                val lattitudde = it.latitude
                val  long= it.longitude
                locTv.text = "Latitude :$lattitudde, Longitude:$long"
            }


        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode ==101){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    getLocattions()
                }
                else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}

