package com.hectorpolendo.movieapptest.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hectorpolendo.movieapptest.data.Repository
import com.hectorpolendo.movieapptest.domain.models.LocationRecord
import com.hectorpolendo.movieapptest.util.Methods
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.HashMap


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    private val _image = MutableLiveData<String>()
    val image: LiveData<String> get() = _image

    private val _LocationRecord = MutableLiveData<List<LocationRecord>>()
    val locationRecord: LiveData<List<LocationRecord>> get() = _LocationRecord

    val list: MutableList<LocationRecord> = mutableListOf()

    private val database = Firebase.database
    private val myRef = database.getReference("Users")

    fun onCreate(){
        viewModelScope.launch {
            val img: String? = repository.getImage()
            if(!img.isNullOrEmpty()){
                _image.postValue(repository.getImage()!!)
            }else{
                _image.postValue("")
            }

        }
    }

    fun insertImage(img: String){
        viewModelScope.launch {

            repository.insertImage(img)
            _image.postValue(img)
        }
    }

    fun readDataFromFirebase(context: Context){
        list.clear()
        myRef.child(Methods.getMacAddress(context)).addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                funConvertValues(snapshot)
                _LocationRecord.postValue(list)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                funConvertValues(snapshot)
                _LocationRecord.postValue(list)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.e("Removed", "Has removed")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.e("Moved", "Has moved")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Error", error.message)
            }
        })
    }

    fun funConvertValues(snapshot: DataSnapshot){
        for (postSnapshot in snapshot.children) {
            var str = postSnapshot.value.toString().replace(" ","").subSequence(1, postSnapshot.value.toString().length-2)
            val map = str.split(",").associateTo(HashMap()) {
                val (left, right) = it.split("=")
                left to right
            }
            val strlat = map["Latitud"].toString()
            val strLon = map["Longitud"].toString()
            var location = LocationRecord(postSnapshot.key.toString(), strlat, strLon)
            list.add(location)
        }
    }
}