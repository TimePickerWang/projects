package com.wang.repository;

import com.wang.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by wangjf
 * Date 2018/12/30 16:02
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
	List<OrderDetail> findByOrderId(String orderId);
}
