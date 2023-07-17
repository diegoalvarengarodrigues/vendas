package market.ju.autoatendimento.repository

import market.ju.autoatendimento.entity.Produto
import org.springframework.data.jpa.repository.JpaRepository

interface ProdutoRepository : JpaRepository<Produto, Long> {
}