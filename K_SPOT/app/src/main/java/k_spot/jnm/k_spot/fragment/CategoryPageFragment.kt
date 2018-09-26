package k_spot.jnm.k_spot.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.SearchActivity
import k_spot.jnm.k_spot.adapter.CategoryPageTabPagerAdapter
import kotlinx.android.synthetic.main.fragment_category_list.*
import kotlinx.android.synthetic.main.fragment_category_list.view.*
import kotlinx.android.synthetic.main.tablayout_category_page_fragment.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class CategoryPageFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_category_list, container, false)
        setOnClickListener(view)
        return view



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configureRankTabMenu()
        toast("카테고리 페이지")
    }

    private fun setOnClickListener(view: View) {
        view.category_list_fragment_search_btn.setOnClickListener {
            startActivity<SearchActivity>()
        }
    }




    private fun whereIsTab(position: Int) {
        if(position == 0){
            celebrity_title_tv.setTextColor(Color.parseColor("#6B6B6B"))
            broadcast_title_tv.setTextColor(Color.parseColor("#D8D8D8"))
        } else {
            celebrity_title_tv.setTextColor(Color.parseColor("#D8D8D8"))
            broadcast_title_tv.setTextColor(Color.parseColor("#6B6B6B"))
        }
    }

    fun configureRankTabMenu() {
        category_list_fragment_viewpager.adapter = CategoryPageTabPagerAdapter(2, childFragmentManager)
        category_list_fragment_tablayout.setupWithViewPager(category_list_fragment_viewpager)

        val headerView: View = (activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.tablayout_category_page_fragment, null, false)
        val likeable = headerView.findViewById(R.id.celebrity_tab_btn) as RelativeLayout
        val unlikeable = headerView.findViewById(R.id.broadcast_tab_btn) as RelativeLayout

        category_list_fragment_tablayout.getTabAt(0)!!.customView = likeable
        category_list_fragment_tablayout.getTabAt(1)!!.customView = unlikeable

        category_list_fragment_tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                whereIsTab(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                whereIsTab(tab!!.position)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                whereIsTab(tab!!.position)
            }
        })
    }
}