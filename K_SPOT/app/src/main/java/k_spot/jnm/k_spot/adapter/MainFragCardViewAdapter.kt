package k_spot.jnm.k_spot.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import k_spot.jnm.k_spot.R



class MainFragCardViewAdapter(val ctx : Context, val myDataset : ArrayList<MainFragRecyclerViewData>) : RecyclerView.Adapter<MainFragCardViewAdapter.ViewHolder>() {

    lateinit var mDataset: ArrayList<MainFragRecyclerViewData>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        mDataset = myDataset
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_main_farg_card_view, parent, false)
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
            rootLayoutParams.leftMargin = (24*dp).toInt()
        }

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position].title)
        holder.mTextView2.setText(mDataset[position].place_name)
        holder.mImageView.setImageResource(mDataset[position].place_img)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val rl : CardView = itemView.findViewById(R.id.main_frag_rv_item_card_view) as CardView
        val mImageView : ImageView = itemView.findViewById(R.id.main_frag_rv_item_card_view_place_iv) as ImageView
        val mTextView2 : TextView = itemView.findViewById(R.id.main_frag_rv_item_card_view_place_tv) as TextView
        val mTextView : TextView = itemView.findViewById(R.id.main_frag_rv_item_card_view_title_tv) as TextView
    }
}

data class MainFragRecyclerViewData (
        val place_name : String,
        val title : String,
        // 지금은 Int 통신 시작하면 String으로 바꿔줘야함
        val place_img : Int
)