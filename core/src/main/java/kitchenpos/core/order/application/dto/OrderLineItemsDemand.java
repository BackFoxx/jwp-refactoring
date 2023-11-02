package kitchenpos.core.order.application.dto;

import java.util.Objects;

public class OrderLineItemsDemand {

    private final Long menuId;
    private final Long quantity;

    public OrderLineItemsDemand(final Long menuId, final Long quantity) {
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getMenuId() {
        return menuId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderLineItemsDemand that = (OrderLineItemsDemand) o;
        return Objects.equals(menuId, that.menuId) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, quantity);
    }
}
