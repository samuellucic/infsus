package hr.unizg.fer.nis.ports.usecases.requests

import java.util.*

data class TourInfo(
    var requestId: String? = null,
    var termFits: Boolean? = null,
    var tourDateTime: String? = null,
    var agentId: String? = null,
    var buyerId: String? = null,
    var estateId: String? = null,
    var startTime: Date? = null,
    var endTime: Date? = null,
    var ended: Boolean? = null,
    var pid: String? = null,
    )