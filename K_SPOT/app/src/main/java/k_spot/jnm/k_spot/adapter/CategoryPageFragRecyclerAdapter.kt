package k_spot.jnm.k_spot.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import k_spot.jnm.k_spot.Delete.DeleteChannelScripteResponse
import k_spot.jnm.k_spot.Get.ChannelListData
import k_spot.jnm.k_spot.Network.ApplicationController
import k_spot.jnm.k_spot.Network.NetworkService
import k_spot.jnm.k_spot.Post.PostChannelSubscripeResponse
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.activity.CategoryDetailActivity
import k_spot.jnm.k_spot.db.SharedPreferenceController
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryPageFragRecyclerAdapter (private var categoryPageItems : ArrayList<ChannelListData>, private var ctx: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.rv_item_category_list_frag, parent, false)
        return CategoryPageFragRecyclerAdapter.Holder(mainView)
    }

    override fun getItemCount(): Int {
        return categoryPageItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var holder : Holder = holder as Holder

        holder.category_list_index.text = (position+1).toString()

        // 통신 시 주석 제거
        Glide.with(ctx)
                .load(categoryPageItems[position].thumbnail_img)
                .into(holder.category_list_image)

        holder.category_list_name.text = categoryPageItems[position].name

        holder.category_list_sub_num.text = categoryPageItems[position].subscription_cnt.toString()

        holder.category_list_post_num.text = categoryPageItems[position].spot_cnt.toString()

        // 구독된 경우
        if(categoryPageItems[position].subscription == 0){
            holder.category_list_sub_btn_image.setImageResource(R.drawable.category_list_unsub_btn)
        }else{
            holder.category_list_sub_btn_image.setImageResource(R.drawable.category_list_sub_btn)
        }

        var subFlag = categoryPageItems[position].subscription

        holder.category_list_sub_btn.setOnClickListener {
            if(subFlag == 0) {
                holder.category_list_sub_btn_image.setImageResource(R.drawable.category_list_sub_btn)
                requestChannelSubscription(categoryPageItems[position].channel_id)
                subFlag = 1
            }else {
                holder.category_list_sub_btn_image.setImageResource(R.drawable.category_list_unsub_btn)
                deleteChannelSubscription(categoryPageItems[position].channel_id)
                subFlag = 0
            }
        }

        holder.category_list_all_btn.setOnClickListener {
            ctx.startActivity<CategoryDetailActivity>("channel_id" to categoryPageItems[position].channel_id.toString())
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category_list_index : TextView = itemView!!.findViewById(R.id.category_list_fragment_rv_item_num_tv)
        var category_list_image : ImageView = itemView!!.findViewById(R.id.category_list_fragment_rv_item_image_iv)
        var category_list_name : TextView = itemView!!.findViewById(R.id.category_list_fragment_rv_item_name_tv)
        var category_list_sub_num : TextView = itemView!!.findViewById(R.id.category_list_fragment_rv_item_subscriber_num_tv)
        var category_list_post_num : TextView = itemView!!.findViewById(R.id.category_list_fragment_rv_item_post_num_tv)
        var category_list_sub_btn_image : ImageView = itemView!!.findViewById(R.id.category_list_fragment_rv_item_subscribe_iv)
        var category_list_all_btn : RelativeLayout = itemView!!.findViewById(R.id.category_list_fragment_rv_item_rl)
        var category_list_sub_btn : RelativeLayout = itemView!!.findViewById(R.id.category_list_fragment_rv_item_subscribe_btn)

    }

    private fun requestChannelSubscription(channel_id : Int){
        val networkService : NetworkService = ApplicationController.instance.networkService
        val postChannelSubscripeResponse = networkService.postChannelSubscripeResponse(0, SharedPreferenceController.getAuthorization(ctx), channel_id)
        postChannelSubscripeResponse.enqueue(object : Callback<PostChannelSubscripeResponse> {
            override fun onFailure(call: Call<PostChannelSubscripeResponse>?, t: Throwable?) {
                Log.e("구독하기 실패", t.toString())
            }
            override fun onResponse(call: Call<PostChannelSubscripeResponse>?, response: Response<PostChannelSubscripeResponse>?) {
                response?.let {
                    if (response.isSuccessful){
                        ctx.toast("구독")
                    }
                }
            }
        })
    }

    private fun deleteChannelSubscription(channel_id : Int){
        val networkService : NetworkService = ApplicationController.instance.networkService
        val deleteChannelScripteResponse = networkService.deleteChannelSubscripeResponse(0, SharedPreferenceController.getAuthorization(ctx), channel_id)
        deleteChannelScripteResponse.enqueue(object : Callback<DeleteChannelScripteResponse> {
            override fun onFailure(call: Call<DeleteChannelScripteResponse>?, t: Throwable?) {
                Log.e("구독 취소 하기 실패", t.toString())
            }

            override fun onResponse(call: Call<DeleteChannelScripteResponse>?, response: Response<DeleteChannelScripteResponse>?) {
                response?.let {
                    if (response.isSuccessful){
                        ctx.toast("구독 취소")
                    }
                }
            }
        })
    }
}
