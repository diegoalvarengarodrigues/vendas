package market.ju.autoatendimento.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import market.ju.autoatendimento.entity.Categoria
import market.ju.autoatendimento.entity.Produto
import market.ju.autoatendimento.repository.ProdutoRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.springframework.test.context.ActiveProfiles
import java.util.*

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class ProdutoServiceTest {
    @MockK
    lateinit var produtoRepository: ProdutoRepository
    @MockK
    lateinit var categoriaRepository: ProdutoRepository
    @InjectMocks
    lateinit var produtoService: ProdutoService

    private lateinit var produtoFalso: Produto
    private lateinit var categoriaFalsa: Categoria

    @BeforeEach
    fun setUp() {
        categoriaFalsa = Categoria(id = 1L, nome = "Bebidas")
        produtoFalso = Produto(
            id = 1L,
            nome = "Suco de Uva",
            unidadeDeMedida = "Unidade",
            precoUnitario = 5.00,
            categoria = categoriaFalsa
        )
    }
    //Teste para criar um novo produto
    @Test
    fun `deve criar um novo produto`() {
        every { categoriaRepository.findById(produtoFalso.categoria.id!!) } returns Optional.of(produtoFalso)
        every { produtoRepository.save(any()) } returns produtoFalso

        val produtoCriado = produtoService.criarProduto(produtoFalso)

        assertNotNull(produtoCriado)
        assertEquals(produtoCriado, produtoFalso)
    }

    //Teste para listar todos os produtos
    @Test
    fun `deve listar todos os produtos`() {
        val produtosCadastrados = listOf(
            Produto(id = 1L, nome = "Suco de Uva", unidadeDeMedida = "Unidade", precoUnitario = 5.00, categoria = categoriaFalsa),
            Produto(id = 2L, nome = "Refrigerante de Cola", unidadeDeMedida = "Unidade", precoUnitario = 8.00, categoria = categoriaFalsa),
            Produto(id = 3L, nome = "Cerveja Puro Malte", unidadeDeMedida = "Unidade", precoUnitario = 6.00, categoria = categoriaFalsa),
            Produto(id = 4L, nome = "Agua sem gás", unidadeDeMedida = "Unidade", precoUnitario = 3.00, categoria = categoriaFalsa)
        )
        every { produtoRepository.findAll() } returns produtosCadastrados
        val atual: List<Produto> = produtoService.listarProdutos()
        assertEquals(produtosCadastrados.size, atual.size)
        assertTrue(atual.containsAll(produtosCadastrados))
    }

    //Teste para buscar produto pelo ID
    @Test
    fun `deve buscar produto por id`() {
        every { produtoRepository.findById(1L) } returns Optional.of(produtoFalso)
        val atual: Produto = produtoService.buscarProdutoPorId(1L)
        assertNotNull(atual)
        assertEquals(atual, produtoFalso)
    }

    //Teste para editar um produto
    @Test
    fun `deve editar um produto`() {
        val produtoEditado = produtoFalso.copy()
        every { produtoRepository.editarProduto(
            produtoEditado.id!!,
            produtoEditado.nome,
            produtoEditado.unidadeDeMedida,
            produtoEditado.precoUnitario,
            produtoEditado.categoria.id!!
        ) } returns 1
        val atual: String = produtoService.editarProduto(produtoEditado)
        val mensagem = "O produto de ID: ${produtoEditado.id} foi alterado com sucesso!"
        assertEquals(atual, mensagem)

    }

    //Teste para excluir um produto
    @Test
    fun `deve excluir um produto`() {
        every { produtoRepository.existsById(produtoFalso.id!!) } returns true
        every { produtoRepository.deleteById(produtoFalso.id!!) } returns Unit
        produtoService.excluirProduto(produtoFalso.id!!)

        verify { produtoRepository.deleteById(produtoFalso.id!!) }
    }


}