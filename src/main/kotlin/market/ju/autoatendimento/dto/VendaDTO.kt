package market.ju.autoatendimento.dto

import market.ju.autoatendimento.enumeration.FormaDePagamento
import java.math.BigDecimal

data class VendaDTO(
    val valorTotal: Double,
    val formaDePagamento: FormaDePagamento
)
