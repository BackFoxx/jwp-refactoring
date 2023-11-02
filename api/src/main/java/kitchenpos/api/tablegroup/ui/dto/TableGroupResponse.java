package kitchenpos.api.tablegroup.ui.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDateTime;
import java.util.List;
import kitchenpos.core.ordertable.application.dto.OrderTableRecord;
import kitchenpos.core.tablegroup.application.dto.TableGroupRecord;

public class TableGroupResponse {
    private Long id;
    private LocalDateTime createdDate;
    private List<OrderTableRecord> orderTables;

    private TableGroupResponse(final Long id, final LocalDateTime createdDate,
                               final List<OrderTableRecord> orderTables) {
        this.id = id;
        this.createdDate = createdDate;
        this.orderTables = orderTables;
    }

    @JsonCreator
    public static TableGroupResponse from(final TableGroupRecord record) {
        return new TableGroupResponse(
                record.getId(),
                record.getCreatedDate(),
                record.getOrderTables()
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
