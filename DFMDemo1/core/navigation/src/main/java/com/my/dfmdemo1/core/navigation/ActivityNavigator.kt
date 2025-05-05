package com.my.dfmdemo1.core.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat

interface ActivityNavigator {
    fun navigateActivityToMainActivity(context: Context, launcher: ActivityResultLauncher<Intent>, options: ActivityOptionsCompat?, dataBundle: Bundle?)
}