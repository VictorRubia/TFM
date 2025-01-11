package com.victorrubia.tfm.data.model.activity_repository


import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ActivityRepository(
    @SerializedName("icon_url")
    val iconUrl: String, // /rails/active_storage/blobs/redirect/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBPdz09IiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--4a90c04a20290a03f70776facdb9f187cb9de822/storefront_FILL0_wght400_GRAD0_opsz48.png
    @PrimaryKey
    @SerializedName("id")
    val id: Int, // 3
    @SerializedName("name")
    val name: String, // Comprar en el supermercado
    @SerializedName("name_wearos")
    val nameWearos: String // Comprar en el súper
)