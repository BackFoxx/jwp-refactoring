package kitchenpos.core.product.domain;

import java.util.Objects;
import org.springframework.data.relational.core.mapping.Column;

public class Name {
    private static final int MAX_NAME_LENGTH = 255;

    @Column("NAME")
    private final String name;

    public Name(final String name) {
        if (name.isEmpty() || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Name name = (Name) o;
        return Objects.equals(this.name, name.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
