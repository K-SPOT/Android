package k_spot.jnm.k_spot.Get

data class GetSearchResultResponse (
        val message: String,
        val data: SearchResultData
)

data class SearchResultData (
        val channel: ArrayList<ChannelSearchResultData>,
        val place: ArrayList<PlaceSearchResultData>,
        val event: ArrayList<PlaceSearchResultData>
)

data class ChannelSearchResultData (
        val channel_id: Int,
        val name: String,
        val subscription_cnt: Int,
        val spot_cnt: Int,
        val thumbnail_img: String,
        val subscription: Int
)

data class PlaceSearchResultData (
        val type: Int,
        val spot_id: Int,
        val name: String,
        val description: String,
        val img: String,
        val address_gu: String,
        val station: String,
        val scrap_cnt: Int
)

//data class EventSearchResultData (
//        val type: Int,
//        val spot_id: Int,
//        val name: String,
//        val description: String,
//        val img: String,
//        val address_gu: String,
//        val station: String,
//        val scrap_cnt: Int
//)
