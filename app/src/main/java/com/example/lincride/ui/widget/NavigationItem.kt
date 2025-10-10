package com.example.lincride.ui.widget

import androidx.annotation.DrawableRes

sealed class NavigationItem(
    val route: String,
    val label: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int
) {
    data object Home : NavigationItem(
        route = "home",
        label = "Home",
        selectedIcon = com.example.lincride.R.drawable.home_2,
        unselectedIcon = com.example.lincride.R.drawable.ic_home_unselect
    )

    data object History : NavigationItem(
        route = "history",
        label = "History",
        selectedIcon = com.example.lincride.R.drawable.calendar__selected,
        unselectedIcon = com.example.lincride.R.drawable.calendar
    )

    data object Profile : NavigationItem(
        route = "profile",
        label = "Profile",
        selectedIcon = com.example.lincride.R.drawable.user_avatar,
        unselectedIcon = com.example.lincride.R.drawable.user_avatar
    )

    companion object {
        fun getAllItems() = listOf(Home, History, Profile)
    }
}
