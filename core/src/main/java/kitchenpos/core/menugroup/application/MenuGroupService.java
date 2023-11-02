package kitchenpos.core.menugroup.application;

import java.util.List;
import kitchenpos.core.menugroup.domain.MenuGroup;
import kitchenpos.core.menugroup.application.dto.MenuGroupRecord;
import kitchenpos.core.product.domain.Name;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuGroupService {
    private final MenuGroupDao menuGroupDao;

    public MenuGroupService(final MenuGroupDao menuGroupDao) {
        this.menuGroupDao = menuGroupDao;
    }

    @Transactional
    public MenuGroupRecord create(final Name menuGroupName) {
        return MenuGroupRecord.from(menuGroupDao.save(new MenuGroup(menuGroupName)));
    }

    public List<MenuGroupRecord> list() {
        return MenuGroupRecord.from(menuGroupDao.findAll());
    }
}
