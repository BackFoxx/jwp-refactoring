package kitchenpos.api.menu.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import kitchenpos.core.menu.application.dto.MenuProductRecord;

public class MenuProductResponse {
    @JsonProperty
    private Long seq;
    @JsonProperty
    private Long productId;
    @JsonProperty
    private long quantity;

    public MenuProductResponse(final Long seq, final Long productId, final long quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static List<MenuProductResponse> from(final List<MenuProductRecord> menuProducts) {
        return menuProducts
                .stream().map(menuProduct -> new MenuProductResponse(
                        menuProduct.getSeq(),
                        menuProduct.getProductId(),
                        menuProduct.getQuantity()
                ))
                .toList();
    }
}
