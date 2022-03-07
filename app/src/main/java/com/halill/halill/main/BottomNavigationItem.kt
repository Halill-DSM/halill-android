package com.halill.halill.main

import com.halill.halill.R

sealed class BottomNavigationItem(var route: String, var iconResId: Int, var title: String) {
    object List : BottomNavigationItem("list", R.drawable.ic_baseline_checklist_24, "List")
    object Calendar : BottomNavigationItem("calendar", R.drawable.ic_baseline_calendar_today_24, "Calendar")
}