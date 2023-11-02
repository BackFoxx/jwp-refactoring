package kitchenpos.core.ordertable.application;

import java.util.List;
import kitchenpos.core.ordertable.application.dto.OrderTableRecord;
import kitchenpos.core.ordertable.domain.NumberOfGuests;
import kitchenpos.core.ordertable.domain.OrderTable;
import kitchenpos.core.ordertable.domain.OrderTables;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableService {
    private final OrderTableDao orderTableDao;
    private final List<OrderTablesChangingEmptinessValidator> changingEmptinessValidators;

    public TableService(final OrderTableDao orderTableDao,
                        final List<OrderTablesChangingEmptinessValidator> changingEmptinessValidators) {
        this.orderTableDao = orderTableDao;
        this.changingEmptinessValidators = changingEmptinessValidators;
    }

    @Transactional
    public OrderTableRecord create(final NumberOfGuests numberOfGuests, boolean empty) {
        final OrderTable orderTable = new OrderTable(numberOfGuests, empty);
        return OrderTableRecord.from(orderTableDao.save(orderTable));
    }

    public List<OrderTableRecord> list() {
        return OrderTableRecord.from(new OrderTables(orderTableDao.findAll()));
    }

    @Transactional
    public OrderTableRecord changeEmpty(final Long orderTableId, final boolean empty) {
        final OrderTable orderTable = orderTableDao.findMandatoryById(orderTableId);
        for (OrderTablesChangingEmptinessValidator changingEmptinessValidator : changingEmptinessValidators) {
            changingEmptinessValidator.validate(orderTable);
        }
        orderTable.changeEmptyStatus(empty);
        return OrderTableRecord.from(orderTableDao.save(orderTable));
    }

    @Transactional
    public OrderTableRecord changeNumberOfGuests(final Long orderTableId, final NumberOfGuests numberOfGuests) {
        final OrderTable savedOrderTable = orderTableDao.findMandatoryById(orderTableId);
        savedOrderTable.changeNumberOfGuests(numberOfGuests);
        return OrderTableRecord.from(orderTableDao.save(savedOrderTable));
    }
}
