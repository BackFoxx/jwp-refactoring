package kitchenpos.core.product.domain;

import java.math.BigDecimal;
import java.util.Objects;
import org.springframework.data.relational.core.mapping.Column;

public class Price {

    private static final int MAX_PRICE_SCALE = 2;
    private static final int MAX_PRICE_PRECISION = 19;

    @Column("PRICE")
    private final BigDecimal price;

    public Price(final BigDecimal price) {
        validatePrice(price);
        this.price = price;
    }

    private static void validatePrice(final BigDecimal value) {
        if (Objects.isNull(value) || isNegativeValue(value)) {
            throw new IllegalArgumentException();
        }

        if (hasInvalidLengthRange(value)) {
            throw new IllegalArgumentException();
        }
    }

    private static boolean hasInvalidLengthRange(final BigDecimal value) {
        return value.scale() > MAX_PRICE_SCALE || value.precision() > MAX_PRICE_PRECISION;
    }

    private static boolean isNegativeValue(final BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    public Price multiply(final BigDecimal value) {
        return new Price(this.price.multiply(value));
    }

    public Price add(final Price price) {
        return new Price(this.price.add(price.price));
    }

    public boolean isBiggerThan(final Price price) {
        return this.price.compareTo(price.price) > 0;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Price price = (Price) o;
        return Objects.equals(this.price, price.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
