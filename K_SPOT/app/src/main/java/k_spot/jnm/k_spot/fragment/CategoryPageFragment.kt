package k_spot.jnm.k_spot.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.adapter.CategoryPageFragRecyclerAdapter
import k_spot.jnm.k_spot.adapter.CategoryPageFragRecyclerAdpaterData
import kotlinx.android.synthetic.main.fragment_category_page.view.*
import org.jetbrains.anko.support.v4.toast

class CategoryPageFragment : Fragment(){

    lateinit var categoryPageItems : ArrayList<CategoryPageFragRecyclerAdpaterData>
    lateinit var categoryPageFragRecyclerAdapter : CategoryPageFragRecyclerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view : View = inflater.inflate(R.layout.fragment_category_page, container, false)

        makeRecyclerView(view)


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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

}