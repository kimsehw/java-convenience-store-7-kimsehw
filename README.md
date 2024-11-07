# 편의점


# ✅ 기능 목록 정리

### **⚙️ Model**

**재고 관리 요구사항**

구매 수량이 재고 수량을 초과한 경우:
<br>`[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.`
<br>존재하지 않는 상품을 입력한 경우:
<br>`[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.`
- 각 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다.
- 고객이 상품을 구매할 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감하여 수량을 관리한다.
- 재고를 차감함으로써 시스템은 최신 재고 상태를 유지하며, 다음 고객이 구매할 때 정확한 재고 정보를 제공한다.

***기능***
- **재고 관리자**
- [ ] 재고들 중 구매 상품을 찾는 기능
   - [ ] 존재하지 않는 상품을 입력받은 경우, 예외를 발생시킨다.
   - [ ] 구매 수량이 재고의 수량을 초과한 경우, 예외를 발생시킨다.
- [ ] 재고 상태를 불러오는 기능
- [ ] 재고 상태를 최신화 하는 기능


- **재고**
- [x] 이름 확인 기능
- [x] 구매 가능 여부 확인 기능
   - [x] 구매 가능할 경우, 결제 된 수량 만큼 재고를 차감한다.

재고 상태 : `products.md`