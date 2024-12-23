package com.hs.workation.core.component

import android.content.Context
import android.graphics.Color
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.clustering.Clusterer.ComplexBuilder
import com.naver.maps.map.clustering.ClusteringKey
import com.naver.maps.map.clustering.DefaultClusterMarkerUpdater
import com.naver.maps.map.clustering.DefaultClusterOnClickListener
import com.naver.maps.map.clustering.DefaultDistanceStrategy
import com.naver.maps.map.clustering.DefaultMarkerManager
import com.naver.maps.map.clustering.DistanceStrategy
import com.naver.maps.map.clustering.Node
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.MultipartPathOverlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.MarkerIcons

object CommonMap {
    /**
     * LocationTrackingMode.None : 표시 안함
     * LocationTrackingMode.NoFollow : 위치만 표시
     * LocationTrackingMode.Follow : 위치로 이동 + 방향 표시
     * LocationTrackingMode.Face : 위치로 이동 + 정면 표시
     */
    val LOCATION_NONE = LocationTrackingMode.None
    val LOCATION_NO_FOLLOW = LocationTrackingMode.NoFollow
    val LOCATION_FOLLOW = LocationTrackingMode.Follow
    val LOCATION_FACE = LocationTrackingMode.Face

    /**
     * 카메라 이동
     *
     * @param cameraPosition : required
     * @param cameraZoom
     * @param animation
     * @param duration
     *
     * [CameraAnimation]
     * CameraAnimation.Linear : 일정한 속도
     * CameraAnimation.Easing : 점점 느려지기
     * CameraAnimation.Fly : 화면 축소 후 이동
     */
    fun NaverMap.move(
        cameraPosition: LatLng,
        cameraZoom: Double = 15.0,
        animation: CameraAnimation = CameraAnimation.Linear,
        duration: Long = 1500
    ) {
        this.moveCamera(
            CameraUpdate.toCameraPosition(CameraPosition(cameraPosition, cameraZoom))
                .animate(animation, duration)
        )
    }

    /**
     * 바운더리가 보이는 영역으로 카메라 이동
     *
     * @param context : required
     * @param bounds  : required
     * @param animation
     * @param duration
     */
    fun NaverMap.moveFitBounds(
        context: Context,
        bounds: LatLngBounds,
        animation: CameraAnimation = CameraAnimation.Linear,
        duration: Long = 1000
    ) {
        val padding = context.resources.getDimensionPixelSize(R.dimen.fit_bounds_padding)
        this.moveCamera(
            CameraUpdate.fitBounds(bounds, padding).animate(animation, duration)
        )
    }

    /**
     * 경로 그리기
     * TODO : 스타일링 관련 파라미터 추가
     * @param context  : required
     * @param naverMap : required
     * @param coords   : required
     * @param pathColor
     */
    fun createPath(
        context: Context,
        naverMap: NaverMap,
        coords: List<LatLng>,
        pathColor: Int = Color.BLACK
    ) = PathOverlay().also {
        it.coords = coords
        it.width = context.resources.getDimensionPixelSize(R.dimen.path_overlay_width)
        it.outlineWidth = 0
        it.color = pathColor
        it.minZoom = 12.0
        it.patternImage = OverlayImage.fromResource(R.drawable.ic_navi_pattern)
        it.patternInterval = context.resources.getDimensionPixelSize(R.dimen.overlay_pattern_interval)
        it.map = naverMap
    }

    fun createMultiPartPath(
        context: Context,
        naverMap: NaverMap,
        coords: List<List<LatLng>>,
        pathColor: List<MultipartPathOverlay.ColorPart>
    ) = MultipartPathOverlay().also {
        it.coordParts = coords
        it.width = context.resources.getDimensionPixelSize(R.dimen.path_overlay_width)
        it.outlineWidth = 0
        it.colorParts = pathColor
        it.minZoom = 12.0
        it.patternImage = OverlayImage.fromResource(R.drawable.ic_navi_pattern)
        it.patternInterval = context.resources.getDimensionPixelSize(R.dimen.overlay_pattern_interval)
        it.map = naverMap
    }

