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
import com.bumptech.glide.Glide
import k_spot.jnm.k_spot.Get.Main
import k_spot.jnm.k_spot.R



class MainFragCardViewAdapter(val ctx : Context, val myDataset : ArrayList<Main>) : RecyclerView.Adapter<MainFragCardViewAdapter.ViewHolder>() {

    lateinit var mDataset: ArrayList<Main>


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
        holder.mTextView.setText(mDataset[position].name)
        holder.mTextView2.setText(mDataset[position].description)
        Glide.with(ctx).load(mDataset[position].img).into(holder.mImageView)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val rl : CardView = itemView.findViewById(R.id.main_frag_rv_item_card_view) as CardView
        val mImageView : ImageView = itemView.findViewById(R.id.main_frag_rv_item_card_view_place_iv) as ImageView
        val mTextView2 : TextView = itemView.findViewById(R.id.main_frag_rv_item_card_view_place_tv) as TextView
        val mTextView : TextView = itemView.findViewById(R.id.main_frag_rv_item_card_view_title_tv) as TextView
    }
}
