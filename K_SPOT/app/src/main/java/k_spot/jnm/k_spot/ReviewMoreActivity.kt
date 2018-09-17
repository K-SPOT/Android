package k_spot.jnm.k_spot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.LinearLayout
import k_spot.jnm.k_spot.adapter.ReviewMoreActivityRecyclerAdapter
import k_spot.jnm.k_spot.adapter.ReviewMoreRecyclerAdpaterData
import kotlinx.android.synthetic.main.activity_review_more.*

class ReviewMoreActivity : AppCompatActivity() {

    lateinit var reviewMoreActivityRecyclerAdapter: ReviewMoreActivityRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_more)

        makeRecyclerView()

        setOnClickListenr()

        makeReviewMoreStar()
    }

    private fun makeRecyclerView() {
        val mRecyclerView = review_more_act_rv as RecyclerView
//        val mRecyclerView = view.findViewById(R.id.main_page_fragment_rv1) as RecyclerView
        mRecyclerView.setHasFixedSize(true)



        var reviewMoreRecyclerAdpaterData = ArrayList<ReviewMoreRecyclerAdpaterData>()

        reviewMoreRecyclerAdpaterData.add(ReviewMoreRecyclerAdpaterData("밤에 야경보러 갔는데","전망도 너무 예쁘고 좋았어요ㅜㅜ 다음엔 꼭 남자친구랑 가고 싶어요^^,,,,방탄 LOVE~!",R.color.colorAccent,3.5,"BTSLOVE","2018-08-31"))
        reviewMoreRecyclerAdpaterData.add(ReviewMoreRecyclerAdpaterData("밤에 야경보러 갔는데","전망도 너무 예쁘고 좋았어요ㅜㅜ 다음엔 꼭 남자친구랑 가고 싶어요^^,,,,방탄 LOVE~!",R.color.colorAccent,3.5,"BTSLOVE","2018-08-31"))
        reviewMoreRecyclerAdpaterData.add(ReviewMoreRecyclerAdpaterData("밤에 야경보러 갔는데","전망도 너무 예쁘고 좋았어요ㅜㅜ 다음엔 꼭 남자친구랑 가고 싶어요^^,,,,방탄 LOVE~!",R.color.colorAccent,3.5,"BTSLOVE","2018-08-31"))
        reviewMoreRecyclerAdpaterData.add(ReviewMoreRecyclerAdpaterData("밤에 야경보러 갔는데","전망도 너무 예쁘고 좋았어요ㅜㅜ 다음엔 꼭 남자친구랑 가고 싶어요^^,,,,방탄 LOVE~!",R.color.colorAccent,3.5,"BTSLOVE","2018-08-31"))
        reviewMoreRecyclerAdpaterData.add(ReviewMoreRecyclerAdpaterData("밤에 야경보러 갔는데","전망도 너무 예쁘고 좋았어요ㅜㅜ 다음엔 꼭 남자친구랑 가고 싶어요^^,,,,방탄 LOVE~!",R.color.colorAccent,3.5,"BTSLOVE","2018-08-31"))

        reviewMoreActivityRecyclerAdapter = ReviewMoreActivityRecyclerAdapter(reviewMoreRecyclerAdpaterData, applicationContext)
//        reviewMoreActivityRecyclerAdapter.setOnItemClickListener()
        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView.adapter = reviewMoreActivityRecyclerAdapter
    }

    fun setOnClickListenr() {
        review_more_act_back_btn.setOnClickListener {
            finish()
        }
    }

    // 큰 별 리뷰 만들기
    fun makeReviewMoreStar() {
        // starCount 통신으로 받아와야함.
        val starCount = 2.89

        // size 5의 이미지 뷰 배열 생성
        var stars: Array<ImageView?> = arrayOfNulls<ImageView>(5)
        // star 생성
        for (i in 0 until 5) {

            if (i < starCount) {

                // 별 반개를 표현 해야 할 때
                if (0.5 <= (starCount - i) && (starCount - i) <= 0.99) {
                    // 별 반개 그리는거
                    stars[i] = ImageView(applicationContext)
                    stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_big_halfstar))
                } else if (0 <= (starCount - i) && (starCount - i) < 0.5) {
                    // 마지막 별이 0~0.5일 때 꽉찬 별 그리는 거
                    stars[i] = ImageView(applicationContext)
                    stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_big_star_empty))
                } else {
                    // 꽉찬 별을 표현 해야 할 때
                    stars[i] = ImageView(applicationContext)
                    stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_big_star))
                }

            } else {
                // 빈 별
                stars[i] = ImageView(applicationContext)
                stars[i]!!.setImageDrawable(resources.getDrawable(R.drawable.category_reveiw_big_star_empty))
            }
            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            )

            // 인디케이터 점 마진 설정
            params.setMargins(3, 0, 3, 0)
            //LinearView에 뷰 생성
            review_more_act_review_star_ll!!.addView(stars[i], params)
        }


        //
        review_more_act_score_tv.text = starCount.toString()
    }

}
