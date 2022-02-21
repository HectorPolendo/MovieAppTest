package com.hectorpolendo.movieapptest.util

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class PermissionRequester(
    activity: ComponentActivity,
    private val permission: String,
    private val onRationale: () -> Unit = {},
    private val onDenied: () -> Unit = {}
){

    var onGranted: () -> Unit = {}

    val permmissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()){
        when{
            it -> onGranted
        }
    }
    fun runWithPermission(body: () -> Unit){
        onGranted = body
        permmissionLauncher.launch(permission)
    }

}