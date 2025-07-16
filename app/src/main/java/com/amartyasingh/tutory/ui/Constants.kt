package com.amartyasingh.tutory.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.BedroomParent
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.amartyasingh.tutory.model.BottomNavigationItemModel

object Constants {
    val navigationItems = listOf(
        BottomNavigationItemModel(
            title = "Home",
            icon = Icons.Default.Home,
            route = Screen.Home.rout
        ),
        BottomNavigationItemModel(
            title = "Properties",
            icon = Icons.Default.BedroomParent,
            route = Screen.Properties.rout
        ),
        BottomNavigationItemModel(
            title = "Reports",
            icon = Icons.Default.Analytics,
            route = Screen.Reports.rout
        ),
        BottomNavigationItemModel(
            title = "Settings",
            icon = Icons.Default.Settings,
            route = Screen.Settings.rout
        )
    )
}

sealed class Screen(val rout: String) {
    object Home: Screen("home")
    object Properties: Screen("properties")
    object Reports: Screen("reports")
    object Settings: Screen("settings")
}