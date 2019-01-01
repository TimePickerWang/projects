package com.wang.controller;

import com.wang.utils.ResultVOUtil;
import com.wang.VO.ProductInfoVo;
import com.wang.VO.ProductVo;
import com.wang.VO.ResultVO;
import com.wang.models.ProductCategory;
import com.wang.models.ProductInfo;
import com.wang.service.CategoryService;
import com.wang.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 * Create by wangjf
 * Date 2018/12/29 13:49
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/list")
	public ResultVO list() {
		// 1.查询所有的上架商品
		List<ProductInfo> productInfoList = productService.findUpAll();
		// 2.查询类目
		List<Integer> categoryTypeList = productInfoList.stream()
				.map(e -> e.getCategoryType())
				.collect(Collectors.toList());
		List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

		List<ProductVo> productVoList = new ArrayList<>();
		for (ProductCategory productCategory : productCategoryList) {
			ProductVo productVo = new ProductVo();
			productVo.setCategoryType(productCategory.getCategoryType());
			productVo.setCategoryName(productCategory.getCategoryName());

			List<ProductInfoVo> productInfoVoList = new ArrayList<>();
			for (ProductInfo productInfo : productInfoList) {
				if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
					ProductInfoVo productInfoVo = new ProductInfoVo();
					BeanUtils.copyProperties(productInfo, productInfoVo);
					productInfoVoList.add(productInfoVo);
				}
			}
			productVo.setProductInfoVoList(productInfoVoList);
			productVoList.add(productVo);
		}

		return ResultVOUtil.success(productVoList);
	}


}
