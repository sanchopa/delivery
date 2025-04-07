package Api.models

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 *
 * @param code Код ошибки
 * @param message Текст ошибки
 */
data class Error(

    @Schema(example = "null", required = true, description = "Код ошибки")
    @get:JsonProperty("code", required = true) val code: Int,

    @Schema(example = "null", required = true, description = "Текст ошибки")
    @get:JsonProperty("message", required = true) val message: String
)

