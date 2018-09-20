package k_spot.jnm.k_spot.Network

import k_spot.jnm.k_spot.data.GetCategoryListResponse
import k_spot.jnm.k_spot.data.GetUserSubscribeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

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
}