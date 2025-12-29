package com.my.xmlnavigationdemo1.bottomnav.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.my.xmlnavigationdemo1.bottomnav.fragment.viewmodel.COneViewModel
import com.my.xmlnavigationdemo1.databinding.FragmentCOneBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource

class COneFragment : Fragment() {

    private var _binding: FragmentCOneBinding? = null
    val binding get() = _binding!!

    private val cOneVM: COneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MYTAG ${javaClass.simpleName}", "onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("MYTAG ${javaClass.simpleName}", "onViewCreated()")

        _binding = FragmentCOneBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.naverMapView.getMapAsync(OnMapReadyCallback { naverMap ->
            cOneVM.map = naverMap

            if(cOneVM.cameraPosition != null) {
                cOneVM.map?.moveCamera(
                    CameraUpdate.scrollTo(
                        LatLng(
                            cOneVM.cameraPosition?.latitude?:0.0,
                            cOneVM.cameraPosition?.longitude?:0.0
                        )
                    )
                )
            }
            if(cOneVM.currentZoom != 0.0) {
                cOneVM.map?.moveCamera(
                    CameraUpdate.zoomTo(cOneVM.currentZoom)
                )
            }

            cOneVM.map?.locationSource = cOneVM.locationSource

            cOneVM.map?.addOnCameraChangeListener { i, b ->
                cOneVM.cameraPosition = LatLng(
                    cOneVM.map?.cameraPosition?.target?.latitude?:0.0, cOneVM.map?.cameraPosition?.target?.longitude?:0.0
                )
                cOneVM.currentTilt = cOneVM.map?.cameraPosition?.tilt?:0.0
                cOneVM.currentZoom = cOneVM.map?.cameraPosition?.zoom?:0.0
                cOneVM.currentBearing = cOneVM.map?.cameraPosition?.bearing?:0.0

                Log.d("MYTAG ${javaClass.simpleName}", "지도 움직임: ${i} ${b}\n${cOneVM.currentZoom}")
            }
        })
        cOneVM.locationSource = FusedLocationSource(this, 100)
    }

    override fun onStart() {
        super.onStart()

        Log.i("MYTAG ${javaClass.simpleName}", "onStart()")
    }

    override fun onResume() {
        super.onResume()

        Log.i("MYTAG ${javaClass.simpleName}", "onResume()")
    }

    override fun onPause() {
        super.onPause()

        Log.i("MYTAG ${javaClass.simpleName}", "onPause()")
    }

    override fun onStop() {
        super.onStop()

        Log.i("MYTAG ${javaClass.simpleName}", "onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        Log.i("MYTAG ${javaClass.simpleName}", "onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i("MYTAG ${javaClass.simpleName}", "onDestroy()")
    }
}