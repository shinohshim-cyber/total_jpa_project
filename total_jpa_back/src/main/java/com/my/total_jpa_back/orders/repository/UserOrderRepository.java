package com.my.total_jpa_back.orders.repository;

import com.my.total_jpa_back.common.entity.OrderStatus;
import com.my.total_jpa_back.orders.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
    // 2. 주문상태로 조회
    // select * from user_order where status = 'COMPLETE'
    List<UserOrder> findByStatus(OrderStatus status);

    // 3. 상품명 중 특정 키워드 찾기
    List<UserOrder> findByProductNameContaining(String keyword);

    // 4. 특정 가격 이상인 주문 조회
    // select * from user_order where price >= 300000;
    List<UserOrder> findByPriceGreaterThanEqual(int price);

    // 5. 특정 userId로 검색
    // select * from user_order where user_id=1;
    List<UserOrder> findByUserId(Long userId);

    // 6. userId = 10이고 COMPLETE 상태인 주문을 조회하라.
    // select * form user_order where
    // userId = 10 and status = 'COMPLETE'
    List<UserOrder> findByUserIdAndStatus(Long userId, OrderStatus status);

    // 7. 가격이 290000 ~ 300000 사이인 주문을 조회
    // select * from user_order where price between 290000 and 300000;
    List<UserOrder> findByPriceBetween(int start, int end);

    // 8. 가격 내림차순..
    // select * from user_order Order by price desc;
    List<UserOrder> findAllByOrderByPriceDesc();

    // 9. 최근 주문 5개
    // select * from user_order order by created_at desc limit 5;
    List<UserOrder> findTop5ByOrderByCreatedAtDesc();

    // 10. READY 또는 SHIPPING 상태인 주문을 조회
    // in 구문은 리스트로 만들어서 재료를 전달한다.
    // select * from user_order where status in ('READY' ,'SHIPPING')
    List<UserOrder> findByStatusIn(List<OrderStatus> list);
}
