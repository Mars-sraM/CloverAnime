package com.mars.cloveranime.core

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService


object CheckInternet {

   fun isInternetOn(context: Context): Boolean {
       val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
       val networkInfo = cm!!.activeNetworkInfo

       return networkInfo != null && networkInfo.isConnected
   }

}