package com.my.viewtransitionanimdemo1.navigate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat
import com.my.viewtransitionanimdemo1.activity.SecondActivity

class NavActivityImpl : NavActivity {

    override fun navigateToSecondActivity(
        context: Context,
        launcher: ActivityResultLauncher<Intent>,
        options: ActivityOptionsCompat?,
        dataBundle: Bundle?
    ) {
        val intent = Intent(context, SecondActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        dataBundle?.let { intent.putExtras(dataBundle) }
        launcher.launch(intent, options)
    }

}