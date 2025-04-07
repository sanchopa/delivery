package Api.models

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.Valid

/**
 *
 * @param id Идентификатор
 * @param name Имя
 * @param location
 */
data class Courier(

    @Schema(example = "null", required = true, description = "Идентификатор")
    @get:JsonProperty("id", required = true) val id: java.util.UUID,

    @Schema(example = "null", required = true, description = "Имя")
    @get:JsonProperty("name", required = true) val name: String,

    @field:Valid
    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("location", required = true) val location: Location
)

