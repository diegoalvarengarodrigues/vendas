package market.ju.autoatendimento.controller

import jakarta.validation.Valid
import market.ju.autoatendimento.dto.CategoriaDTO
import market.ju.autoatendimento.entity.Categoria
import market.ju.autoatendimento.service.CategoriaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categorias")
class CategoriaController(private val categoriaService: CategoriaService) {

    @PostMapping
    fun adicionarCategoria(@Valid @RequestBody categoriaDTO: CategoriaDTO): ResponseEntity<String> {
        val novaCategoria = categoriaService.adicionarCategoria(categoriaDTO)
        val resposta: String = "Categoria: '${novaCategoria.nome}' adicionada com sucesso!"
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta)
    }

    @GetMapping
    fun listarCategorias(): ResponseEntity<List<Categoria>> {
        val categorias = categoriaService.listarCategorias()
        return ResponseEntity.ok(categorias)
    }
    @GetMapping("/{id}")
    fun buscarCategoriaPorId(@PathVariable id: Long): ResponseEntity<Categoria> {
        val categoria = categoriaService.buscarCategoriaPorId(id)
        return ResponseEntity.ok(categoria)
    }
    @PutMapping("/editar")
    fun editarCategoria(
        @PathVariable @Valid id: Long,
        @RequestBody categoriaDTO: CategoriaDTO): ResponseEntity<CategoriaDTO> {
        val categoriaExistente = categoriaService.buscarCategoriaPorId(id)
        val categoriaAtualizada = Categoria(id = categoriaExistente.id, nome = categoriaDTO.nome)
        val categoriaFinal = categoriaService.editarCategoria(id, categoriaAtualizada)
        return ResponseEntity.ok(CategoriaDTO(categoriaFinal.nome))
    }
    @DeleteMapping("/{id}")
    fun excluirCategoria(@PathVariable id: Long) {
        categoriaService.excluirCategoria(id)
    }
}