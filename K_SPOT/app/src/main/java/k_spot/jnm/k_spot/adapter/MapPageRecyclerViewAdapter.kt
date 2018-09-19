package k_spot.jnm.k_spot.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.data.MyAroundKSpotData

class MapPageRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<MyAroundKSpotData>): RecyclerView.Adapter<MapPageRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_map_page_my_around_k_spot, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //holder.image
        holder.title.text = dataList[position].title
        holder.starPoint.text = dataList[position].start_pnt.toString()
        holder.content.text = dataList[position].content
        holder.address.text = dataList[position].address
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.rv_item_map_page_my_around_k_spot_img) as ImageView
        val title : TextView = itemView.findViewById(R.id.rv_item_map_page_my_around_k_spot_title) as TextView
        val content : TextView = itemView.findViewById(R.id.rv_item_map_page_my_around_k_spot_content) as TextView
        val starPoint : TextView = itemView.findViewById(R.id.rv_item_map_page_my_around_k_spot_star_pnt) as TextView
        val address : TextView = itemView.findViewById(R.id.rv_item_map_page_my_around_k_spot_address) as TextView
        //val badgeList : ImageView = itemView.findViewById(R.id.rv_item_map_page_my_around_k_spot_img) as ImageView
    }

}