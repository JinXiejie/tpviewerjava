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
import com.mip.tp.data.partdischarge.PDViewObject;

/**
 * 局放图谱文件图片生成器
 * @author longyz
 * @date 2012-4-23
 */
public class PDImageGenerator implements ImageGenerator {

	private PDViewObject vo;
	/* (non-Javadoc)
	 * @see com.mip.tp.image.ImageGenerator#generate(com.mip.tp.data.ViewObject, java.lang.String)
	 */
	@Override
	public String generate(ViewObject vo, String saveImagePath)
			throws ImageGeneratorException {
		if(!(vo instanceof PDViewObject)){
			throw new ImageGeneratorException("vo不是PDViewObject的对象");
		}
		this.vo = (PDViewObject)vo;
		
		ImageGenerator generator = null; 
		if(this.vo.tptype == 0){
			generator = new PDPrpdImageGenerator();
		}else if(this.vo.tptype == 1){
			generator = new PDPrpsImageGenerator();
		}
		return generator.generate(vo,saveImagePath);
	}

}
