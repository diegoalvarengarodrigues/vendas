package market.ju.autoatendimento.service

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import market.ju.autoatendimento.dto.CategoriaDTO
import market.ju.autoatendimento.entity.Categoria
import market.ju.autoatendimento.repository.CategoriaRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.springframework.test.context.ActiveProfiles
import java.util.*


@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CategoriaServiceTest {
    @MockK lateinit var categoriaRepository: CategoriaRepository
    @InjectMocks lateinit var categoriaService: CategoriaService
    private lateinit var categoria: Categoria
    private lateinit var dto: CategoriaDTO
    @BeforeEach
    fun setUp() {
        categoriaRepository = mockk()
        categoriaService = CategoriaService(categoriaRepository)

        categoria = Categoria(id = 1L, nome = "Categoria Teste")
        dto = CategoriaDTO("Categoria Teste")
    }

    //Teste para verificar se a categoria é criada com sucesso
    @Test
    fun `deve salvar categoria`(){
        every { categoriaRepository.save(any()) } returns categoria
        val resultado = categoriaService.adicionarCategoria(dto)
        assertEquals(categoria, resultado)
    }

    //Teste para listar as categorias
    @Test
    fun `deve listar as categorias`() {
        val lista = listOf(categoria)
        every { categoriaRepository.findAll() } returns lista
        val resultado = categoriaService.listarCategorias()
        assertEquals(lista, resultado)
    }

    //Teste para busca de categoria por ID
    @Test
    fun `deve buscar categoria pelo ID`() {
        val idCategoria = 1L
        every { categoriaRepository.findById(idCategoria) } returns Optional.of(categoria)
        val resultado = categoriaService.buscarCategoriaPorId(idCategoria)
        assertEquals(categoria, resultado)
    }

    //Teste para editar uma categoria
    @Test
    fun `deve editar uma categoria`() {
        val novoNome = "Nova Categoria"
        val categoriaAtualizada = categoria.copy(nome = novoNome)
        every { categoriaRepository.findById(categoria.id!!) } returns Optional.of(categoria)
        every { categoriaRepository.save(categoria) } returns categoriaAtualizada

        val resultado = categoriaService.editarCategoria(categoria.id!!, categoriaAtualizada)

        assertEquals(categoriaAtualizada, resultado)
    }

    //Teste para excluir uma categoria
    @Test
    fun `deve deletar uma categoria`() {
        every { categoriaRepository.findById(categoria.id!!) } returns Optional.of(categoria)
        every { categoriaRepository.delete(categoria) } just Runs

        categoriaService.excluirCategoria(categoria.id!!)

        verify(exactly = 1) { categoriaRepository.delete(categoria) }
    }


}