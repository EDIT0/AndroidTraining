package com.hs.workation.feature.splash.test2.event

import android.net.Uri

sealed interface Test2ViewModelEvent {
    class CallApiTest(): Test2ViewModelEvent
    class SaveImage(val images: List<Uri>): Test2ViewModelEvent
}