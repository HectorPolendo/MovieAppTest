package com.hectorpolendo.movieapptest.ui.view.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.hectorpolendo.movieapptest.adapters.LocationRecordAdapter
import com.hectorpolendo.movieapptest.adapters.MostPopularMoviesAdapter
import com.hectorpolendo.movieapptest.databinding.ActivityUserBinding
import com.hectorpolendo.movieapptest.domain.models.LocationRecord
import com.hectorpolendo.movieapptest.ui.viewmodel.UserViewModel
import com.hectorpolendo.movieapptest.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private val userViewModel: UserViewModel by viewModels()
    private val database = Firebase.database
    private val myRef = database.getReference("Users")
    private lateinit var locationRecordAdapter: LocationRecordAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!Methods.isOnline(this@UserActivity)){
            var dialog = CustomDialogFragment()
            dialog.show(supportFragmentManager, "customDialog")
        }else{
            userViewModel.onCreate()

            locationRecordAdapter = LocationRecordAdapter()

            binding.rvLocation.apply {
                layoutManager = LinearLayoutManager(this@UserActivity, LinearLayoutManager.VERTICAL, false)
                adapter = locationRecordAdapter
            }

            binding.ivProfile.setOnClickListener {
                if(Methods.hasPermissions(this)){
                    openGallery()
                }
            }

            subscribeObservers()
        }
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
            locationRecordAdapter.setLocations(it as ArrayList<LocationRecord>)
            if(it == null){
                binding.rvLocation.visibility = View.GONE
                binding.constContainer.visibility = View.VISIBLE
            }else{
                binding.rvLocation.visibility = View.VISIBLE
                binding.constContainer.visibility = View.GONE
            }
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
}