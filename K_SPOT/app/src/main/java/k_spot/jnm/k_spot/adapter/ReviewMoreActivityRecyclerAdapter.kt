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

class ReviewMoreActivityRecyclerAdapter (private var reviewMoreRecyclerAdpaterData : ArrayList<ReviewMoreRecyclerAdpaterData>, private var ctx: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainView : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_review_more_act, parent, false)
        return ReviewMoreActivityRecyclerAdapter.Holder(mainView)
    }

    override fun getItemCount(): Int {
        return reviewMoreRecyclerAdpaterData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var holder : Holder = holder as Holder


        holder.review_more_title.text = reviewMoreRecyclerAdpaterData[position].title

        holder.review_more_content.text = reviewMoreRecyclerAdpaterData[position].content

        holder.review_more_img.setImageResource(reviewMoreRecyclerAdpaterData[position].Image)

        holder.review_more_writer.text = reviewMoreRecyclerAdpaterData[position].writer

        holder.review_more_date.text = reviewMoreRecyclerAdpaterData[position].date


        // starCount 통신으로 받아와야함.
        val starCount = reviewMoreRecyclerAdpaterData[position].score

        // size 5의 이미지 뷰 배열 생성
        var stars: Array<ImageView?> = arrayOfNulls<ImageView>(5)
        // star 생성
        for (i in 0 until 5) {

            if (i < starCount) {

                // 별 반개를 표현 해야 할 때
                if (0.5 <= (starCount - i) && (starCount - i) <= 0.99) {
                    // 별 반개 그리는거
                    stars[i] = ImageView(ctx)
                    stars[i]!!.setImageResource(R.drawable.reveiw_page_small_star_half)
//                    stars[i]!!.setImageDrawable(R.drawable.review_page_big_star)

//                    stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_small_star_half))
                } else if (0 <= (starCount - i) && (starCount - i) < 0.5) {
                    // 마지막 별이 0~0.5일 때 꽉찬 별 그리는 거
                    stars[i] = ImageView(ctx)
                    stars[i]!!.setImageResource(R.drawable.reveiw_page_small_star)
//                    stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_small_star))
                } else {
                    // 꽉찬 별을 표현 해야 할 때
                    stars[i] = ImageView(ctx)
                    stars[i]!!.setImageResource(R.drawable.reveiw_page_small_star)
//                    stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_small_star))
                }

            } else {
                // 빈 별
                stars[i] = ImageView(ctx)
                stars[i]!!.setImageResource(R.drawable.reveiw_page_small_star_empty)
//                stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_small_star_empty))
            }
            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            )

            // 인디케이터 점 마진 설정
            params.setMargins(3, 0, 3, 0)
            //LinearView에 뷰 생성
            holder.review_more_star!!.addView(stars[i], params)
        }




    }


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var review_more_title : TextView = itemView!!.findViewById(R.id.review_more_act_rv_item_title)
        var review_more_content : TextView = itemView!!.findViewById(R.id.review_more_act_rv_item_content_tv)
        var review_more_img : ImageView = itemView!!.findViewById(R.id.review_more_act_rv_item_content_iv)
        var review_more_star : LinearLayout = itemView!!.findViewById(R.id.review_more_act_rv_item_star_ll)
        var review_more_writer : TextView = itemView!!.findViewById(R.id.review_more_act_rv_item_writer_tv)
        var review_more_date : TextView = itemView!!.findViewById(R.id.review_more_act_rv_item_date_tv)
    }
}

data class ReviewMoreRecyclerAdpaterData(
        // 원래는 String
        var title : String,
        var content : String,
        var Image : Int,
        var score : Double,
        var writer : String,
        var date : String

)