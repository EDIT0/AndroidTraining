package com.hs.workation.core.util

import com.google.firebase.messaging.FirebaseMessaging

object FCMToken {

    fun initFirebaseCloudMessaging(
        onReceiveToken: (String) -> Unit,
        onFail: () -> Unit
    ) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(!task.isSuccessful) {
                return@addOnCompleteListener
            }

            val token = task.result

            LogUtil.d_dev("Firebase token: ${token}")
            onReceiveToken.invoke(token)
//            val lowerToken = token?.lowercase()
//
//            if (lowerToken.isNullOrBlank() ||
//                lowerToken.contains(";") ||
//                lowerToken.contains("--") ||
//                lowerToken.contains("exec") ||
//                lowerToken.contains("select") ||
//                lowerToken.contains("update") ||
//                lowerToken.contains("delete") ||
//                lowerToken.contains("insert") ||
//                lowerToken.contains("alter") ||
//                lowerToken.contains("drop") ||
//                lowerToken.contains("create") ||
//                lowerToken.contains("shutdown")
//            ) {
//                FirebaseMessaging.getInstance().deleteToken()
//                    .addOnCompleteListener {
//                        initFirebaseCloudMessaging()
//                    }
//                    .addOnFailureListener {
//                        viewModelScope.launch {
//                            saveFcmTokenUseCase.invoke(token?:"")
//                        }
//                        pushFcmId = token
//                        introProcess()
//                    }
//                return@addOnCompleteListener
//            } else {
//                viewModelScope.launch {
//                    saveFcmTokenUseCase.invoke(token?:"")
//                }
//                pushFcmId = token
//                introProcess()
//            }
        }.addOnFailureListener {
            onFail.invoke()
        }.addOnCanceledListener {
            onFail.invoke()
        }
    }

}