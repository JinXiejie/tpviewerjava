/**
 * Copyright (C) 2011 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.mip.tp.parser;

import java.io.IOException;
import java.io.InputStream;

import com.mip.tp.data.ViewObject;
import com.mip.tp.factory.TPType;

/**
 * 转换接口
 * @author longyz
 * @date 2012-4-19
 */
public interface IParser {

	/**
	 * @param data 要解析的原始数据
	 * 
	 * @return ViewObject 转换后用于展现的试图对象
	 */ 
	ViewObject parser(InputStream is) throws IOException;
	ViewObject parser_prps(InputStream is, String destFilePathTrain) throws IOException;
	ViewObject parser_prpd(InputStream is, String destFilePathTrain) throws IOException;

	/**
	 * 返回转换器类型
	 * @return
	 */
	TPType getType();
}
