package k_spot.jnm.k_spot

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import k_spot.jnm.k_spot.Get.ChannelSearchResultData
import k_spot.jnm.k_spot.Get.GetSearchResultResponse
import k_spot.jnm.k_spot.Get.PlaceSearchResultData
import k_spot.jnm.k_spot.Network.ApplicationController
import k_spot.jnm.k_spot.Network.NetworkService
import k_spot.jnm.k_spot.adapter.SearchResultActBroadRecyclerAdapter
import k_spot.jnm.k_spot.adapter.SearchResultActSpotRecyclerAdapter
import k_spot.jnm.k_spot.db.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_search_result.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultActivity : AppCompatActivity() {

    lateinit var searchBroadItems: ArrayList<ChannelSearchResultData>
    lateinit var searchSpotItems: ArrayList<PlaceSearchResultData>
    lateinit var searchEventItems: ArrayList<PlaceSearchResultData>
    lateinit var searchResultActBroadRecyclerAdapter: SearchResultActBroadRecyclerAdapter
    lateinit var searchResultActSpotRecyclerAdapter: SearchResultActSpotRecyclerAdapter
    lateinit var networkService: NetworkService



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        var keyword = intent.getStringExtra("keyword")
        getSearchResult(keyword)
        setStatusBarTransparent()
    }

    fun getSearchResult(keyword : String) {
        var keyword : String = keyword
        networkService = ApplicationController.instance.networkService
        val authorization: String = SharedPreferenceController.getAuthorization(context = applicationContext)
        Log.v("123123", authorization)
        val getSearchResultResponse = networkService.getSearchResult(1, authorization, keyword)
        getSearchResultResponse.enqueue(object : Callback<GetSearchResultResponse> {
            override fun onFailure(call: Call<GetSearchResultResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetSearchResultResponse>?, response: Response<GetSearchResultResponse>?) {
                if (response!!.isSuccessful) {

                    searchBroadItems = ArrayList()
                    searchSpotItems = ArrayList()
                    searchEventItems = ArrayList()



                    searchBroadItems = response!!.body()!!.data!!.channel

                    searchSpotItems = response!!.body()!!.data!!.place
                    searchEventItems = response!!.body()!!.data!!.event

                    if(searchBroadItems.size != 0){
                        // 첫 번째 CardView 생성 function
                        if(searchBroadItems.size == 1){
                            makeRecyclerView(searchBroadItems,1)
                        }else if(searchBroadItems.size >= 2){
                            makeRecyclerView(searchBroadItems,2)
                        }

                    }
                    if(searchSpotItems.size != 0){
                        if(searchSpotItems.size == 1){
                            // 첫 번째 CardView 생성 function
                            makeRecyclerView1(searchSpotItems,1)
                        }else if(searchSpotItems.size == 2){
                            makeRecyclerView1(searchSpotItems,2)
                        }else if(searchSpotItems.size == 3){
                            makeRecyclerView1(searchSpotItems,3)
                        }else if(searchSpotItems.size >= 4){
                            makeRecyclerView1(searchSpotItems,4)
                        }

                    }
                    if(searchEventItems.size != 0){
                        if(searchEventItems.size == 1){
                            // 첫 번째 CardView 생성 function
                            makeRecyclerView1(searchEventItems,1)
                        }else if(searchEventItems.size == 2){
                            makeRecyclerView1(searchEventItems,2)
                        }else if(searchEventItems.size == 3){
                            makeRecyclerView1(searchEventItems,3)
                        }else if(searchEventItems.size >= 4){
                            makeRecyclerView1(searchEventItems,4)
                        }
                    }
                }
            }

        })
    }


    private fun makeRecyclerView(searchBroadItems : ArrayList<ChannelSearchResultData>, itemCount: Int) {
        searchResultActBroadRecyclerAdapter = SearchResultActBroadRecyclerAdapter(searchBroadItems, applicationContext,itemCount)
        search_result_act_celeb_borad_rv.layoutManager = LinearLayoutManager(applicationContext)
        search_result_act_celeb_borad_rv.adapter = searchResultActBroadRecyclerAdapter
    }

    private fun makeRecyclerView1(searchSpotItems: ArrayList<PlaceSearchResultData>,itemCount: Int) {

        searchResultActSpotRecyclerAdapter = SearchResultActSpotRecyclerAdapter(searchSpotItems, applicationContext,itemCount)
        search_result_act_spot_rv.layoutManager = LinearLayoutManager(applicationContext)
        search_result_act_spot_rv.adapter = searchResultActSpotRecyclerAdapter
    }

    private fun makeRecyclerView2(searchEventItems: ArrayList<PlaceSearchResultData>, itemCount: Int) {
        searchResultActSpotRecyclerAdapter = SearchResultActSpotRecyclerAdapter(searchEventItems, applicationContext,itemCount)
        search_result_act_event_rv.layoutManager = LinearLayoutManager(applicationContext)
        search_result_act_event_rv.adapter = searchResultActSpotRecyclerAdapter
    }

    // 상태바 투명하게 하는 함수
    private fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
//            DrawableCompat.setTint(, "#757575")
        }
        // 밑에 두줄 아이콘 회색으로 바꾸는 코드
        val view: View? = window.decorView
        view!!.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }

    // 상태바 투명하게 하는 함수
    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    // 상단바 밑으로 뷰 보이게하는 코드
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val window = window
        val decorView = window.decorView
        if (Configuration.ORIENTATION_LANDSCAPE === newConfig.orientation) {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = Color.parseColor("#55000000") // set dark color, the icon will auto change light
            }
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = Color.parseColor("#fffafafa")
            }
        }
    }
}
