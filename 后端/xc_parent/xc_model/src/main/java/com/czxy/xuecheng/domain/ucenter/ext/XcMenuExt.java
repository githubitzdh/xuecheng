package com.czxy.xuecheng.domain.ucenter.ext;

import com.czxy.xuecheng.domain.course.ext.CategoryNode;
import com.czxy.xuecheng.domain.ucenter.XcMenu;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 *
 */
@Data
@ToString
public class XcMenuExt extends XcMenu {

    List<CategoryNode> children;
}
