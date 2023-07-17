package market.ju.autoatendimento.controller


import market.ju.autoatendimento.dto.ProdutoDTO
import market.ju.autoatendimento.service.ProdutoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/produtos")
class ProdutoController(private val produtoService: ProdutoService) {

    @PostMapping
    fun adicionarProduto(@RequestBody produtoDTO: ProdutoDTO): ResponseEntity<ProdutoDTO>{
        val novoProduto = produtoDTO.categoriaId?.let { produtoService.adicionarProduto(produtoDTO, it) }
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto)
    }
    @GetMapping
    fun listarProdutos(): ResponseEntity<List<ProdutoDTO>> {
        val produtos = produtoService.listarProdutos().map { produto -> ProdutoDTO(
            id = produto.id,
            nome = produto.nome,
            precoUnitario = produto.precoUnitario,
            unidadeDeMedida = produto.unidadeDeMedida,
            categoriaId = produto.categoriaId
        ) }
        return ResponseEntity.ok(produtos)
    }
    @GetMapping("/{id}")
    fun buscarProdutoPorId(@PathVariable id: Long): ResponseEntity<ProdutoDTO> {
        val produto = produtoService.buscarProdutoPorId(id)
        return if (produto != null) {
            ResponseEntity.ok(produto)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    @PutMapping("/{id}")
    fun atualizarProduto(@PathVariable id: Long, @RequestBody produtoDTO: ProdutoDTO): ResponseEntity<ProdutoDTO> {
        val produtoExistente = produtoService.buscarProdutoPorId(id)
        return if (produtoExistente != null) {
            produtoExistente.categoriaId?.let { produtoService.atualizarProduto(produtoDTO, it) }
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
    @DeleteMapping("/{id}")
    fun excluirProduto(@PathVariable id: Long) {
        produtoService.excluirProduto(id)
    }
}