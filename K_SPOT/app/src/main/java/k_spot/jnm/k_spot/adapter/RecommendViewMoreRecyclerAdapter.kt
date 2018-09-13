package k_spot.jnm.k_spot.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import k_spot.jnm.k_spot.R

class RecommendViewMoreRecyclerAdapter (private var recommendViewMorePageItems : ArrayList<RecommendViewMoreRecyclerAdpaterData>, private var ctx: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainView : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_recommend_view_more_act, parent, false)
        return RecommendViewMoreRecyclerAdapter.Holder(mainView)
    }

    override fun getItemCount(): Int {
        return recommendViewMorePageItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var holder : Holder = holder as Holder


        holder.recommend_view_more_index.text = (position+1).toString()

        holder.recommend_view_more_title.text = recommendViewMorePageItems[position].title



        // image수가 한 개 일 경우
        if(recommendViewMorePageItems[position].Image.size == 1){

            holder.recommed_view_more_image_second_ll.visibility = View.GONE
            holder.recommend_view_more_image1.setImageResource(recommendViewMorePageItems[position].Image[0])

        }else if(recommendViewMorePageItems[position].Image.size == 2){

            holder.recommed_view_more_image_second_ll.visibility = View.VISIBLE
            holder.recommend_view_more_image3.visibility = View.GONE
            holder.recommend_view_more_image1.setImageResource(recommendViewMorePageItems[position].Image[0])
            holder.recommend_view_more_image2.setImageResource(recommendViewMorePageItems[position].Image[1])

        }else if(recommendViewMorePageItems[position].Image.size == 3){

            holder.recommed_view_more_image_second_ll.visibility = View.VISIBLE
            holder.recommend_view_more_image1.setImageResource(recommendViewMorePageItems[position].Image[0])
            holder.recommend_view_more_image2.setImageResource(recommendViewMorePageItems[position].Image[1])
            holder.recommend_view_more_image3.setImageResource(recommendViewMorePageItems[position].Image[2])

        }else if(recommendViewMorePageItems[position].Image.size > 3){

            holder.recommend_view_more_image1.setImageResource(recommendViewMorePageItems[position].Image[0])
            holder.recommend_view_more_image2.setImageResource(recommendViewMorePageItems[position].Image[1])
            //
            holder.recommend_view_more_image3.setImageResource(R.drawable.category_reveiw_small_star)

        }

        holder.recommend_view_more_text1.text = recommendViewMorePageItems[position].text1

        holder.recommend_view_more_text2.text = recommendViewMorePageItems[position].text2

        holder.recommend_view_more_text3.text = recommendViewMorePageItems[position].text3
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recommend_view_more_index : TextView = itemView!!.findViewById(R.id.recommend_view_more_rv_item_index_tv)
        var recommend_view_more_title : TextView = itemView!!.findViewById(R.id.recommend_view_more_rv_item_title_tv)
        var recommend_view_more_image1 : ImageView = itemView!!.findViewById(R.id.recommend_view_more_rv_item_contents_iv1)
        var recommend_view_more_image2 : ImageView = itemView!!.findViewById(R.id.recommend_view_more_rv_item_contents_iv2)
        var recommend_view_more_image3 : ImageView = itemView!!.findViewById(R.id.recommend_view_more_rv_item_contents_iv3)
        var recommed_view_more_image_second_ll : LinearLayout = itemView!!.findViewById(R.id.recommend_view_more_rv_item_contents_three_ll2)
        var recommend_view_more_text1 : TextView = itemView!!.findViewById(R.id.recommend_view_more_rv_item_first_explain_tv)
        var recommend_view_more_text2 : TextView = itemView!!.findViewById(R.id.recommend_view_more_rv_item_explain_second_tv)
        var recommend_view_more_text3 : TextView = itemView!!.findViewById(R.id.recommend_view_more_rv_item_explain_third_tv)
    }
}

data class RecommendViewMoreRecyclerAdpaterData(
        // 원래는 String
        var title : String,
        var Image : ArrayList<Int>,
        var text1 : String,
        var text2 : String,
        var text3 : String
)
