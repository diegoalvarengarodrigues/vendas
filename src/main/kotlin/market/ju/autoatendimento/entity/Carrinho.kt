package market.ju.autoatendimento.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal


@Entity
data class Carrinho(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @ManyToOne(fetch = FetchType.EAGER)// Referência entre as entidades
    @JoinColumn(name = "produto_id")// Identificação da foreign key
    val produto: Produto,
    @Column(nullable = false) @NotNull(message = "A quantidade não pode ser nula")
    var quantidade: Int,
    @Column(nullable = false) @NotNull(message = "O preço final não pode ser nulo")
    var precoFinal: Double?,
    @ManyToOne
    var venda: Venda? = null,
)
