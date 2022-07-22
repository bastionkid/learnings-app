package com.azuredragon.learnings.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.ActivityNavigationTestBinding
import timber.log.Timber

class NavigationTestActivity : AppCompatActivity() {

    private lateinit var navigationTestBinding: ActivityNavigationTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigationTestBinding = DataBindingUtil.setContentView(this, R.layout.activity_navigation_test)
    }

    override fun onDestroy() {
        super.onDestroy()

        Timber.d("NavigationTestActivity onDestroy()")
    }
}