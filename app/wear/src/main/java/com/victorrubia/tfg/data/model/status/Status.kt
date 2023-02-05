package com.victorrubia.tfg.data.model.status

/**
 * [Tag] of the [User]'s [Activity] at a certain [Timestamp].
 *
 * @property status status of the [User]'s [Activity]
 * @property context context of the [User]'s [Activity]
 * @property emotions emotions of the [User]'s [Activity]
 * @property feelings  feelings of the [User]'s [Activity]
 */
@kotlinx.serialization.Serializable
data class Status(
    val status: String,
    val context: ArrayList<String>,
    val emotions: String,
    val feelings: String,
)