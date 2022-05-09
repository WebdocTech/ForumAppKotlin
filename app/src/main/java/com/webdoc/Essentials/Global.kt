package com.webdoc.Essentials

import android.app.Application
import android.net.Uri

public class Global : Application() {



    companion object {
        @JvmField
        var utils: Utils? = Utils()
        val APARTMENT_ID_KEY: String? = "wazifa_id"
        var uriArrayList = ArrayList<Uri>()
    }
}