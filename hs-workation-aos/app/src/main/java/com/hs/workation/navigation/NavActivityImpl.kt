package com.hs.workation.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat
import com.hs.workation.core.common.navigation.NavActivity
import com.hs.workation.feature.main.view.activity.HomeActivity
import com.hs.workation.feature.login.main.view.activity.LoginActivity
import com.hs.workation.feature.permission.main.view.activity.PermissionActivity

/**
 * 각 모듈별 Activity 호출을 위한 클래스
 * @param context context
 * @param launcher Launcher
 * @param options 이동 옵션
 * @param dataBundle 전달 데이터
 * */
class NavActivityImpl : NavActivity {

    override fun navigateToPermissionActivity(
        context: Context,
        launcher: ActivityResultLauncher<Intent>,
        options: ActivityOptionsCompat?,
        dataBundle: Bundle?
    ) {
        val intent = Intent(context, PermissionActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        dataBundle?.let { intent.putExtras(dataBundle) }
        launcher.launch(intent, options)
    }

    override fun navigateToLoginActivity(
        context: Context,
        launcher: ActivityResultLauncher<Intent>,
        options: ActivityOptionsCompat?,
        dataBundle: Bundle?
    ) {
        val intent = Intent(context, LoginActivity::class.java)
        dataBundle?.let { intent.putExtras(dataBundle) }
        launcher.launch(intent, options)
    }

    override fun navigateToHomeActivity(
        context: Context,
        launcher: ActivityResultLauncher<Intent>,
        options: ActivityOptionsCompat?,
        dataBundle: Bundle?
    ) {
        val intent = Intent(context, com.hs.workation.feature.main.view.activity.HomeActivity::class.java)
        dataBundle?.let { intent.putExtras(dataBundle) }
        launcher.launch(intent, options)
    }

}