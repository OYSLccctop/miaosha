package com.miaoshaproject.service.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

// 用户下单的交易模型
@Data
public class OrderModel implements Serializable {
    // 订单号
    private String id;
    // 购买的用户id
    private Integer userId;

    // 若非空，则表示以秒杀商品方式下单
    private Integer promoId;

    // 购买商品的单价（当时商品的价格）,若promoId非空，则表示秒杀商品价格
    private BigDecimal itemPrice;
    // 购买的商品id
    private Integer itemId;
    // 购买数量
    private Integer amount;
    // 购买金额，若promoId非空，则表示秒杀商品价格
    private BigDecimal orderPrice;
}
