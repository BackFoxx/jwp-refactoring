package kitchenpos.api.order.ui.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;
import kitchenpos.core.order.application.dto.OrderLineItemsDemand;

public class OrderRequest {
    @JsonProperty
    private final Long orderTableId;

    @JsonProperty("orderLineItems")
    private final List<OrderLineItemsDemand> orderLineItems;

    @JsonCreator
    public OrderRequest(final Long orderTableId, final List<OrderLineItemsDemand> orderLineItems) {
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
        final OrderRequest that = (OrderRequest) o;
        return Objects.equals(orderTableId, that.orderTableId) && Objects.equals(orderLineItems,
                that.orderLineItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderTableId, orderLineItems);
    }
}
