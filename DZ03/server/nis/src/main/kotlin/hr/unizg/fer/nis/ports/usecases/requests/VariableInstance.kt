package hr.unizg.fer.nis.ports.usecases.requests

data class VariableInstance(
    val type: String,
    val value: Any,
    val id: String,
    val name: String,
    val processDefinitionKey: String,
    val processDefinitionId: String,
    val processInstanceId: String
)