package kitchenpos.core.order.application.dto;

import java.util.List;
import java.util.Objects;

public class OrderDemand {
    private final Long orderTableId;
    private final List<OrderLineItemsDemand> orderLineItems;

    public OrderDemand(final Long orderTableId, final List<OrderLineItemsDemand> orderLineItems) {
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public Long getOrderTableId() {
        return orderTableId;
    }

    public List<OrderLineItemsDemand> getOrderLineItems() {
        return orderLineItems;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderDemand that = (OrderDemand) o;
        return Objects.equals(orderTableId, that.orderTableId) && Objects.equals(orderLineItems,
                that.orderLineItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderTableId, orderLineItems);
    }
}
