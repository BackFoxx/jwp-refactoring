package kitchenpos.core.menu.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.core.menu.domain.Menu;
import kitchenpos.core.product.domain.Price;

public class MenuRecord {

    private Long id;

    private String name;

    private Price price;

    private Long menuGroupId;

    private List<MenuProductRecord> menuProducts;

    public MenuRecord(final Long id, final String name, final Price price, final Long menuGroupId,
                      final List<MenuProductRecord> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public static MenuRecord from(final Menu menu) {
        return new MenuRecord(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getMenuGroupId(),
                MenuProductRecord.from(menu.getMenuProducts())
        );
    }

    public static List<MenuRecord> from(final List<Menu> menus) {
        return menus
                .stream().map(MenuRecord::from)
                .toList();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductRecord> getMenuProducts() {
        return menuProducts;
    }
}
