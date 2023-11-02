package kitchenpos.core.product.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.core.product.domain.Name;
import kitchenpos.core.product.domain.Price;
import kitchenpos.core.product.domain.Product;

public class ProductRecord {

    private final Long id;
    private final Name name;
    private final Price price;

    public ProductRecord(final Long id, final Name name, final Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static List<ProductRecord> from(final List<Product> products) {
        return products
                .stream().map(product -> new ProductRecord(product.getId(), product.getName(), product.getPrice()))
                .collect(Collectors.toList());
    }

    public static ProductRecord from(final Product product) {
        return new ProductRecord(product.getId(), product.getName(), product.getPrice());
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
