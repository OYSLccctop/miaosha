package com.miaoshaproject.dao;

import com.miaoshaproject.dataproject.OrderDO;
import org.springframework.stereotype.Component;

@Component(value = "OrderDOMapper")
public interface OrderDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Wed Oct 07 14:10:19 CST 2020
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Wed Oct 07 14:10:19 CST 2020
     */
    int insert(OrderDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Wed Oct 07 14:10:19 CST 2020
     */
    int insertSelective(OrderDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Wed Oct 07 14:10:19 CST 2020
     */
    OrderDO selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Wed Oct 07 14:10:19 CST 2020
     */
    int updateByPrimaryKeySelective(OrderDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Wed Oct 07 14:10:19 CST 2020
     */
    int updateByPrimaryKey(OrderDO record);
}