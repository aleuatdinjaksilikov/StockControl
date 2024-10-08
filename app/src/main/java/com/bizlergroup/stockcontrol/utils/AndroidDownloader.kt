package com.bizlergroup.stockcontrol.utils

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

class AndroidDownloader(
    private val context: Context
):Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("file/xlsx")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("Excel Statistika")
            .addRequestHeader("Authorization","Bearer-${SharedPref.pref.getString("token", "")}")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"file.xlsx")
        return downloadManager.enqueue(request)
    }
}