package kitchenpos.core.ordertable.application.dto;

import java.util.ArrayList;
import java.util.List;
import kitchenpos.core.ordertable.domain.NumberOfGuests;
import kitchenpos.core.ordertable.domain.OrderTable;
import kitchenpos.core.ordertable.domain.OrderTables;

public class OrderTableRecord {

    private final Long id;
    private final Long tableGroupId;
    private final NumberOfGuests numberOfGuests;
    private final boolean empty;

    public OrderTableRecord(final Long id, final Long tableGroupId, final NumberOfGuests numberOfGuests, final boolean empty) {
        this.id = id;
        this.tableGroupId = tableGroupId;
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public static OrderTableRecord from(final OrderTable orderTable) {
        return new OrderTableRecord(
                orderTable.getId(),
                orderTable.getTableGroupId(),
                orderTable.getNumberOfGuests(),
                orderTable.isEmpty());
    }

    public static List<OrderTableRecord> from(final OrderTables orderTables) {
        final ArrayList<OrderTableRecord> orderTableRespons = new ArrayList<>();
        for (OrderTable orderTable : orderTables) {
            orderTableRespons.add(from(orderTable));
        }
        return orderTableRespons;
    }

    public Long getId() {
        return id;
    }

    public Long getTableGroupId() {
        return tableGroupId;
    }

    public NumberOfGuests getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }
}
