package com.chill.mallang.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JoinResponse(
    @SerialName("is_registered")
    val isRegister: Boolean? = false,
    @SerialName("token")
    val token: String? = "",
)