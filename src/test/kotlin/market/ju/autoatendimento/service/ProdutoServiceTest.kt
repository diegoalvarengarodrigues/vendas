package market.ju.autoatendimento.service

import market.ju.autoatendimento.entity.Categoria
import market.ju.autoatendimento.entity.Produto
import market.ju.autoatendimento.exceptions.NaoEncontradoException
import market.ju.autoatendimento.repository.CategoriaRepository
import market.ju.autoatendimento.repository.ProdutoRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class ProdutoServiceTest {
    private lateinit var produtoRepository: ProdutoRepository
    private lateinit var categoriaRepository: CategoriaRepository
    private lateinit var produtoService: ProdutoService
    @BeforeEach
    fun setUp() {
        produtoRepository = mock(ProdutoRepository::class.java)
        categoriaRepository = mock(CategoriaRepository::class.java)
        produtoService = ProdutoService(produtoRepository, categoriaRepository)
    }
    @Test
    fun criarProduto_DeveRetornarProduto_QuandoCategoriaExistir() {
        val produto = Produto(1L, "Produto 1", "unidade", 10.0, Categoria(1L, "Categoria 1"))
        `when`(categoriaRepository.existsById(produto.categoria.id!!)).thenReturn(true)
        `when`(produtoRepository.save(produto)).thenReturn(produto)
        val resultado = produtoService.criarProduto(produto)
        assertEquals(produto, resultado)
    }
    @Test
    fun criarProduto_DeveLancarExcecao_QuandoCategoriaNaoExistir() {
        val produto = Produto(1L, "Produto 1", "unidade", 10.0, Categoria(1L, "Categoria 1"))
        `when`(categoriaRepository.existsById(produto.categoria.id!!)).thenReturn(false)
        assertThrows(NaoEncontradoException::class.java) {
            produtoService.criarProduto(produto)
        }
    }
    @Test
    fun listarProdutos_DeveRetornarListaVazia_QuandoNaoExistiremProdutos() {
        `when`(produtoRepository.findAll()).thenReturn(emptyList())
        val resultado = produtoService.listarProdutos()
        assertTrue(resultado.isEmpty())
    }
    @Test
    fun listarProdutos_DeveRetornarListaDeProdutos_QuandoExistiremProdutos() {
        val produtos = listOf(
            Produto(1L, "Produto 1", "unidade", 10.0, Categoria(1L, "Categoria 1")),
            Produto(2L, "Produto 2", "unidade", 20.0, Categoria(2L, "Categoria 2"))
        )
        `when`(produtoRepository.findAll()).thenReturn(produtos)
        val resultado = produtoService.listarProdutos()
        assertEquals(produtos, resultado)
    }
    @Test
    fun buscarProdutoPorNome_DeveRetornarListaVazia_QuandoNenhumProdutoCorresponderAoNome() {
        val nome = "Produto 1"
        `when`(produtoRepository.findByNome(nome)).thenReturn(emptyList())
        val resultado = produtoService.buscarProdutoPorNome(nome)
        assertTrue(resultado.isEmpty())
    }
    @Test
    fun buscarProdutoPorNome_DeveRetornarListaDeProdutos_QuandoExistiremProdutosComNomeCorrespondente() {
        val nome = "Produto 1"
        val produtos = listOf(
            Produto(1L, "Produto 1", "unidade", 10.0, Categoria(1L, "Categoria 1")),
            Produto(2L, "Produto 1", "unidade", 20.0, Categoria(2L, "Categoria 2"))
        )
        `when`(produtoRepository.findByNome(nome)).thenReturn(produtos)
        val resultado = produtoService.buscarProdutoPorNome(nome)
        assertEquals(produtos, resultado)
    }
    @Test
    fun buscarProdutoPorId_DeveRetornarProduto_QuandoProdutoExistir() {
        val id = 1L
        val produto = Produto(1L, "Produto 1", "unidade", 10.0, Categoria(1L, "Categoria 1"))
        `when`(produtoRepository.findById(id)).thenReturn(java.util.Optional.of(produto))
        val resultado = produtoService.buscarProdutoPorId(id)
        assertEquals(produto, resultado)
    }
    @Test
    fun buscarProdutoPorId_DeveLancarExcecao_QuandoProdutoNaoExistir() {
        val id = 1L
        `when`(produtoRepository.findById(id)).thenReturn(java.util.Optional.empty())
        assertThrows(NaoEncontradoException::class.java) {
            produtoService.buscarProdutoPorId(id)
        }
    }
    @Test
    fun editarProduto_DeveRetornarMensagemDeSucesso_QuandoProdutoForEditado() {
        val produto = Produto(1L, "Produto 1", "unidade", 10.0, Categoria(1L, "Categoria 1"))
        `when`(
            produtoRepository.editarProduto(
                produto.id!!,
                produto.nome,
                produto.unidadeDeMedida,
                produto.precoUnitario,
                produto.categoria.id!!
            )
        ).thenReturn(1)
        val resultado = produtoService.editarProduto(produto)
        assertEquals("O produto de ID: ${produto.id} foi alterado com sucesso!", resultado)
    }
    @Test
    fun editarProduto_DeveRetornarMensagemDeErro_QuandoProdutoNaoForEncontrado() {
        val produto = Produto(1L, "Produto 1", "unidade", 10.0, Categoria(1L, "Categoria 1"))
        `when`(
            produtoRepository.editarProduto(
                produto.id!!,
                produto.nome,
                produto.unidadeDeMedida,
                produto.precoUnitario,
                produto.categoria.id!!
            )
        ).thenReturn(0)
        val resultado = produtoService.editarProduto(produto)
        assertEquals("Produto não encontrado", resultado)
    }
    @Test
    fun excluirProduto_DeveLancarExcecao_QuandoProdutoNaoExistir() {
        val id = 1L
        `when`(produtoRepository.existsById(id)).thenReturn(false)
        assertThrows(NaoEncontradoException::class.java) {
            produtoService.excluirProduto(id)
        }
    }
    @Test
    fun excluirProduto_DeveChamarMetodoDeleteById_QuandoProdutoExistir() {
        val id = 1L
        `when`(produtoRepository.existsById(id)).thenReturn(true)
        produtoService.excluirProduto(id)
        verify(produtoRepository).deleteById(id)
    }
}