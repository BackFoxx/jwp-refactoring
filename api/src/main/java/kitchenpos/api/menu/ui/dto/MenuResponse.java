package kitchenpos.api.menu.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.math.BigDecimal;
import java.util.List;
import kitchenpos.core.menu.application.dto.MenuRecord;
import kitchenpos.core.product.domain.Price;

public class MenuResponse {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonUnwrapped
    private BigDecimal price;
    @JsonProperty
    private Long menuGroupId;
    @JsonProperty
    private List<MenuProductResponse> menuProducts;

    public MenuResponse(final Long id, final String name, final Price price, final Long menuGroupId,
                        final List<MenuProductResponse> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price.getPrice();
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public static MenuResponse from(final MenuRecord record) {
        return new MenuResponse(
                record.getId(),
                record.getName(),
                record.getPrice(),
                record.getMenuGroupId(),
                MenuProductResponse.from(record.getMenuProducts())
        );
    }

    public static List<MenuResponse> from(final List<MenuRecord> records) {
        return records
                .stream().map(MenuResponse::from)
                .toList();
    }

    public String getName() {
        return name;
    }
}
