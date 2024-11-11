package store.view.outputview;

import java.util.List;
import java.util.Map;
import store.model.domain.ReceiptInformation;

public class ReceiptView implements OutputView {

    private static final String STORE_NAME = "==============W 편의점================";
    private static final String HEADER = "상품명\t\t수량\t금액";
    private static final String PROMOTION_HEADER = "=============증\t정===============";
    private static final String AMOUNT_INFORMATION_LINE = "====================================";
    private static final String SALES_DATA_FORMAT = "%s\t\t%,d \t%,d";
    private static final String PROMOTION_FORMAT = "%s\t\t%,d";
    private static final String TOTAL_PURCHASED_AMOUNT_FORMAT = "총구매액\t\t%,d\t%,d";
    private static final String TOTAL_PROMOTION_DISCOUNT_FORMAT = "행사할인\t\t\t-%,d";
    private static final String TOTAL_MEMBERSHIP_DISCOUNT_FORMAT = "멤버십할인\t\t\t-%,d";
    private static final String TOTAL_PAY_FORMAT = "내실돈\t\t\t %,d";
    private static final int ZERO = 0;
    private static final int QUANTITY_INDEX = 0;
    private static final int TOTAL_PURCHASED_AMOUNT_INDEX = 1;
    private static final int TOTAL_PROMOTION_DISCOUNT_INDEX = 2;
    private static final int TOTAL_MEMBERSHIP_DISCOUNT_INDEX = 3;
    private static final int TOTAL_PAY_AMOUNT_INDEX = 4;

    private final ReceiptInformation receiptInformation;

    public ReceiptView(ReceiptInformation receiptInformation) {
        this.receiptInformation = receiptInformation;
    }

    @Override
    public void display() {
        displayHeaderPart();
        displaySalesData();
        displayPromotion();
        displayAmountInformation();
    }

    private void displayHeaderPart() {
        System.out.println(STORE_NAME);
        System.out.println(HEADER);
    }

    private void displaySalesData() {
        Map<String, List> salesData = receiptInformation.getSalesDatas();
        int numberOfProductKinds = salesData.get("names").size();
        for (int productKindIndex = ZERO; productKindIndex < numberOfProductKinds; productKindIndex++) {
            showSalesData(salesData, productKindIndex);
        }
    }

    private static void showSalesData(Map<String, List> salesData, int productKindIndex) {
        String name = (String) salesData.get("names").get(productKindIndex);
        int quantity = (int) salesData.get("quantities").get(productKindIndex);
        int price = (int) salesData.get("prices").get(productKindIndex);
        System.out.println(String.format(SALES_DATA_FORMAT, name, quantity, price * quantity));
    }

    private void displayPromotion() {
        System.out.println(PROMOTION_HEADER);
        Map<String, List> salesData = receiptInformation.getSalesDatas();
        int numberOfProductKinds = salesData.get("names").size();
        for (int productKindIndex = ZERO; productKindIndex < numberOfProductKinds; productKindIndex++) {
            int promotionCount = (int) salesData.get("promotionCounts").get(productKindIndex);
            showPromotionInformation(promotionCount, salesData, productKindIndex);
        }
    }

    private void showPromotionInformation(int promotionCount, Map<String, List> salesData,
                                          int productKindIndex) {
        if (promotionCount != 0) {
            String name = (String) salesData.get("names").get(productKindIndex);
            System.out.println(String.format(PROMOTION_FORMAT, name, promotionCount));
        }
    }

    private void displayAmountInformation() {
        System.out.println(AMOUNT_INFORMATION_LINE);
        List<Integer> amountInformation = receiptInformation.getAmountInformation();
        showTotalPurchasedAmount(amountInformation);
        showTotalPromotionDiscount(amountInformation);
        showTotalMembershipDiscount(amountInformation);
        showTotalPayAmount(amountInformation);
    }

    private void showTotalPayAmount(List<Integer> amountInformation) {
        int pay = amountInformation.get(TOTAL_PAY_AMOUNT_INDEX);
        System.out.println(String.format(TOTAL_PAY_FORMAT, pay));
    }

    private void showTotalMembershipDiscount(List<Integer> amountInformation) {
        int memberShipDiscount = amountInformation.get(TOTAL_MEMBERSHIP_DISCOUNT_INDEX);
        System.out.println(String.format(TOTAL_MEMBERSHIP_DISCOUNT_FORMAT, memberShipDiscount));
    }

    private void showTotalPromotionDiscount(List<Integer> amountInformation) {
        int promotionDiscount = amountInformation.get(TOTAL_PROMOTION_DISCOUNT_INDEX);
        System.out.println(String.format(TOTAL_PROMOTION_DISCOUNT_FORMAT, promotionDiscount));
    }

    private void showTotalPurchasedAmount(List<Integer> amountInformation) {
        int totalQuantity = amountInformation.get(QUANTITY_INDEX);
        int totalPurchased = amountInformation.get(TOTAL_PURCHASED_AMOUNT_INDEX);
        System.out.println(String.format(TOTAL_PURCHASED_AMOUNT_FORMAT, totalQuantity, totalPurchased));
    }
}
