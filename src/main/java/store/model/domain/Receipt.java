package store.model.domain;

import java.util.ArrayList;
import java.util.List;

public class Receipt {

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
}
