package k_spot.jnm.k_spot.data

data class MyAroundKSpotData(
        val url : String,
        val title : String,
        val start_pnt : Float,
        val content : String,
        val address : String,
        val badges : ArrayList<String>
)