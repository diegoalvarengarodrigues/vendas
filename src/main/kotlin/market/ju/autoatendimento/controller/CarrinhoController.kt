package market.ju.autoatendimento.controller


import market.ju.autoatendimento.entity.Produto
import market.ju.autoatendimento.enumeration.FormaDePagamento
import market.ju.autoatendimento.service.CarrinhoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/carrinho")
class CarrinhoController(private val carrinhoService: CarrinhoService) {

    @PostMapping("/adicionar")
    fun adicionarProduto(@RequestBody produto: Produto) {
        carrinhoService.adicionarProduto(produto)
    }
    @PostMapping("/remover")
    fun removerProduto(@RequestBody produto: Produto) {
        carrinhoService.removerProduto(produto)
    }
    @PostMapping("/limpar")
    fun limparCarrinho() {
        carrinhoService.limparCarrinho()
    }
    @GetMapping("/categoria/{nome}")
    fun listarProdutosPorCategoria(@PathVariable nome: String): List<Produto> {
        return carrinhoService.listarProdutosPorCategoria(nome)
    }
    @GetMapping("/valor-total")
    fun calcularValorTotal(): Double {
        return carrinhoService.calcularValorTotal()
    }
    @PostMapping("/finalizar-compra")
    fun finalizarCompra(@RequestParam formaDePagamento: FormaDePagamento): FormaDePagamento {
        return carrinhoService.finalizarCompra(formaDePagamento)
    }
}