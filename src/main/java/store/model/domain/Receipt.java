package store.model.domain;

import java.util.ArrayList;
import java.util.List;

public class Receipt {

    private final List<String> name = new ArrayList<>();
    private final List<Integer> quantity = new ArrayList<>();
    private final List<Integer> price = new ArrayList<>();
    private final List<Integer> promotionCount = new ArrayList<>();

    public void addSalesData(SalesData salesData) {
        name.add(salesData.getName());
        quantity.add(salesData.getQuantity());
        price.add(salesData.getPrice());
        promotionCount.add(salesData.getPromotionCount());
    }
}
