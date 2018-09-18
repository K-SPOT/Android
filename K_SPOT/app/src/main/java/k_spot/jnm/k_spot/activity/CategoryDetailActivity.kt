package k_spot.jnm.k_spot.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.adapter.RecommendSpotRecyclerViewAdapter
import k_spot.jnm.k_spot.adapter.RelativeSpotRecyclerViewAdapter
import k_spot.jnm.k_spot.data.RecommendSpotData
import k_spot.jnm.k_spot.data.RelativeSpotData
import kotlinx.android.synthetic.main.activity_detailed_entertainer_page.*

class CategoryDetailActivity : AppCompatActivity() {


    val recommendSpotDataList : ArrayList<RecommendSpotData> by lazy {
        ArrayList<RecommendSpotData>()
    }
    val recommendSpotRecyclerViewAdapter : RecommendSpotRecyclerViewAdapter by lazy {
        RecommendSpotRecyclerViewAdapter(this, recommendSpotDataList)
    }
    val relativeSpotDataList : ArrayList<RelativeSpotData> by lazy {
        ArrayList<RelativeSpotData>()
    }
    val relativeSpotRecyclerViewAdapter : RelativeSpotRecyclerViewAdapter by lazy {
        RelativeSpotRecyclerViewAdapter(this, relativeSpotDataList)
    }

    val relativeEventDataList : ArrayList<RelativeSpotData> by lazy {
        ArrayList<RelativeSpotData>()
    }
    val relativeEventRecyclerViewAdapter : RelativeSpotRecyclerViewAdapter by lazy {
        RelativeSpotRecyclerViewAdapter(this, relativeEventDataList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_detailed_entertainer_page)

        hideActionBar()

        setStatusBarColor()

        //통신시 옮기기
        setRecommendSpotRecyclerView()
        setRelativeSpotRecyclerView()
        setRelativeEventRecyclerView()

//        setSupportActionBar(toolbar)
//        supportActionBar!!.run {
//            setDisplayShowCustomEnabled(true)
//            setDisplayShowTitleEnabled(false)
//            setHomeAsUpIndicator(R.drawable.category_back_arrow)
//
//        }

    }

    private fun setRecommendSpotRecyclerView(){
        //데이터 받는곳 별로도 구성하기
        recommendSpotDataList.add(RecommendSpotData("SMTOWN\n코엑스아티움", "", ""))
        recommendSpotDataList.add(RecommendSpotData("야경을 한눈에\n서울 남산타워", "", ""))
        recommendSpotDataList.add(RecommendSpotData("SMTOWN\n코엑스아티움", "", ""))
        recommendSpotDataList.add(RecommendSpotData("야경을 한눈에\n서울 남산타워", "", ""))

        rv_category_detail_recommend_spot_list.layoutManager = LinearLayoutManager(this, 0, false)
        rv_category_detail_recommend_spot_list.adapter = recommendSpotRecyclerViewAdapter
    }

    private fun setRelativeSpotRecyclerView(){
        relativeSpotDataList.add(RelativeSpotData("", "서울남산타워", "서울 야경을 한눈에!", "용산구", "1,020"))
        relativeSpotDataList.add(RelativeSpotData("", "서울남산타워", "서울 야경을 한눈에!", "용산구", "1,020"))
        relativeSpotDataList.add(RelativeSpotData("", "서울남산타워", "서울 야경을 한눈에!", "용산구", "1,020"))
        relativeSpotDataList.add(RelativeSpotData("", "서울남산타워", "서울 야경을 한눈에!", "용산구", "1,020"))

        rv_category_detail_relative_spot_list.layoutManager = LinearLayoutManager(this)
        rv_category_detail_relative_spot_list.adapter = relativeSpotRecyclerViewAdapter
    }
    private fun setRelativeEventRecyclerView(){
        relativeEventDataList.add(RelativeSpotData("", "서울남산타워", "서울 야경을 한눈에!", "용산구", "1,020"))
        relativeEventDataList.add(RelativeSpotData("", "서울남산타워", "서울 야경을 한눈에!", "용산구", "1,020"))
        relativeEventDataList.add(RelativeSpotData("", "서울남산타워", "서울 야경을 한눈에!", "용산구", "1,020"))
        relativeEventDataList.add(RelativeSpotData("", "서울남산타워", "서울 야경을 한눈에!", "용산구", "1,020"))

        rv_category_detail_relative_event_list.layoutManager = LinearLayoutManager(this)
        rv_category_detail_relative_event_list.adapter = relativeEventRecyclerViewAdapter
    }

    private fun setStatusBarColor() {
        val view: View? = window.decorView
        view!!.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)

    }

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

    private fun hideActionBar(){
        supportActionBar?.let {
            it.hide()
        }
    }
}
