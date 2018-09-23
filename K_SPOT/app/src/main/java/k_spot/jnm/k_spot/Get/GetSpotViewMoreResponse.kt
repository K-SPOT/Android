package k_spot.jnm.k_spot.Get

data class GetSpotViewMoreResponse (
        val message: String,
        val data: ArrayList<SpotViewMoreData>
)

data class SpotViewMoreData (
        val spot_id: Int,
        val img: ArrayList<String>,
        val name: String,
        val description: String,
        val address: String,
        val review_score: Double,
        val review_cnt: Int,
        val line_number: String,
        val station: String,
        val prev_station: String,
        val next_station: String,
        val open_time: String,
        val close_time: String,
        val contact: String,
        val scrap_cnt: Int,
        val is_scrap: Int,
        val channel: ChannelSpotViewMoreData,
        val reviews: ArrayList<ReviewSpotViewMoreData>
)

data class ChannelSpotViewMoreData (
        val channel_id: ArrayList<String>,
        val channel_name: ArrayList<String>,
        val thumbnail_img: ArrayList<String>,
        val is_subscription: ArrayList<String>
)

data class ReviewSpotViewMoreData (
        val review_id : Int,
        val name: String,
        val title: String,
        val content: String,
        val img: String,
        val review_score: Double,
        val reg_time: String
)

data class ChannelRecyclerViewData (
        val channel_id: String,
        val channel_name: String,
        val thumbnail_img: String,
        val is_subscription: String
)


data class ViewPagerSpotViewMoreActData(
        val img : String
)