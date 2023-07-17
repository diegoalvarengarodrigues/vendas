package market.ju.autoatendimento.repository

import market.ju.autoatendimento.entity.Carrinho
import org.springframework.data.jpa.repository.JpaRepository

interface CarrinhoRepository : JpaRepository<Carrinho, Long> {
}