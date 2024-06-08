package hr.unizg.fer.nis.ports.usecases.requests

import java.util.*

data class TaskInfo(
    val tId: String,
    val taskName: String,
    val taskKey: String,
    val pId: String,
    val startTime: Date,
    var variables: MutableMap<String, Any>? = null
)