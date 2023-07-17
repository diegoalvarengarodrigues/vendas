package market.ju.autoatendimento.service

import market.ju.autoatendimento.entity.Carrinho
import market.ju.autoatendimento.entity.Produto
import market.ju.autoatendimento.enumeration.FormaDePagamento
import market.ju.autoatendimento.repository.CarrinhoRepository
import market.ju.autoatendimento.repository.ProdutoRepository
import org.springframework.stereotype.Service

@Service
class CarrinhoService(private val carrinhoRepository: CarrinhoRepository, private val produtoRepository: ProdutoRepository) {

    private val carrinho: Carrinho = Carrinho()

    fun adicionarProduto(produto: Produto) {
        carrinho.produtos.add(produto)
    }
    fun removerProduto(produto: Produto) {
        carrinho.produtos.remove(produto)
    }
    fun limparCarrinho() {
        carrinho.produtos.clear()
    }
    fun listarProdutosPorCategoria(nome: String): List<Produto> {
        return carrinho.produtos.filter { it.categoria.nome == nome }
    }
    fun calcularValorTotal(): Double {
        return carrinho.produtos.sumOf { it.precoUnitario }
    }
    fun finalizarCompra(formaDePagamento: FormaDePagamento): FormaDePagamento{
        return formaDePagamento
    }
}