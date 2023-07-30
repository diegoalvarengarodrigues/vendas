package market.ju.autoatendimento.dto

import java.math.BigDecimal

data class CarrinhoDTO(
    val produtoId: Long,
    val quantidade: Int,
    val precoFinal: Double
)
