package com.halill.halill2.main

import com.halill.halill2.R

sealed class BottomNavigationItem(var route: String, var iconResId: Int, var title: String) {
    object List : BottomNavigationItem("list", R.drawable.ic_baseline_checklist_24, "List")
    object Calendar : BottomNavigationItem("calendar", R.drawable.ic_baseline_calendar_today_24, "Calendar")
    object MyPage : BottomNavigationItem("myPage", R.drawable.ic_baseline_person_24, "MyPage")
}