package kitchenpos.core.order.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.core.order.domain.Order;
import kitchenpos.core.order.domain.OrderStatus;

public class OrderRecord {

    private final Long id;
    private final Long orderTableId;
    private final OrderStatus orderStatus;
    private final LocalDateTime orderedTime;
    private final List<OrderLineItemRecord> orderLineItems;

    public OrderRecord(final Long id, final Long orderTableId, final OrderStatus orderStatus,
                       final LocalDateTime orderedTime,
                       final List<OrderLineItemRecord> orderLineItems) {
        this.id = id;
        this.orderTableId = orderTableId;
        this.orderStatus = orderStatus;
        this.orderedTime = orderedTime;
        this.orderLineItems = orderLineItems;
    }

    public static OrderRecord from(final Order order) {
        return new OrderRecord(
                order.getId(),
                order.getOrderTableId(),
                order.getOrderStatus(),
                order.getOrderedTime(),
                OrderLineItemRecord.from(order.getOrderLineItems())
        );
    }

    public static List<OrderRecord> from(final List<Order> orders) {
        return orders
                .stream().map(OrderRecord::from)
                .toList();
    }

    public Long getId() {
        return id;
    }

    public Long getOrderTableId() {
        return orderTableId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public List<OrderLineItemRecord> getOrderLineItems() {
        return orderLineItems;
    }
}
