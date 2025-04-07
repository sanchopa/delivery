package Api.models

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.Valid

/**
 *
 * @param id Идентификатор
 * @param location
 */
data class Order(

    @Schema(example = "null", required = true, description = "Идентификатор")
    @get:JsonProperty("id", required = true) val id: java.util.UUID,

    @field:Valid
    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("location", required = true) val location: Location
)

