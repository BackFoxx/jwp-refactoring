package kitchenpos.api.ordertable.ui.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import kitchenpos.core.ordertable.application.dto.OrderTableRecord;
import kitchenpos.core.ordertable.domain.NumberOfGuests;

public class OrderTableResponse {
    @JsonProperty
    private final Long id;
    @JsonProperty
    private final Long tableGroupId;
    private final int numberOfGuests;
    @JsonProperty
    private final boolean empty;

    @JsonCreator
    public OrderTableResponse(final Long id, final Long tableGroupId, final NumberOfGuests numberOfGuests, final boolean empty) {
        this.id = id;
        this.tableGroupId = tableGroupId;
        this.numberOfGuests = numberOfGuests.getNumberOfGuests();
        this.empty = empty;
    }

    public static OrderTableResponse from(final OrderTableRecord tableRecord) {
        return new OrderTableResponse(
                tableRecord.getId(),
                tableRecord.getTableGroupId(),
                tableRecord.getNumberOfGuests(),
                tableRecord.isEmpty()
        );
    }

    public static List<OrderTableResponse> from(final List<OrderTableRecord> tableRecords) {
        return tableRecords
                .stream().map(OrderTableResponse::from)
                .toList();
    }
}
