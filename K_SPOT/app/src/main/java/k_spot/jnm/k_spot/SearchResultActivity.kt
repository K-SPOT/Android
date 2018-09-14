package k_spot.jnm.k_spot

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import k_spot.jnm.k_spot.adapter.SearchResultActBroadRecyclerAdapter
import k_spot.jnm.k_spot.adapter.SearchResultActBroadRecyclerAdapterData
import k_spot.jnm.k_spot.adapter.SearchResultActSpotRecyclerAdapter
import k_spot.jnm.k_spot.adapter.SearchResultActSpotRecyclerAdapterData
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity : AppCompatActivity() {

    lateinit var searchBroadItems: ArrayList<SearchResultActBroadRecyclerAdapterData>
    lateinit var searchSpotItems: ArrayList<SearchResultActSpotRecyclerAdapterData>
    lateinit var searchEventItems: ArrayList<SearchResultActSpotRecyclerAdapterData>
    lateinit var searchResultActBroadRecyclerAdapter: SearchResultActBroadRecyclerAdapter
    lateinit var searchResultActSpotRecyclerAdapter: SearchResultActSpotRecyclerAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        makeRecyclerView()

        makeRecyclerView1()

        makeRecyclerView2()

        setStatusBarTransparent()
    }

    fun makeRecyclerView() {
        searchBroadItems = ArrayList()

        searchBroadItems.add(SearchResultActBroadRecyclerAdapterData(R.drawable.category_list_blackpink_img, "블랙핑크", "20만", "3300만", true))
        searchBroadItems.add(SearchResultActBroadRecyclerAdapterData(R.drawable.category_list_bts_img, "방탄소년단", "10만", "30만", false))
        searchBroadItems.add(SearchResultActBroadRecyclerAdapterData(R.drawable.category_list_exo_img, "엑소", "30만", "30만", true))

        searchResultActBroadRecyclerAdapter = SearchResultActBroadRecyclerAdapter(searchBroadItems, applicationContext)
        search_result_act_celeb_borad_rv.layoutManager = LinearLayoutManager(applicationContext)
        search_result_act_celeb_borad_rv.adapter = searchResultActBroadRecyclerAdapter
    }

    fun makeRecyclerView1() {
        searchSpotItems = ArrayList()

        searchSpotItems.add(SearchResultActSpotRecyclerAdapterData("방탄삼겹살", "용산구 · 숙대입구역",0))
        searchSpotItems.add(SearchResultActSpotRecyclerAdapterData("방탄삼겹살", "용산구 · 숙대입구역",0))
        searchSpotItems.add(SearchResultActSpotRecyclerAdapterData("방탄삼겹살", "용산구 · 숙대입구역",0))
        searchSpotItems.add(SearchResultActSpotRecyclerAdapterData("방탄삼겹살", "용산구 · 숙대입구역",0))


        searchResultActSpotRecyclerAdapter = SearchResultActSpotRecyclerAdapter(searchSpotItems, applicationContext)
        search_result_act_spot_rv.layoutManager = LinearLayoutManager(applicationContext)
        search_result_act_spot_rv.adapter = searchResultActSpotRecyclerAdapter
    }

    fun makeRecyclerView2() {
        searchEventItems = ArrayList()

        searchEventItems.add(SearchResultActSpotRecyclerAdapterData("방탄생일", "용산구 · 숙대입구역",4))
        searchEventItems.add(SearchResultActSpotRecyclerAdapterData("방탄생일", "용산구 · 숙대입구역",4))
        searchEventItems.add(SearchResultActSpotRecyclerAdapterData("방탄생일", "용산구 · 숙대입구역",4))
        searchEventItems.add(SearchResultActSpotRecyclerAdapterData("방탄생일", "용산구 · 숙대입구역",4))
        searchResultActSpotRecyclerAdapter = SearchResultActSpotRecyclerAdapter(searchEventItems, applicationContext)
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
