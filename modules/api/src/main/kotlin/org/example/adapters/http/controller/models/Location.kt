package Api.models

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 *
 * @param x X
 * @param y Y
 */
data class Location(

    @Schema(example = "null", required = true, description = "X")
    @get:JsonProperty("x", required = true) val x: Int,

    @Schema(example = "null", required = true, description = "Y")
    @get:JsonProperty("y", required = true) val y: Int
)

