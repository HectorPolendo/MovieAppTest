package com.hectorpolendo.movieapptest.util

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.util.*

object Methods {
    private val database = Firebase.database
    private val myRef = database.getReference("Users")

    fun hasPermissions(context: Context?): Boolean {
        if (context != null && Constants.PERMISSIONS != null) {
            for (permission in Constants.PERMISSIONS) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    fun getMacAddress(context: Context): String{
        val manager = context.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        val info = manager.connectionInfo
        val address = if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Activity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return ""
        }else{
            return info.bssid
        }
    }

    fun getLocation(context: Context, activity: Activity){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }else{
            var location = LocationServices.getFusedLocationProviderClient(context)
            location.lastLocation
                .addOnSuccessListener { it : Location? ->
                    if(it != null){
                        val stamp = Timestamp(System.currentTimeMillis())
                        val date = Date(stamp.time)

                        val hashMap = HashMap<String, String>()
                        hashMap["Latitud"] = "${it.latitude}"
                        hashMap["Longitud"] = "${it.longitude}"
                        myRef.child(getMacAddress(context)).child("Location").child(date.toString()).setValue(hashMap)
                    }
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}