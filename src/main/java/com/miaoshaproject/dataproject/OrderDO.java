package com.miaoshaproject.dataproject;

import lombok.Data;

@Data
public class OrderDO {
    private String id;

    private Integer userId;

    private Integer itemId;

    private Integer promoId;

    private Double itemPrice;

    private Integer amount;

    private Double orderPrice;
}