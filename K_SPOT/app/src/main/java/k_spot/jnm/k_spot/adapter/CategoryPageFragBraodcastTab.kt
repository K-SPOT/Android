package k_spot.jnm.k_spot.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import k_spot.jnm.k_spot.R
import kotlinx.android.synthetic.main.fragment_category_list_broadcast_tab.view.*

class CategoryPageFragBraodcastTab: Fragment() {

    lateinit var categoryPageItems: ArrayList<CategoryPageFragRecyclerAdpaterData>
    lateinit var categoryPageFragRecyclerAdapter: CategoryPageFragRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_category_list_broadcast_tab, container, false)
        makeRecyclerView(view)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun makeRecyclerView(view: View) {
        categoryPageItems = ArrayList()

        categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_blackpink_img, "런닝맨", "20만", "3300만", true))
        categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_bts_img, "무한도전", "10만", "30만", false))
        categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_exo_img, "나혼자산다", "30만", "30만", true))
        categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_redvelvet_img, "레드벨벳", "50만", "30만", false))
        categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_twice_img, "트와이스", "20만", "90만", true))
        categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_winner_img, "위너", "20만", "40만", false))
        categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_winner_img, "위너", "20만", "330만", true))

        var i = 0
        while (i < 20) {
            categoryPageItems.add(CategoryPageFragRecyclerAdpaterData(R.drawable.category_list_blackpink_img, "블랙핑크", "20만", "30만", true))
            i++
        }
        categoryPageFragRecyclerAdapter = CategoryPageFragRecyclerAdapter(categoryPageItems, this!!.context!!)
        view.category_list_brodcast_fragment_tab_rv.layoutManager = LinearLayoutManager(context)
        view.category_list_brodcast_fragment_tab_rv.adapter = categoryPageFragRecyclerAdapter
    }
}