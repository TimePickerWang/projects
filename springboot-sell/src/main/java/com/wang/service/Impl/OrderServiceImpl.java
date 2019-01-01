package com.wang.service.Impl;

import com.wang.utils.KeyUtil;
import com.wang.converter.OrderMaster2OrderDTOConverter;
import com.wang.dto.CartDTO;
import com.wang.dto.OrderDTO;
import com.wang.enums.OrderStatusEnum;
import com.wang.enums.PayStatusEnum;
import com.wang.enums.ResultEnum;
import com.wang.exception.SellException;
import com.wang.models.OrderDetail;
import com.wang.models.OrderMaster;
import com.wang.models.ProductInfo;
import com.wang.repository.OrderDetailRepository;
import com.wang.repository.OrderMasterRepository;
import com.wang.service.OrderService;
import com.wang.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by wangjf
 * Date 2018/12/30 17:52
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Override
	@Transactional
	public OrderDTO create(OrderDTO orderDTO) {
		String orderId = KeyUtil.genUniqueKey();
		BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

		// 1.查询商品(数量，价格)
		for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
			ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}

			// 2.计算订单总价
			orderAmount = productInfo.getProductPrice()
					.multiply(new BigDecimal(orderDetail.getProductQuantity()))
					.add(orderAmount);

			// 订单详情入库
			BeanUtils.copyProperties(productInfo, orderDetail); // 这里需要注意顺序，如果后进行拷贝，之前赋的值可能会被覆盖
			orderDetail.setDetailId(KeyUtil.genUniqueKey());
			orderDetail.setOrderId(orderId);
			orderDetailRepository.save(orderDetail);
		}

		// 3.写入订单数据库(orderMaster和orderDetail)
		OrderMaster orderMaster = new OrderMaster();
		orderDTO.setOrderId(orderId);
		BeanUtils.copyProperties(orderDTO, orderMaster);
		orderMaster.setOrderAmount(orderAmount);
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		orderMaster.setCreateTime(new Date());
		orderMaster.setUpdateTime(new Date());
		orderMasterRepository.save(orderMaster);

		// 4.扣库存
		List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
				.map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
				.collect(Collectors.toList());
		productService.decreaseStock(cartDTOList);

		return orderDTO;
	}

	@Override
	public OrderDTO findOne(String orderId) {
		OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
		if (orderMaster == null) {
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}

		List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
		if (CollectionUtils.isEmpty(orderDetailList)) {
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}

		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		orderDTO.setOrderDetailList(orderDetailList);

		return orderDTO;
	}

	@Override
	public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
		Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
		List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
		return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
	}

	@Override
	@Transactional
	public OrderDTO cancle(OrderDTO orderDTO) {
		OrderMaster orderMaster = new OrderMaster();

		// 判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}

		// 修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updataResult = orderMasterRepository.save(orderMaster);
		if (updataResult == null) {
			log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}

		// 返回库存
		if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
			log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
			throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
		}
		List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
				.map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
				.collect(Collectors.toList());
		productService.increaseStock(cartDTOList);

		// 如果已支付，需要退款
		if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS)) {
			// TODO
		}

		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO finish(OrderDTO orderDTO) {
		// 判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【完结订单】订单状态不正确, orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}

		// 修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO paid(OrderDTO orderDTO) {
		// 判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【订单完成支付】订单状态不正确, orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}

		// 判断支付状态
		if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
			log.error("【订单完成支付】订单支付状态不正确, orderDTO={}", orderDTO);
			throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
		}

		// 修改支付状态
		orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("【订单完成支付】更新失败, orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}

		return orderDTO;
	}
}
