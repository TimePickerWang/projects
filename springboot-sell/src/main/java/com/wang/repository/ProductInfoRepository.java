package com.wang.repository;

import com.wang.models.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by wangjf
 * Date 2018/12/29 12:09
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

	List<ProductInfo> findByProductStatus(Integer productStatus);
}
