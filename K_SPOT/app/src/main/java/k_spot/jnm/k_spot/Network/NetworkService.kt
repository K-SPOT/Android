package k_spot.jnm.k_spot.Network

import k_spot.jnm.k_spot.Post.PostKakaoResponse
import k_spot.jnm.k_spot.Get.GetCategoryListResponse
import k_spot.jnm.k_spot.Get.GetMainFragResponse
import k_spot.jnm.k_spot.Get.GetUserSubscribeResponse
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    // 1. 유저 구독 정보 가져오기 made by 형민
    @GET("user/subscription/")
    fun getUserSubscribe(
            @Header("flag") flag : Int? = 0,
            @Header("authorization") tokenValue : String? = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODk0MzI2NTc4LCJpYXQiOjE1MzY3MjAwODcsImV4cCI6MTUzOTMxMjA4N30.GnYMR2wVmllHQaIri5O4uXD6TqWwxyAxS3O719hxr-M"
    ) : Call<GetUserSubscribeResponse>

    @GET("channel/list/")
    fun getCategoryList(
            @Header("flag") flag : Int? = 0,
            @Header("authorization") tokenValue : String? = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODk0MzI2NTc4LCJpYXQiOjE1MzY3MjAwODcsImV4cCI6MTUzOTMxMjA4N30.GnYMR2wVmllHQaIri5O4uXD6TqWwxyAxS3O719hxr-M"
    ) : Call<GetCategoryListResponse>

    @GET("main/")
    fun getMainFrag(
            @Header("flag") flag : Int?,
            @Header("authorization") tokenValue : String?
    ) : Call<GetMainFragResponse>

    // 카카오 로그인 통신
    @FormUrlEncoded
    @POST("user/kakao/signin")
    fun postKakaoLogin(
            @Header("flag") flag : Int?,
            @Header("authorization") tokenValue : String?,
            @Field("access_token") access_token: String
    ): Call<PostKakaoResponse>

}