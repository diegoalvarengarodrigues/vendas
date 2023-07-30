package market.ju.autoatendimento.controller


import jakarta.validation.Valid
import market.ju.autoatendimento.dto.CarrinhoDTO
import market.ju.autoatendimento.dto.VendaDTO
import market.ju.autoatendimento.entity.Carrinho
import market.ju.autoatendimento.entity.Produto
import market.ju.autoatendimento.enumeration.FormaDePagamento
import market.ju.autoatendimento.service.CarrinhoService
import market.ju.autoatendimento.service.VendaService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/carrinho")
class CarrinhoController(private val carrinhoService: CarrinhoService, private val vendaService: VendaService) {

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
    @GetMapping("/valor-total")
    fun valorTotalCarrinho(): ResponseEntity<String> {
        val valorTotal = carrinhoService.valorTotalCarrinho()
        return ResponseEntity.ok("Valor Total: $valorTotal")
    }
    @PostMapping("/finalizar-venda")
    fun finalizarVenda(@RequestBody vendaDTO: VendaDTO): ResponseEntity<String> {
        try {
            val venda = carrinhoService.finalizarVenda(vendaDTO)
            return ResponseEntity.ok("Venda finalizada. Forma de Pagamento: ${vendaDTO.formaDePagamento}")
        } catch (ex: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao finalizar a venda.")
        }
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