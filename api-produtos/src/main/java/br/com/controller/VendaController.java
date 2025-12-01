package br.com.bentoquirino.api_produtos.controller;

// Imports para VendaService, VendaDTO, e ResponseEntity...

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping
    public ResponseEntity<Venda> registrarVenda(@RequestBody VendaDTO vendaDto) {
        try {
            Venda vendaRegistrada = vendaService.registrarVenda(vendaDto);
            return new ResponseEntity<>(vendaRegistrada, HttpStatus.CREATED); 
        } catch (EstoqueInsuficienteException e) {
            // Retorna erro 400 Bad Request se o estoque for insuficiente
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
        }
        // Adicionar tratamento para RecursoNaoEncontradoException (HTTP 404)
    }
}