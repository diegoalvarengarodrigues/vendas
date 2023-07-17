package market.ju.autoatendimento.controller

import market.ju.autoatendimento.dto.CategoriaDTO
import market.ju.autoatendimento.entity.Categoria
import market.ju.autoatendimento.service.CategoriaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categorias")
class CategoriaController(private val categoriaService: CategoriaService) {

    @PostMapping
    fun adicionarCategoria(@RequestBody categoriaDTO: CategoriaDTO): ResponseEntity<CategoriaDTO> {
        val novaCategoria = categoriaService.adicionarCategoria(categoriaDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria)
    }

    @GetMapping
    fun listarCategorias(): ResponseEntity<List<CategoriaDTO>> {
        val categorias = categoriaService.listarCategorias()
        return ResponseEntity.ok(categorias)
    }
    @GetMapping("/{id}")
    fun buscarCategoriaPorId(@PathVariable id: Long): ResponseEntity<CategoriaDTO> {
        val categoria = categoriaService.buscarCategoriaPorId(id)
        return ResponseEntity.ok(categoria)
    }
    @PutMapping("/{id}")
    fun atualizarCategoria(@PathVariable id: Long, @RequestBody categoriaDTO: CategoriaDTO): Categoria {
        return categoriaService.atualizarCategoria(id, categoriaDTO)
    }
    @DeleteMapping("/{id}")
    fun excluirCategoria(@PathVariable id: Long) {
        categoriaService.excluirCategoria(id)
    }
}