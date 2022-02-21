package com.hectorpolendo.movieapptest.ui.view.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.hectorpolendo.movieapptest.R
import com.hectorpolendo.movieapptest.databinding.ActivityUserBinding
import com.hectorpolendo.movieapptest.domain.models.LocationRecord
import com.hectorpolendo.movieapptest.ui.viewmodel.UserViewModel
import com.hectorpolendo.movieapptest.util.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class UserActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityUserBinding
    private val userViewModel: UserViewModel by viewModels()
    private val database = Firebase.database
    private val myRef = database.getReference("Users")
    private lateinit var map: GoogleMap
    private var locations = ArrayList<LocationRecord>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createFragment()
        if(!Methods.isOnline(this@UserActivity)){
            var dialog = CustomDialogFragment()
            dialog.show(supportFragmentManager, "customDialog")
        }else{
            userViewModel.onCreate()

            binding.ivProfile.setOnClickListener {
                if(Methods.hasPermissions(this)){
                    openGallery()
                }
            }

            subscribeObservers()
        }
    }

    private fun createFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun subscribeObservers() {
        userViewModel.image.observe(this, {
            if(it.isNotEmpty()){
                Glide.with(this)
                    .load(it!!)
                    .into(binding.ivProfile)
            }
        })

        userViewModel.readDataFromFirebase(this)

        userViewModel.locationRecord.observe(this, {
            locations = it as ArrayList<LocationRecord>
            createMarker()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.SELECT_PICTURE) {
            if (resultCode == RESULT_OK) {
                val FileUri = data!!.data
                val Folder: StorageReference =
                    FirebaseStorage.getInstance().getReference().child("Images")
                val file_name: StorageReference = Folder.child("file" + FileUri!!.lastPathSegment)
                file_name.putFile(FileUri).addOnSuccessListener { taskSnapshot ->
                    file_name.getDownloadUrl().addOnSuccessListener { uri ->
                        val hashMap =
                            HashMap<String, String>()
                        hashMap["Images"] = java.lang.String.valueOf(uri)
                        myRef.child(Methods.getMacAddress(getApplicationContext())).child("Images").setValue(hashMap)
                        userViewModel.insertImage(java.lang.String.valueOf(uri))
                        Methods.getMacAddress(getApplicationContext())?.let { Log.e("MAC ADDRESS", it) }
                    }
                }
            }
        }
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        gallery.type = "image/*"
        startActivityForResult(
            Intent.createChooser(gallery, "Seleccione la app"),
            Constants.SELECT_PICTURE
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
    }

    private fun createMarker() {
        if(!locations.isNullOrEmpty()){
            locations.forEach {
                val  coordinates = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                val marker = MarkerOptions().position(coordinates).title(it.date)
                map.addMarker(marker)
            }
        }
    }
}