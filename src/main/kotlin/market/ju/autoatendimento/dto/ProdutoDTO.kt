package market.ju.autoatendimento.dto

import market.ju.autoatendimento.entity.Categoria
import market.ju.autoatendimento.entity.Produto

data class ProdutoDTO(
    val id: Long?,
    val nome: String,
    val precoUnitario: Double,
    val unidadeDeMedida: String,
    val categoriaId: Long?
) {
    fun toEntity(categoria: Categoria): Produto = Produto(id = id, nome = nome, precoUnitario = precoUnitario, unidadeDeMedida = unidadeDeMedida, categoria = categoria)
}
