package com.czxy.xuecheng.domain.order.response;

import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.common.model.response.ResultCode;
import com.czxy.xuecheng.domain.order.XcOrders;
import lombok.Data;
import lombok.ToString;

/**
 *
 */
@Data
@ToString
public class OrderResult extends ResponseResult {
    private XcOrders xcOrders;
    public OrderResult(ResultCode resultCode, XcOrders xcOrders) {
        super(resultCode);
        this.xcOrders = xcOrders;
    }


}
