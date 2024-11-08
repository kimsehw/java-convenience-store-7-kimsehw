package store.model.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockManagerTest {

    public static final int HEADER_LINE_COUNT = 1;

    private StockManager stockManager;

    @BeforeEach
    void setUp() throws IOException {
        stockManager = new StockManager();
    }

    @DisplayName("상품 목록 불러오기 테스트")
    @Test
    void readProductsTest() throws IOException {
        long productsCount = Files.lines(Path.of(StockManager.PRODUCTS_FILE_PATH)).count();
        assertThat(stockManager).extracting("stock").asInstanceOf(LIST)
                .hasSize((int) productsCount - HEADER_LINE_COUNT);
    }
}