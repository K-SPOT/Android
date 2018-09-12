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

class SpotViewMoreActCardViewAdapter(val ctx : Context, val myDataset : ArrayList<SpotViewMoreActRecyclerViewData>) : RecyclerView.Adapter<SpotViewMoreActCardViewAdapter.ViewHolder>() {

    lateinit var mDataset: ArrayList<SpotViewMoreActRecyclerViewData>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        mDataset = myDataset
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_view_more_act, parent, false)
        val vh = ViewHolder(v)
        return vh
    }

    override fun getItemCount(): Int {
        mDataset = myDataset
        return mDataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dp = ctx.resources.displayMetrics.density
        val rootLayoutParams : RelativeLayout.LayoutParams = holder.rl.layoutParams as RelativeLayout.LayoutParams
        if (position == 0) {
            rootLayoutParams.leftMargin = (16*dp).toInt()
        }

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(mDataset[position].title)
        holder.mImageView.setImageResource(mDataset[position].img)

        if(mDataset[position].subscribeFlag == true){
            holder.subscribeBtn.setImageResource(R.drawable.category_list_unsub_btn)
        }else{
            holder.subscribeBtn.setImageResource(R.drawable.category_list_sub_btn)
        }


    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val rl : RelativeLayout = itemView.findViewById(R.id.view_more_act_rv_item_card_view) as RelativeLayout
        val mImageView : ImageView = itemView.findViewById(R.id.view_more_act_rv_item_broadcast_iv) as ImageView
        val title : TextView = itemView.findViewById(R.id.view_more_act_rv_item_broadcast_title_tv) as TextView
        val subscribeBtn : ImageView = itemView.findViewById(R.id.view_more_act_rv_item_subscribe_tv) as ImageView
    }
}

data class SpotViewMoreActRecyclerViewData (
        // 지금은 Int 통신 시작하면 String으로 바꿔줘야함
        val img : Int,
        val title : String,
        val subscribeFlag : Boolean
)