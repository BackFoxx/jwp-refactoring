package kitchenpos.api.order.ui;

import java.net.URI;
import java.util.List;
import java.util.Map;
import kitchenpos.api.order.ui.dto.OrderRequest;
import kitchenpos.api.order.ui.dto.OrderResponse;
import kitchenpos.core.order.application.dto.OrderDemand;
import kitchenpos.core.order.application.OrderService;
import kitchenpos.core.order.application.dto.OrderRecord;
import kitchenpos.core.order.domain.OrderStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {
    private final OrderService orderService;

    public OrderRestController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/orders")
    public ResponseEntity<OrderResponse> create(@RequestBody final OrderRequest orderRequest) {
        final OrderRecord created = orderService.create(orderRequest.getOrderTableId(), orderRequest.getOrderLineItems());
        final URI uri = URI.create("/api/orders/" + created.getId());
        return ResponseEntity.created(uri).body(OrderResponse.from(created));
    }

    @GetMapping("/api/orders")
    public ResponseEntity<List<OrderRecord>> list() {
        return ResponseEntity.ok().body(orderService.list());
    }

    @PutMapping("/api/orders/{orderId}/order-status")
    public ResponseEntity<OrderResponse> changeOrderStatus(@PathVariable final Long orderId, @RequestBody Map<String, Object> parameters) {
        return ResponseEntity.ok(OrderResponse.from(orderService.changeOrderStatus(orderId, OrderStatus.valueOf(parameters.get("orderStatus").toString()))));
    }
}
