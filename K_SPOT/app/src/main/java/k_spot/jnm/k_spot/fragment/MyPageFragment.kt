package k_spot.jnm.k_spot.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.adapter.MySubscribeRecyclerViewAdapter
import k_spot.jnm.k_spot.adapter.MySubscribeRecyclerViewData
import kotlinx.android.synthetic.main.fragment_my_page.*
import org.jetbrains.anko.support.v4.toast

class MyPageFragment : Fragment(){

    lateinit var mySubscribeDataList : ArrayList<MySubscribeRecyclerViewData>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setMySubscribeRecyclerView()

        toast("마이 페이지")
    }

    private fun setMySubscribeRecyclerView(){

        mySubscribeDataList = arrayListOf()

        //데이터 받는곳 별로도 구성하기
        mySubscribeDataList.add(MySubscribeRecyclerViewData(R.drawable.main_img, "BLACK PINK", "블랙핑크",true))
        mySubscribeDataList.add(MySubscribeRecyclerViewData(R.drawable.main_img, "BLACK PINK", "블랙핑크",true))
        mySubscribeDataList.add(MySubscribeRecyclerViewData(R.drawable.main_img, "BLACK PINK", "블랙핑크",true))
        mySubscribeDataList.add(MySubscribeRecyclerViewData(R.drawable.main_img, "BLACK PINK", "블랙핑크",true))
        mySubscribeDataList.add(MySubscribeRecyclerViewData(R.drawable.main_img, "BLACK PINK", "블랙핑크",true))
        mySubscribeDataList.add(MySubscribeRecyclerViewData(R.drawable.main_img, "BLACK PINK", "블랙핑크",true))
        my_page_frag_my_subscribe_rv.layoutManager = LinearLayoutManager(context, 0, false)
        my_page_frag_my_subscribe_rv.adapter = MySubscribeRecyclerViewAdapter(this!!.context!!, mySubscribeDataList)
    }
}
