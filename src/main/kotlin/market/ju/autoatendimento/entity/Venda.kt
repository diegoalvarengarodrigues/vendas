package market.ju.autoatendimento.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import market.ju.autoatendimento.enumeration.FormaDePagamento

@Entity
data class Venda(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "venda")
    var itens: MutableList<Carrinho>,
    @Column(nullable = false)
    var valorTotal: Double,
    @Column(nullable = false) var formaDePagamento: FormaDePagamento
)
