package kitchenpos.api.order.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import kitchenpos.core.order.application.dto.OrderRecord;
import kitchenpos.core.order.domain.OrderStatus;

public class OrderResponse {
    @JsonProperty
    private final Long id;
    @JsonProperty
    private final Long orderTableId;
    @JsonProperty
    private final OrderStatus orderStatus;
    @JsonProperty
    private final LocalDateTime orderedTime;
    @JsonProperty
    private final List<OrderLineItemResponse> orderLineItems;

    public OrderResponse(final Long id, final Long orderTableId, final OrderStatus orderStatus,
                         final LocalDateTime orderedTime,
                         final List<OrderLineItemResponse> orderLineItems) {
        this.id = id;
        this.orderTableId = orderTableId;
        this.orderStatus = orderStatus;
        this.orderedTime = orderedTime;
        this.orderLineItems = orderLineItems;
    }

    public static OrderResponse from(final OrderRecord record) {
        return new OrderResponse(
                record.getId(),
                record.getOrderTableId(),
                record.getOrderStatus(),
                record.getOrderedTime(),
                OrderLineItemResponse.from(record.getOrderLineItems())
        );
    }

    public Long getId() {
        return id;
    }
}
