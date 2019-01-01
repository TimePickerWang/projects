package com.wang.repository;

import com.wang.models.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create by wangjf
 * Date 2018/12/30 15:56
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
	Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
