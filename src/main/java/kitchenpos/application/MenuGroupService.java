package kitchenpos.application;

import kitchenpos.application.response.MenuGroupResponse;
import kitchenpos.dao.MenuGroupDao;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.product.Name;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuGroupService {
    private final MenuGroupDao menuGroupDao;

    public MenuGroupService(final MenuGroupDao menuGroupDao) {
        this.menuGroupDao = menuGroupDao;
    }

    @Transactional
    public MenuGroupResponse create(final Name menuGroupName) {
        return MenuGroupResponse.from(menuGroupDao.save(new MenuGroup(menuGroupName)));
    }

    public List<MenuGroupResponse> list() {
        return MenuGroupResponse.from(menuGroupDao.findAll());
    }
}
