package k_spot.jnm.k_spot.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import k_spot.jnm.k_spot.R

class SearchResultActBroadRecyclerAdapter(private var searchBroadItems : ArrayList<SearchResultActBroadRecyclerAdapterData>, private var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainView : View = LayoutInflater.from(context).inflate(R.layout.rv_item_search_result_act_celeb_broad_result, parent, false)
        return SearchResultActBroadRecyclerAdapter.Holder(mainView)
    }

    override fun getItemCount(): Int {
        return searchBroadItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var holder : Holder = holder as Holder

        holder.result_image.setImageResource(searchBroadItems[position].Image)

        // 통신 시 주석 제거
//        Glide.with(context)
//                .load(categoryPageItems[position].Image)
//                .into(holder.category_list_image)

        holder.result_name.text = searchBroadItems[position].name

        holder.result_sub_num.text = searchBroadItems[position].sub_num

        holder.result_post_num.text = searchBroadItems[position].post_num

        if(searchBroadItems[position].flag == true){
            holder.result_sub_btn_image.setImageResource(R.drawable.category_list_sub_btn)
        }else{
            holder.result_sub_btn_image.setImageResource(R.drawable.category_list_unsub_btn)
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var result_image : ImageView = itemView!!.findViewById(R.id.search_result_act_rv_item_image_iv)
        var result_name : TextView = itemView!!.findViewById(R.id.search_result_act_rv_item_name_tv)
        var result_sub_num : TextView = itemView!!.findViewById(R.id.search_result_act_rv_item_subscriber_num_tv)
        var result_post_num : TextView = itemView!!.findViewById(R.id.search_result_act_rv_item_post_num_tv)
        var result_sub_btn_image : ImageView = itemView!!.findViewById(R.id.search_result_act_rv_item_subscribe_iv)
        var result_sub_btn_btn : RelativeLayout = itemView!!.findViewById(R.id.search_result_act_rv_item_subscribe_btn)
    }
}

data class SearchResultActBroadRecyclerAdapterData(
        var Image : Int,
        var name : String,
        var sub_num : String,
        var post_num : String,
        var flag : Boolean
)