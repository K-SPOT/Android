package k_spot.jnm.k_spot.activity

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.adapter.MainBottomTabAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast


class MainActivity : AppCompatActivity() {
    private val FINISH_INTERVAL_TIME: Long = 2000
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureMainTabMenu()


        // 상태바 투명하게 하는 코드
        // MainActivity에는 필요없으므로 주석처리
        setStatusBarTransparent()


    }

    fun hideBottomPageTab(){
        main_bottom_tab_layout.visibility = View.GONE
    }
    fun showBottomPageTab(){
        main_bottom_tab_layout.visibility = View.VISIBLE
    }


    private fun configureMainTabMenu() {
        main_fragment_pager.adapter = MainBottomTabAdapter(5, supportFragmentManager)
        main_fragment_pager.offscreenPageLimit = 5
        main_bottom_tab_layout.setupWithViewPager(main_fragment_pager)

        val bottomTabView: View = (this.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.bottom_tab_main, null, false)

        main_bottom_tab_layout.getTabAt(0)!!.customView = bottomTabView.findViewById(R.id.main_page_btn) as RelativeLayout
        main_bottom_tab_layout.getTabAt(1)!!.customView = bottomTabView.findViewById(R.id.category_page_btn) as RelativeLayout
        main_bottom_tab_layout.getTabAt(2)!!.customView = bottomTabView.findViewById(R.id.map_page_btn) as RelativeLayout
        main_bottom_tab_layout.getTabAt(3)!!.customView = bottomTabView.findViewById(R.id.board_page_btn) as RelativeLayout
        main_bottom_tab_layout.getTabAt(4)!!.customView = bottomTabView.findViewById(R.id.mypage_page_btn) as RelativeLayout
    }



    private fun setStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
    }

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

    private fun setStatusBarColor() {
        val view: View? = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance

//            // 아이콘 회색으로 바꾸는 코드 (이거 없애면 흰색나옴)
//            view!!.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

            // 상태바 투명으로 바꾸는 코드
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }

    override fun onBackPressed() {
        var tempTime: Long = System.currentTimeMillis()
        var intervalTime: Long = tempTime - backPressedTime
        if (intervalTime in 0..FINISH_INTERVAL_TIME) {
            super.onBackPressed()
        } else {
            backPressedTime = tempTime
            longToast("한번 더 뒤로가기를 누르면 종료됩니다.")
        }
    }

}
