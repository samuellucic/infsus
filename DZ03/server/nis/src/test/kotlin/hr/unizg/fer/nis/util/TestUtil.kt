package hr.unizg.fer.nis.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import java.io.IOException

class TestUtil {

    companion object {
        private val mapper: ObjectMapper = createObjectMapper()

        private fun createObjectMapper(): ObjectMapper {
            val mapper = ObjectMapper()
            mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false)
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            mapper.registerModule(JavaTimeModule())
            return mapper
        }

        @Throws(IOException::class)
        fun convertObjectToJsonBytes(obj: Any): ByteArray {
            return mapper.writeValueAsBytes(obj)
        }
    }
}
