package market.ju.autoatendimento.service

import market.ju.autoatendimento.dto.CarrinhoDTO
import market.ju.autoatendimento.dto.VendaDTO
import market.ju.autoatendimento.entity.Carrinho
import market.ju.autoatendimento.entity.Venda
import market.ju.autoatendimento.exceptions.NaoEncontradoException
import market.ju.autoatendimento.repository.CarrinhoRepository
import market.ju.autoatendimento.repository.ProdutoRepository
import market.ju.autoatendimento.repository.VendaRepository
import org.springframework.stereotype.Service

@Service
class CarrinhoService(private val carrinhoRepository: CarrinhoRepository, private val produtoRepository: ProdutoRepository, private val vendaRepository: VendaRepository) {

    //Adiciona produtos ao carrinho
    fun adicionarProduto(carrinhoListDto: List<CarrinhoDTO>): List<Carrinho>{
        val carrinhoList = mutableListOf<Carrinho>()

        //Busca produtos através do Id e caso não encontre lança a exceção
        for (carrinhoDTO in carrinhoListDto) {
            val produto = produtoRepository.findById(carrinhoDTO.produtoId).orElseThrow { NaoEncontradoException("O Produto com o Id ${carrinhoDTO.produtoId} não foi encontrado.")}

            //Lógica para calcular o preço final do produto
            val precoFinal = produto.precoUnitario * carrinhoDTO.quantidade
            val carrinho = Carrinho(produto = produto, quantidade = carrinhoDTO.quantidade, precoFinal = precoFinal)
            carrinhoList.add(carrinho)
        }
        carrinhoRepository.saveAll(carrinhoList)
        return carrinhoList
    }

    //Calcula o valor total do Carrinho
    fun valorTotalCarrinho(): String {
        val carrinhoList = carrinhoRepository.findAll()
        val valorTotal = carrinhoList.sumOf { carrinho -> (carrinho.precoFinal ?: 0.0) * carrinho.quantidade }
        return String.format("%.2f", valorTotal)
    }

    //Lista todos os Produtos do Carrinho
    fun listarCarrinho(): List<Carrinho> {
        return carrinhoRepository.findAll()
    }

    //Finaliza a Venda
    fun finalizarVenda(vendaDTO: VendaDTO): Venda {
        val itens = carrinhoRepository.findAll()
        val valorTotal = itens.sumOf { carrinho -> carrinho.precoFinal ?: 0.0 }
        val venda = Venda(valorTotal = valorTotal, formaDePagamento = vendaDTO.formaDePagamento, itens = itens)
        vendaRepository.save(venda)
        carrinhoRepository.deleteAll()
        return venda
    }

    //Exclui item do carrinho pelo ID do Produto
    fun removerItem(id: Long) {
        if (carrinhoRepository.existsById(id)) {
            carrinhoRepository.deleteById(id)
        } else {
            throw NaoEncontradoException("Item não encontrado.")
        }
    }

    //Exclui todos os itens do carrinho
    fun limparCarrinho() {
        carrinhoRepository.deleteAll()
    }
}