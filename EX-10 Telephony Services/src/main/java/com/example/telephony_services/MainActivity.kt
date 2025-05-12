package com.example.telephony_services

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var operatorName: EditText
    private lateinit var phoneType: EditText
    private lateinit var networkCountry: EditText
    private lateinit var simCountry: EditText
    private lateinit var deviceSoftware: EditText
    private lateinit var getInfoButton: Button

    private val PERMISSION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        operatorName = findViewById(R.id.operatorName)
        phoneType = findViewById(R.id.phoneType)
        networkCountry = findViewById(R.id.networkCountry)
        simCountry = findViewById(R.id.simCountry)
        deviceSoftware = findViewById(R.id.deviceSoftware)
        getInfoButton = findViewById(R.id.getInfoButton)

        getInfoButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    PERMISSION_REQUEST_CODE
                )
            } else {
                displayTelephonyInfo()
            }
        }
    }

    private fun displayTelephonyInfo() {
        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        operatorName.setText(telephonyManager.networkOperatorName ?: "N/A")
        phoneType.setText(getPhoneTypeString(telephonyManager.phoneType))
        networkCountry.setText(telephonyManager.networkCountryIso ?: "N/A")
        simCountry.setText(telephonyManager.simCountryIso ?: "N/A")
        deviceSoftware.setText(telephonyManager.deviceSoftwareVersion ?: "N/A")
    }

    private fun getPhoneTypeString(type: Int): String {
        return when (type) {
            TelephonyManager.PHONE_TYPE_GSM -> "GSM"
            TelephonyManager.PHONE_TYPE_CDMA -> "CDMA"
            TelephonyManager.PHONE_TYPE_SIP -> "SIP"
            TelephonyManager.PHONE_TYPE_NONE -> "None"
            else -> "Unknown"
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayTelephonyInfo()
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
