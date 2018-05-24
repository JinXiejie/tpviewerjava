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

import com.mip.tp.image.ImageGeneratorException;
import com.mip.tp.parser.IParser;
import com.mip.tp.parser.impl.CBParser;
import com.mip.tp.parser.impl.DMSPDParser;
import com.mip.tp.parser.impl.GasWaterParser;
import com.mip.tp.parser.impl.NewDMSPDParser;

/**
 * 转化器工厂
 * @author longyz
 * @date 2012-4-19
 */
public class ParserFactory {

	private static final Map<TPType,IParser> parserMap = new HashMap<TPType,IParser>();
	
	static{
		parserMap.put(TPType.NPD, new NewDMSPDParser());
		parserMap.put(TPType.PD, new DMSPDParser());
		parserMap.put(TPType.CB, new CBParser());
		parserMap.put(TPType.GASWATER, new GasWaterParser());
	}
	
	/**
	 * 获取转化器类型
	 * @param parserType ParserType中的静态变量
	 * @return 
	 * @throws ImageGeneratorException 
	 */
	public static final IParser getParser(TPType type) throws ImageGeneratorException{
		if(type!=null){
			IParser parser = parserMap.get(type);
			if(parser != null){
				return parser;
			}else{
				throw new ImageGeneratorException("无法识别的图谱类型,请确定是枚举值"+TPType.class.getName()+"中的一种");
			}
		}else{
			throw new ImageGeneratorException("type 不能为空");
		}
	}
	
}
