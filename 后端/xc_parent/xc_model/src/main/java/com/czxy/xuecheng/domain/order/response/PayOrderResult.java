package com.czxy.xuecheng.domain.order.response;

import com.czxy.xuecheng.common.model.response.ResponseResult;
import com.czxy.xuecheng.common.model.response.ResultCode;
import com.czxy.xuecheng.domain.order.XcOrdersPay;
import lombok.Data;
import lombok.ToString;

/**
 *
 */
@Data
@ToString
public class PayOrderResult extends ResponseResult {
    public PayOrderResult(ResultCode resultCode) {
        super(resultCode);
    }
    public PayOrderResult(ResultCode resultCode, XcOrdersPay xcOrdersPay) {
        super(resultCode);
        this.xcOrdersPay = xcOrdersPay;
    }
    private XcOrdersPay xcOrdersPay;
    private String orderNumber;

    //当tradeState为NOTPAY（未支付）时显示支付二维码
    private String codeUrl;
    private Float money;


}
