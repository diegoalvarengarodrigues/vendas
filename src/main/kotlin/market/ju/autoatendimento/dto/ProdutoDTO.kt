package market.ju.autoatendimento.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import market.ju.autoatendimento.entity.Categoria
import market.ju.autoatendimento.entity.Produto

data class ProdutoDTO(
    val id: Long,
    @field:NotNull(message = "O nome não pode ser nulo")
    @field:NotBlank(message = "O nome não pode ficar em branco")
    val nome: String,
    @field:NotNull(message = "O preço não pode ser nulo")
    @field:PositiveOrZero(message = "O preço deve ser um número positivo")
    val precoUnitario: Double,
    @field:NotNull(message = "A unidade de medida não pode ser nula")
    @field:NotBlank(message = "A unidade de medida não pode ficar em branco")
    val unidadeDeMedida: String,
    val categoria: Categoria,
){
    fun toEntity(): Produto = Produto(
        nome = this.nome,
        precoUnitario = this.precoUnitario,
        unidadeDeMedida = this.unidadeDeMedida,
        categoria = Categoria(id, nome = "")
    )
}
