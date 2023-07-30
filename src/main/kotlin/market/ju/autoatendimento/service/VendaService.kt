package market.ju.autoatendimento.service

import market.ju.autoatendimento.dto.VendaDTO
import market.ju.autoatendimento.entity.Venda
import market.ju.autoatendimento.repository.CarrinhoRepository
import market.ju.autoatendimento.repository.VendaRepository
import org.springframework.stereotype.Service

@Service
class VendaService(private val carrinhoRepository: CarrinhoRepository, private val vendaRepository: VendaRepository) {

    //Finaliza a Venda
    fun finalizarVenda(vendaDTO: VendaDTO): String {
        val itens = carrinhoRepository.findAll()
        val valorTotal = itens.sumOf { carrinho -> carrinho.precoFinal ?: 0.0 }
        val venda = Venda(valorTotal = valorTotal, formaDePagamento = vendaDTO.formaDePagamento, itens = itens)
        vendaRepository.save(venda)
        carrinhoRepository.deleteAll()
        return "Venda finalizada com sucesso. Valor Total: R$ $valorTotal, Forma de Pagamento: ${vendaDTO.formaDePagamento}"
    }
}