package com.djgamarra.blaster.data

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

/**
 * This singleton holds information settings. At the moment the only
 * setting is the fps.
 *
 * Settings get saved on a file called config.properties.
 */
object Settings {
    /**
     * Current (desired) fps. When set it saves changes to the config file.
     */
    var fps: Long = 60L
        set(value) {
            field = value
            propertiesFile.setProperty(FPS_CONFIG_KEY, value.toString())
            storeConfig()
        }

    /**
     * Properties holder.
     */
    private val propertiesFile = Properties()

    /**
     * Default config file name.
     */
    private const val CONFIG_FILE_NAME = "config.properties"

    /**
     * Key for fps in config file.
     */
    private const val FPS_CONFIG_KEY = "fps"

    /**
     * Sync will get enabled once settings get loaded for
     * the first time, so that on the initial load it will
     * not resave the settings.
     */
    private var syncEnabled = false

    init {
        loadInitialConfig()
        syncEnabled = true
    }

    /**
     * Creates an empty file if there is no settings file.
     */
    private fun initializeConfigFileIfNecessary() {
        val file = File(CONFIG_FILE_NAME)
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    /**
     * Loads the initial config file into the holder.
     */
    private fun loadInitialConfig() {
        initializeConfigFileIfNecessary()
        FileInputStream(CONFIG_FILE_NAME).use { propertiesFile.load(it) }
        fps = propertiesFile.getProperty(FPS_CONFIG_KEY)?.toLongOrNull() ?: fps
    }

    /**
     * Saves settings in the holder to the file.
     */
    private fun storeConfig() {
        if (syncEnabled) {
            FileOutputStream(CONFIG_FILE_NAME).use { propertiesFile.store(it, null) }
        }
    }
}
