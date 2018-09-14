package k_spot.jnm.k_spot.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import k_spot.jnm.k_spot.R



class SearchSpotViewMoreActRecyclerAdapter(private var searchSpotViewMoreItems : ArrayList<SearchSpotViewMoreActSpotRecyclerAdapterData>, private var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainView : View = LayoutInflater.from(context).inflate(R.layout.rv_item_search_spot_view_more_act, parent, false)
        return SearchSpotViewMoreActRecyclerAdapter.Holder(mainView)
    }

    override fun getItemCount(): Int {
        return searchSpotViewMoreItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var holder : Holder = holder as Holder

        val drawable = context.getDrawable(R.drawable.search_spot_view_more_img_background) as GradientDrawable

        holder.result_image.background = drawable
        holder.result_image.clipToOutline = true

        holder.result_image.setImageResource(searchSpotViewMoreItems[position].Image)

        // 통신 시 주석 제거
//        Glide.with(context)
//                .load(categoryPageItems[position].Image)
//                .into(holder.category_list_image)

        holder.result_name.text = searchSpotViewMoreItems[position].name

        holder.result_sub_text.text = searchSpotViewMoreItems[position].sub_text

        holder.result_address.text = searchSpotViewMoreItems[position].address

        holder.result_sub_num.text = searchSpotViewMoreItems[position].sub_num
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var result_image : ImageView = itemView!!.findViewById(R.id.search_spot_view_more_act_rv_item_iv)
        var result_name : TextView = itemView!!.findViewById(R.id.search_spot_view_more_act_rv_item_title_tv)
        var result_sub_text : TextView = itemView!!.findViewById(R.id.search_spot_view_more_act_rv_item_sub_text_tv)
        var result_address : TextView = itemView!!.findViewById(R.id.search_spot_view_more_act_rv_item_address_tv)
        var result_sub_num : TextView = itemView!!.findViewById(R.id.search_spot_view_more_act_rv_item_sub_tv)
    }
}

data class SearchSpotViewMoreActSpotRecyclerAdapterData(
        var Image : Int,
        var name : String,
        var sub_text : String,
        var address : String,
        var sub_num : String
)