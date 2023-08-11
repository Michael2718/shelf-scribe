package com.example.shelf_scribe

import android.app.Application
import com.example.shelf_scribe.data.AppContainer
import com.example.shelf_scribe.data.DefaultAppContainer

class ShelfScribeApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}