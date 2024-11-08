package store.model.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        List<String> productsData = Files.readAllLines(Path.of(StockManager.PRODUCTS_FILE_PATH));
        Set<String> names = new HashSet<>();
        productsData.forEach(productData -> names.add(productData.split(StockManager.SEPARATOR)[0]));
        assertThat(stockManager).extracting("stock").asInstanceOf(MAP)
                .hasSize(names.size() - HEADER_LINE_COUNT);
    }

    @DisplayName("행사 목록 불러오기 테스트")
    @Test
    void readPromotionsTest() throws IOException {
        long productsCount = Files.lines(Path.of(StockManager.PROMOTIONS_FILE_PATH)).count();
        assertThat(stockManager).extracting("promotions").asInstanceOf(MAP)
                .hasSize((int) productsCount - HEADER_LINE_COUNT);
    }


}