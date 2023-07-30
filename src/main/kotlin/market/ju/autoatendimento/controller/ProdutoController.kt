package market.ju.autoatendimento.controller


import jakarta.validation.Valid
import market.ju.autoatendimento.dto.ProdutoDTO
import market.ju.autoatendimento.entity.Produto
import market.ju.autoatendimento.service.ProdutoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/produtos")
class ProdutoController(private val produtoService: ProdutoService) {

   /*
    @PostMapping
    fun criarProduto(@Valid @RequestBody produtoDTO: ProdutoDTO, categoriaId: Long): ResponseEntity<Produto>{
        val novoProduto = produtoService.criarProduto(produtoDTO, categoriaId)
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto)
    }*/
   @PostMapping
   fun criarProduto(@Valid @RequestBody produtoDTO: ProdutoDTO): ResponseEntity<String>{
       return try {
           //val novoProduto: Produto = this.produtoService.criarProduto(produtoDTO.toEntity())
           val novoProduto = produtoService.criarProduto(produtoDTO.toEntity())
           val resposta: String = "Produto: '${novoProduto.nome}' criado com sucesso"
           ResponseEntity.status(HttpStatus.OK).body(resposta)
       } catch (e: IllegalArgumentException){
           ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
       }
   }
    @GetMapping
    fun listarProdutos(): ResponseEntity<List<Produto>> {
        val produtos = produtoService.listarProdutos()
        return ResponseEntity.ok(produtos)
    }
    @GetMapping("/{id}")
    fun buscarProdutoPorId(@PathVariable id: Long): ResponseEntity<Produto> {
        val produto = produtoService.buscarProdutoPorId(id)
        return ResponseEntity.ok(produto)
    }

    @GetMapping("/{nome}")
    fun buscarProdutoPorNome(@RequestParam("nome") @Valid nome: String): ResponseEntity<List<Produto>> {
        val produto = produtoService.buscarProdutoPorNome(nome)
        return ResponseEntity.ok(produto)

    }
    @PutMapping("/editar")
    fun editarProduto(@RequestBody @Valid produtoDTO: ProdutoDTO): ResponseEntity<String> {
        var response: String = produtoService.editarProduto(produtoDTO.toEntity())
        if(response == "Produto não encontrado") {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(response)
        }

    }
    @DeleteMapping("/{id}")
    fun excluirProduto(@PathVariable id: Long) {
        produtoService.excluirProduto(id)
    }
}