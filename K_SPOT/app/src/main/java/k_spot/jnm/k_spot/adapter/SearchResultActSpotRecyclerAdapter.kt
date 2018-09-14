package k_spot.jnm.k_spot.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import k_spot.jnm.k_spot.R

class SearchResultActSpotRecyclerAdapter(private var searchSpotItems : ArrayList<SearchResultActSpotRecyclerAdapterData>, private var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainView : View = LayoutInflater.from(context).inflate(R.layout.rv_item_search_result_act_spot, parent, false)
        return SearchResultActSpotRecyclerAdapter.Holder(mainView)
    }

    override fun getItemCount(): Int {
        return searchSpotItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var holder : Holder = holder as Holder

        // 통신 시 주석 제거
//        Glide.with(context)
//                .load(categoryPageItems[position].Image)
//                .into(holder.category_list_image)

        holder.result_name.text = searchSpotItems[position].name

        holder.result_address.text = searchSpotItems[position].address

        // 아이콘 네 개 논리처리
        if(searchSpotItems[position].iconFlag == 0){
            holder.result_icon_image.setImageResource(R.drawable.search_page_place_restaurant_icon)
        }else if(searchSpotItems[position].iconFlag == 1){
            holder.result_icon_image.setImageResource(R.drawable.search_page_place_restaurant_icon)
        }else if(searchSpotItems[position].iconFlag == 2) {
            holder.result_icon_image.setImageResource(R.drawable.search_page_place_restaurant_icon)
        }else {
            holder.result_icon_image.setImageResource(R.drawable.search_page_event_birthday_icon)
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var result_icon_image : ImageView = itemView!!.findViewById(R.id.search_result_rv_item_icon_iv)
        var result_name : TextView = itemView!!.findViewById(R.id.search_result_rv_item_title_tv)
        var result_address : TextView = itemView!!.findViewById(R.id.search_result_rv_item_spot_tv)
    }
}

data class SearchResultActSpotRecyclerAdapterData(
        var name : String,
        var address : String,
        var iconFlag : Int
)