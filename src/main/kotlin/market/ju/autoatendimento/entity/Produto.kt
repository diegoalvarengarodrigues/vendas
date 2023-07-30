package market.ju.autoatendimento.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal


@Entity
data class Produto(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @Column(nullable = false) @NotNull(message = "O nome é obrigatório") var nome: String,
    @Column(nullable = false) @NotNull(message = "A unidade de medida é obrigatória") var unidadeDeMedida: String,
    @Column(nullable = false) @NotNull(message = "O preço unitário é obrigatório") var precoUnitario: Double,
    @ManyToOne(fetch = FetchType.EAGER)// Referência entre as entidades
    @JoinColumn(name = "categoria_id")// Identificação da foreign key
    var categoria: Categoria
)
