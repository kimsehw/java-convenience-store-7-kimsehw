package store.dto;

import store.model.domain.PurchaseResponseCode;

public class PurchaseResponse {

    private String name;
    private PurchaseResponseCode purchaseResponseCode;
    private int promotionCount;
    private int restCount;

    public PurchaseResponse(PurchaseResponseCode purchaseResponseCode, int promotionCount, int restCount) {
        this.purchaseResponseCode = purchaseResponseCode;
        this.promotionCount = promotionCount;
        this.restCount = restCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PurchaseResponseCode getPurchaseResponseCode() {
        return purchaseResponseCode;
    }

    public void setPurchaseResponseCode(PurchaseResponseCode purchaseResponseCode) {
        this.purchaseResponseCode = purchaseResponseCode;
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
