package k_spot.jnm.k_spot.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import k_spot.jnm.k_spot.Get.ChannelMyPageData
import k_spot.jnm.k_spot.Get.GetMyPageResponse
import k_spot.jnm.k_spot.Get.UserMyPageData
import k_spot.jnm.k_spot.Network.ApplicationController
import k_spot.jnm.k_spot.Network.NetworkService
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.adapter.MySubscribeRecyclerViewAdapter
import k_spot.jnm.k_spot.db.SharedPreferenceController
import kotlinx.android.synthetic.main.fragment_my_page.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageFragment : Fragment(){

    lateinit var networkService : NetworkService
    lateinit var userMyPageData : UserMyPageData
    lateinit var ChannelMyPageData : ArrayList<ChannelMyPageData>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        getMyPage()
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toast("마이 페이지")
    }

    private fun setMySubscribeRecyclerView(ChannelMyPageData : ArrayList<ChannelMyPageData>){

        my_page_frag_my_subscribe_rv.layoutManager = LinearLayoutManager(context, 0, false)
        my_page_frag_my_subscribe_rv.adapter = MySubscribeRecyclerViewAdapter(this!!.context!!, ChannelMyPageData)
    }

    fun getMyPage() {

        networkService = ApplicationController.instance.networkService
        val authorization: String = SharedPreferenceController.getAuthorization(context = this!!.context!!)
        val getMyPageResponse = networkService.getMyPage(0, authorization)
        getMyPageResponse.enqueue(object : Callback<GetMyPageResponse> {
            override fun onFailure(call: Call<GetMyPageResponse>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<GetMyPageResponse>?, response: Response<GetMyPageResponse>?) {
                if (response!!.isSuccessful) {
                    userMyPageData = response!!.body()!!.data!!.user
                    ChannelMyPageData = ArrayList()
                    ChannelMyPageData = response!!.body()!!.data!!.channel

                    Glide.with(ctx).load(userMyPageData.profile_img).into(my_page_frag_my_info_iv)
                    my_page_frag_my_name_tv.text = userMyPageData.name
                    setMySubscribeRecyclerView(ChannelMyPageData)
                    setMySubscribeRecyclerView(ChannelMyPageData)


                }
            }

        })
    }
}
