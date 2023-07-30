package market.ju.autoatendimento.dto

import market.ju.autoatendimento.enumeration.FormaDePagamento

data class VendaDTO(
    val valorTotal: Double,
    val formaDePagamento: FormaDePagamento
)
