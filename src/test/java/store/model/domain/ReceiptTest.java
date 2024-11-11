package store.model.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.constant.ConstantBox;

class ReceiptTest {

    private final Receipt receipt = new Receipt();

    @DisplayName("영수증 구성 정보를 만드는 기능 테스트")
    @ParameterizedTest
    @MethodSource("generateReceiptInformationCase")
    void getReceiptInformation(Map<String, List> expectedSalesDatas, List<Integer> expectedAmountInformation) {
        receipt.addSalesData(new SalesData("콜라", 3, 1000, 1, 0));
        receipt.addSalesData(new SalesData("에너지바", 5, 2000, 0, 0));
        assertThat(receipt.getReceiptInformation(ConstantBox.CUSTOMER_RESPOND_Y))
                .extracting("salesDatas", "amountInformation")
                .containsExactly(expectedSalesDatas, expectedAmountInformation);
    }

    static Stream<Arguments> generateReceiptInformationCase() {
        Map<String, List> expectedSalesDatas = Map.of(
                "names", List.of("콜라", "에너지바"),
                "quantities", List.of(3, 5),
                "prices", List.of(1000, 2000),
                "promotionCounts", List.of(1, 0)
        );
        return Stream.of(
                Arguments.of(expectedSalesDatas, List.of(8, 13000, 1000, 3000, 9000))
        );
    }
}