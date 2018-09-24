package k_spot.jnm.k_spot.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
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
import k_spot.jnm.k_spot.Get.GetMapPageDataFromGPSResponse
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
        FilterOptionData(1.0, 0.0, 0.0, 1, 1, 1, 1, 1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map_page, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerViewAdapter()

        setClickListener()
        setFillterBtnVisible()

        if (!checkPermissions()) {
            startLocationPermissionRequest()
        } else {
            getLastLocation()
        }

        btn_map_page_my_spot.setOnClickListener {

            if (!checkPermissions()) {
                startLocationPermissionRequest()
            } else {
                getLastLocation()
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
//        if (!checkPermissions()) {
//            startLocationPermissionRequest()
//        } else {
//            getLastLocation()
//        }
    }

    //시간나면 애니메이션 처리
    private fun setFillterBtnVisible(){
        ns_map_page_scroll.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (oldScrollY < scrollY) {
                btn_map_page_open_filtering.visibility = View.INVISIBLE
            } else {
                btn_map_page_open_filtering.visibility = View.VISIBLE
            }
        }
    }

    private fun getLastLocation() {
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        //로딩돌리기

        if (isGPSEnabled || isNetworkEnabled) {
            if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                toast("위치 탐색 중...")
                requestSpotDataFromGPS()
                fusedLocationClientInfoStatus.lastLocation.addOnCompleteListener {
                    if (it.isSuccessful && it.result != null) {
                        //gps 비동기 처리
                        requestSpotDataFromGPS()
                    }
                }
            }
        } else {
            requestSpotDataFromGPS()
            toast("GPS를 체크해주세요.")
        }
    }

    fun setClickListener() {
        btn_map_page_close_filtering.setOnClickListener {
            (activity as MainActivity).showBottomPageTab()
            ll_map_page_filtering.visibility = View.GONE
            btn_map_page_close_filtering.visibility = View.GONE
        }

        btn_map_page_open_filtering.setOnClickListener {
            (activity as MainActivity).hideBottomPageTab()
            btn_map_page_close_filtering.bringToFront()
            ll_map_page_filtering.visibility = View.VISIBLE
            btn_map_page_close_filtering.visibility = View.VISIBLE
        }
    }

    private fun requestSpotDataFromGPS() {
        val networkService = ApplicationController.instance.networkService
        val getMapPageDataFromGPSResponse = networkService.getMapPageDataFromGPSResponse(0, SharedPreferenceController.getAuthorization(context!!),
                filterOption.distance, 36.3651452368083, 127.445744408671, filterOption.is_food, filterOption.is_cafe, filterOption.is_sights, filterOption.is_event, filterOption.is_etc)
        getMapPageDataFromGPSResponse.enqueue(object : Callback<GetMapPageDataFromGPSResponse> {
            override fun onFailure(call: Call<GetMapPageDataFromGPSResponse>?, t: Throwable?) {
                Log.e("위치 기반 통신 실패", t.toString())
            }

            override fun onResponse(call: Call<GetMapPageDataFromGPSResponse>?, response: Response<GetMapPageDataFromGPSResponse>?) {
                response?.let {
                    if (response.isSuccessful) {
                        spotDataListFromGPS.addAll(response.body()!!.data)
                        Log.e("위치 기반 통신 데이터들", spotDataListFromGPS.toString())
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

}


//if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//    fusedLocationClientInfoStatus.lastLocation
//            .addOnCompleteListener {
//                if (it.isSuccessful){
//                    toast("성공!!!")
//                }
//            }
//}