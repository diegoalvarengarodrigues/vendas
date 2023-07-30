package market.ju.autoatendimento.controller


import jakarta.validation.Valid
import market.ju.autoatendimento.dto.CarrinhoDTO
import market.ju.autoatendimento.entity.Carrinho
import market.ju.autoatendimento.service.CarrinhoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/carrinho")
class CarrinhoController(private val carrinhoService: CarrinhoService) {

    @PostMapping("/adicionar")
    fun adicionarProduto(@Valid @RequestBody carrinhoListDto: List<CarrinhoDTO>): ResponseEntity<List<Carrinho>> {
        val carrinhoList = carrinhoService.adicionarProduto(carrinhoListDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinhoList)
    }

    @GetMapping
    fun listarCarrinho(): ResponseEntity<List<Carrinho>> {
        val itensDoCarrinho = carrinhoService.listarCarrinho()
        return ResponseEntity.ok(itensDoCarrinho)
    }

    @DeleteMapping("/{id}")
    fun removerItem(@PathVariable id: Long): ResponseEntity<String> {
        carrinhoService.removerItem(id)
        return ResponseEntity.ok("Item removido.")
    }

    @DeleteMapping
    fun limparCarrinho(): ResponseEntity<String> {
        carrinhoService.limparCarrinho()
        return ResponseEntity.ok("O carrinho está vazio.")
    }

}