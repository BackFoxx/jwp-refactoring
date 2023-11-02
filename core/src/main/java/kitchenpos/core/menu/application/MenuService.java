package kitchenpos.core.menu.application;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.core.menu.application.dto.MenuProductDemand;
import kitchenpos.core.menu.application.dto.MenuRecord;
import kitchenpos.core.menu.domain.Menu;
import kitchenpos.core.menu.domain.MenuProduct;
import kitchenpos.core.menu.domain.MenuProducts;
import kitchenpos.core.menugroup.application.MenuGroupDao;
import kitchenpos.core.product.application.ProductDao;
import kitchenpos.core.product.domain.Price;
import kitchenpos.core.product.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {
    private final MenuDao menuDao;
    private final MenuGroupDao menuGroupDao;
    private final ProductDao productDao;

    public MenuService(
            final MenuDao menuDao,
            final MenuGroupDao menuGroupDao,
            final ProductDao productDao
    ) {
        this.menuDao = menuDao;
        this.menuGroupDao = menuGroupDao;
        this.productDao = productDao;
    }

    @Transactional
    public MenuRecord create(final String name, final Price price, final Long menuGroupId, final List<MenuProductDemand> menuProductRequests) {
        validateMenuGroupIdExists(menuGroupId);

        final MenuProducts menuProducts = new MenuProducts(menuProductRequests
                .stream().map(this::getMenuProduct)
                .toList());

        if (menuProducts.isBiggerThanTotalPrice(price)) {
            throw new IllegalArgumentException();
        }

        return MenuRecord.from(menuDao.save(new Menu(name, price, menuGroupId, menuProducts)));
    }

    private void validateMenuGroupIdExists(final Long menuGroupId) {
        if (!menuGroupDao.existsById(menuGroupId)) {
            throw new IllegalArgumentException();
        }
    }

    private MenuProduct getMenuProduct(final MenuProductDemand menuProductDemand) {
        final Product product = productDao.findMandatoryById(menuProductDemand.getProductId());
        return new MenuProduct(product.getPrice(), product.getId(), menuProductDemand.getQuantity());
    }

    public List<MenuRecord> list() {
        return MenuRecord.from(menuDao.findAll());
    }

    public MenuRecord findById(final Long id) {
        return MenuRecord.from(menuDao.findById(id).orElseThrow(IllegalArgumentException::new));
    }
}
