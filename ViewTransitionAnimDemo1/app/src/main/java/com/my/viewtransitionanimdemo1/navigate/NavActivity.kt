package com.my.viewtransitionanimdemo1.navigate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat

interface NavActivity {
    fun navigateToSecondActivity(context: Context, launcher: ActivityResultLauncher<Intent>, options: ActivityOptionsCompat?, dataBundle: Bundle?)
}