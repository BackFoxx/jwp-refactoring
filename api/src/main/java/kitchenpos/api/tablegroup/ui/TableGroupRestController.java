package kitchenpos.api.tablegroup.ui;

import kitchenpos.api.tablegroup.ui.dto.TableGroupResponse;
import kitchenpos.api.tablegroup.ui.dto.TableGroupsRequest;
import java.net.URI;
import kitchenpos.core.tablegroup.application.TableGroupService;
import kitchenpos.core.tablegroup.application.dto.TableGroupRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableGroupRestController {
    private final TableGroupService tableGroupService;

    public TableGroupRestController(final TableGroupService tableGroupService) {
        this.tableGroupService = tableGroupService;
    }

    @PostMapping("/api/table-groups")
    public ResponseEntity<TableGroupResponse> create(@RequestBody final TableGroupsRequest tableGroup) {
        final TableGroupRecord created = tableGroupService.create(tableGroup.getOrderTableIds());
        final URI uri = URI.create("/api/table-groups/" + created.getId());
        return ResponseEntity.created(uri).body(TableGroupResponse.from(created));
    }

    @DeleteMapping("/api/table-groups/{tableGroupId}")
    public ResponseEntity<Void> ungroup(@PathVariable final Long tableGroupId) {
        tableGroupService.ungroup(tableGroupId);
        return ResponseEntity.noContent().build();
    }
}
