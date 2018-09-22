package k_spot.jnm.k_spot.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import k_spot.jnm.k_spot.Get.ChannelSearchResultData
import k_spot.jnm.k_spot.R

class SearchResultActBroadRecyclerAdapter(private var searchBroadItems : ArrayList<ChannelSearchResultData>, private var context: Context, private var ItemCount: Int, private var onItemClick: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainView : View = LayoutInflater.from(context).inflate(R.layout.rv_item_search_result_act_celeb_broad_result, parent, false)
        mainView.setOnClickListener(onItemClick)
        return SearchResultActBroadRecyclerAdapter.Holder(mainView)
    }

    override fun getItemCount(): Int {
        return ItemCount
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var holder : Holder = holder as Holder

        Glide.with(context)
                .load(searchBroadItems[position]!!.thumbnail_img)
                .into(holder.result_image)

        holder.result_name.text = searchBroadItems[position]!!.name

        holder.result_sub_num.text = searchBroadItems[position]!!.subscription_cnt.toString()

        holder.result_post_num.text = searchBroadItems[position]!!.spot_cnt.toString()

        var flag: Int = searchBroadItems[position]!!.subscription
        if(searchBroadItems[position]!!.subscription == 0){
            holder.result_sub_btn_image.setImageResource(R.drawable.category_list_sub_btn)
        }else{
            holder.result_sub_btn_image.setImageResource(R.drawable.category_list_unsub_btn)
        }

        holder.result_sub_btn_btn.setOnClickListener {
//            if 조건문으로 구독 안한 flag 일 경우
//            subscription Flag 바꾸는 통신을 하고 한번 터치 시 tempFlag 값을 바꾸고
            if(flag == 0){
                holder.result_sub_btn_image.setImageResource(R.drawable.category_list_unsub_btn)
                // 구독 신청 통신 필요
                flag = 1
            }else {
                holder.result_sub_btn_image.setImageResource(R.drawable.category_list_sub_btn)
                // 플래그 바꾸는 통신 필요
                flag = 0
            }
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