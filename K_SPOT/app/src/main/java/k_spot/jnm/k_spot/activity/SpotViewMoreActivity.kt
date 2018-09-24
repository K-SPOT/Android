package k_spot.jnm.k_spot.activity

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import k_spot.jnm.k_spot.Delete.DeleteChannelScripteResponse
import k_spot.jnm.k_spot.Get.*
import k_spot.jnm.k_spot.Network.ApplicationController
import k_spot.jnm.k_spot.Network.NetworkService
import k_spot.jnm.k_spot.Post.PostChannelSubscripeResponse
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.ReviewMoreActivity
import k_spot.jnm.k_spot.ReviewWriteActivity
import k_spot.jnm.k_spot.adapter.SpotViewMoreActAutoScrollAdapter
import k_spot.jnm.k_spot.adapter.SpotViewMoreActCardViewAdapter
import k_spot.jnm.k_spot.db.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_spot_view_more.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SpotViewMoreActivity : AppCompatActivity() {

    lateinit var spotViewMoreActAutoScrollAdapter: SpotViewMoreActAutoScrollAdapter
    lateinit var networkService: NetworkService
    lateinit var spotViewMoreData : ArrayList<SpotViewMoreData>

    lateinit var channelSpotViewMoreData: ChannelSpotViewMoreData


    lateinit var reviewSpotViewMoreData : ArrayList<ReviewSpotViewMoreData>

    lateinit var viewPagerImg: ArrayList<String>

    lateinit var channelRecyclerViewData: ArrayList<ChannelRecyclerViewData>
    lateinit var viewPagerSpotViewMoreActData : ArrayList<ViewPagerSpotViewMoreActData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot_view_more)

        var spot_id = intent.getIntExtra("spot_id", 0)

        spotViewMoreData = ArrayList()
        reviewSpotViewMoreData = ArrayList()
        channelRecyclerViewData = ArrayList()
        viewPagerSpotViewMoreActData = ArrayList()
        spotViewMoreActAutoScrollAdapter = SpotViewMoreActAutoScrollAdapter(applicationContext, viewPagerSpotViewMoreActData)
        channelSpotViewMoreData = ChannelSpotViewMoreData(ArrayList(), ArrayList(),ArrayList(),ArrayList())
        viewPagerImg = ArrayList()

        // 상태바 투명하게 하는 코드
        // MainActivity에는 필요없으므로 주석처리
        setStatusBarTransparent()

        setOnClickListener()
        getSpotViewMore(spot_id)
    }


    fun getSpotViewMore(spot_id : Int) {

        networkService = ApplicationController.instance.networkService
        val authorization: String = SharedPreferenceController.getAuthorization(context = applicationContext)
        val getSpotViewMoreResponse = networkService.getSpotViewMore(0, authorization, spot_id)
        getSpotViewMoreResponse.enqueue(object : Callback<GetSpotViewMoreResponse> {
            override fun onFailure(call: Call<GetSpotViewMoreResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetSpotViewMoreResponse>?, response: Response<GetSpotViewMoreResponse>?) {
                if (response!!.isSuccessful) {

                    spotViewMoreData = response!!.body()!!.data
                    channelSpotViewMoreData = response!!.body()!!.data!![0].channel
                    reviewSpotViewMoreData = response!!.body()!!.data!![0].reviews
                    viewPagerImg = response!!.body()!!.data!![0].img
                    var j = 0
                    while(j < viewPagerImg.size){
                        viewPagerSpotViewMoreActData.add(ViewPagerSpotViewMoreActData(viewPagerImg[j]))
                        j ++
                    }

                    if(viewPagerSpotViewMoreActData.size > 0){
                        makeSpotViewMoreActViewPager(viewPagerSpotViewMoreActData)
                    }


                    var i = 0
                    while(i<channelSpotViewMoreData.channel_id.size){
                        channelRecyclerViewData.add(ChannelRecyclerViewData(channelSpotViewMoreData.channel_id[i],channelSpotViewMoreData.channel_name[i]
                        , channelSpotViewMoreData.thumbnail_img[i],channelSpotViewMoreData.is_subscription[i]))
                        i++
                    }

                    if (channelRecyclerViewData.size != 0){
                        makeSpotViewMoreActCardView(channelRecyclerViewData)
                        spot_view_more_act_relative_celev_num_tv.text = channelRecyclerViewData.size.toString()
                    }else{
                        spot_view_more_act_relative_celev_rl.visibility = View.GONE
                        spot_view_more_act_relative_celev_num_tv.text = "0"
                    }






                    if(spotViewMoreData.size != 0){
                        makeBigReviewStar(spotViewMoreData[0].review_score)
                        spot_view_more_act_spot_title_tv.text = spotViewMoreData[0].name

                        spot_view_more_act_spot_semi_info_tv.text = spotViewMoreData[0].description

                        // ## 구글 맵으로 넘어가기
                        spot_view_more_act_spot_address_tv.text = spotViewMoreData[0].address

                        spot_view_more_act_spot_station_name_tv.text = spotViewMoreData[0].station

                        spot_view_more_act_spot_review_num_tv.text = spotViewMoreData[0].review_score.toString()

                        spot_view_more_act_review_num_tv.text = spotViewMoreData[0].review_cnt.toString()

                        spot_view_more_act_spot_left_station_tv.text = spotViewMoreData[0].prev_station

                        spot_view_more_act_spot_right_station_tv.text = spotViewMoreData[0].next_station

                        spot_view_more_act_open_time_tv.text = spotViewMoreData[0].open_time

                        spot_view_more_act_close_time_tv.text = spotViewMoreData[0].close_time

                        spot_view_more_act_phone_num_tv.text = spotViewMoreData[0].contact

                        // 스크랩
                        spot_view_more_act_scrap_num_tv.text = spotViewMoreData[0].scrap_cnt.toString()

                        var scrapFlag = spotViewMoreData[0].is_scrap
                        // 스크랩 안 됐을 시 하얀색으로
                        if(scrapFlag == 0) {
                            spot_view_more_act_scrap_iv.setImageResource(R.drawable.category_scrap_btn)
                            //## 스크랩 통신 필요
                            scrapFlag = 1
                        }else{
                            spot_view_more_act_scrap_iv.setImageResource(R.drawable.category_unscrap_btn)
                            scrapFlag = 0
                        }

                    }else {
                        makeBigReviewStar(0.0)
                    }


                    if(reviewSpotViewMoreData.size != 0){
                        makeSmallReviewStar(reviewSpotViewMoreData[0].review_score)
                        spot_view_more_act_review_box_title_tv.text = reviewSpotViewMoreData[0].title
                        spot_view_more_act_review_box_explain_tv.text = reviewSpotViewMoreData[0].content
                        spot_view_more_act_review_box_writer_date_tv.text = reviewSpotViewMoreData[0].reg_time + " · " + reviewSpotViewMoreData[0].reg_time

                        if (reviewSpotViewMoreData[0].img.length > 0) {
                            Glide.with(applicationContext).load(reviewSpotViewMoreData[0].img).into(spot_view_more_act_review_box_picture_iv)
                        }else{
                            // ## Noimage 넣어야됌
                            spot_view_more_act_review_box_picture_iv.setImageResource(R.drawable.category_unscrap_btn_gray)
                        }
                    }else {
                        makeSmallReviewStar(0.0)
                        spot_view_more_act_review_box_title_tv.text = ""
                        spot_view_more_act_review_box_explain_tv.text = ""
                        spot_view_more_act_review_box_writer_date_tv.text = ""
                        // ## Noimage 넣어야됌
                    }







                }
            }

        })
    }

    private fun requestSpotSubscription(spot_id : Int){
        val networkService : NetworkService = ApplicationController.instance.networkService
        val postSpotSubscripeResponse = networkService.postSpotSubscripeResponse(0, SharedPreferenceController.getAuthorization(this), spot_id)
        postSpotSubscripeResponse.enqueue(object : Callback<PostChannelSubscripeResponse>{
            override fun onFailure(call: Call<PostChannelSubscripeResponse>?, t: Throwable?) {
                Log.e("스크랩 실패", t.toString())
            }
            override fun onResponse(call: Call<PostChannelSubscripeResponse>?, response: Response<PostChannelSubscripeResponse>?) {
                response?.let {
                    if (response.isSuccessful){
                        toast("스크랩 완료")
                    }
                }
            }
        })
    }

    private fun deleteSpotSubscription(spot_id : Int){
        val networkService : NetworkService = ApplicationController.instance.networkService
        val deleteSpotSubscripeResponse = networkService.deleteSpotSubscripeResponse(0, SharedPreferenceController.getAuthorization(this), spot_id)
        deleteSpotSubscripeResponse.enqueue(object : Callback<DeleteChannelScripteResponse> {
            override fun onFailure(call: Call<DeleteChannelScripteResponse>?, t: Throwable?) {
                Log.e("스크랩 취소 실패", t.toString())
            }

            override fun onResponse(call: Call<DeleteChannelScripteResponse>?, response: Response<DeleteChannelScripteResponse>?) {
                response?.let {
                    if (response.isSuccessful){
                        toast("스크랩 취소")
                    }
                }
            }
        })
    }


    // ViewPager 생성 function
    fun makeSpotViewMoreActViewPager(viewPagerSpotViewMoreActData : ArrayList<ViewPagerSpotViewMoreActData> ) {

        // Auto Slider Adapter 적용
        spotViewMoreActAutoScrollAdapter = SpotViewMoreActAutoScrollAdapter(applicationContext, viewPagerSpotViewMoreActData)
        spot_view_more_act_viewpager.adapter = spotViewMoreActAutoScrollAdapter

        // 아래 세 줄 위 세 줄로 대체
        //view.homeslider = AutoScrollViewPager(this!!.context!!)
        //homeslider!!.setAdapter(MainFragViewPagerImageSliderAdapter)
        //setUiPageViewController

        // Viewpager가 Chager될 때 마다 인디케이터 점 변환
        spot_view_more_act_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            // 페이지가 선택되었을 때
            override fun onPageSelected(position: Int) {

                var realPos = position

                // 페이지 바뀔 때마다 현재 페이지 num 표시
                if (realPos + 1 > viewPagerSpotViewMoreActData.size) {
                    realPos = realPos % viewPagerSpotViewMoreActData.size
                }

                spot_view_more_act_viewpager_now_page_num_tv.text = (realPos + 1).toString()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        spot_view_more_act_viewpager_all_page_tv.text = viewPagerSpotViewMoreActData.size.toString()
        spotViewMoreActAutoScrollAdapter!!.notifyDataSetChanged()
        spot_view_more_act_viewpager!!.setInterval(5000)
        spot_view_more_act_viewpager!!.startAutoScroll(1000)

    }

    // ViewPager 5초마다 이동시키기
    public override fun onResume() {
        super.onResume()

        val handler = Handler()
        handler.postDelayed(Runnable {
            // Auto Scroll
            spotViewMoreActAutoScrollAdapter!!.notifyDataSetChanged()
            spot_view_more_act_viewpager!!.setInterval(5000)
            spot_view_more_act_viewpager!!.startAutoScroll(1000)
        }, 3000)
    }

    // ViewPager 다른 엑티비티 갔을 떄 멈춰.
    public override fun onPause() {
        super.onPause()
        spot_view_more_act_viewpager.stopAutoScroll()
    }

    // 큰 별 리뷰 만들기
    fun makeBigReviewStar(starCount: Double) {

        // size 5의 이미지 뷰 배열 생성
        var stars: Array<ImageView?> = arrayOfNulls<ImageView>(5)
        // star 생성
        for (i in 0 until 5) {

            if (i < starCount) {

                // 별 반개를 표현 해야 할 때
                if (0.5 <= (starCount - i) && (starCount - i) <= 0.99) {
                    // 별 반개 그리는거
                    stars[i] = ImageView(applicationContext)
                    stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_big_halfstar))
                } else if (0 <= (starCount - i) && (starCount - i) < 0.5) {
                    // 마지막 별이 0~0.5일 때 꽉찬 별 그리는 거
                    stars[i] = ImageView(applicationContext)
                    stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_big_star_empty))
                } else {
                    // 꽉찬 별을 표현 해야 할 때
                    stars[i] = ImageView(applicationContext)
                    stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_big_star))
                }

            } else {
                // 빈 별
                stars[i] = ImageView(applicationContext)
                stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_big_star_empty))
            }
            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            )

            // 인디케이터 점 마진 설정
            params.setMargins(3, 0, 3, 0)
            //LinearView에 뷰 생성
            spot_view_more_act_review_star_ll!!.addView(stars[i], params)
        }


        //
        spot_view_more_act_spot_review_num_tv.text = starCount.toString()
        spot_view_more_act_review_star_num_tv.text = starCount.toString()
    }

    fun makeSmallReviewStar(starCount: Double) {
        // starCount 통신으로 받아와야함.


        // size 5의 이미지 뷰 배열 생성
        var stars: Array<ImageView?> = arrayOfNulls<ImageView>(5)
        // star 생성
        for (i in 0 until 5) {

            if (i < starCount) {

                // 별 반개를 표현 해야 할 때
                if (0.5 <= (starCount - i) && (starCount - i) <= 0.99) {
                    // 별 반개 그리는거
                    stars[i] = ImageView(applicationContext)
                    stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_small_star_half))
                } else if (0 <= (starCount - i) && (starCount - i) < 0.5) {
                    // 마지막 별이 0~0.5일 때 꽉찬 별 그리는 거
                    stars[i] = ImageView(applicationContext)
                    stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_small_star))
                } else {
                    // 꽉찬 별을 표현 해야 할 때
                    stars[i] = ImageView(applicationContext)
                    stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_small_star))
                }

            } else {
                // 빈 별
                stars[i] = ImageView(applicationContext)
                stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_small_star_empty))
            }
            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            )

            // 인디케이터 점 마진 설정
            params.setMargins(3, 0, 3, 0)
            //LinearView에 뷰 생성
            spot_view_more_act_review_box_star_ll!!.addView(stars[i], params)
        }
    }

    // CardView 만들기
    fun makeSpotViewMoreActCardView(myDataset: ArrayList<ChannelRecyclerViewData>) {
        val mRecyclerView = spot_view_more_act_relative_celev_rl as RecyclerView
//        val mRecyclerView = view.findViewById(R.id.main_page_fragment_rv1) as RecyclerView
        mRecyclerView.setHasFixedSize(true)

        val mLayoutManager = GridLayoutManager(applicationContext, 1, GridLayoutManager.HORIZONTAL, false)

        mRecyclerView.layoutManager = mLayoutManager
        val mAdapter = SpotViewMoreActCardViewAdapter(applicationContext, myDataset)
        mRecyclerView.adapter = mAdapter
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

    fun setOnClickListener() {
        spot_view_more_act_move_top_btn.setOnClickListener {
            spot_view_more_act_scroll_view.post(Runnable { spot_view_more_act_scroll_view.scrollTo(0, 0) })
        }
        spot_view_more_act_scroll_view.setOnScrollChangeListener(object : View.OnScrollChangeListener {
            override fun onScrollChange(v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {

                // 맨 위 스크롤이 아닐 때
                if (!(scrollY <= 0)) {
                    window.statusBarColor = Color.WHITE
                    spot_view_more_act_top_bar_rl.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    spot_view_more_act_back_iv.setColorFilter(Color.parseColor("#5E5E5E"))
                    spot_view_more_act_scrap_iv.setColorFilter(Color.parseColor("#5E5E5E"))
                    spot_view_more_act_scrap_num_tv.setTextColor(Color.parseColor("#5E5E5E"))
                    spot_view_more_act_top_bar_bottom_line.visibility = View.VISIBLE
                }else{
                    window.statusBarColor = Color.TRANSPARENT
                    spot_view_more_act_top_bar_rl.setBackgroundColor(Color.parseColor("#00000000"))
                    spot_view_more_act_back_iv.setColorFilter(Color.parseColor("#FFFFFF"))
                    spot_view_more_act_scrap_iv.setColorFilter(Color.parseColor("#FFFFFF"))
                    spot_view_more_act_scrap_num_tv.setTextColor(Color.parseColor("#FFFFFF"))
                    spot_view_more_act_top_bar_bottom_line.visibility = View.GONE
                }
            }
        })

        spot_view_more_act_back_btn.setOnClickListener {
            finish()
        }

        // 스크랩 버튼
        spot_view_more_act_scrap_btn.setOnClickListener {

            // 스크랩 안 됐을 시 하얀색으로
            if(spotViewMoreData[0].is_scrap == 0) {
                spot_view_more_act_scrap_iv.setImageResource(R.drawable.category_scrap_btn)
                requestSpotSubscription(spotViewMoreData[0].spot_id)
                Log.v("spotViewMoreData[0].spot_id", spotViewMoreData[0].spot_id.toString())
                //## 스크랩 통신 필요
                spotViewMoreData[0].is_scrap = 1
                spot_view_more_act_scrap_num_tv.text = (spotViewMoreData[0].scrap_cnt + 1).toString()
            }else{
                Log.v("spotViewMoreData[0].spot_id", spotViewMoreData[0].spot_id.toString())
                spot_view_more_act_scrap_iv.setImageResource(R.drawable.category_unscrap_btn)
                deleteSpotSubscription(spotViewMoreData[0].spot_id)
                spotViewMoreData[0].is_scrap = 0
                spot_view_more_act_scrap_num_tv.text = (spotViewMoreData[0].scrap_cnt).toString()
            }


            //  ## 로그인이 되어있지 않은 경우 로그인 팝업이 뜬다.
        }

        spot_view_more_act_spot_address_rl.setOnClickListener {
            // ## Google map 연동 필요
        }

        // 리뷰 모두 보기
        spot_view_more_act_all_review_btn.setOnClickListener {

            var intent = Intent(applicationContext, ReviewMoreActivity::class.java)
            var spot_id = spotViewMoreData[0].spot_id
            intent.putExtra("spot_id", spot_id)
            startActivity(intent)

        }

        spot_view_more_act_review_write_box_btn.setOnClickListener {
            // ## 리뷰 Write로 이동
            startActivity<ReviewWriteActivity>("spot_id" to spotViewMoreData[0].spot_id)
        }

        spot_view_more_act_phone_num_btn.setOnClickListener {
            var tel = "tel:"+spotViewMoreData[0].contact
            var intent = Intent("android.intent.action.DIAL", Uri.parse(tel))
            startActivity(intent)

        }


    }
}
