package com.example.namanassignment.feature.base

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.namanassignment.R
import com.example.namanassignment.util.LocaleManager


class MainActivity : AppCompatActivity() {
    var sharedPreferences: SharedPreferences? = null
    var themeName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("Theme", MODE_PRIVATE)
        themeName = sharedPreferences!!.getString("ThemeName", "Default")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Resources.getSystem().configuration.isNightModeActive) {
                setTheme(R.style.DarkTheme)
                val preferences = getSharedPreferences("Theme", MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putString("ThemeName", "Dark")
                editor.apply()
            } else {
                setTheme(R.style.LightTheme)
                val preferences = getSharedPreferences("Theme", MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putString("ThemeName", "Light")
                editor.apply()
            }
        }else {
            setTheme(R.style.LightTheme)
            val preferences = getSharedPreferences("Theme", MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("ThemeName", "Light")
            editor.apply()
        }
        setContentView(R.layout.activity_main)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.language_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.m_english -> {
                setNewLocale(this@MainActivity, LocaleManager.ENGLISH)
                true
            }
            R.id.m_arabic -> {
                setNewLocale(this@MainActivity, LocaleManager.ARABIC)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (newConfig.isNightModeActive) {
                setMyTheme("Dark")
            } else {
                setMyTheme("Light")
            }
        }else{
            setMyTheme("Light")
        }
    }

    private fun setMyTheme(name: String?) {
        // Create preference to store theme name
        val preferences = getSharedPreferences("Theme", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("ThemeName", name)
        editor.apply()
        //        recreate();
        val i = intent
        overridePendingTransition(0, 0)
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        //restart the activity without animation
        overridePendingTransition(0, 0)
        startActivity(i)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(base!!))
    }

    private fun setNewLocale(mContext: AppCompatActivity,@LocaleManager.Companion.LocaleDef language: String) {
        LocaleManager.setNewLocale(this, language)
        val i = intent
        overridePendingTransition(0, 0)
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        //restart the activity without animation
        overridePendingTransition(0, 0)
        startActivity(i)
    }
}