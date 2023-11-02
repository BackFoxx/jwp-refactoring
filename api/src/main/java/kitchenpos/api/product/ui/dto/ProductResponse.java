package kitchenpos.api.product.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.List;
import kitchenpos.core.product.application.dto.ProductRecord;
import kitchenpos.core.product.domain.Name;
import kitchenpos.core.product.domain.Price;

public class ProductResponse {
    @JsonProperty("id")
    private final Long id;

    @JsonUnwrapped
    private final Name name;

    @JsonUnwrapped
    private final Price price;

    public ProductResponse(final Long id, final Name name, final Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse from(final ProductRecord record) {
        return new ProductResponse(
                record.getId(),
                record.getName(),
                record.getPrice()
        );
    }

    public static List<ProductResponse> from(final List<ProductRecord> records) {
        return records
                .stream().map(ProductResponse::from)
                .toList();
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
}
