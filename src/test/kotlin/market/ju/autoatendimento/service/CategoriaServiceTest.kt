package market.ju.autoatendimento.service

import market.ju.autoatendimento.dto.CategoriaDTO
import market.ju.autoatendimento.entity.Categoria
import market.ju.autoatendimento.exceptions.NaoEncontradoException
import market.ju.autoatendimento.repository.CategoriaRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*
class CategoriaServiceTest {
    @Mock
    private lateinit var categoriaRepository: CategoriaRepository
    private lateinit var categoriaService: CategoriaService
    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        categoriaService = CategoriaService(categoriaRepository)
    }
    @Test
    fun adicionarCategoria_DeveRetornarNovaCategoria() {
        // Arrange
        val categoriaDTO = CategoriaDTO(nome = "Categoria Teste")
        val novaCategoria = Categoria(id = 1L, nome = "Categoria Teste")
        `when`(categoriaRepository.save(novaCategoria)).thenReturn(novaCategoria)
        // Act

        val resultado = categoriaService.adicionarCategoria(categoriaDTO)
        // Assert
        assertNotNull(resultado)
        assertEquals(novaCategoria, resultado)
    }
    @Test
    fun listarCategorias_DeveRetornarListaDeCategorias() {
        // Arrange
        val categorias = listOf(
            Categoria(id = 1L, nome = "Categoria 1"),
            Categoria(id = 2L, nome = "Categoria 2")
        )
        `when`(categoriaRepository.findAll()).thenReturn(categorias)
        // Act
        val resultado = categoriaService.listarCategorias()
        // Assert
        assertNotNull(resultado)
        assertEquals(categorias, resultado)
    }
    @Test
    fun buscarCategoriaPorId_IdExistente_DeveRetornarCategoria() {
        // Arrange
        val id = 1L
        val categoria = Categoria(id = id, nome = "Categoria Teste")
        `when`(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria))
        // Act
        val resultado = categoriaService.buscarCategoriaPorId(id)
        // Assert
        assertNotNull(resultado)
        assertEquals(categoria, resultado)
    }
    @Test
    fun buscarCategoriaPorId_IdInexistente_DeveLancarExcecao() {
        // Arrange
        val id = 1L
        `when`(categoriaRepository.findById(id)).thenReturn(Optional.empty())
        // Act & Assert
        assertThrows(NaoEncontradoException::class.java) {
            categoriaService.buscarCategoriaPorId(id)
        }
    }
    @Test
    fun editarCategoria_IdExistente_DeveRetornarCategoriaAtualizada() {
        // Arrange
        val id = 1L
        val categoriaAtual = Categoria(id = id, nome = "Categoria Antiga")
        val categoriaNova = Categoria(id = id, nome = "Categoria Nova")
        `when`(categoriaRepository.findById(id)).thenReturn(Optional.of(categoriaAtual))
        `when`(categoriaRepository.save(categoriaAtual)).thenReturn(categoriaAtual)
        // Act
        val resultado = categoriaService.editarCategoria(id, categoriaNova)
        // Assert
        assertNotNull(resultado)
        assertEquals(categoriaNova, resultado)
    }
    @Test
    fun editarCategoria_IdInexistente_DeveLancarExcecao() {
        // Arrange
        val id = 1L
        val categoriaNova = Categoria(id = id, nome = "Categoria Nova")
        `when`(categoriaRepository.findById(id)).thenReturn(Optional.empty())
        // Act & Assert
        assertThrows(NaoEncontradoException::class.java) {
            categoriaService.editarCategoria(id, categoriaNova)
        }
    }
    @Test
    fun excluirCategoria_IdExistente_DeveExcluirCategoria() {
        // Arrange
        val id = 1L
        val categoriaCadastrada = Categoria(id = id, nome = "Categoria Teste")
        `when`(categoriaRepository.findById(id)).thenReturn(Optional.of(categoriaCadastrada))
        // Act
        categoriaService.excluirCategoria(id)
        // Assert
        // Verifica se o método deleteById foi chamado com o id correto
        verify(categoriaRepository).deleteById(id)
    }
    @Test
    fun excluirCategoria_IdInexistente_DeveLancarExcecao() {
        // Arrange
        val id = 1L
        `when`(categoriaRepository.findById(id)).thenReturn(Optional.empty())
        // Act & Assert
        assertThrows(NaoEncontradoException::class.java) {
            categoriaService.excluirCategoria(id)
        }
    }
}