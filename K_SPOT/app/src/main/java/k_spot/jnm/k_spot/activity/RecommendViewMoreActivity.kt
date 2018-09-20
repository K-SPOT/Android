package k_spot.jnm.k_spot.activity

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import k_spot.jnm.k_spot.Get.GetThemeDetailResponse
import k_spot.jnm.k_spot.Get.ThemeDetailThemeContentData
import k_spot.jnm.k_spot.Network.ApplicationController
import k_spot.jnm.k_spot.Network.NetworkService
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.adapter.RecommendViewMoreRecyclerAdapter
import k_spot.jnm.k_spot.db.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_recommend_view_more.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class RecommendViewMoreActivity : AppCompatActivity() {

    lateinit var networkService : NetworkService
    lateinit var themeDetailRecyclerViewItem: ArrayList<ThemeDetailThemeContentData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend_view_more)

        setStatusBarTransparent()

        getThemeDetailResponse()

        setListener()
    }

    private fun makeRecyclerView(themeDetailRecyclerViewItem: ArrayList<ThemeDetailThemeContentData>) {
        val mRecyclerView = recommend_view_more_act_rv as RecyclerView
//        val mRecyclerView = view.findViewById(R.id.main_page_fragment_rv1) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView.adapter = RecommendViewMoreRecyclerAdapter(themeDetailRecyclerViewItem, applicationContext)
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

    fun setListener() {
        recommend_view_more_act_scroll_view.setOnScrollChangeListener(object : View.OnScrollChangeListener {
            override fun onScrollChange(v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                // 맨 위 스크롤이 아닐 때
                if (!(scrollY <= 0)) {
                    window.statusBarColor = Color.WHITE
                    recommend_view_more_act_top_bar_rl.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    recommend_view_more_act_back_iv.setColorFilter(Color.parseColor("#5E5E5E"))
                    recommend_view_more_act_scrap_iv.setImageResource(R.drawable.category_scrab_btn_gray)
                    recommend_view_more_act_scrap_num_tv.setTextColor(Color.parseColor("#5E5E5E"))
                    recommend_view_more_act_top_bar_bottom_line.visibility = View.VISIBLE
                } else {
                    window.statusBarColor = Color.TRANSPARENT
                    recommend_view_more_act_top_bar_rl.setBackgroundColor(Color.parseColor("#00000000"))
                    recommend_view_more_act_back_iv.setColorFilter(Color.parseColor("#FFFFFF"))
                    recommend_view_more_act_scrap_iv.setImageResource(R.drawable.category_scrap_btn)
                    recommend_view_more_act_scrap_num_tv.setTextColor(Color.parseColor("#FFFFFF"))
                    recommend_view_more_act_top_bar_bottom_line.visibility = View.GONE
                }
            }
        })
    }

    fun getThemeDetailResponse() {
        // 통신
        networkService = ApplicationController.instance.networkService
        val authorization: String = SharedPreferenceController.getAuthorization(context = applicationContext)
        val getThemeDetailResponse = networkService.getThemeDetail(0, authorization, 6

        )
        getThemeDetailResponse.enqueue(object : Callback<GetThemeDetailResponse> {
            override fun onFailure(call: Call<GetThemeDetailResponse>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<GetThemeDetailResponse>?, response: Response<GetThemeDetailResponse>?) {
                if (response!!.isSuccessful) {

                    themeDetailRecyclerViewItem = ArrayList()

                    Glide.with(applicationContext).load(response!!.body()!!.data!!.theme.img)
                    recommend_view_more_act_title_tv2.text = response!!.body()!!.data!!.theme.title
                    recommend_view_more_act_hash_tag_tv.text = response!!.body()!!.data!!.theme.subtitle

                    themeDetailRecyclerViewItem = response!!.body()!!.data!!.theme_contents
                    makeRecyclerView(themeDetailRecyclerViewItem)
                }
            }

        })

    }
}
