package store.model.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StockManagerTest {

    private final StockManager stockManager = new StockManager();

    @DisplayName("상품 목록 불러오기 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"src/main/resources/products.md"})
    void readProductsTest(String productsFilePath) throws IOException {
        stockManager.readProductsFrom(productsFilePath);
        long productsCount = Files.lines(Path.of(productsFilePath)).count();
        assertThat(stockManager).extracting("stock").asInstanceOf(LIST)
                .hasSize((int) productsCount - 1);
    }
}