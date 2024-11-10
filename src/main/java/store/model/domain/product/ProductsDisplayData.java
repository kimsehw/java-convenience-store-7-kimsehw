package store.model.domain.product;

public class ProductsDisplayData {

    private String promotionProductData;
    private String normalProductData;

    public ProductsDisplayData(String promotionProductData, String normalProductData) {
        this.promotionProductData = promotionProductData;
        this.normalProductData = normalProductData;
    }

    public String getPromotionProductData() {
        return promotionProductData;
    }

    public void setPromotionProductData(String promotionProductData) {
        this.promotionProductData = promotionProductData;
    }

    public String getNormalProductData() {
        return normalProductData;
    }

    public void setNormalProductData(String normalProductData) {
        this.normalProductData = normalProductData;
    }
}
