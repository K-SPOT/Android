package k_spot.jnm.k_spot

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import k_spot.jnm.k_spot.adapter.SubscribeActRecyclerViewAdapter
import k_spot.jnm.k_spot.adapter.SubscribeActRecyclerViewAdapterData
import kotlinx.android.synthetic.main.activity_subscribe.*


class SubscribeActivity : AppCompatActivity() {

    // tabFlag가 true일 땐 연예인 탭 활성화
    // tabFlag가 false일 땐 방송 탭 활성화
    var tabFlag = true

    // 초록색
    var tabActiveColor = Color.parseColor("#40D39F")

    // 검정색
    var tabUnActiveColor = Color.parseColor("#000000")


    lateinit var subscribeActBroadCastTabItems: ArrayList<SubscribeActRecyclerViewAdapterData>
    lateinit var subscribeActCelebTabItems: ArrayList<SubscribeActRecyclerViewAdapterData>
    lateinit var subscribeActRecyclerViewAdapter: SubscribeActRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe)

        setOnClickListener()
    }

    fun setOnClickListener() {

        // 방송 탭 버튼
        subscribe_act_broadcast_btn.setOnClickListener {

            // tabFlag = true (연예인 탭 활성화)
            // 연예인 탭이 활성화 돼있는 경우에만 애니메이션 구동
            if(tabFlag == true){

                // 탭바를 오른쪽 방송 탭 밑으로 이동!
                clickBoradcastTabAnimation()

                // 방송 tv를 Green으로 0.3초동안 서서히 변경!
                convertGreenAnimation(subscribe_act_broadcast_tv)

                // 연예인 tv를 Black으로 0.3초동안 서서히 변경!
                convertBlackAnimation(subscribe_act_celeb_tv)

                tabFlag = false



                // 통신하면 당연히 삭제.
                subscribeActBroadCastTabItems = ArrayList()
                subscribeActBroadCastTabItems.add(SubscribeActRecyclerViewAdapterData(R.drawable.category_list_blackpink_img,"블랙핑크",false))
                subscribeActBroadCastTabItems.add(SubscribeActRecyclerViewAdapterData(R.drawable.category_list_bts_img,"BTS",true))
                subscribeActBroadCastTabItems.add(SubscribeActRecyclerViewAdapterData(R.drawable.category_list_exo_img,"엑소",true))
                var i = 0
                while (i < 20) {
                    subscribeActBroadCastTabItems.add(SubscribeActRecyclerViewAdapterData(R.drawable.category_list_blackpink_img,"블랙핑크",true))
                    i++
                }
                makeSubscribeActRecyclerView(subscribeActBroadCastTabItems)

            }
        }

        // 연예인 탭 버튼
        subscribe_act_celeb_btn.setOnClickListener {

            // tabFlag = false (방송 탭 활성화)
            // 방송 탭이 활성화 돼있는 경우에만 애니메이션 구동
            if(tabFlag == false) {

                // 탭바를 왼쪽 연예인 탭 밑으로 0.3초동안 이동!
                clickCelebTabAnimation()

                // 연예인 tv를 Green으로 0.3초동안 서서히 변경!
                convertGreenAnimation(subscribe_act_celeb_tv)

                // 방송 tv를 Black으로 0.3초동안 서서히 변경!
                convertBlackAnimation(subscribe_act_broadcast_tv)

                tabFlag = true

                // 통신하면 당연히 삭제.
                subscribeActCelebTabItems = ArrayList()
                subscribeActCelebTabItems.add(SubscribeActRecyclerViewAdapterData(R.drawable.category_list_bts_img,"BTS",true))
                subscribeActCelebTabItems.add(SubscribeActRecyclerViewAdapterData(R.drawable.category_list_exo_img,"엑소",true))
                subscribeActCelebTabItems.add(SubscribeActRecyclerViewAdapterData(R.drawable.category_list_blackpink_img,"블랙핑크",false))
                var j = 0
                while (j < 20) {
                    subscribeActCelebTabItems.add(SubscribeActRecyclerViewAdapterData(R.drawable.category_list_blackpink_img,"블랙핑크",true))
                    j++
                }
                makeSubscribeActRecyclerView(subscribeActCelebTabItems)

            }
        }

        // 백 버튼
        subscribe_act_back_btn.setOnClickListener {
            finish()
        }




    }

    // 탭바를 오른쪽 방송 탭 밑으로 이동!
    private fun clickBoradcastTabAnimation(){
        val anim = AnimationUtils
                .loadAnimation(applicationContext,
                        R.anim.subscribe_tab_convert_broadcast_anim)
        subscribe_act_tab_line.startAnimation(anim)
        subscribe_act_tab_line.visibility = View.GONE
    }

    private fun clickCelebTabAnimation(){
        val anotherAnim = AnimationUtils
                .loadAnimation(applicationContext,
                        R.anim.subscribe_tab_convert_celeb_anim)
        subscribe_act_tab_line.startAnimation(anotherAnim)
        subscribe_act_tab_line.visibility = View.VISIBLE
    }

    // tv를 Green으로 0.3초동안 서서히 변경!
    private fun convertGreenAnimation(textView: TextView){
        val convertGreenAnimation = ValueAnimator.ofObject(ArgbEvaluator(), tabUnActiveColor, tabActiveColor)
        convertGreenAnimation.duration = 300
        convertGreenAnimation.addUpdateListener { animator -> textView.setTextColor(animator.animatedValue as Int)}
        convertGreenAnimation.start()
    }

    // tv를 Black으로 0.3초동안 서서히 변경!
    private fun convertBlackAnimation(textView: TextView){
        val convertBlackAnimation = ValueAnimator.ofObject(ArgbEvaluator(), tabActiveColor, tabUnActiveColor)
        convertBlackAnimation.duration = 300
        convertBlackAnimation.addUpdateListener { animator -> textView.setTextColor(animator.animatedValue as Int)}
        convertBlackAnimation.start()
    }

    private fun makeSubscribeActRecyclerView(subscribeActItems : ArrayList<SubscribeActRecyclerViewAdapterData>) {
        subscribeActRecyclerViewAdapter = SubscribeActRecyclerViewAdapter(subscribeActItems, applicationContext)
        subscribe_act_rv.layoutManager = LinearLayoutManager(applicationContext)
        subscribe_act_rv.adapter = subscribeActRecyclerViewAdapter
    }


}
