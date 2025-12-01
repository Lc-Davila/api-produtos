public class ItemVendaDTO {
    private Long produtoId;
    private Integer quantidade;
}

public class VendaDTO {
    private Long clienteId;
    private List<ItemVendaDTO> itens;
}