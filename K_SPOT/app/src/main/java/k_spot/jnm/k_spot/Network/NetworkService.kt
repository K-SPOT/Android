package k_spot.jnm.k_spot.Network

import k_spot.jnm.k_spot.Delete.DeleteChannelScripteResponse
import k_spot.jnm.k_spot.Get.*
import k_spot.jnm.k_spot.Post.PostChannelSubscripeResponse
import k_spot.jnm.k_spot.Post.PostKakaoResponse
import k_spot.jnm.k_spot.Post.PostSpotReviewWriteResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    // 1. 유저 구독 정보 가져오기 made by 형민
    @GET("user/subscription/")
    fun getUserSubscribe(
            @Header("flag") flag: Int? = 0,
            @Header("authorization") tokenValue: String? = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODk0MzI2NTc4LCJpYXQiOjE1MzY3MjAwODcsImV4cCI6MTUzOTMxMjA4N30.GnYMR2wVmllHQaIri5O4uXD6TqWwxyAxS3O719hxr-M"
    ): Call<GetUserSubscribeResponse>

    @GET("channel/list/")
    fun getCategoryList(
            @Header("flag") flag: Int? = 0,
            @Header("authorization") tokenValue: String? = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODk0MzI2NTc4LCJpYXQiOjE1MzY3MjAwODcsImV4cCI6MTUzOTMxMjA4N30.GnYMR2wVmllHQaIri5O4uXD6TqWwxyAxS3O719hxr-M"
    ): Call<GetCategoryListResponse>

    @GET("main/")
    fun getMainFrag(
            @Header("flag") flag: Int?,
            @Header("authorization") tokenValue: String?
    ): Call<GetMainFragResponse>

    @GET("theme/{theme_id}")
    fun getThemeDetail(
            @Header("flag") flag: Int?,
            @Header("authorization") tokenValue: String?,
            @Path("theme_id") theme_id: Int
    ): Call<GetThemeDetailResponse>

    @GET("search/")
    fun getSearchView(
            @Header("flag") flag: Int?,
            @Header("authorization") tokenValue: String?
    ): Call<GetSearchViewResponse>

    @GET("search/{keyword}")
    fun getSearchResult(
            @Header("flag") flag: Int?,
            @Header("authorization") tokenValue: String?,
            @Path("keyword") keyword: String
    ): Call<GetSearchResultResponse>


    // 카카오 로그인 통신
    @FormUrlEncoded
    @POST("user/kakao/signin")
    fun postKakaoLogin(
            @Header("flag") flag: Int?,
            @Header("authorization") tokenValue: String?,
            @Field("access_token") access_token: String
    ): Call<PostKakaoResponse>


    //카테고리 상세보기 통신
    @GET("channel/detail/{channel_id}")
    fun getCategotyDetailResponse(
            @Header("flag") flag: Int?,
            @Header("authorization") tokenValue: String?,
            @Path("channel_id") channel_id: Int
    ): Call<GetCategoryDetailResponse>

    //채널 구독하기
    @FormUrlEncoded
    @POST("channel/subscription")
    fun postChannelSubscripeResponse(
            @Header("flag") flag: Int?,
            @Header("authorization") tokenValue: String?,
            @Field("channel_id") channel_id: Int
    ): Call<PostChannelSubscripeResponse>

    //채널 구독 취소
    @DELETE("channel/subscription/{channel_id}")
    fun deleteChannelSubscripeResponse(
            @Header("flag") flag: Int?,
            @Header("authorization") tokenValue: String?,
            @Path("channel_id") channel_id: Int
    ): Call<DeleteChannelScripteResponse>

    // SpotViewMoreAct + 리뷰 더보기 통신
    @GET("spot/{spot_id}/detail")
    fun getSpotViewMore(
            @Header("flag") flag: Int?,
            @Header("authorization") tokenValue: String?,
            @Path("spot_id") spot_id: Int
    ): Call<GetSpotViewMoreResponse>

    // 리뷰 더보기
    @GET("spot/{spot_id}/review")
    fun getSpotReViewMore(
            @Header("flag") flag: Int?,
            @Header("authorization") tokenValue: String?,
            @Path("spot_id") spot_id: Int
    ): Call<GetSpotViewReviewMoreResponse>

    // myPage 가져오기
    @GET("user/mypage")
    fun getMyPage(
            @Header("flag") flag: Int?,
            @Header("authorization") tokenValue: String?
    ): Call<GetMyPageResponse>

    // 리뷰 글 쓰기 ( 실패 )
    @Multipart
    @POST("spot/review")
    fun postSpotReviewWriteResponse(
            @Header("flag") flag: Int?,
            @Header("authorization") tokenValue: String?,
            @Part("spot_id") spot_id: Int,
            @Part("title") title : String,
            @Part("content") content : String,
            @Part review_img: MultipartBody.Part?,
            @Part("review_score") review_score : Double
    ) : Call<PostSpotReviewWriteResponse>

    //채널 구독하기
    @FormUrlEncoded
    @POST("spot/scrap")
    fun postSpotSubscripeResponse(
            @Header("flag") flag: Int?,
            @Header("authorization") tokenValue: String?,
            @Field("spot_id") spot_id: Int
    ): Call<PostChannelSubscripeResponse>

    //채널 구독 취소
    @DELETE("spot/scrap/{spot_id}")
    fun deleteSpotSubscripeResponse(
            @Header("flag") flag: Int?,
            @Header("authorization") tokenValue: String?,
            @Path("spot_id") spot_id: Int
    ): Call<DeleteChannelScripteResponse>

    //채널에서 더보기
    @GET("channel/{channel_id}/spot/{is_event}")
    fun getChannelViewMoreResponse(
            @Header("flag") flag : Int?,
            @Header("authorization") tokenValue : String?,
            @Path("channel_id") channel_id : Int,
            @Path("is_event") is_event : Int
    ) : Call<GetChannelViewMoreResponse>

    //맵페이지 스팟 불러오기(GPS로)
    @GET("/spot/{distance}/{latitude}/{longitude}/{is_food}/{is_cafe}/{is_sights}/{is_event}/{is_etc}")
    fun getMapPageDataFromGPSResponse(
            @Header("flag") flag : Int?,
            @Header("authorization") tokenValue : String?,
            @Path("distance") distance : Double,
            @Path("latitude") latitude : Double,
            @Path("longitude") longitude : Double,
            @Path("is_food") is_food : Int,
            @Path("is_cafe") is_cafe : Int,
            @Path("is_sights") is_sights : Int,
            @Path("is_event") is_event : Int,
            @Path("is_etc") is_etc : Int
    ) : Call<GetMapPageDataFromGPSResponse>

}