package kitchenpos.application;

import static kitchenpos.application.kitchenposFixture.메뉴그룹만들기;
import static kitchenpos.application.kitchenposFixture.메뉴상품만들기;
import static kitchenpos.application.kitchenposFixture.상품만들기;
import static kitchenpos.application.kitchenposFixture.저장할메뉴만들기;
import static kitchenpos.application.kitchenposFixture.주문테이블만들기;
import static kitchenpos.application.kitchenposFixture.주문할메뉴만들기;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import kitchenpos.dao.JdbcTemplateMenuDao;
import kitchenpos.dao.JdbcTemplateMenuGroupDao;
import kitchenpos.dao.JdbcTemplateMenuProductDao;
import kitchenpos.dao.JdbcTemplateOrderDao;
import kitchenpos.dao.JdbcTemplateOrderLineItemDao;
import kitchenpos.dao.JdbcTemplateOrderTableDao;
import kitchenpos.dao.JdbcTemplateProductDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderLineItem;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import({ProductService.class, JdbcTemplateProductDao.class, MenuService.class,
        JdbcTemplateMenuDao.class, MenuGroupService.class, JdbcTemplateMenuGroupDao.class,
        JdbcTemplateMenuProductDao.class, JdbcTemplateOrderTableDao.class, OrderService.class,
        JdbcTemplateOrderDao.class, TableService.class,
        JdbcTemplateOrderLineItemDao.class})
class TableServiceTest {

    private TableService tableService;

    @Autowired
    DataSource dataSource;

    @BeforeEach
    void setUp() {
        this.tableService = new TableService(
                new JdbcTemplateOrderDao(dataSource),
                new JdbcTemplateOrderTableDao(dataSource)
        );
    }

    @Nested
    @DisplayName("주문 테이블의 비어있음 상태를 변경하는 경우")
    class EmptyTable {

        @Test
        @DisplayName("orderTableId에 해당하는 pathVariable에 주문 테이블 식별자를 제공하여 테이블의 비어있음 여부를 변경할 수 있다.")
        void givenOrderTableId() {
            final OrderTable orderTable = new OrderTable();
            orderTable.setEmpty(true);
            final OrderTable savedTable = tableService.create(orderTable);

            assertThat(savedTable.isEmpty()).isTrue();

            final OrderTable notEmptyTable = new OrderTable(); // 비어있음 상태를 변경하기 위한 객체
            notEmptyTable.setEmpty(false);

            final OrderTable changedTable = tableService.changeEmpty(savedTable.getId(), notEmptyTable);
            assertThat(changedTable.isEmpty()).isFalse();
        }

        @Test
        @DisplayName("실재하지 않는 주문 테이블의 상태를 변경할 수 없다.")
        void notExistingTable() {
            final OrderTable notEmptyTable = new OrderTable(); // 비어있음 상태를 변경하기 위한 객체
            notEmptyTable.setEmpty(false);

            assertThatThrownBy(() -> tableService.changeEmpty(0L, notEmptyTable))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("주문 상태가 COOKING 또는 MEAL인 주문 테이블의 상태를 변경할 수 없다.")
        void invalidTableStatus(
                @Autowired ProductService productService,
                @Autowired MenuService menuService,
                @Autowired OrderService orderService,
                @Autowired MenuGroupService menuGroupService,
                @Autowired TableService tableService
        ) {
            // given : 상품
            final Product savedProduct = productService.create(상품만들기("상품!", "4000", productService));

            // given : 메뉴 그룹
            final MenuGroup savedMenuGroup = 메뉴그룹만들기(menuGroupService);

            // given : 메뉴
            final MenuProduct menuProduct = 메뉴상품만들기(savedProduct, 4L);

            final Menu savedMenu = menuService.create(저장할메뉴만들기("메뉴!", "4000", savedMenuGroup.getId(), menuProduct));
            final Menu savedMenu2 = menuService.create(저장할메뉴만들기("메뉴 2!", "9000", savedMenuGroup.getId(), menuProduct));

            final OrderLineItem orderLineItem = 주문할메뉴만들기(savedMenu, 4);
            final OrderLineItem orderLineItem2 = 주문할메뉴만들기(savedMenu2, 3);

            // given : 주문 테이블
            final OrderTable savedOrderTable = 주문테이블만들기(tableService, false);

            // given : 주문
            final Order order = new Order();
            order.setOrderTableId(savedOrderTable.getId());
            order.setOrderLineItems(List.of(orderLineItem, orderLineItem2));
            final Order savedOrder = orderService.create(order);

            final Order changingStatusOrder = new Order();
            changingStatusOrder.setOrderStatus(OrderStatus.MEAL.name());
            orderService.changeOrderStatus(savedOrder.getId(), changingStatusOrder);

            final OrderTable changingEmptyOrderTable = new OrderTable();
            changingEmptyOrderTable.setEmpty(false);

            assertThatThrownBy(() -> tableService.changeEmpty(savedOrderTable.getId(), changingEmptyOrderTable))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("손님 수를 변경하는 경우")
    class changeGuestNumber {

        @Test
        @DisplayName("orderTableId에 해당하는 pathVariable에 주문 테이블 식별자를, requestBody에 변경하려는 손님 수를 제공하여 변경할 수 있다.")
        void given(@Autowired OrderTableDao orderTableDao) {
            final OrderTable orderTable = new OrderTable();
            orderTable.setEmpty(false);
            final OrderTable savedTable = tableService.create(orderTable);

            final OrderTable changingGuestNumberOrderTable = new OrderTable(); // 손님의 수를 변경하기 위한 객체
            changingGuestNumberOrderTable.setNumberOfGuests(9);

            tableService.changeNumberOfGuests(savedTable.getId(), changingGuestNumberOrderTable);

            final Optional<OrderTable> changedOrderTable = orderTableDao.findById(savedTable.getId());
            assertThat(changedOrderTable.get().getNumberOfGuests()).isEqualTo(9);
        }

        @Test
        @DisplayName("변경하려는 손님 수가 음수라면 변경할 수 없다.")
        void invalidGuestNumber() {
            final OrderTable orderTable = new OrderTable();
            orderTable.setEmpty(false);
            final OrderTable savedTable = tableService.create(orderTable);

            final OrderTable changingGuestNumberOrderTable = new OrderTable(); // 손님의 수를 변경하기 위한 객체
            changingGuestNumberOrderTable.setNumberOfGuests(-1);

            assertThatThrownBy(
                    () -> tableService.changeNumberOfGuests(savedTable.getId(), changingGuestNumberOrderTable))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("실재하지 않는 주문 테이블의 손님 수를 변경할 수 없다.")
        void notExistingTable() {
            final OrderTable changingGuestNumberOrderTable = new OrderTable(); // 손님의 수를 변경하기 위한 객체
            changingGuestNumberOrderTable.setNumberOfGuests(9);

            assertThatThrownBy(
                    () -> tableService.changeNumberOfGuests(0L, changingGuestNumberOrderTable))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("주문 테이블이 빈 테이블이라면 손님 수 변경할 수 없다.")
        void emptyTable() {
            final OrderTable orderTable = new OrderTable();
            orderTable.setEmpty(true);
            final OrderTable savedTable = tableService.create(orderTable);

            final OrderTable changingGuestNumberOrderTable = new OrderTable(); // 손님의 수를 변경하기 위한 객체
            changingGuestNumberOrderTable.setNumberOfGuests(9);

            assertThatThrownBy(
                    () -> tableService.changeNumberOfGuests(savedTable.getId(), changingGuestNumberOrderTable))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
