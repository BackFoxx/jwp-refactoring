package kitchenpos.api.order.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.api.menu.ui.dto.MenuProductResponse;
import kitchenpos.core.order.application.dto.OrderLineItemRecord;
import kitchenpos.core.order.domain.OrderLineItem;

public class OrderLineItemResponse {
    @JsonProperty
    private final Long seq;
    @JsonProperty
    private final Long menuId;
    @JsonProperty
    private final long quantity;

    public OrderLineItemResponse(final Long seq, final Long menuId, final long quantity) {
        this.seq = seq;
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public static List<OrderLineItemResponse> from(final List<OrderLineItemRecord> records) {
        return records
                .stream().map(itemRecord -> new OrderLineItemResponse(
                        itemRecord.getSeq(),
                        itemRecord.getMenuId(),
                        itemRecord.getQuantity()
                ))
                .collect(Collectors.toList());
    }
}
