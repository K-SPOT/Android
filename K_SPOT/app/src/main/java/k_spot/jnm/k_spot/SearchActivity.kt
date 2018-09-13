package k_spot.jnm.k_spot

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.WindowManager
import k_spot.jnm.k_spot.adapter.SearchActTagRecyclerAdapter
import k_spot.jnm.k_spot.adapter.SearchActivityHashTagData

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val celebRecyclerView : RecyclerView = findViewById(R.id.search_act_search_recommend_celeb_rv)
        makeRecommededHashTag(celebRecyclerView)

        val broadcastRecyclerView : RecyclerView = findViewById(R.id.search_act_search_recommend_broadcast_rv)
        makeRecommededHashTag(broadcastRecyclerView)

        val eventRecyclerView : RecyclerView = findViewById(R.id.search_act_search_recommend_event_rv)
        makeRecommededHashTag(eventRecyclerView)

        setStatusBarTransparent()


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

    private fun makeRecommededHashTag(recyclerView: RecyclerView) {
        val mRecyclerView = recyclerView
//        val mRecyclerView = view.findViewById(R.id.main_page_fragment_rv1) as RecyclerView
        mRecyclerView.setHasFixedSize(true)

        val mLayoutManager = GridLayoutManager(applicationContext, 1, GridLayoutManager.HORIZONTAL, false)

        mRecyclerView.layoutManager = mLayoutManager

        var myDataset = ArrayList<SearchActivityHashTagData>()

        myDataset.add(SearchActivityHashTagData("블랙핑크"))
        myDataset.add(SearchActivityHashTagData("블랙핑크"))
        myDataset.add(SearchActivityHashTagData("블랙핑크"))
        myDataset.add(SearchActivityHashTagData("블랙핑크"))
        myDataset.add(SearchActivityHashTagData("블랙핑크"))
        myDataset.add(SearchActivityHashTagData("블랙핑크"))
        myDataset.add(SearchActivityHashTagData("블랙핑크"))


        val mAdapter = SearchActTagRecyclerAdapter(applicationContext, myDataset)
        mRecyclerView.adapter = mAdapter
    }
}
