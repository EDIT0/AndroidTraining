package com.my.dfmdemo1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat
import com.my.dfmdemo1.core.navigation.ActivityNavigator

class ActivityNavigatorImpl: ActivityNavigator {

    override fun navigateActivityToMainActivity(
        context: Context,
        launcher: ActivityResultLauncher<Intent>,
        options: ActivityOptionsCompat?,
        dataBundle: Bundle?
    ) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        dataBundle?.let { intent.putExtras(dataBundle) }
        launcher.launch(intent, options)
    }

}