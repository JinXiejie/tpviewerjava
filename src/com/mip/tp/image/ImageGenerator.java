/**
 * Copyright (C) 2011 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.mip.tp.image;

import com.mip.tp.data.ViewObject;

/**
 * 图片生成器接口
 * @author longyz
 * @date 2012-4-20
 */
public interface ImageGenerator {

	/**
	 * 生成图片
	 * @param vo 数据对象
	 * @param saveImagePath 如果不为空,那么生成saveImagePath所指路径的图片文件
	 * @return
	 * @throws ImageGeneratorException 
	 */
	String generate(ViewObject vo,String saveImagePath) throws ImageGeneratorException;
}
