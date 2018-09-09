package k_spot.jnm.k_spot.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import k_spot.jnm.k_spot.R

class CategoryPageFragRecyclerAdapter (private var categoryPageItems : ArrayList<CategoryPageFragRecyclerAdpaterData>, private var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
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

        holder.category_list_image.setImageResource(categoryPageItems[position].Image)

        // 통신 시 주석 제거
//        Glide.with(context)
//                .load(categoryPageItems[position].Image)
//                .into(holder.category_list_image)

        holder.category_list_name.text = categoryPageItems[position].name

        holder.category_list_sub_num.text = categoryPageItems[position].sub_num

        holder.category_list_post_num.text = categoryPageItems[position].post_num

        if(categoryPageItems[position].flag == true){
            holder.category_list_sub_btn_image.setImageResource(R.drawable.category_list_sub_btn)
        }else{
            holder.category_list_sub_btn_image.setImageResource(R.drawable.category_list_unsub_btn)
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category_list_index : TextView = itemView!!.findViewById(R.id.category_list_fragment_rv_item_num_tv)
        var category_list_image : ImageView = itemView!!.findViewById(R.id.category_list_fragment_rv_item_image_iv)
        var category_list_name : TextView = itemView!!.findViewById(R.id.category_list_fragment_rv_item_name_tv)
        var category_list_sub_num : TextView = itemView!!.findViewById(R.id.category_list_fragment_rv_item_subscriber_num_tv)
        var category_list_post_num : TextView = itemView!!.findViewById(R.id.category_list_fragment_rv_item_post_num_tv)
        var category_list_sub_btn_image : ImageView = itemView!!.findViewById(R.id.category_list_fragment_rv_item_subscribe_iv)
    }
}

data class CategoryPageFragRecyclerAdpaterData(
        var Image : Int,
        var name : String,
        var sub_num : String,
        var post_num : String,
        var flag : Boolean
)