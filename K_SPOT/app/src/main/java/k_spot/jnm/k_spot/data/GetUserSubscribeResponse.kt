package k_spot.jnm.k_spot.data

data class GetUserSubscribeResponse (
        val message: String,
        val data: UserSubscribeData
)

data class UserSubscribeData (
        val celebrity: List<Broadcast>,
        val broadcast: List<Broadcast>
)

data class Broadcast (
        val channel_id: Long,
        val name: String,
        val thumbnail_img: String,
        val new_post_check: Long
)

