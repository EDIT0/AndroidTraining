package com.hs.workation.feature.splash.view.fragment.component

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.CommonMap
import com.hs.workation.core.component.CommonMap.move
import com.hs.workation.core.component.CommonMap.moveFitBounds
import com.hs.workation.core.model.mock.MapItemData
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentCommonMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.clustering.Clusterer
import com.naver.maps.map.overlay.MultipartPathOverlay
import com.naver.maps.map.overlay.MultipartPathOverlay.ColorPart
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommonMapFragment : BaseDataBindingFragment<FragmentCommonMapBinding>(R.layout.fragment_common_map), OnMapReadyCallback {

    private var backPressCallback: OnBackPressedCallback? = null

    private lateinit var locationSource: FusedLocationSource
    private lateinit var map: NaverMap

    override fun onAttach(context: Context) {
        super.onAttach(context)

        backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressCallback!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 지도 초기화
        val option = NaverMapOptions().camera(
            CameraPosition(
                NaverMap.DEFAULT_CAMERA_POSITION.target,
                NaverMap.DEFAULT_CAMERA_POSITION.zoom
            )
        ).locationButtonEnabled(true)

        val mapFragment = MapFragment.newInstance(option).also {
            childFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
        }

        mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    private var clusterer: Clusterer<CommonMap.ItemKey>? = null

    override fun onMapReady(naverMap: NaverMap) {
        map = naverMap

        naverMap.locationSource = locationSource

        naverMap.addOnOptionChangeListener {
            // 나침반 표시
            val mode = naverMap.locationTrackingMode
            locationSource.isCompassEnabled = mode == CommonMap.LOCATION_FOLLOW || mode == CommonMap.LOCATION_FACE
        }

        naverMap.locationTrackingMode = CommonMap.LOCATION_FACE

        // 말풍선
        val infoWindow = CommonMap.createInfoWindow(requireContext())

        binding.btn1.setOnClickListener {
            val POSITION = LatLng(37.51822, 126.89588)

            // 마커
            CommonMap.createMarker(
                naverMap = naverMap,
                pos = POSITION,
                markerIcon = MarkerIcons.PINK,
                caption = "마커를 클릭하세요",
                subCaption = "서브캡션",
                infoTag = "누르면 사라집니다",
                onClick = infoWindow::open
            )

            // 카메라
            naverMap.move(
                cameraPosition = POSITION,
                animation = CameraAnimation.Easing
            )
        }

        binding.btn2.setOnClickListener {
            // 경로 표시
            CommonMap.createPath(
                context = requireContext(),
                naverMap = naverMap,
                coords = COORDS,
                pathColor = Color.RED
            )

            // 시작점 + 도착점 마커
            CommonMap.createMarker(
                naverMap = naverMap,
                pos = COORDS.first(),
                zoom = 13.0,
                markerIcon = OverlayImage.fromResource(com.hs.workation.core.component.R.drawable.ic_pin_start),
                onClick = {
                    naverMap.move(COORDS.first(), cameraZoom = 16.0, duration = 300)
                }
            )
            CommonMap.createMarker(
                naverMap = naverMap,
                pos = COORDS.last(),
                zoom = 13.0,
                markerIcon = OverlayImage.fromResource(com.hs.workation.core.component.R.drawable.ic_pin_end),
                onClick = {
                    naverMap.move(COORDS.last(), cameraZoom = 16.0, duration = 300)
                }
            )

            // 경로 범위가 보이게 카메라 이동
            naverMap.moveFitBounds(
                context = requireContext(),
                bounds = LatLngBounds(COORDS.first(), COORDS.last()),
                animation = CameraAnimation.Fly
            )

            val sizes = listOf(3, 3, 6, 12, 18, 3, 3, 2) // 리스트 내 숫자 아이템 합 <= 좌표 갯수(COORDS)
            val dividedLists = mutableListOf<List<LatLng>>()
            var startIndex = 0
            if(sizes.sum() > COORDS.size) {
                showToast("리스트 크기 초과 (${sizes.sum()} <= ${COORDS.size})")
                return@setOnClickListener
            }
            // sizes 아이템만큼 각 좌표 리스트 생성
            for (size in sizes) {
                val endIndex = startIndex + size
                if (endIndex <= COORDS.size) {
                    dividedLists.add(COORDS.subList(startIndex, endIndex))
                    startIndex = endIndex
                }
            }

            // 각 좌표 그룹 리스트끼리 연결하기 위해 A리스트의 바로 앞 리스트의 0번째 좌표를 A리스트 가장 마지막에 넣어준다.
            for (i in 0 until dividedLists.size-1) {
                val nextFirstItem = dividedLists[i+1][0]
                dividedLists[i] = dividedLists[i] + listOf(nextFirstItem)
            }
            // 임의 좌표 생성
            val multiPartPathList: MutableList<List<LatLng>> = mutableListOf()
            for(list in dividedLists) {
                val tmpList: MutableList<LatLng> = mutableListOf()
                for(item in list) {
                    val newItem = LatLng(item.latitude, item.longitude + 0.003)
                    tmpList.add(newItem)
                }
                multiPartPathList.add(tmpList)
            }
            // sizes의 크기가 리스트 그룹의 수 이므로 각 리스트별 색을 넣어준다.
            val colorArray = arrayOf(Color.RED, Color.LTGRAY, Color.BLACK, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.YELLOW)
            val multiPartColorList: MutableList<ColorPart> = mutableListOf()
            for(i in 0 until sizes.size) {
                val index = if(i == colorArray.size) 0 else i
                multiPartColorList.add(
                    MultipartPathOverlay.ColorPart(
                        colorArray[index], Color.WHITE, Color.GRAY, Color.LTGRAY
                    )
                )
            }

            CommonMap.createMultiPartPath(
                context = requireContext(),
                naverMap = naverMap,
                coords = multiPartPathList,
                pathColor = multiPartColorList
            )
            for(i in 0 until multiPartPathList.size-1) {
                CommonMap.createMarker(
                    naverMap = naverMap,
                    pos = multiPartPathList[i][multiPartPathList[i].size-1],
                    markerIcon = MarkerIcons.GREEN,
                    caption = "지점 ${i}",
                    zoom = 13.0,
                    subCaption = "서브캡션",
                    infoTag = "누르면 사라집니다",
                    onClick = infoWindow::open
                )
            }
        }

        // 클러스터링
        clusterer = CommonMap.buildClustering()

        // 목업 데이터를 ClusteringKey 로 변환
        CoroutineScope(Dispatchers.IO).launch {
            val keyTagMap = buildMap {
                MOCK_DATA.forEachIndexed { index, data ->
                    val split = data.split(",")
                    put(
                        CommonMap.ItemKey(index, LatLng(split[3].toDouble(), split[2].toDouble())),
                        MapItemData(split[0], split[1]),
                    )
                }
            }
            run {
                clusterer?.addAll(keyTagMap)
                clusterer?.setMap(naverMap)
            }
        }
    }

    // 권한 체크
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                map.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100

        // 경로
        private val COORDS = listOf(
            LatLng(37.5660645, 126.9826732),
            LatLng(37.5660294, 126.9826723),
            LatLng(37.5658526, 126.9826611),
            LatLng(37.5658040, 126.9826580),
            LatLng(37.5657697, 126.9826560),
            LatLng(37.5654413, 126.9825880),
            LatLng(37.5652157, 126.9825273),
            LatLng(37.5650560, 126.9824843),
            LatLng(37.5647789, 126.9824114),
            LatLng(37.5646788, 126.9823861),
            LatLng(37.5644062, 126.9822963),
            LatLng(37.5642519, 126.9822566),
            LatLng(37.5641517, 126.9822312),
            LatLng(37.5639965, 126.9821915),
            LatLng(37.5636536, 126.9820920),
            LatLng(37.5634424, 126.9820244),
            LatLng(37.5633241, 126.9819890),
            LatLng(37.5632772, 126.9819712),
            LatLng(37.5629404, 126.9818433),
            LatLng(37.5627733, 126.9817584),
            LatLng(37.5626694, 126.9816980),
            LatLng(37.5624588, 126.9815738),
            LatLng(37.5620376, 126.9813140),
            LatLng(37.5619426, 126.9812252),
            LatLng(37.5613227, 126.9814831),
            LatLng(37.5611995, 126.9815372),
            LatLng(37.5609414, 126.9816749),
            LatLng(37.5606785, 126.9817390),
            LatLng(37.5605659, 126.9817499),
            LatLng(37.5604892, 126.9817459),
            LatLng(37.5604540, 126.9817360),
            LatLng(37.5603484, 126.9816993),
            LatLng(37.5602092, 126.9816097),
            LatLng(37.5600048, 126.9814390),
            LatLng(37.5599702, 126.9813612),
            LatLng(37.5599401, 126.9812923),
            LatLng(37.5597114, 126.9807346),
            LatLng(37.5596905, 126.9806826),
            LatLng(37.5596467, 126.9805663),
            LatLng(37.5595203, 126.9801199),
            LatLng(37.5594901, 126.9800149),
            LatLng(37.5594544, 126.9798883),
            LatLng(37.5594186, 126.9797436),
            LatLng(37.5593948, 126.9796634),
            LatLng(37.5593132, 126.9793526),
            LatLng(37.5592831, 126.9792622),
            LatLng(37.5590904, 126.9788854),
            LatLng(37.5589081, 126.9786365),
            LatLng(37.5587088, 126.9784125),
            LatLng(37.5586699, 126.9783698),
        )

        // 클러스터링
        private val MOCK_DATA = listOf(
            "샛강역 화장실(9호선),영등포구,126.929061140,37.516625517",
            "9호선여의도역 화장실(외부),영등포구,126.923952255,37.521903572",
            "9호선여의도역 화장실(내부),영등포구,126.923952255,37.521903572",
            "9호선국회의사당역 화장실(외부),영등포구,126.917867075,37.528062387",
            "9호선국회의사당역 화장실(내부),영등포구,126.917867075,37.528062387",
            "9호선당산역 화장실(B1층),영등포구,126.902153703,37.533737042",
            "선유도역 화장실(9호선),영등포구,126.893402287,37.538127750",
            "대림역,영등포구,126.896593984,37.492741021",
            "신풍역,영등포구,126.909821405,37.500119293",
            "여의나루역,영등포구,126.932846094,37.527080523",
            "여의도역,영등포구,126.924468113,37.521838905",
            "신길역,영등포구,126.914253586,37.517639144",
            "영등포시장역,영등포구,126.904942887,37.522733468",
            "영등포구청역,영등포구,126.895272195,37.524225243",
            "양평역,영등포구,126.886852167,37.525509671",
            "영등포로타리 지하상가,영등포구,126.905710665,37.518198548",
            "영등포역 지하상가,영등포구,126.906432531,37.516788733",
            "영등포시장,영등포구,126.905901233,37.519745270",
            "방문자안내센터-선유도공원,영등포구,126.901761200,37.542416800",
            "여의도공원(4호. 민들레화장실),영등포구,126.922264248,37.526804697",
            "선유도공원(야외화장실),영등포구,126.898784500,37.544048500",
            "선유도이야기관(1층. 2층 화장실),영등포구,126.901060000,37.543320900",
            "여의도공원(8호화장실),영등포구,126.923517300,37.525856400",
            "여의도공원(7호. 은방울화장실),영등포구,126.920419900,37.523930400",
            "여의도공원(6호. 봉선화호장실),영등포구,126.926174300,37.527663000",
            "여의도공원(5호. 수선화화장실),영등포구,126.924349500,37.528005700",
            "여의도공원(3호. 무궁화호장실),영등포구,126.920880300,37.525861500",
            "여의도공원(2호. 개나리화장실),영등포구,126.919055000,37.524816000",
            "여의도공원(1호. 진달래화장실),영등포구,126.918874800,37.523246800",
            "물놀이장 내,영등포구,126.892998400,37.544942800",
            "3호매점,영등포구,126.927543200,37.530689200",
            "2호매점,영등포구,126.933514800,37.526770100",
            "원효대교 하부,영등포구,126.939481500,37.523425700",
            "파크골프장,영등포구,126.941792400,37.519323800",
            "안양천 입구,영등포구,126.878111500,37.553234300",
            "하류 주차장,영등포구,126.891754500,37.544787600",
            "축구장,영등포구,126.892951900,37.543703200",
            "선유교 상류,영등포구,126.896772900,37.541532500",
            "양화대교 하부,영등포구,126.900877600,37.539236900",
            "상류 주차장,영등포구,126.904012300,37.537202200",
            "샛강 파천주차장,영등포구,126.913404300,37.525991400",
            "샛강 서울교 하부,영등포구,126.916528700,37.521518100",
            "국회주차장,영등포구,126.915458000,37.534731100",
            "축구장,영등포구,126.918500700,37.534259400",
            "서강대교 하부,영등포구,126.923257700,37.533111000",
            "수상무대,영등포구,126.926752200,37.532010300",
            "물빛광장,영등포구,126.930070100,37.530295800",
            "마포대교,영등포구,126.932203200,37.531090300",
            "유람선선착장,영등포구,126.938147100,37.524800200",
            "청사(센터),영등포구,126.933660200,37.526248000",
            "안내센터앞,영등포구,126.934504400,37.527613100",
            "파라다이스앞,영등포구,126.941883900,37.521464900"
        )
    }
}