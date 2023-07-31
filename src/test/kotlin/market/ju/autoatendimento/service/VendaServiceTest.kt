package market.ju.autoatendimento.service

import market.ju.autoatendimento.dto.VendaDTO
import market.ju.autoatendimento.entity.Carrinho
import market.ju.autoatendimento.entity.Categoria
import market.ju.autoatendimento.entity.Produto
import market.ju.autoatendimento.enumeration.FormaDePagamento
import market.ju.autoatendimento.repository.CarrinhoRepository
import market.ju.autoatendimento.repository.VendaRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
class VendaServiceTest {
    @Mock
    private lateinit var carrinhoRepository: CarrinhoRepository
    @InjectMocks
    private lateinit var vendaService: VendaService
    @Mock
    private lateinit var vendaRepository: VendaRepository
    @Test
    fun finalizarVenda_deveRetornarAVendaCorretamente() {
        val categoriaTeste = Categoria(1, "Categoria de Teste")
        val produto1 = Produto(1,"Produto 1", "Unidade", 5.0, categoriaTeste)
        val produto2 = Produto(2,"Produto 2", "Unidade", 30.0, categoriaTeste)
        val carrinho1 = Carrinho(1, produto1, 2, 10.0)
        val carrinho2 = Carrinho(2, produto2, 3, 90.0)
        val itens = listOf(carrinho1, carrinho2)
        val vendaDTO = VendaDTO(valorTotal = 100.0, FormaDePagamento.CARTAO_DE_CREDITO)
        carrinhoRepository = mock(CarrinhoRepository::class.java)
        vendaRepository = mock(VendaRepository::class.java)
        vendaService = VendaService(carrinhoRepository, vendaRepository)
        `when`(carrinhoRepository.findAll()).thenReturn(itens)
        val venda = vendaService.finalizarVenda(vendaDTO)
        assertEquals(100.0, venda.valorTotal)
        assertEquals(FormaDePagamento.CARTAO_DE_CREDITO, venda.formaDePagamento)
        assertEquals(2, itens.size)
        verify(vendaRepository).save(venda)
        verify(carrinhoRepository).deleteAll()
    }
}