# 편의점

# ✅ 기능 목록 정리

### **⚙️ Model**

***기능***

- **재고 관리자**
- [ ] 재고들 중 구매 상품을 찾는 기능
    - [ ] 존재하지 않는 상품을 입력받은 경우, 예외를 발생시킨다.
    - [ ] 구매 수량이 재고의 수량을 초과한 경우, 예외를 발생시킨다.
- [x] 재고 상태를 불러오는 기능
    - [x] 프로모션 유무에 따라 상품의 종류를 달리하여 저장합니다.
- [x] 행사 목록을 불러오는 기능.
- [ ] 재고 상태를 최신화 하는 기능

재고 상태, 상품 목록 : `products.md`

- **일반 상품**
- [x] 이름 확인 기능
- [x] 구매 가능 여부 확인 기능
    - [x] 구매 가능할 경우, 결제 된 수량 만큼 재고를 차감하고, 정상 구매 코드를 반환합니다.
    - [ ] 재고가 부족할 경우, 재고 부족 코드를 반환합니다.


- **프로모션 상품**
- [x] 이름 확인 기능
- [ ] 구매 가능 여부 확인 기능
    - [ ] 요청 수량이 프로모션 할인 수량 보다 적은 경우, 추가 수량 요청코드를 반환합니다.
    - [ ] 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우,
        - [ ] 일반 상품 재고도 없는 경우, 재고 부족 코드를 반환합니다.
        - [ ] 일반 상품 재고일부 수량 정가 구매 요청 코드를 반환합니다.
    - [ ] 구매 가능할 경우, 결제 된 수량 만큼 재고를 차감하고, 정상 구매 코드를 반환합니다.

- **프로모션**
- [x] 프로모션 유효 기간 검사 기능
    - [x] 오늘 날짜가 프로모션 기간에 포함되는지 검사합니다.
- [x] Buy N Get 1 Free 기능
    - [x] 프로모션을 적용한 판매 개수를 반환합니다.

## **📝 요구 사항**

**재고 관리 요구사항**

구매 수량이 재고 수량을 초과한 경우:
<br>`[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.`
<br>존재하지 않는 상품을 입력한 경우:
<br>`[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.`

- 각 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다.
- 고객이 상품을 구매할 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감하여 수량을 관리한다.
- 재고를 차감함으로써 시스템은 최신 재고 상태를 유지하며, 다음 고객이 구매할 때 정확한 재고 정보를 제공한다.

**프로모션 할인 요구 사항**

- 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용한다.
- 프로모션은 N개 구매 시 1개 무료 증정(Buy N Get 1 Free)의 형태로 진행된다.
- 1+1 또는 2+1 프로모션이 각각 지정된 상품에 적용되며, 동일 상품에 여러 프로모션이 적용되지 않는다.
- 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있다.
- 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.
- 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우,
    - 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
- 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우,
    - 일부 수량에 대해 정가로 결제하게 됨을 안내한다.
