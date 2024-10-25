package com.djgamarra.blaster.data

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

object Settings {
    var fps: Long = 60L
        set(value) {
            field = value
            propertiesFile.setProperty(FPS_CONFIG_KEY, value.toString())
            storeConfig()
        }

    private val propertiesFile = Properties()

    private const val CONFIG_FILE_NAME = "config.properties"
    private const val FPS_CONFIG_KEY = "fps"

    init {
        loadInitialConfig()
    }

    private fun initializeConfigFileIfNecessary() {
        val file = File(CONFIG_FILE_NAME)
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    private fun loadInitialConfig() {
        initializeConfigFileIfNecessary()

        FileInputStream(CONFIG_FILE_NAME).use { fileStream ->
            propertiesFile.load(fileStream)
        }

        fps = propertiesFile.getProperty(FPS_CONFIG_KEY)?.toLongOrNull() ?: fps
    }

    private fun storeConfig() {
        FileOutputStream(CONFIG_FILE_NAME).use { fileStream ->
            propertiesFile.store(fileStream, null)
        }
    }
}
