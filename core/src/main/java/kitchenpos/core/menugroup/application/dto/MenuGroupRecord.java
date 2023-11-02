package kitchenpos.core.menugroup.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.core.menugroup.domain.MenuGroup;
import kitchenpos.core.product.domain.Name;

public class MenuGroupRecord {
    private Long id;
    private Name name;

    public MenuGroupRecord(final Long id, final Name name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroupRecord from(final MenuGroup menuGroup) {
        return new MenuGroupRecord(menuGroup.getId(), menuGroup.getName());
    }

    public static List<MenuGroupRecord> from(final List<MenuGroup> menuGroups) {
        return menuGroups
                .stream().map(menuGroup -> new MenuGroupRecord(menuGroup.getId(), menuGroup.getName()))
                .toList();
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }
}
