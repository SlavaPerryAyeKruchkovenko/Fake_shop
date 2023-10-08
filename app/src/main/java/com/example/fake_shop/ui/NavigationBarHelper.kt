package com.example.fake_shop.ui

import androidx.fragment.app.FragmentActivity
import com.example.fake_shop.MainActivity

class NavigationBarHelper {
    companion object {
        fun hideNavigationBar(activity: FragmentActivity?) {
            try {
                val mainActivity = activity as MainActivity
                mainActivity.hideBottomNavigationView()
            } catch (_: Exception) {

            }
        }

        fun showNavigationBar(activity: FragmentActivity?) {
            try {
                val mainActivity = activity as MainActivity
                mainActivity.showBottomNavigationView()
            } catch (_: Exception) {

            }
        }
    }
}