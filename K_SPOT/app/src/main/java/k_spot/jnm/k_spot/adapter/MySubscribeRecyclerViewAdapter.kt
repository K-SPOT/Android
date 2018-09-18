package k_spot.jnm.k_spot.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import k_spot.jnm.k_spot.R




class MySubscribeRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<MySubscribeRecyclerViewData>) : RecyclerView.Adapter<MySubscribeRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_my_page_fragment, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.img_btn.setImageResource(dataList[position].img)

        val drawable = ctx.getDrawable(R.drawable.rv_item_review_more_act_box_shape) as GradientDrawable

        holder.img_btn.background = drawable
        holder.img_btn.clipToOutline = true

        holder.engTitle.text = dataList[position].engTitle

        holder.korTitle.text = dataList[position].korTitle

    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val img_btn : ImageView = itemView.findViewById(R.id.my_page_frag_rv_item_img_btn) as ImageView
        val engTitle : TextView = itemView.findViewById(R.id.my_page_frag_rv_item_english_tv) as TextView
        val korTitle : TextView = itemView.findViewById(R.id.my_page_frag_rv_item_hangeul_tv) as TextView
        val subscribeImg : ImageView = itemView.findViewById(R.id.my_page_frag_rv_item_subscribe_iv) as ImageView
        val subscribeBtn : RelativeLayout = itemView.findViewById(R.id.my_page_frag_rv_item_subscribe_btn) as RelativeLayout
    }
}

data class MySubscribeRecyclerViewData(
        val img : Int,
        val engTitle : String,
        val korTitle : String,
        val flag : Boolean
)