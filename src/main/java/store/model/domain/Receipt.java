package store.model.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import store.constant.ConstantBox;

public class Receipt {

    private static final int ZERO = 0;
    public static final int MAX_MEMBERSHIP_DISCOUNT = 8000;
    public static final int MIN_MEMBERSHIP_DISCOUNT = 1000;

    private final List<String> names = new ArrayList<>();
    private final List<Integer> quantities = new ArrayList<>();
    private final List<Integer> prices = new ArrayList<>();
    private final List<Integer> promotionCounts = new ArrayList<>();

    public void addSalesData(SalesData salesData) {
        names.add(salesData.getName());
        quantities.add(salesData.getQuantity());
        prices.add(salesData.getPrice());
        promotionCounts.add(salesData.getPromotionCount());
    }

    public ReceiptInformation getReceiptInformation(String membershipRespond) {
        int totalPurchased = ZERO;
        int promotionDiscount = ZERO;
        int totalQuantity = ZERO;
        List<Integer> amountInformation = generateAmountInformation(membershipRespond, totalPurchased,
                promotionDiscount, totalQuantity);
        Map<String, List> salesDatas = generateSalesDatas();
        return new ReceiptInformation(salesDatas, amountInformation);
    }

    private List<Integer> generateAmountInformation(String membershipRespond, int totalPurchased, int promotionDiscount,
                                                    int totalQuantity) {
        for (int productKindIndex = ZERO; productKindIndex < names.size(); productKindIndex++) {
            totalPurchased += quantities.get(productKindIndex) * prices.get(productKindIndex);
            promotionDiscount += promotionCounts.get(productKindIndex) * prices.get(productKindIndex);
            totalQuantity += quantities.get(productKindIndex);
        }
        int memberShipDiscount = getMembershipDiscount(membershipRespond, totalPurchased, promotionDiscount);
        int pay = totalPurchased - promotionDiscount - memberShipDiscount;
        return List.of(totalQuantity, totalPurchased, promotionDiscount, memberShipDiscount, pay);
    }

    private int getMembershipDiscount(String membershipRespond, int totalPurchasedAmount,
                                      int promotionDiscountAmount) {
        if (membershipRespond.equals(ConstantBox.CUSTOMER_RESPOND_N)) {
            return ZERO;
        }
        return calculateMembershipDiscount(totalPurchasedAmount, promotionDiscountAmount);
    }

    private static int calculateMembershipDiscount(int totalPurchasedAmount, int promotionDiscountAmount) {
        double discount = (totalPurchasedAmount - promotionDiscountAmount) * 0.3;
        int memberShipDiscountAmount = ((int) discount / 1000) * 1000;
        if (memberShipDiscountAmount < MIN_MEMBERSHIP_DISCOUNT) {
            return ZERO;
        }
        if (memberShipDiscountAmount > MAX_MEMBERSHIP_DISCOUNT) {
            return MAX_MEMBERSHIP_DISCOUNT;
        }
        return memberShipDiscountAmount;
    }

    private Map<String, List> generateSalesDatas() {
        return Map.of(
                "names", Collections.unmodifiableList(names),
                "quantities", Collections.unmodifiableList(quantities),
                "prices", Collections.unmodifiableList(prices),
                "promotionCounts", Collections.unmodifiableList(promotionCounts)
        );
    }
}
