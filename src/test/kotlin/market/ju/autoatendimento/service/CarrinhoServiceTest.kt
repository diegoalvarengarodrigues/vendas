package market.ju.autoatendimento.service

import market.ju.autoatendimento.dto.CarrinhoDTO
import market.ju.autoatendimento.entity.Carrinho
import market.ju.autoatendimento.entity.Categoria
import market.ju.autoatendimento.entity.Produto
import market.ju.autoatendimento.exceptions.NaoEncontradoException
import market.ju.autoatendimento.repository.CarrinhoRepository
import market.ju.autoatendimento.repository.ProdutoRepository
import market.ju.autoatendimento.repository.VendaRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class CarrinhoServiceTest {

    @Mock
    private lateinit var carrinhoRepository: CarrinhoRepository

    @Mock
    private lateinit var produtoRepository: ProdutoRepository

    @InjectMocks
    private lateinit var carrinhoService: CarrinhoService

    @Mock
    private lateinit var vendaRepository: VendaRepository


    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testAdicionarProduto() {
        val categoriaTeste = Categoria(id = 1, nome = "Categoria de Teste")
        val produto1 = Produto(id = 1, nome = "Produto 1", precoUnitario = 10.0, unidadeDeMedida = "Unidade", categoria = categoriaTeste)
        val produto2 = Produto(id = 2, nome = "Produto 2", precoUnitario = 20.0, unidadeDeMedida = "Unidade", categoria = categoriaTeste)
        val carrinhoDto1 = CarrinhoDTO(produtoId = 1, quantidade = 2, precoFinal = 20.0)
        val carrinhoDto2 = CarrinhoDTO(produtoId = 2, quantidade = 3, precoFinal = 60.0)
        val carrinhoListDto = listOf(carrinhoDto1, carrinhoDto2)
        Mockito.`when`(produtoRepository.findById(1)).thenReturn(Optional.of(produto1))
        Mockito.`when`(produtoRepository.findById(2)).thenReturn(Optional.of(produto2))
        val carrinhoList = carrinhoService.adicionarProduto(carrinhoListDto)
        assertEquals(2, carrinhoList.size)
        assertEquals(produto1, carrinhoList[0].produto)
        assertEquals(2, carrinhoList[0].quantidade)
        assertEquals(20.0, carrinhoList[0].precoFinal)
        assertEquals(produto2, carrinhoList[1].produto)
        assertEquals(3, carrinhoList[1].quantidade)
        assertEquals(60.0, carrinhoList[1].precoFinal)
    }
    @Test
    fun testAdicionarProduto_ProdutoInvalido() {
        val carrinhoDto = CarrinhoDTO(produtoId = 3, quantidade = 1, precoFinal = 9.0)
        val carrinhoListDto = listOf(carrinhoDto)
        Mockito.`when`(produtoRepository.findById(3)).thenReturn(Optional.empty())
        assertThrows(NaoEncontradoException::class.java) {
            carrinhoService.adicionarProduto(carrinhoListDto)
        }
    }
    @Test
    fun testListarCarrinho() {
        val categoriaTeste = Categoria(id = 1, nome = "Categoria de Teste")
        val produto1 = Produto(id = 1, nome = "Produto 1", precoUnitario = 10.0, unidadeDeMedida = "Unidade", categoria = categoriaTeste)
        val produto2 = Produto(id = 2, nome = "Produto 2", precoUnitario = 20.0, unidadeDeMedida = "Unidade", categoria = categoriaTeste)
        val carrinho1 = Carrinho(produto = produto1, quantidade = 2, precoFinal = 20.0)
        val carrinho2 = Carrinho(produto = produto2, quantidade = 3, precoFinal = 60.0)
        val carrinhoList = listOf(carrinho1, carrinho2)
        Mockito.`when`(carrinhoRepository.findAll()).thenReturn(carrinhoList)
        val result = carrinhoService.listarCarrinho()
        assertEquals(2, result.size)
        assertEquals(carrinho1, result[0])
        assertEquals(carrinho2, result[1])
    }
    @Test
    fun testRemoverItem() {
        val carrinhoId = 1L
        Mockito.`when`(carrinhoRepository.existsById(carrinhoId)).thenReturn(true)
        carrinhoService.removerItem(carrinhoId)
        Mockito.verify(carrinhoRepository, Mockito.times(1)).deleteById(carrinhoId)
    }

    @Test
    fun testRemoverItem_ItemNaoEncontrado() {
        val carrinhoId = 1L
        Mockito.`when`(carrinhoRepository.existsById(carrinhoId)).thenReturn(false)
        assertThrows(NaoEncontradoException::class.java) {
            carrinhoService.removerItem(carrinhoId)
        }
    }
    @Test
    fun testLimparCarrinho() {
        carrinhoService.limparCarrinho()
        Mockito.verify(carrinhoRepository, Mockito.times(1)).deleteAll()
    }
}