package k_spot.jnm.k_spot.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import k_spot.jnm.k_spot.Get.PlaceRecommendData
import k_spot.jnm.k_spot.R


class PlaceRecommendRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<PlaceRecommendData>) : RecyclerView.Adapter<PlaceRecommendRecyclerViewAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_category_detail_recommend_spot, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //holder.image
        holder.mainTitle.text = dataList[position].name

        val requestOptions = RequestOptions()
        Glide.with(ctx)
                .load(dataList[position].img)
                .into(holder.image)
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var image : ImageView = itemView.findViewById(R.id.iv_rv_item_spot_main_img) as ImageView
        var mainTitle : TextView = itemView.findViewById(R.id.tv_rv_item_spot_main_title) as TextView
    }
}