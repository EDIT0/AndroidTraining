package com.hs.workation.core.common.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat

interface NavActivity {
    fun navigateToPermissionActivity(context: Context, launcher: ActivityResultLauncher<Intent>, options: ActivityOptionsCompat?, dataBundle: Bundle?)
    fun navigateToLoginActivity(context: Context, launcher: ActivityResultLauncher<Intent>, options: ActivityOptionsCompat?, dataBundle: Bundle?)
    fun navigateToHomeActivity(context: Context, launcher: ActivityResultLauncher<Intent>, options: ActivityOptionsCompat?, dataBundle: Bundle?)
}