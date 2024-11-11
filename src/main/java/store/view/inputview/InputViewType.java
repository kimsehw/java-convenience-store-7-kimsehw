package store.view.inputview;

public enum InputViewType {

    PRODUCTS_NAME_AMOUNT("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    MEMBERSHIP("멤버십 할인을 받으시겠습니까? (Y/N)"),
    FREE_PRODUCT_REMIND("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    PROMOTION_PARTIAL_UNAVAILABLE("현재 %s %,d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
    CONTINUE_SHOPPING("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");

    private final String requestMessage;

    InputViewType(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public String getRequestMessage() {
        return this.requestMessage;
    }
}
