package kitchenpos.api.menugroup.ui;

import java.net.URI;
import java.util.List;
import java.util.Map;
import kitchenpos.api.menugroup.ui.dto.MenuGroupResponse;
import kitchenpos.core.menugroup.application.MenuGroupService;
import kitchenpos.core.menugroup.application.dto.MenuGroupRecord;
import kitchenpos.core.product.domain.Name;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuGroupRestController {
    private final MenuGroupService menuGroupService;

    public MenuGroupRestController(final MenuGroupService menuGroupService) {
        this.menuGroupService = menuGroupService;
    }

    @PostMapping("/api/menu-groups")
    public ResponseEntity<MenuGroupResponse> create(@RequestBody final Map<String, Object> parameter) {
        final MenuGroupRecord created = menuGroupService.create(new Name(String.valueOf(parameter.get("name"))));
        final URI uri = URI.create("/api/menu-groups/" + created.getId());
        return ResponseEntity.created(uri).body(MenuGroupResponse.from(created));
    }

    @GetMapping("/api/menu-groups")
    public ResponseEntity<List<MenuGroupResponse>> list() {
        return ResponseEntity.ok().body(MenuGroupResponse.from(menuGroupService.list()));
    }
}
