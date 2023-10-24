package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.application.response.MenuGroupResponse;
import kitchenpos.dao.MenuCustomDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

@DataJdbcTest
@Import({MenuGroupService.class, MenuCustomDao.class})
class MenuGroupServiceTest {

    @Autowired
    private MenuGroupService menuGroupService;

    @Test
    @DisplayName("메뉴 그룹의 이름을 제공하여 메뉴 그룹을 저장할 수 있다.")
    void givenName() {
        final MenuGroupResponse savedMenuGroup = menuGroupService.create("메뉴메뉴~!");

        assertThat(savedMenuGroup).isNotNull();
    }

    @Test
    @DisplayName("메뉴 그룹의 이름은 255자까지 표현할 수 있다.")
    void nameSize() {
        assertThatThrownBy(() -> menuGroupService.create("맴".repeat(256)))
                .as("이름의 길이가 255자를 초과하면 저장할 수 없다.")
                .hasCauseInstanceOf(DataIntegrityViolationException.class);
    }
}
