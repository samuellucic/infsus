package hr.unizg.fer.nis.controller

data class EstateCreateRequest(
    val price: Double,
    val address: String,
    val area: Int,
    val description: String,
    val ownerId: Long,
    val townId: Long,
    val estateTypeName: String
)

data class EstateUpdateRequest(
    val id: Long,
    val price: Double,
    val address: String,
    val area: Int,
    val description: String,
    val ownerId: Long,
    val townId: Long,
    val estateTypeName: String
)

data class EstateResponse(
    val id: Long?,
    val price: Double,
    val address: String,
    val area: Int,
    val description: String,
    val estateTypeName: String,
    val townId: Long?
)
