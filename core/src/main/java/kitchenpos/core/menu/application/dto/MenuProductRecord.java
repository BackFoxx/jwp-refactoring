package kitchenpos.core.menu.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.core.menu.domain.MenuProduct;

public class MenuProductRecord {
    private Long seq;
    private Long productId;
    private long quantity;

    public MenuProductRecord(final Long seq, final Long productId, final long quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static List<MenuProductRecord> from(final List<MenuProduct> menuProducts) {
        return menuProducts
                .stream().map(menuProduct -> new MenuProductRecord(
                        menuProduct.getSeq(),
                        menuProduct.getProductId(),
                        menuProduct.getQuantity()
                )).toList();
    }

    public Long getSeq() {
        return seq;
    }

    public Long getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }
}
