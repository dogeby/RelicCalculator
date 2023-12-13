package com.dogeby.reliccalculator.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkElement(
    val id: String,
    val name: String,
    val icon: String,
)
