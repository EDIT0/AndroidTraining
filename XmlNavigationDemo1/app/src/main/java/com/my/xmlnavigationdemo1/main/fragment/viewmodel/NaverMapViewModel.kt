package com.my.xmlnavigationdemo1.main.fragment.viewmodel

import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.util.FusedLocationSource

class NaverMapViewModel: ViewModel() {

    var locationSource: FusedLocationSource? = null
    var map: NaverMap? = null
    var cameraPosition: LatLng? = null
    var currentZoom: Double = 0.0
    var currentTilt: Double = 0.0
    var currentBearing: Double = 0.0

}