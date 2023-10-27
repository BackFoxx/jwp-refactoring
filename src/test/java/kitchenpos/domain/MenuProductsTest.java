package kitchenpos.domain;

import static kitchenpos.application.KitchenposFixture.메뉴그룹만들기;
import static kitchenpos.application.KitchenposFixture.메뉴상품만들기;
import static kitchenpos.application.KitchenposFixture.상품만들기;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.application.request.MenuProductRequest;
import kitchenpos.domain.product.Price;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductsTest {
    @Test
    @DisplayName("메뉴에 있는 각 상품의 금액 총합보다 주어진 금액이 크다면 true를 반환한다.")
    void invalidMenuPrice() {
        // given
        final MenuProducts menuProducts = new MenuProducts(List.of(
                new MenuProduct(1L, new Price(new BigDecimal("4000")), 1L, 1L),
                new MenuProduct(2L, new Price(new BigDecimal("4000")), 2L, 1L)
        ));

        // when & then
        assertThat(menuProducts.isBiggerThanTotalPrice(new Price(new BigDecimal("13000")))).isTrue();
    }
}
