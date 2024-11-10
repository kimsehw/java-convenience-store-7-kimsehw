package store.model.domain;

public class SalesData {

    private String name;
    private int quantity;
    private int price;
    private int promotionCount;
    private int restCount;

    public SalesData(String name, int quantity, int price, int promotionCount, int restCount) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.promotionCount = promotionCount;
        this.restCount = restCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPromotionCount() {
        return promotionCount;
    }

    public void setPromotionCount(int promotionCount) {
        this.promotionCount = promotionCount;
    }

    public int getRestCount() {
        return restCount;
    }

    public void setRestCount(int restCount) {
        this.restCount = restCount;
    }
}
