package market.ju.autoatendimento.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import market.ju.autoatendimento.dto.CarrinhoDTO
import market.ju.autoatendimento.dto.VendaDTO
import market.ju.autoatendimento.entity.Carrinho
import market.ju.autoatendimento.entity.Categoria
import market.ju.autoatendimento.entity.Produto
import market.ju.autoatendimento.entity.Venda
import market.ju.autoatendimento.enumeration.FormaDePagamento
import market.ju.autoatendimento.repository.CarrinhoRepository
import market.ju.autoatendimento.repository.ProdutoRepository
import market.ju.autoatendimento.repository.VendaRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.util.*

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CarrinhoServiceTest {
    @MockK
    lateinit var carrinhoRepository: CarrinhoRepository
    @MockK
    lateinit var produtoRepository: ProdutoRepository
    @MockK
    lateinit var vendaRepository: VendaRepository

    lateinit var carrinhoService: CarrinhoService
    lateinit var produtoFalso: Produto
    lateinit var categoriaFalsa: Categoria
    lateinit var carrinhoFalso: Carrinho
    lateinit var vendaFalsa: Venda
    lateinit var vendaFalsaDTO: VendaDTO

    @BeforeEach
    fun setUp() {
        carrinhoService = CarrinhoService(carrinhoRepository, produtoRepository, vendaRepository)
        carrinhoFalso = Carrinho(
            id = 1,
            produto = produtoFalso,
            quantidade = 1,
            precoFinal = produtoFalso.precoUnitario * carrinhoFalso.quantidade,
            venda = vendaFalsa
        )
        produtoFalso = Produto(
            id = 1,
            nome = "Produto Teste",
            unidadeDeMedida = "Unidade",
            precoUnitario = 2.00,
            categoria = categoriaFalsa
        )
        vendaFalsaDTO = VendaDTO(valorTotal = 2.00, FormaDePagamento.CARTAO_DE_CREDITO)

        categoriaFalsa = Categoria(id = 1L, nome = "Categoria Teste")
    }
    //Teste para adicionar produto no carrinho
    @Test
    fun `deve adicionar um item ao carrinho`() {
        every { produtoRepository.findById(any()) } returns Optional.of(produtoFalso)
        every { carrinhoRepository.save(any()) } returns carrinhoFalso
        val listaDTO = listOf(CarrinhoDTO(1L, 2, 4.0))
        val lista = carrinhoService.adicionarProduto(listaDTO)
        assertNotNull(lista)
        assertEquals(listaDTO.size, lista.size)

    }

    //Teste para listar o carrinho
    @Test
    fun `deve listar o carrinho`() {
        val fakeCarrinhos: List<Carrinho> = listOf(carrinhoFalso)
        every { carrinhoService.listarCarrinho() } returns fakeCarrinhos
        val carrinhos: List<Carrinho> = carrinhoService.listarCarrinho()
        assertEquals(fakeCarrinhos, carrinhos)
        verify(exactly = 1) { carrinhoService.listarCarrinho() }
    }

    //Teste para remover item do carrinho
    @Test
    fun `deve remover item do carrinho`() {
        every { carrinhoRepository.existsById(carrinhoFalso.id!!) } returns true
        every { carrinhoRepository.deleteById(carrinhoFalso.id!!) } returns Unit
        carrinhoService.removerItem(carrinhoFalso.id!!)
        verify { carrinhoRepository.deleteById(carrinhoFalso.id!!) }
    }
    //Teste para limpar carrinho
    @Test
    fun `deve limpar o carrinho`() {
        every { carrinhoRepository.deleteAll() } just runs
        carrinhoService.limparCarrinho()
        verify(exactly = 1) { carrinhoRepository.deleteAll()}
    }
}