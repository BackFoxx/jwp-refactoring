package kitchenpos.core.order.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.core.order.domain.OrderLineItem;

public class OrderLineItemRecord {

    private final Long seq;
    private final Long menuId;
    private final long quantity;

    public OrderLineItemRecord(final Long seq, final Long menuId, final long quantity) {
        this.seq = seq;
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public static List<OrderLineItemRecord> from(final List<OrderLineItem> orderLineItems) {
        return orderLineItems
                .stream()
                .map(orderLineItem -> new OrderLineItemRecord(orderLineItem.getSeq(),
                        orderLineItem.getMenuId(), orderLineItem.getQuantity()))
                .toList();
    }

    public Long getSeq() {
        return seq;
    }

    public Long getMenuId() {
        return menuId;
    }

    public long getQuantity() {
        return quantity;
    }
}
