package com.victorrubia.tfg.data.model.tag_repository


import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class TagRepository(
    @SerializedName("icon_url")
    val iconUrl: String, // /rails/active_storage/blobs/redirect/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBOUT09IiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--a1028bf1e2b6c83dc55583a7fc88396fa30ac84a/carro.png
    @PrimaryKey
    @SerializedName("id")
    val id: Int, // 8
    @SerializedName("name")
    val name: String, // Cesta de la compra
    @SerializedName("name_wearos")
    val nameWearos: String, // DEJAR CARRO
    @SerializedName("type")
    val type: Int // 1
)