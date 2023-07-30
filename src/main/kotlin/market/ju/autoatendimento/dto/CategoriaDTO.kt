package market.ju.autoatendimento.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CategoriaDTO(

    @field:NotNull(message = "O nome não pode ser nulo")
    @field:NotBlank(message = "O nome não pode ficar em branco")
    var nome: String
)
