package com.arlanallacsta.githubuserapp3

import androidx.annotation.StringRes
import androidx.datastore.preferences.core.booleanPreferencesKey

object ConstValue {
    const val EXTRA_USERNAME = "extra_username"
    const val EXTRA_ID = "extra_id"
    const val EXTRA_AVATAR_URL = "extra_avatar_url"

    val THEME_KEY = booleanPreferencesKey("theme_setting")

    @StringRes
    val TAB_TITLES = intArrayOf(R.string.followers, R.string.following)
}