    /**
     * 마커
     *
     * @param naverMap : required
     * @param pos      : required
     * @param markerIcon : OverlayImage 혹은 MarkerIcons.{COLOR}
     * @param caption
     * @param subCaption
     * @param infoTag : 말풍선 텍스트
     * @param onClick
     */
    fun createMarker(
        naverMap: NaverMap,
        pos: LatLng,
        zoom: Double = NaverMap.MINIMUM_ZOOM.toDouble(),
        markerIcon: OverlayImage? = null,
        caption: String? = null,
        subCaption: String? = null,
        infoTag: String? = null,
        onClick: (Marker) -> Unit = {}
    ) = Marker().apply {
        position = pos
        icon = markerIcon ?: MarkerIcons.BLACK
        minZoom = zoom
        captionTextSize = 14f
        captionText = caption ?: ""
        captionMinZoom = 12.0
        subCaptionTextSize = 10f
        subCaptionColor = Color.GRAY
        subCaptionText = subCaption ?: ""
        subCaptionMinZoom = 13.0
        setOnClickListener {
            onClick(this)
            true
        }
        tag = infoTag
        map = naverMap
    }

    /**
     * 말풍선
     */
    fun createInfoWindow(context: Context) = InfoWindow().apply {
        adapter = object : InfoWindow.DefaultTextAdapter(context) {
            override fun getText(info: InfoWindow): CharSequence {
                val marker = info.marker
                return if (marker != null) info.marker?.tag.toString() else ""
            }
        }
        // 다시 눌러서 닫기
        setOnClickListener {
            close()
            true
        }
    }

    /**
     * 커스텀 클러스터링 키
     *
     * @property id
     * @property latLng
     */
    class ItemKey(val id: Int, private val latLng: LatLng) : ClusteringKey {
        override fun getPosition() = latLng

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || javaClass != other.javaClass) return false
            val itemKey = other as ItemKey
            return id == itemKey.id
        }

        override fun hashCode() = id
    }

    /**
     * 클러스터링 생성
     */
    fun buildClustering() = ComplexBuilder<ItemKey>()
        .minClusteringZoom(10) // 최소 줌레벨
        .maxClusteringZoom(16) // 최대 줌레벨
//        .maxScreenDistance(200.0) // 클러스터링 최대 화면 거리 TODO: 추후 추가
//        .thresholdStrategy { zoom ->
//            if (zoom <= 10) { // 줌 레벨에 따른 노드 간의 거리 TODO: 추후 추가
//                0.0
//            } else {
//                70.0
//            }
//        }
        .distanceStrategy(object : DistanceStrategy {
            private val defaultDistanceStrategy = DefaultDistanceStrategy()

            override fun getDistance(zoom: Int, node1: Node, node2: Node): Double {
                /** 줌 레벨에 따라 세밀하게 보여짐 */
                return if (zoom <= 10) {
                    -1.0
                } else if (zoom <= 13) {
                    defaultDistanceStrategy.getDistance(zoom, node1, node2)
                } else {
                    10000.0
                }
            }
        })
        /** 태그에 따른 병합 TODO: 추후 추가 */
//        .tagMergeStrategy { cluster ->
//            ItemData("", (cluster.children.first().tag as ItemData).gu)
//        }
        /** 마커 공통 속성 지정 */
        .markerManager(object : DefaultMarkerManager() {
            override fun createMarker() = super.createMarker().apply {
                anchor = DefaultClusterMarkerUpdater.DEFAULT_CLUSTER_ANCHOR
                captionColor = Color.WHITE
                captionHaloColor = Color.TRANSPARENT
                setCaptionAligns(Align.Center)
            }
        })
        /** 클러스터 된 데이터 표시 */
        .clusterMarkerUpdater { info, marker ->
            val size = info.size
            marker.icon = when {
                info.minZoom <= 10 -> MarkerIcons.CLUSTER_HIGH_DENSITY
                size < 4 -> MarkerIcons.CLUSTER_LOW_DENSITY
                else -> MarkerIcons.CLUSTER_MEDIUM_DENSITY
            }
            marker.captionText = size.toString() // TODO : 캡션에 차량 개수 표시
            marker.onClickListener = DefaultClusterOnClickListener(info)
        }
        /** 단일 데이터 표시 */
        .leafMarkerUpdater { info, marker ->
            marker.icon = OverlayImage.fromResource(R.drawable.bg_pin)
            marker.captionText = "1" // TODO : 캡션에 차량 개수 표시
            marker.onClickListener = null
        }
        .build()
}