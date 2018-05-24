/**
 * Copyright (C) 2011 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.mip.tp.factory;

import java.util.HashMap;
import java.util.Map;

import com.mip.tp.image.CBImageGenerator;
import com.mip.tp.image.GasWaterImageGenerator;
import com.mip.tp.image.ImageGenerator;
import com.mip.tp.image.ImageGeneratorException;
import com.mip.tp.image.PDImageGenerator;

/**
 * 
 * @author longyz
 * @date 2012-4-22
 */
public class ImageGeneratorFactory {

	private static final Map<TPType,ImageGenerator> generatorMap = new HashMap<TPType,ImageGenerator>();
	static{
		generatorMap.put(TPType.NPD, new PDImageGenerator());
		generatorMap.put(TPType.PD, new PDImageGenerator());
		generatorMap.put(TPType.GASWATER, new GasWaterImageGenerator());
		generatorMap.put(TPType.CB, new CBImageGenerator());
	}
	public static ImageGenerator getImageGenerator(TPType type) throws ImageGeneratorException{
		if(type !=null){
			ImageGenerator generator = generatorMap.get(type);
			if(generator != null){
				return generator;
			}else{
				throw new ImageGeneratorException("无法识别的图谱类型,请确定是枚举值"+TPType.class.getName()+"中的一种");
			}
		}else{
			throw new ImageGeneratorException("type 不能为空");
		}
	}
}
