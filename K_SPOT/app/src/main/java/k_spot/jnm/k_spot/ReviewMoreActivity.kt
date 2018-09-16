package k_spot.jnm.k_spot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import k_spot.jnm.k_spot.adapter.ReviewMoreActivityRecyclerAdapter
import k_spot.jnm.k_spot.adapter.ReviewMoreRecyclerAdpaterData
import kotlinx.android.synthetic.main.activity_review_more.*

class ReviewMoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_more)

        makeRecyclerView()
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

        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView.adapter = ReviewMoreActivityRecyclerAdapter(reviewMoreRecyclerAdpaterData, applicationContext)
    }
}
