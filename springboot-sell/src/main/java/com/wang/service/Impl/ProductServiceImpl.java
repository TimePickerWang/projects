package com.wang.service.Impl;

import com.wang.dto.CartDTO;
import com.wang.enums.ProductStatusEnum;
import com.wang.enums.ResultEnum;
import com.wang.exception.SellException;
import com.wang.models.ProductInfo;
import com.wang.repository.ProductInfoRepository;
import com.wang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create by wangjf
 * Date 2018/12/29 12:39
 */
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductInfoRepository productInfoRepository;

	@Override
	public ProductInfo findOne(String producdId) {
		return productInfoRepository.findOne(producdId);
	}

	@Override
	public List<ProductInfo> findUpAll() {
		return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
	}

	@Override
	public Page<ProductInfo> findAll(Pageable pageable) {
		return productInfoRepository.findAll(pageable);
	}

	@Override
	public ProductInfo save(ProductInfo productInfo) {
		return productInfoRepository.save(productInfo);
	}

	@Override
	@Transactional
	public void increaseStock(List<CartDTO> cartDTOList) {
		for (CartDTO cartDTO : cartDTOList) {
			ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}

			Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
			productInfo.setProductStock(result);
			productInfoRepository.save(productInfo);
		}


	}

	@Override
	@Transactional
	public void decreaseStock(List<CartDTO> cartDTOList) {
		for (CartDTO cartDTO : cartDTOList) {
			ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}

			Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();

			if (result < 0) {
				throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
			}
			productInfo.setProductStock(result);
			productInfoRepository.save(productInfo);
		}
	}
}
