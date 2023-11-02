package kitchenpos.core.tablegroup.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import kitchenpos.core.ordertable.application.dto.OrderTableRecord;
import kitchenpos.core.tablegroup.domain.TableGroup;

public class TableGroupRecord {
    private Long id;
    private LocalDateTime createdDate;
    private List<OrderTableRecord> orderTables;

    private TableGroupRecord(final Long id, final LocalDateTime createdDate,
                             final List<OrderTableRecord> orderTables) {
        this.id = id;
        this.createdDate = createdDate;
        this.orderTables = orderTables;
    }

    public static TableGroupRecord from(final TableGroup tableGroup) {
        return new TableGroupRecord(
                tableGroup.getId(),
                tableGroup.getCreatedDate(),
                OrderTableRecord.from(tableGroup.getOrderTables())
        );
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public List<OrderTableRecord> getOrderTables() {
        return orderTables;
    }
}
