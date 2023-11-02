package kitchenpos.api.menugroup.ui.dto;

import java.util.List;
import kitchenpos.core.menugroup.application.dto.MenuGroupRecord;
import kitchenpos.core.product.domain.Name;

public class MenuGroupResponse {
    private Long id;
    private String name;

    public MenuGroupResponse(final Long id, final Name name) {
        this.id = id;
        this.name = name.getName();
    }

    public static MenuGroupResponse from(final MenuGroupRecord menuGroupRecord) {
        return new MenuGroupResponse(
                menuGroupRecord.getId(),
                menuGroupRecord.getName()
        );
    }

    public static List<MenuGroupResponse> from(final List<MenuGroupRecord> records) {
        return records
                .stream().map(MenuGroupResponse::from)
                .toList();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
