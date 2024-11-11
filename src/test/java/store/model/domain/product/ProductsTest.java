package store.model.domain.product;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.constant.ConstantBox;
import store.model.domain.Promotion;
import store.model.domain.PurchaseResponse;
import store.model.domain.PurchaseResponseCode;
import store.model.domain.Receipt;

class ProductsTest {

    private static final int TEST_PROMOTION_QUANTITY = 7;
    private static final int TEST_NORMAL_QUANTITY = 3;
    private static final int TEST_TOTAL_QUANTITY = 10;
    private static final String TEST_NAME = "콜라";
    private static final int TEST_PRICE = 1000;
    private static final Product normalProduct = new NormalProduct(TEST_NAME, TEST_PRICE, TEST_NORMAL_QUANTITY);

    private Products products;
    private Product promotionProduct = new PromotionProduct(TEST_NAME, TEST_PRICE, TEST_PROMOTION_QUANTITY, null);

    @BeforeEach
    void init() {
        products = null;
    }

    @DisplayName("상품 추가 테스트(두 종류)")
    @Test
    void addTest() {
        products = new Products(normalProduct);
        products.add(promotionProduct);
        assertThat(products).extracting("totalQuantity")
                .isEqualTo(TEST_TOTAL_QUANTITY);
        assertThat(products).extracting("products")
                .asInstanceOf(LIST)
                .containsExactly(promotionProduct, normalProduct);
    }

    @DisplayName("상품 추가 테스트(일반 한 종류)")
    @Test
    void oneNormalAddTest() {
        products = new Products(normalProduct);
        assertThat(products).extracting("totalQuantity")
                .isEqualTo(TEST_NORMAL_QUANTITY);
        assertThat(products).extracting("products")
                .asInstanceOf(LIST)
                .containsExactly(null, normalProduct);
    }

    @DisplayName("상품 추가 테스트(프로모션 한 종류)")
    @Test
    void onePromotionAddTest() {
        products = new Products(promotionProduct);
        assertThat(products).extracting("totalQuantity")
                .isEqualTo(TEST_PROMOTION_QUANTITY);
        assertThat(products).extracting("products")
                .asInstanceOf(LIST)
                .containsExactly(promotionProduct, null);
    }

    @DisplayName("재고 검사 기능 테스트")
    @ParameterizedTest
    @MethodSource("generatePurchaseCase")
    void checkQuantityTest(String startDate, String endDate, int requestQuantity, PurchaseResponseCode expectedCode) {
        Promotion promotion = new Promotion(List.of("2+1", "2", "1", startDate, endDate));
        promotionProduct = new PromotionProduct(TEST_NAME, TEST_PRICE, TEST_PROMOTION_QUANTITY, promotion);
        products = new Products(promotionProduct);
        products.add(normalProduct);
        assertThat(products.checkQuantity(requestQuantity))
                .extracting("purchaseResponseCode")
                .isEqualTo(expectedCode);
    }

    static Stream<Arguments> generatePurchaseCase() {
        return Stream.of(
                Arguments.of("2024-01-01", "2024-12-31", 3, PurchaseResponseCode.PURCHASE_SUCCESS),
                Arguments.of("2023-01-01", "2023-12-31", 3, PurchaseResponseCode.PROMOTION_PARTIAL_UNAVAILABLE),
                Arguments.of("2024-01-01", "2024-12-31", 2, PurchaseResponseCode.FREE_PRODUCT_REMIND),
                Arguments.of("2024-01-01", "2024-12-31", 11, PurchaseResponseCode.OUT_OF_STOCK)
        );
    }

    @DisplayName("재고 검사 기능 테스트(일반 상품만 있는 경우)")
    @ParameterizedTest
    @MethodSource("generateOnlyNormalPurchaseCase")
    void checkOnlyNormalQuantityTest(int requestQuantity, PurchaseResponseCode expectedCode, int expectedRestCount) {
        products = new Products(normalProduct);
        assertThat(products.checkQuantity(requestQuantity))
                .extracting("purchaseResponseCode", "restCount")
                .containsExactly(expectedCode, expectedRestCount);
    }

    static Stream<Arguments> generateOnlyNormalPurchaseCase() {
        return Stream.of(
                Arguments.of(2, PurchaseResponseCode.PURCHASE_SUCCESS, 2),
                Arguments.of(3, PurchaseResponseCode.PURCHASE_SUCCESS, 3),
                Arguments.of(11, PurchaseResponseCode.OUT_OF_STOCK, 11)
        );
    }

