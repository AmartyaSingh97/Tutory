package com.amartyasingh.tutory.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.amartyasingh.tutory.ui.Constants.navigationItems

@Composable
fun MainScreen(){

    val navController = rememberNavController()

    Scaffold(
         bottomBar = {
            BottomNavigationBar(navController = navController)
        }, content = { padding ->
             NavHostContainer(navController = navController, padding = padding)
        }
    )
}

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {

    NavHost(
        navController = navController,

         startDestination = "home",

         modifier = Modifier.padding(paddingValues = padding),

        builder = {

             composable("home") {
                 HomeScreen()
            }

             composable("properties") {
                 PropertiesScreen()
            }

             composable("reports") {
                 ReportsScreen()
            }

            composable("settings") {
                SettingsScreen()
            }
        })
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    NavigationBar(

         containerColor = Color.White) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

         navigationItems.forEach { navItem ->

             NavigationBarItem(

                 selected = currentRoute == navItem.route,

                 onClick = {
                    navController.navigate(navItem.route)
                },

                 icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.title)
                },

                 label = {
                    Text(text = navItem.title)
                },
                alwaysShowLabel = true,

                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.Black,
                    indicatorColor = Color(0xFF1A94E5)
                )
            )}
    }
}