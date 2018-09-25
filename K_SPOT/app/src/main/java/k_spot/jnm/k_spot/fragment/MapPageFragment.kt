package k_spot.jnm.k_spot.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import k_spot.jnm.k_spot.Get.GetMapPageSpotDataResponse
import k_spot.jnm.k_spot.Get.MapPageSpotData
import k_spot.jnm.k_spot.Network.ApplicationController
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.activity.MainActivity
import k_spot.jnm.k_spot.adapter.MapPageRecyclerViewAdapter
import k_spot.jnm.k_spot.data.FilterOptionData
import k_spot.jnm.k_spot.db.SharedPreferenceController
import kotlinx.android.synthetic.main.fragment_map_page.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapPageFragment : Fragment() {

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 7778
    private val locationManager: LocationManager by lazy {
        context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    private val mapPageRecyclerViewAdapter: MapPageRecyclerViewAdapter by lazy {
        MapPageRecyclerViewAdapter(context!!, spotDataListFromGPS)
    }
    private val spotDataListFromGPS: ArrayList<MapPageSpotData> by lazy {
        ArrayList<MapPageSpotData>()
    }

    private val fusedLocationClientInfoStatus: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context!!)
    }

    private val filterOption: FilterOptionData by lazy {
        FilterOptionData(0, 1.0, 0.0, 0.0, 1, 1, 1, 1, 1)
    }
    private val filterOptionTemp: FilterOptionData by lazy {
        FilterOptionData(0, 1.0, 0.0, 0.0, 1, 1, 1, 1, 1)
    }

    private val distanceArrayList: ArrayList<Double> by lazy {
        arrayListOf<Double>(1.0, 0.5, 0.3)
    }
    private var currentDistanceOptionIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map_page, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerViewAdapter()

        setFilterBtnVisible()
        setFilterOption()
        filterOptionListener()

        setMapAddressGuClickListener()

        btn_map_page_my_spot.setOnClickListener {
            if (!checkPermissions()) {
                startLocationPermissionRequest()
            } else {
                getLastLocation()
            }
        }

        if (!checkPermissions()) {
            startLocationPermissionRequest()
        } else {
            getLastLocation()
        }
    }




    //시간나면 애니메이션 처리
    private fun setFilterBtnVisible() {
        ns_map_page_scroll.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (oldScrollY < scrollY) {
                btn_map_page_open_filtering.visibility = View.INVISIBLE
            } else {
                btn_map_page_open_filtering.visibility = View.VISIBLE
            }
        }
    }

    private fun setFilterOption() {
        //정렬
        when (filterOption.order_option) {
            0 -> {
                tv_map_page_filter_is_popular.setTextColor(Color.parseColor("#40D39F"))
                tv_map_page_filter_is_new.setTextColor(Color.parseColor("#E0E0E0"))
            }
            1 -> {
                tv_map_page_filter_is_popular.setTextColor(Color.parseColor("#E0E0E0"))
                tv_map_page_filter_is_new.setTextColor(Color.parseColor("#40D39F"))
            }
        }
        //거리
        when (filterOption.distance) {
            1.0 -> tv_map_page_filter_distance.text = "1.0Km까지 설정"
            0.5 -> tv_map_page_filter_distance.text = "0.5Km까지 설정"
            else -> tv_map_page_filter_distance.text = "0.3Km까지 설정"
        }
        //레스토랑
        when (filterOption.is_food) {
            1 -> {
                btn_map_page_filter_is_restaurant.setImageResource(R.drawable.filter_restaurant_btn_green)
            }
            0 -> {
                btn_map_page_filter_is_restaurant.setImageResource(R.drawable.filter_restaurant_btn_gray)
            }
        }
        //카페
        when (filterOption.is_cafe) {
            1 -> {
                btn_map_page_filter_is_cafe.setImageResource(R.drawable.filter_cafe_btn_green)
            }
            0 -> {
                btn_map_page_filter_is_cafe.setImageResource(R.drawable.filter_cafe_btn_gray)
            }
        }
        //핫 플레이스
        when (filterOption.is_sights) {
            1 -> {
                btn_map_page_filter_is_hot_place.setImageResource(R.drawable.filter_hotplace_btn_green)
            }
            0 -> {
                btn_map_page_filter_is_hot_place.setImageResource(R.drawable.filter_hotplace_btn_gray)
            }
        }
        //이벤트
        when (filterOption.is_event) {
            1 -> {
                btn_map_page_filter_is_event.setImageResource(R.drawable.filter_event_btn_green)
            }
            0 -> {
                btn_map_page_filter_is_event.setImageResource(R.drawable.filter_event_btn_gray)
            }
        }
        when (filterOption.is_etc) {
            1 -> {
                btn_map_page_filter_is_etc.setImageResource(R.drawable.filter_etc_btn_green)
            }
            0 -> {
                btn_map_page_filter_is_etc.setImageResource(R.drawable.filter_etc_btn_gray)
            }
        }
    }

    private fun filterOptionListener() {
        btn_map_page_filter_distance_left.setOnClickListener {
            if (currentDistanceOptionIndex == 2) {
                currentDistanceOptionIndex = 0
            } else {
                currentDistanceOptionIndex++
            }
            tv_map_page_filter_distance.text = "${distanceArrayList[currentDistanceOptionIndex]}Km까지 설정"
            filterOptionTemp.distance = distanceArrayList[currentDistanceOptionIndex]
        }
        btn_map_page_filter_distance_right.setOnClickListener {
            if (currentDistanceOptionIndex == 0) {
                currentDistanceOptionIndex = 2
            } else {
                currentDistanceOptionIndex--
            }
            tv_map_page_filter_distance.text = "${distanceArrayList[currentDistanceOptionIndex]}Km까지 설정"
            filterOptionTemp.distance = distanceArrayList[currentDistanceOptionIndex]
        }


        btn_map_page_filter_is_popular.setOnClickListener {
            filterOptionTemp.order_option = 0
            tv_map_page_filter_is_popular.setTextColor(Color.parseColor("#40D39F"))
            tv_map_page_filter_is_new.setTextColor(Color.parseColor("#E0E0E0"))
        }
        btn_map_page_filter_is_new.setOnClickListener {
            filterOptionTemp.order_option = 1
            tv_map_page_filter_is_popular.setTextColor(Color.parseColor("#E0E0E0"))
            tv_map_page_filter_is_new.setTextColor(Color.parseColor("#40D39F"))
        }

        btn_map_page_filter_is_restaurant.setOnClickListener {
            if (filterOptionTemp.is_food == 1) {
                btn_map_page_filter_is_restaurant.setImageResource(R.drawable.filter_restaurant_btn_gray)
                filterOptionTemp.is_food = 0
            } else {
                btn_map_page_filter_is_restaurant.setImageResource(R.drawable.filter_restaurant_btn_green)
                filterOptionTemp.is_food = 1
            }
        }

        btn_map_page_filter_is_cafe.setOnClickListener {
            if (filterOptionTemp.is_cafe == 1) {
                btn_map_page_filter_is_cafe.setImageResource(R.drawable.filter_cafe_btn_gray)
                filterOptionTemp.is_cafe = 0
            } else {
                btn_map_page_filter_is_cafe.setImageResource(R.drawable.filter_cafe_btn_green)
                filterOptionTemp.is_cafe = 1
            }
        }

        btn_map_page_filter_is_hot_place.setOnClickListener {
            if (filterOptionTemp.is_sights == 1) {
                btn_map_page_filter_is_hot_place.setImageResource(R.drawable.filter_hotplace_btn_gray)
                filterOptionTemp.is_sights = 0
            } else {
                btn_map_page_filter_is_hot_place.setImageResource(R.drawable.filter_hotplace_btn_green)
                filterOptionTemp.is_sights = 1
            }
        }

        btn_map_page_filter_is_event.setOnClickListener {
            if (filterOptionTemp.is_event == 1) {
                btn_map_page_filter_is_event.setImageResource(R.drawable.filter_event_btn_gray)
                filterOptionTemp.is_event = 0
            } else {
                btn_map_page_filter_is_event.setImageResource(R.drawable.filter_event_btn_green)
                filterOptionTemp.is_event = 1
            }
        }

        btn_map_page_filter_is_etc.setOnClickListener {
            if (filterOptionTemp.is_etc == 1) {
                btn_map_page_filter_is_etc.setImageResource(R.drawable.filter_etc_btn_gray)
                filterOptionTemp.is_etc = 0
            } else {
                btn_map_page_filter_is_etc.setImageResource(R.drawable.filter_etc_btn_green)
                filterOptionTemp.is_etc = 1
            }
        }

        btn_map_page_filter_apply_btn.setOnClickListener {
            filterOption.run {
                order_option = filterOptionTemp.order_option
                distance = filterOptionTemp.distance
                is_food = filterOptionTemp.is_food
                is_cafe = filterOptionTemp.is_cafe
                is_sights = filterOptionTemp.is_sights
                is_event = filterOptionTemp.is_event
                is_etc = filterOptionTemp.is_etc
            }

            btn_map_page_close_filtering.callOnClick()

            toast("필터가 적용되었습니다.")
        }

        btn_map_page_close_filtering.setOnClickListener {
            filterOptionTemp.run {
                order_option = filterOption.order_option
                distance = filterOption.distance
                is_food = filterOption.is_food
                is_cafe = filterOption.is_cafe
                is_sights = filterOption.is_sights
                is_event = filterOption.is_event
                is_etc = filterOption.is_etc
            }

            when (filterOption.distance) {
                1.0 -> currentDistanceOptionIndex = 0
                0.5 -> currentDistanceOptionIndex = 1
                0.3 -> currentDistanceOptionIndex = 2
            }

            setFilterOption()
            rl_map_page_open_filtering_backgound.visibility = View.GONE
            (activity as MainActivity).showBottomPageTab()
            ll_map_page_filtering.visibility = View.GONE
            btn_map_page_close_filtering.visibility = View.GONE
        }

        btn_map_page_open_filtering.setOnClickListener {
            (activity as MainActivity).hideBottomPageTab()
            rl_map_page_open_filtering_backgound.visibility = View.VISIBLE
            ll_map_page_filtering.visibility = View.VISIBLE
            btn_map_page_close_filtering.visibility = View.VISIBLE
        }

    }


    private fun getLastLocation() {
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        //로딩돌리기

        if (isGPSEnabled || isNetworkEnabled) {
            if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                toast("위치 탐색 중...")
                fusedLocationClientInfoStatus.lastLocation.addOnCompleteListener {
                    if (it.isSuccessful && it.result != null) {
                        filterOption.latitude = it.result.latitude
                        filterOption.longitude = it.result.longitude
                        requestSpotDataFromGPS()
                    }
                }
            }
        } else {
            toast("GPS를 체크해주세요.")
        }
    }

    private fun requestSpotDataFromSpot(address_gu: String) {
        val networkService = ApplicationController.instance.networkService
        val getMapPageResponseFromSpot = networkService.getMapPageResponseFromSpot(0, SharedPreferenceController.getAuthorization(context!!),
                address_gu, filterOption.order_option, filterOption.is_food, filterOption.is_cafe, filterOption.is_sights, filterOption.is_event, filterOption.is_etc)
        getMapPageResponseFromSpot.enqueue(object : Callback<GetMapPageSpotDataResponse> {
            override fun onFailure(call: Call<GetMapPageSpotDataResponse>?, t: Throwable?) {
                Log.e("맵 기반 통신 실패", t.toString())
            }

            override fun onResponse(call: Call<GetMapPageSpotDataResponse>?, responseSpot: Response<GetMapPageSpotDataResponse>?) {
                responseSpot?.let {
                    if (responseSpot.isSuccessful) {
                        if (spotDataListFromGPS.isNotEmpty()) {
                            spotDataListFromGPS.clear()
                            //(rv_map_page_my_around_k_spot.adapter as MapPageRecyclerViewAdapter).clearDataList()
                            spotDataListFromGPS.addAll(responseSpot.body()!!.data)
                        } else {
                            spotDataListFromGPS.addAll(responseSpot.body()!!.data)
                        }
                        setRecyclerViewAdapter()
                    }
                }
            }
        })

    }


    private fun requestSpotDataFromGPS() {
        val networkService = ApplicationController.instance.networkService
        val getMapPageDataFromGPSResponse = networkService.getMapPageDataFromGPSResponse(0, SharedPreferenceController.getAuthorization(context!!),
                filterOption.distance, filterOption.latitude, filterOption.longitude, filterOption.is_food, filterOption.is_cafe, filterOption.is_sights, filterOption.is_event, filterOption.is_etc)
        getMapPageDataFromGPSResponse.enqueue(object : Callback<GetMapPageSpotDataResponse> {
            override fun onFailure(call: Call<GetMapPageSpotDataResponse>?, t: Throwable?) {
                Log.e("위치 기반 통신 실패", t.toString())
            }

            override fun onResponse(call: Call<GetMapPageSpotDataResponse>?, responseSpot: Response<GetMapPageSpotDataResponse>?) {
                responseSpot?.let {
                    if (responseSpot.isSuccessful) {
                        if (spotDataListFromGPS.isNotEmpty()) {
                            spotDataListFromGPS.clear()
                            //(rv_map_page_my_around_k_spot.adapter as MapPageRecyclerViewAdapter).clearDataList()
                            spotDataListFromGPS.addAll(responseSpot.body()!!.data)
                        } else {
                            spotDataListFromGPS.addAll(responseSpot.body()!!.data)
                        }
                        setRecyclerViewAdapter()
                    }
                }
            }
        })

    }

    private fun setRecyclerViewAdapter() {
        rv_map_page_my_around_k_spot.layoutManager = LinearLayoutManager(context)
        rv_map_page_my_around_k_spot.adapter = mapPageRecyclerViewAdapter
    }

    private fun checkPermissions() =
            ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED


    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> getLastLocation()
                else -> toast("권한 요청 실패")
            }
        }

    }

    private fun setMapAddressGuClickListener() {
        btn_map_page_spot_gangseo.setOnClickListener {
            requestSpotDataFromSpot("강서구")
        }
        btn_map_page_spot_yangcheon.setOnClickListener {
            requestSpotDataFromSpot("양천구")
        }
        btn_map_page_spot_gulo.setOnClickListener {
            requestSpotDataFromSpot("구로구")
        }
        btn_map_page_spot_yeongdeungpo.setOnClickListener {
            requestSpotDataFromSpot("영등포구")
        }
        btn_map_page_spot_geumcheon.setOnClickListener {
            requestSpotDataFromSpot("금천구")
        }
        btn_map_page_spot_dongjag.setOnClickListener {
            requestSpotDataFromSpot("동작구")
        }
        btn_map_page_spot_seocho.setOnClickListener {
            requestSpotDataFromSpot("서초구")
        }
        btn_map_page_spot_gangnam.setOnClickListener {
            requestSpotDataFromSpot("강남구")
        }
        btn_map_page_spot_songpa.setOnClickListener {
            requestSpotDataFromSpot("송파구")
        }
        btn_map_page_spot_gangdong.setOnClickListener {
            requestSpotDataFromSpot("강동구")
        }
        btn_map_page_spot_mapo.setOnClickListener {
            requestSpotDataFromSpot("마포구")
        }
        btn_map_page_spot_yongsan.setOnClickListener {
            requestSpotDataFromSpot("용산구")
        }
        btn_map_page_spot_seongdong.setOnClickListener {
            requestSpotDataFromSpot("성동구")
        }
        btn_map_page_spot_gwangjin.setOnClickListener {
            requestSpotDataFromSpot("광진구")
        }
        btn_map_page_spot_junggu.setOnClickListener {
            requestSpotDataFromSpot("중구")
        }
        btn_map_page_spot_seodaemun.setOnClickListener {
            requestSpotDataFromSpot("서대문구")
        }
        btn_map_page_spot_eunpyeong.setOnClickListener {
            requestSpotDataFromSpot("은평구")
        }
        btn_map_page_spot_jonglo.setOnClickListener {
            requestSpotDataFromSpot("종로구")
        }
        btn_map_page_spot_seongbug.setOnClickListener {
            requestSpotDataFromSpot("성북구")
        }
        btn_map_page_spot_dongdaemun.setOnClickListener {
            requestSpotDataFromSpot("동대문구")
        }
        btn_map_page_spot_junglang.setOnClickListener {
            requestSpotDataFromSpot("중랑구")
        }
        btn_map_page_spot_gangbug.setOnClickListener {
            requestSpotDataFromSpot("강북구")
        }
        btn_map_page_spot_dobong.setOnClickListener {
            requestSpotDataFromSpot("도봉구")
        }
        btn_map_page_spot_nowon.setOnClickListener {
            requestSpotDataFromSpot("노원구")
        }
        btn_map_page_spot_gwanag.setOnClickListener {
            requestSpotDataFromSpot("관악구")
        }
    }
}


//if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//    fusedLocationClientInfoStatus.lastLocation
//            .addOnCompleteListener {
//                if (it.isSuccessful){
//                    toast("성공!!!")
//                }
//            }
//}