package k_spot.jnm.k_spot.activity

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.adapter.SpotViewMoreActAutoScrollAdapter
import k_spot.jnm.k_spot.adapter.SpotViewMoreActCardViewAdapter
import k_spot.jnm.k_spot.adapter.SpotViewMoreActData
import k_spot.jnm.k_spot.adapter.SpotViewMoreActRecyclerViewData
import kotlinx.android.synthetic.main.activity_spot_view_more.*


class SpotViewMoreActivity : AppCompatActivity() {

    lateinit var spotViewMoreActAutoScrollAdapter: SpotViewMoreActAutoScrollAdapter
    lateinit var images: ArrayList<SpotViewMoreActData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot_view_more)
        // 상태바 투명하게 하는 코드
        // MainActivity에는 필요없으므로 주석처리
        setStatusBarTransparent()

        makeSpotViewMoreActViewPager()

        makeSpotViewMoreActCardView()

        makeBigReviewStar()

        makeSmallReviewStar()

        setOnClickListener()
    }


    // ViewPager 생성 function
    fun makeSpotViewMoreActViewPager() {

        // 배열 생성.
        images = ArrayList()
        images.add(SpotViewMoreActData(R.drawable.category_list_exo_img))
        images.add(SpotViewMoreActData(R.drawable.category_list_exo_img))
        images.add(SpotViewMoreActData(R.drawable.category_list_exo_img))
        images.add(SpotViewMoreActData(R.drawable.category_list_exo_img))
        images.add(SpotViewMoreActData(R.drawable.category_list_exo_img))
        images.add(SpotViewMoreActData(R.drawable.category_list_exo_img))


        // Auto Slider Adapter 적용
        spotViewMoreActAutoScrollAdapter = SpotViewMoreActAutoScrollAdapter(applicationContext, images)
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
                if (realPos + 1 > images.size) {
                    realPos = realPos % images.size
                }

                spot_view_more_act_viewpager_now_page_num_tv.text = (realPos + 1).toString()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        spot_view_more_act_viewpager_all_page_tv.text = images.size.toString()

    }

    // ViewPager 5초마다 이동시키기
    public override fun onResume() {
        super.onResume()

        spotViewMoreActAutoScrollAdapter.notifyDataSetChanged()
        spot_view_more_act_viewpager.setInterval(5000)
        spot_view_more_act_viewpager.startAutoScroll(5000)
    }

    // ViewPager 다른 엑티비티 갔을 떄 멈춰.
    public override fun onPause() {
        super.onPause()
        spot_view_more_act_viewpager.stopAutoScroll()
    }

    // 큰 별 리뷰 만들기
    fun makeBigReviewStar() {
        // starCount 통신으로 받아와야함.
        val starCount = 2.89

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

    fun makeSmallReviewStar() {
        // starCount 통신으로 받아와야함.
        val starCount = 3.5

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
    fun makeSpotViewMoreActCardView() {
        val mRecyclerView = spot_view_more_act_relative_celev_rl as RecyclerView
//        val mRecyclerView = view.findViewById(R.id.main_page_fragment_rv1) as RecyclerView
        mRecyclerView.setHasFixedSize(true)

        val mLayoutManager = GridLayoutManager(applicationContext, 1, GridLayoutManager.HORIZONTAL, false)

        mRecyclerView.layoutManager = mLayoutManager

        var myDataset = ArrayList<SpotViewMoreActRecyclerViewData>()

        myDataset.add(SpotViewMoreActRecyclerViewData(R.drawable.category_list_blackpink_img, "블랙핑크", true))
        myDataset.add(SpotViewMoreActRecyclerViewData(R.drawable.category_list_blackpink_img, "블랙핑크", false))
        myDataset.add(SpotViewMoreActRecyclerViewData(R.drawable.category_list_blackpink_img, "블랙핑크", false))
        myDataset.add(SpotViewMoreActRecyclerViewData(R.drawable.category_list_blackpink_img, "블랙핑크", true))
        myDataset.add(SpotViewMoreActRecyclerViewData(R.drawable.category_list_blackpink_img, "블랙핑크", false))
        myDataset.add(SpotViewMoreActRecyclerViewData(R.drawable.category_list_blackpink_img, "블랙핑크", true))

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

        spot_view_more_act_scroll_view.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                val y = spot_view_more_act_scroll_view.scrollY
                Log.v("ssd",y.toString())


                // 맨 위 스크롤이 아닐 때
                if (!(y == 0)) {
                    window.statusBarColor = Color.WHITE
                    spot_view_more_act_top_bar_rl.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    spot_view_more_act_back_iv.setColorFilter(Color.parseColor("#5E5E5E"))
                    spot_view_more_act_scrap_iv.setImageResource(R.drawable.category_scrab_btn_gray)
                    spot_view_more_act_scrap_num_tv.setTextColor(Color.parseColor("#5E5E5E"))
                    spot_view_more_act_top_bar_bottom_line.visibility = View.VISIBLE
                }else{
                    window.statusBarColor = Color.TRANSPARENT
                    spot_view_more_act_top_bar_rl.setBackgroundColor(Color.parseColor("#00000000"))
                    spot_view_more_act_back_iv.setColorFilter(Color.parseColor("#FFFFFF"))
                    spot_view_more_act_scrap_iv.setImageResource(R.drawable.category_scrap_btn)
                    spot_view_more_act_scrap_num_tv.setTextColor(Color.parseColor("#FFFFFF"))
                    spot_view_more_act_top_bar_bottom_line.visibility = View.GONE
                }

                return false
            }

        })
    }
}
