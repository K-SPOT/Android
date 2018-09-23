package k_spot.jnm.k_spot.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import k_spot.jnm.k_spot.Get.ChannelMyPageData
import k_spot.jnm.k_spot.Network.ApplicationController
import k_spot.jnm.k_spot.Network.NetworkService
import k_spot.jnm.k_spot.Post.PostChannelSubscripeResponse
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.db.SharedPreferenceController
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MySubscribeRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<ChannelMyPageData>) : RecyclerView.Adapter<MySubscribeRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_my_page_fragment, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val drawable = ctx.getDrawable(R.drawable.rv_item_review_more_act_box_shape) as GradientDrawable

        holder.img_btn.background = drawable
        holder.img_btn.clipToOutline = true

        Glide.with(ctx).load(dataList[position].background_img).into(holder.img_btn)

        holder.img_btn.setOnClickListener {
            //            ## celeb보기로 이동!
        }

        holder.engTitle.text = dataList[position].eng_name

        holder.korTitle.text = dataList[position].kor_name

//        ##
//        val subFlag = dataList[position].subFlag

        holder.subscribeBtn.setOnClickListener { requestChannelSubscription(dataList[position].channel_id) }
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

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val img_btn : ImageView = itemView.findViewById(R.id.my_page_frag_rv_item_img_btn) as ImageView
        val engTitle : TextView = itemView.findViewById(R.id.my_page_frag_rv_item_english_tv) as TextView
        val korTitle : TextView = itemView.findViewById(R.id.my_page_frag_rv_item_hangeul_tv) as TextView
        val subscribeImg : ImageView = itemView.findViewById(R.id.my_page_frag_rv_item_subscribe_iv) as ImageView
        val subscribeBtn : RelativeLayout = itemView.findViewById(R.id.my_page_frag_rv_item_subscribe_btn) as RelativeLayout
    }
}