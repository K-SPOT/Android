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
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.adapter.RecommendViewMoreRecyclerAdapter
import k_spot.jnm.k_spot.adapter.RecommendViewMoreRecyclerAdpaterData
import kotlinx.android.synthetic.main.activity_recommend_view_more.*

class RecommendViewMoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend_view_more)

        setStatusBarTransparent()

        makeRecyclerView()

        setListener()
    }

    private fun makeRecyclerView() {
        val mRecyclerView = recommend_view_more_act_rv as RecyclerView
//        val mRecyclerView = view.findViewById(R.id.main_page_fragment_rv1) as RecyclerView
        mRecyclerView.setHasFixedSize(true)

        var ImageSet0 = ArrayList<Int>()

        ImageSet0.add(R.drawable.category_list_blackpink_img)
        ImageSet0.add(R.drawable.category_list_exo_img)
        ImageSet0.add(R.drawable.category_list_bts_img)

        var ImageSet1 = ArrayList<Int>()

        ImageSet1.add(R.drawable.category_list_exo_img)
        ImageSet1.add(R.drawable.category_list_bts_img)

        var ImageSet2 = ArrayList<Int>()

        ImageSet2.add(R.drawable.category_list_bts_img)

        var RecommendViewMoreDataSet = ArrayList<RecommendViewMoreRecyclerAdpaterData>()

        RecommendViewMoreDataSet.add(RecommendViewMoreRecyclerAdpaterData("방탄소년단의 힘의 원천 ' 유정식당'\n" +
                "\n",ImageSet0,"유정식당은 방탄 신인 시절 방송에서 추천한 맛집"
                , "토종 흑돼지 전문식당! 방탄소년단 멤버들이 주로 먹었던 흑돼지고추장 오겹살과 돌솥비빔밥이 인기메뉴"
                , "1인은 흑돼지돌솥비빔밥 2인은 쌈밥 추천!\n" + "\n"))

        RecommendViewMoreDataSet.add(RecommendViewMoreRecyclerAdpaterData("방탄소년단이 나서서 홍보해주는 CAFE ' THE MINS '"
                ,ImageSet1,"2AM의 멤버 이창민이 운영하는 카페\n" + "\n"
                , "방탄소년단 멤버들이 자주 방문하는 곳으로도 유명\n" + "\n"
                , "1인은 흑돼지돌솥비빔밥 2인은 쌈밥 추천!\n" + "\n"))

        RecommendViewMoreDataSet.add(RecommendViewMoreRecyclerAdpaterData("방탄소년단의 힘의 원천 ' 유정식당'\n" +
                "\n",ImageSet2,"유정식당은 방탄 신인 시절 방송에서 추천한 맛집"
                , "토종 흑돼지 전문식당! 방탄소년단 멤버들이 주로 먹었던 흑돼지고추장 오겹살과 돌솥비빔밥이 인기메뉴"
                ,"더민스는 생과일 에이드가 유명하며 특히 방탄소년단이 주로 마시는 생체리 에이드와 생레몬 에이드가 인기 메뉴다."))

        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView.adapter = RecommendViewMoreRecyclerAdapter(RecommendViewMoreDataSet, applicationContext)
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
                }else{
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
}
