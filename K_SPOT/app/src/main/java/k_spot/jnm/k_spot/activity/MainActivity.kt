package k_spot.jnm.k_spot.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import k_spot.jnm.k_spot.adapter.MainBottomTabAdapter
import k_spot.jnm.k_spot.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureMainTabMenu()

    }



    private fun configureMainTabMenu(){
        main_fragment_pager.adapter = MainBottomTabAdapter(5, supportFragmentManager)
        main_fragment_pager.offscreenPageLimit = 5
        main_bottom_tab_layout.setupWithViewPager(main_fragment_pager)

        val bottomTabView : View = (this.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.bottom_tab_main, null, false)

        main_bottom_tab_layout.getTabAt(0)!!.customView = bottomTabView.findViewById(R.id.main_page_btn) as RelativeLayout
        main_bottom_tab_layout.getTabAt(1)!!.customView = bottomTabView.findViewById(R.id.category_page_btn) as RelativeLayout
        main_bottom_tab_layout.getTabAt(2)!!.customView = bottomTabView.findViewById(R.id.map_page_btn) as RelativeLayout
        main_bottom_tab_layout.getTabAt(3)!!.customView = bottomTabView.findViewById(R.id.board_page_btn) as RelativeLayout
        main_bottom_tab_layout.getTabAt(4)!!.customView = bottomTabView.findViewById(R.id.mypage_page_btn) as RelativeLayout


    }
}
