package com.my.xmlnavigationdemo1.main.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.my.xmlnavigationdemo1.R
import com.my.xmlnavigationdemo1.databinding.FragmentNaverMapBinding
import com.my.xmlnavigationdemo1.main.fragment.viewmodel.NaverMapViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource

class NaverMapFragment : Fragment() {

    private var _binding: FragmentNaverMapBinding? = null
    val binding get() = _binding!!

    private val naverMapVM: NaverMapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let {
            binding.naverMapView.onCreate(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("MYTAG ${javaClass.simpleName}", "onViewCreated()")

        _binding = FragmentNaverMapBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.naverMapView.getMapAsync(OnMapReadyCallback { naverMap ->
            naverMapVM.map = naverMap

            if(naverMapVM.cameraPosition != null) {
                naverMapVM.map?.moveCamera(
                    CameraUpdate.scrollTo(
                        LatLng(
                            naverMapVM.cameraPosition?.latitude?:0.0,
                            naverMapVM.cameraPosition?.longitude?:0.0
                        )
                    )
                )
            }
            if(naverMapVM.currentZoom != 0.0) {
                naverMapVM.map?.moveCamera(
                    CameraUpdate.zoomTo(naverMapVM.currentZoom)
                )
            }

            naverMapVM.map?.locationSource = naverMapVM.locationSource

            naverMapVM.map?.addOnCameraChangeListener { i, b ->
                naverMapVM.cameraPosition = LatLng(
                    naverMapVM.map?.cameraPosition?.target?.latitude?:0.0, naverMapVM.map?.cameraPosition?.target?.longitude?:0.0
                )
                naverMapVM.currentTilt = naverMapVM.map?.cameraPosition?.tilt?:0.0
                naverMapVM.currentZoom = naverMapVM.map?.cameraPosition?.zoom?:0.0
                naverMapVM.currentBearing = naverMapVM.map?.cameraPosition?.bearing?:0.0

                Log.d("MYTAG ${javaClass.simpleName}", "지도 움직임: ${i} ${b}\n${naverMapVM.currentZoom}")
            }
        })
        naverMapVM.locationSource = FusedLocationSource(this, 100)

        binding.btnDialog1Fragment.setOnClickListener {
            findNavController().navigate(R.id.dialog1Fragment)
        }
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_naverMapFragment_to_twoFragment)
        }

        Glide.with(binding.ivImage.context)
            .load("https://picsum.photos/250/250")
            .into(binding.ivImage)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.naverMapView.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()

        Log.i("MYTAG ${javaClass.simpleName}", "onStart()")

        binding.naverMapView.onStart()
    }

    override fun onResume() {
        super.onResume()

        Log.i("MYTAG ${javaClass.simpleName}", "onResume()")

        binding.naverMapView.onResume()
    }

    override fun onPause() {
        super.onPause()

        Log.i("MYTAG ${javaClass.simpleName}", "onPause()")

        binding.naverMapView.onPause()
    }

    override fun onStop() {
        super.onStop()

        Log.i("MYTAG ${javaClass.simpleName}", "onStop()")

        binding.naverMapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.naverMapView.onDestroy()
        _binding = null

        Log.i("MYTAG ${javaClass.simpleName}", "onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i("MYTAG ${javaClass.simpleName}", "onDestroy()")
    }
}