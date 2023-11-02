package kitchenpos.api.menugroup.ui.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.core.menugroup.application.dto.MenuGroupRecord;
import kitchenpos.core.product.domain.Name;

public class MenuGroupResponse {
    private Long id;
    @JsonUnwrapped
    private Name name;

    public MenuGroupResponse(final Long id, final Name name) {
        this.id = id;
        this.name = name;
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

    public Name getName() {
        return name;
    }
}
