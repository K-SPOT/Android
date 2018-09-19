package k_spot.jnm.k_spot.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.adapter.MapPageRecyclerViewAdapter
import k_spot.jnm.k_spot.data.MyAroundKSpotData
import kotlinx.android.synthetic.main.fragment_map_page.*
import org.jetbrains.anko.support.v4.toast

class MapPageFragment : Fragment(){

    val mapPageRecyclerViewAdapter : MapPageRecyclerViewAdapter by lazy {
        MapPageRecyclerViewAdapter(context!!, myAroundKSpotDataList)
    }
    val myAroundKSpotDataList : ArrayList<MyAroundKSpotData> by lazy {
        ArrayList<MyAroundKSpotData>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_map_page, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerViewAdapter()

    }

    private fun setRecyclerViewAdapter(){
        myAroundKSpotDataList.run {
            add(MyAroundKSpotData("","서울남산타워1", 4.5f,"서울의 야경을 한 번에 볼 수 있는 랜드마크","용산구 · 명동역",ArrayList()))
            add(MyAroundKSpotData("","서울남산타워2", 4.5f,"서울의 야경을 한 번에 볼 수 있는 랜드마크","용산구 · 명동역",ArrayList()))
            add(MyAroundKSpotData("","서울남산타워3", 4.5f,"서울의 야경을 한 번에 볼 수 있는 랜드마크","용산구 · 명동역",ArrayList()))
            add(MyAroundKSpotData("","서울남산타워4", 4.5f,"서울의 야경을 한 번에 볼 수 있는 랜드마크","용산구 · 명동역",ArrayList()))
        }
        rv_map_page_my_around_k_spot.layoutManager = LinearLayoutManager(context)
        rv_map_page_my_around_k_spot.adapter = mapPageRecyclerViewAdapter
    }
}