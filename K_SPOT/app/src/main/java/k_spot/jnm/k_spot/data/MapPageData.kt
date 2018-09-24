package k_spot.jnm.k_spot.data

data class FilterOptionData(
        val distance : Double,
        val latitude : Double,
        val longitude : Double,
        val is_food : Int,
        val is_cafe : Int,
        val is_sights : Int,
        val is_event : Int,
        val is_etc :Int
)


