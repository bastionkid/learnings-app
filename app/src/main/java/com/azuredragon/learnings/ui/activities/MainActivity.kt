package com.azuredragon.learnings.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()

        Timber.d("NavigationTestActivity onDestroy()")
    }
}