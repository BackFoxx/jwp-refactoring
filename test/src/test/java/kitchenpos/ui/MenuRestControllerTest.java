package kitchenpos.ui;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import kitchenpos.api.menu.ui.MenuRestController;
import kitchenpos.api.menu.ui.dto.MenuRequest;
import kitchenpos.core.menu.application.MenuService;
import kitchenpos.core.menu.application.dto.MenuProductDemand;
import kitchenpos.core.menu.application.dto.MenuProductRecord;
import kitchenpos.core.menu.application.dto.MenuRecord;
import kitchenpos.core.product.domain.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MenuRestController.class)
class MenuRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MenuService menuService;

    @Test
    @DisplayName("POST /api/menus")
    void createProduct() throws Exception {
        final List<MenuProductDemand> menuProductRequests = List.of(
                new MenuProductDemand(1L, 4L),
                new MenuProductDemand(2L, 8L)
        );
        final MenuRequest menuRequest = new MenuRequest("준팍", new BigDecimal("4000"), 1L, menuProductRequests);

        final MenuRecord menuRecord = new MenuRecord(1L, "준팍", new Price(new BigDecimal("4000")), 1L,
                List.of(new MenuProductRecord(1L, 1L, 4L), new MenuProductRecord(1L, 1L, 4L)));

        when(menuService.create("준팍", new Price(new BigDecimal("4000")), 1L, menuProductRequests))
                .thenReturn(menuRecord);

        mockMvc.perform(post("/api/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}
