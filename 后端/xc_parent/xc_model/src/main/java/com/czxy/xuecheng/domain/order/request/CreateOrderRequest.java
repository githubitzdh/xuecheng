package com.czxy.xuecheng.domain.order.request;

import com.czxy.xuecheng.common.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 *
 */
@Data
@ToString
public class CreateOrderRequest extends RequestData {

    String courseId;

}