    @DisplayName("진열 데이터 생성 테스트")
    @ParameterizedTest
    @MethodSource("generateDisplayCase")
    void getProductsDataTest(boolean onlyPromotion, Product promotionProduct, String expectedPromotionProductData,
                             String expectedNormalProductData) {
        products = new Products(normalProduct);
        if (promotionProduct != null) {
            products.add(promotionProduct);
        }
        if (onlyPromotion) {
            products = new Products(promotionProduct);
        }
        assertThat(products.getProductsData())
                .extracting("promotionProductData", "normalProductData")
                .containsExactly(expectedPromotionProductData, expectedNormalProductData);
    }

    static Stream<Arguments> generateDisplayCase() {
        Promotion promotion = new Promotion(List.of("탄산2+1", "2", "1", "null", "null"));
        Product promotionProduct = new PromotionProduct(TEST_NAME, TEST_PRICE, TEST_PROMOTION_QUANTITY,
                promotion);
        String expectedPromotionProductData = promotionProduct.getProductData();
        String expectedNormalProductData = normalProduct.getProductData();
        return Stream.of(
                Arguments.of(false, promotionProduct, expectedPromotionProductData, expectedNormalProductData),
                Arguments.of(false, null, null, expectedNormalProductData),
                Arguments.of(true, promotionProduct, expectedPromotionProductData,
                        String.join(ConstantBox.SEPARATOR, TEST_NAME, Integer.toString(TEST_PRICE),
                                ConstantBox.NO_QUANTITY))
        );
    }

    @DisplayName("영수증 업데이트 기능 테스트")
    @ParameterizedTest
    @MethodSource({"generateFreeRemindUpdateCase", "generatePartialUnavailableUpdateCase", "generateSuccessUpdateCase"})
    void updateReceiptTest(String customerRespond, PurchaseResponse purchaseResponse,
                           List<Integer> expectedQuantities, List<Integer> expectedPromotionCounts) {
        Receipt receipt = new Receipt();
        Promotion promotion = new Promotion(List.of("2+1", "2", "1", "startDate", "endDate"));
        promotionProduct = new PromotionProduct(TEST_NAME, TEST_PRICE, TEST_PROMOTION_QUANTITY, promotion);
        products = new Products(promotionProduct);
        products.add(new NormalProduct(TEST_NAME, TEST_PRICE, TEST_NORMAL_QUANTITY));
        products.updateReceipt(receipt, customerRespond, purchaseResponse);
        assertThat(receipt)
                .extracting("names", "quantities", "prices", "promotionCounts")
                .containsExactly(List.of(TEST_NAME), expectedQuantities, List.of(TEST_PRICE), expectedPromotionCounts);
    }

    static Stream<Arguments> generateFreeRemindUpdateCase() {
        return Stream.of(
                Arguments.of("Y", new PurchaseResponse(PurchaseResponseCode.FREE_PRODUCT_REMIND, 1, 2),
                        List.of(6), List.of(2)),
                Arguments.of("N", new PurchaseResponse(PurchaseResponseCode.FREE_PRODUCT_REMIND, 1, 2),
                        List.of(5), List.of(1))
        );
    }

    static Stream<Arguments> generatePartialUnavailableUpdateCase() {
        return Stream.of(
                Arguments.of("Y", new PurchaseResponse(PurchaseResponseCode.PROMOTION_PARTIAL_UNAVAILABLE, 2, 4),
                        List.of(10), List.of(2)),
                Arguments.of("N", new PurchaseResponse(PurchaseResponseCode.PROMOTION_PARTIAL_UNAVAILABLE, 2, 4),
                        List.of(6), List.of(2)),
                Arguments.of("Y", new PurchaseResponse(PurchaseResponseCode.PROMOTION_PARTIAL_UNAVAILABLE, 1, 1),
                        List.of(4), List.of(1)),
                Arguments.of("N", new PurchaseResponse(PurchaseResponseCode.PROMOTION_PARTIAL_UNAVAILABLE, 1, 1),
                        List.of(3), List.of(1))
        );
    }

    static Stream<Arguments> generateSuccessUpdateCase() {
        return Stream.of(
                Arguments.of("Y", new PurchaseResponse(PurchaseResponseCode.PURCHASE_SUCCESS, 2, 0),
                        List.of(6), List.of(2)),
                Arguments.of("N", new PurchaseResponse(PurchaseResponseCode.PURCHASE_SUCCESS, 0, 4),
                        List.of(4), List.of(0))
        );
    }
}