package market.ju.autoatendimento.repository

import market.ju.autoatendimento.entity.Venda
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VendaRepository: JpaRepository<Venda, Long>{
}