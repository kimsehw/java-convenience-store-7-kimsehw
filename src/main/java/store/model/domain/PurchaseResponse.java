package store.model.domain;

public class PurchaseResponse {
    private PurchaseResponseCode purchaseResponseCode;
    private int promotionCount;
    private int partialCount;

    public PurchaseResponse(PurchaseResponseCode purchaseResponseCode, int promotionCount, int partialCount) {
        this.purchaseResponseCode = purchaseResponseCode;
        this.promotionCount = promotionCount;
        this.partialCount = partialCount;
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

    public int getPartialCount() {
        return partialCount;
    }

    public void setPartialCount(int partialCount) {
        this.partialCount = partialCount;
    }
}
