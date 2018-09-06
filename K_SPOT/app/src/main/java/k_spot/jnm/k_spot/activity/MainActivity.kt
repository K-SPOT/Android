package k_spot.jnm.k_spot.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.adapter.MainBottomTabAdapter
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureMainTabMenu()

        setStatusBarColor()



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


    private fun setStatusBarColor() {
        val view: View? = window.decorView
        view!!.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)

    }


    private fun setStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
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
}
