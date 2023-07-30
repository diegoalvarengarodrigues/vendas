package market.ju.autoatendimento.controller

import market.ju.autoatendimento.dto.VendaDTO
import market.ju.autoatendimento.service.VendaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/venda")
class VendaController (private val vendaService: VendaService) {
    @PostMapping("/finalizar-venda")
    fun finalizarVenda(@RequestBody vendaDTO: VendaDTO): ResponseEntity<String> {
        return try {
            vendaService.finalizarVenda(vendaDTO)
            ResponseEntity.ok("Venda finalizada. Forma de Pagamento: ${vendaDTO.formaDePagamento}")
        } catch (ex: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao finalizar a venda.")
        }
    }
}