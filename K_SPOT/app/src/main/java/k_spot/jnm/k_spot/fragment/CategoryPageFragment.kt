package k_spot.jnm.k_spot.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.adapter.CategoryPageFragRecyclerAdapter
import k_spot.jnm.k_spot.adapter.CategoryPageFragRecyclerAdpaterData
import k_spot.jnm.k_spot.adapter.CategoryPageTabPagerAdapter
import kotlinx.android.synthetic.main.fragment_category_page.*
import kotlinx.android.synthetic.main.fragment_category_page.view.*
import kotlinx.android.synthetic.main.tablayout_category_page_fragment.*
import org.jetbrains.anko.support.v4.toast

class CategoryPageFragment : Fragment(){

    lateinit var categoryPageItems : ArrayList<CategoryPageFragRecyclerAdpaterData>
    lateinit var categoryPageFragRecyclerAdapter : CategoryPageFragRecyclerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_category_page, container, false)

//        makeRecyclerView(view)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configureRankTabMenu()
        toast("카테고리 페이지")
    }

    fun makeRecyclerView(view : View) {
        categoryPageItems = ArrayList()

        categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_blackpink_img,"블랙핑크","20만","3300만",true))
        categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_bts_img,"방탄소년단","10만","30만",false))
        categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_exo_img,"엑소","30만","30만",true))
        categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_redvelvet_img,"레드벨벳","50만","30만",false))
        categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_twice_img,"트와이스","20만","90만",true))
        categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_winner_img,"위너","20만","40만",false))
        categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_winner_img,"위너","20만","330만",true))

        var i =0
        while (i < 20){
            categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_blackpink_img,"블랙핑크","20만","30만",true))
            i++
        }
        categoryPageFragRecyclerAdapter = CategoryPageFragRecyclerAdapter(categoryPageItems, this!!.context!!)
        view.category_page_fragment_tap_bar_rv.layoutManager = LinearLayoutManager(context)
        view.category_page_fragment_tap_bar_rv.adapter = categoryPageFragRecyclerAdapter
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
        category_page_fragment_viewpager.adapter = CategoryPageTabPagerAdapter(2, childFragmentManager)
        category_page_fragment_tablayout.setupWithViewPager(category_page_fragment_viewpager)

        val headerView: View = (activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.tablayout_category_page_fragment, null, false)
        val likeable = headerView.findViewById(R.id.celebrity_tab_btn) as RelativeLayout
        val unlikeable = headerView.findViewById(R.id.broadcast_tab_btn) as RelativeLayout

        category_page_fragment_tablayout.getTabAt(0)!!.customView = likeable
        category_page_fragment_tablayout.getTabAt(1)!!.customView = unlikeable

        category_page_fragment_tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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