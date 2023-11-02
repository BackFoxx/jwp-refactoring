package kitchenpos.api.ordertable.ui;

import java.net.URI;
import java.util.List;
import java.util.Map;
import kitchenpos.api.ordertable.ui.dto.OrderTableResponse;
import kitchenpos.core.ordertable.application.TableService;
import kitchenpos.core.ordertable.application.dto.OrderTableRecord;
import kitchenpos.core.ordertable.domain.NumberOfGuests;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableRestController {
    private final TableService tableService;

    public TableRestController(final TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping("/api/tables")
    public ResponseEntity<OrderTableResponse> create(@RequestBody final Map<String, Object> parameter) {
        final OrderTableRecord created = tableService.create(
                new NumberOfGuests(Integer.parseInt(String.valueOf(parameter.get("numberOfGuests")))),
                Boolean.parseBoolean(String.valueOf(parameter.get("empty")))
        );
        final URI uri = URI.create("/api/tables/" + created.getId());
        return ResponseEntity.created(uri).body(OrderTableResponse.from(created));
    }

    @GetMapping("/api/tables")
    public ResponseEntity<List<OrderTableResponse>> list() {
        return ResponseEntity.ok().body(OrderTableResponse.from(tableService.list()));
    }

    @PutMapping("/api/tables/{orderTableId}/empty")
    public ResponseEntity<OrderTableResponse> changeEmpty(
            @PathVariable final Long orderTableId,
            @RequestBody final Map<String, Object> parameter
    ) {
        final boolean empty = Boolean.parseBoolean(String.valueOf(parameter.get("empty")));
        return ResponseEntity.ok().body(OrderTableResponse.from(tableService.changeEmpty(orderTableId, empty)));
    }

    @PutMapping("/api/tables/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTableResponse> changeNumberOfGuests(
            @PathVariable final Long orderTableId,
            @RequestBody final Map<String, Object> parameter
    ) {
        final NumberOfGuests numberOfGuests = new NumberOfGuests(Integer.parseInt(String.valueOf(parameter.get("numberOfGuests"))));
        return ResponseEntity.ok().body(OrderTableResponse.from(tableService.changeNumberOfGuests(orderTableId, numberOfGuests)));
    }
}
