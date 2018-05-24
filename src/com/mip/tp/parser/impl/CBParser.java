/**
 * Copyright (C) 2011 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.mip.tp.parser.impl;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.mip.tp.data.ViewObject;
import com.mip.tp.data.circuitbreaker.CBDataValue;
import com.mip.tp.data.circuitbreaker.CBViewObject;
import com.mip.tp.data.circuitbreaker.CBWaveData;
import com.mip.tp.factory.TPType;
import com.mip.tp.parser.IParser;
import com.mip.tp.util.ParserUtil;

/**
 * 断路器解析器
 * @author longyz
 * @date 2012-4-19
 */
public class CBParser implements IParser {

	@Override
	public ViewObject parser_prps(InputStream is, String destFilePathTrain) throws IOException {
		return null;
	}
	@Override
	public ViewObject parser_prpd(InputStream is, String destFilePathTrain) throws IOException {
		return null;
	}

	/* (non-Javadoc)
         * @see com.mip.tp.parser.IParser#parser(java.lang.Object)
         */
	@Override
	public ViewObject parser(InputStream is) throws IOException {
		DataInputStream stream = new DataInputStream(is);
		CBViewObject vo = new CBViewObject();
		
		vo.dataversion = stream.readLong();
		vo.recordtime = stream.readDouble();
		vo.devnum = ParserUtil.readString(stream,50);
		vo.wavetype = stream.readByte();
		vo.unit = ParserUtil.readString(stream, 5);
		vo.datapointnum = stream.readLong();
		
		float minInterval = 1000;//最小间隔
		int currentCount = 0;
		for(int y = 0;y < CBViewObject.WAVE_COUNT; y++){
			try {
				CBWaveData waveData = new CBWaveData();//一条波形
				waveData.status = stream.readByte();
				waveData.wlength = stream.readLong();
				waveData.wdata = new CBDataValue[(int)waveData.wlength]; 
				waveData.value = stream.readFloat();
				waveData.xInterval = stream.readFloat();
				if(waveData.xInterval < minInterval ){
					minInterval = waveData.xInterval;
				}
				
				waveData.xUnit = ParserUtil.readString(stream, 5);
				waveData.yUnit = ParserUtil.readString(stream, 5);
				
				for (int j = 0; j < waveData.wlength; j++) {
					float xvalue = (j+1) * waveData.xInterval;
					float yvalue = stream.readFloat();
				  	CBDataValue dv = new CBDataValue();
				  	dv.xvalue = xvalue;
				  	dv.yvalue = yvalue;
				  	waveData.wdata[j] = dv;
				  	
				  	if(xvalue > vo.maxXCoordinate){
				  		vo.maxXCoordinate = xvalue;
				  	}else if(xvalue < vo.minXCoordinate){
				  		vo.minXCoordinate = xvalue;
				  	}
				  	
				  	if(yvalue > vo.maxYCoordinate){
				  		vo.maxYCoordinate = yvalue;
				  	}else if(yvalue < vo.minYCoordinate){
				  		vo.minYCoordinate = yvalue;
				  	}
				}
				vo.wavedatas.add( waveData );
				currentCount ++;
			} catch (Exception e) {
				System.out.println("警告,当前波形文件的波形数为" + currentCount);
			}
		}
		
		//数据
		return vo;
	}

	/* (non-Javadoc)
	 * @see com.mip.tp.parser.IParser#getType()
	 */
	@Override
	public TPType getType() {
		return TPType.CB;
	}

}
