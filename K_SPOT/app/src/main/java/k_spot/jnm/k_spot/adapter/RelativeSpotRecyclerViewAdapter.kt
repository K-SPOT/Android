package k_spot.jnm.k_spot.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.data.RelativeSpotData

class RelativeSpotRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<RelativeSpotData>) : RecyclerView.Adapter<RelativeSpotRecyclerViewAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_category_detail_relative_spot_event, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = dataList[position].title
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val img : ImageView = itemView.findViewById(R.id.rv_item_categoty_detail_relative_spot_img) as ImageView
        val title : TextView = itemView.findViewById(R.id.rv_item_categoty_detail_relative_spot_title) as TextView

    }
}