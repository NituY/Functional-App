package com.example.founctional_application
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    //IMEI noumber
//    lateinit var button: Button
//    lateinit var textView: TextView
//    private lateinit var IMEINumber: String
//    private val REQUEST_CODE = 101
    // timestamp
    lateinit var calendar: Calendar
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var date : String
    lateinit var timeStamp : TextView
    lateinit var TimeButton : Button

    // location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        title = "KotlinApp"
//        textView = findViewById(R.id.textView)
//        button = findViewById(R.id.button)
//        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as
//                TelephonyManager
//      //  IMEINumber = telephonyManager.deviceId
//        textView.text = IMEINumber
//        button.setOnClickListener {
//
//            if (ActivityCompat.checkSelfPermission(this@MainActivity,
//                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this@MainActivity,
//                    arrayOf(Manifest.permission.READ_PHONE_STATE), REQUEST_CODE)
//                return@setOnClickListener
//            }
//
//        }
        timeStamp = findViewById(R.id.timeTv)
        TimeButton = findViewById(R.id.TimeBtn)
        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        date = simpleDateFormat.format(calendar.time)
        TimeButton.setOnClickListener {
            timeStamp.text = date
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        findViewById<Button>(R.id.locationBtn).setOnClickListener {
            fetchLocation()
        }


    }

    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
        !=PackageManager.PERMISSION_GRANTED && ActivityCompat.
            checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        task.addOnSuccessListener {
            if(it != null){
                Toast.makeText(applicationContext,"${it.latitude} ${it.longitude}", Toast.LENGTH_SHORT).show()

            }
        }

    }



//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>,
//                                            grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            REQUEST_CODE -> {
//                if (grantResults.isNotEmpty() && grantResults[0] ==
//                    PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
}

