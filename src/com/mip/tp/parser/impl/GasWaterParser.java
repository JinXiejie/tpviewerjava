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
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.mip.tp.data.ViewObject;
import com.mip.tp.data.gaswater.Channel;
import com.mip.tp.data.gaswater.ChannelPeak;
import com.mip.tp.data.gaswater.GasWaterViewObject;
import com.mip.tp.factory.TPType;
import com.mip.tp.parser.IParser;
import com.mip.tp.util.ParserUtil;

/**
 * 中分给的二进制数据需要特殊处理,readFloat和readInt都不能用DataInputStream的readFloat和readInt方法
 * 对应flash的TPParser
 * @author longyz
 * @date 2012-4-19
 */
public class GasWaterParser implements IParser {

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
		GasWaterViewObject vo = new GasWaterViewObject();
		vo.dataversion = readFloat(stream);//1
		vo.createTime = readDate(stream);//2011-5-10 10:1:40
		vo.fileNumber = ParserUtil.readString(stream, 50);//zf3000
		vo.channelCount = stream.readByte();//2
		vo.channels = new Channel[vo.channelCount];
		for(int i=0; i<vo.channelCount; i++){
			Channel channel = new Channel();
			vo.channels[i] = channel;
			channel.peakcount = stream.readByte();
			
			channel.peaks = new ChannelPeak[channel.peakcount];
			//各锋号信息,各通道峰值信息
			for(int j=0; j<channel.peakcount; j++){
				ChannelPeak peak = new ChannelPeak();
				peak.peakname = ParserUtil.readString(stream, 10);//CO2
				peak.orderid = stream.readByte();//1
				peak.peaktime = readFloat(stream);//109.9366
				peak.peakstarttime = readFloat(stream);//96.8023
				peak.peakendtime = readFloat(stream);//127.0265
				peak.peakhigh = readFloat(stream);//1514.7153
				peak.peakarea = readFloat(stream);//13814.1992
				channel.peaks[j] = peak;
			}
			
		}
		
		//通道数据
		for(int j=0; j<vo.channelCount; j++){
			Channel channel = vo.channels[j];
			channel.datacount = readInt(stream);
			channel.datas = new float[channel.datacount];
			channel.xMaxValue = readFloat(stream);
			channel.sampletime = (int)channel.xMaxValue;
			channel.yMaxValue = readFloat(stream);
			
			if(channel.datacount > channel.maxXCoordinate){
				channel.maxXCoordinate = channel.datacount;
			}
			
			for(int k=0; k<channel.datacount; k++){
				float value = readFloat(stream);
				channel.datas[k] = value;
				
				if(value > channel.maxYCoordinate){
					channel.maxYCoordinate = value;
				}else if(value < channel.minYCoordinate){
					channel.minYCoordinate = value;
				}
			}
		}
		
		return vo;
	}
	
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String readDate(DataInputStream stream) throws IOException{
		long time = readInt(stream);
		Date date = new Date((time-8*60*60)*1000);
		return df.format(date);
	}
	
	private static float readFloat(DataInputStream stream) throws IOException{
		byte[] b = {stream.readByte(),stream.readByte(),stream.readByte(),stream.readByte()};
		return byte2float(b);
	}
	
	private static int readInt(DataInputStream stream) throws IOException{
		byte[] b = {stream.readByte(),stream.readByte(),stream.readByte(),stream.readByte()};
		return byte2int(b);
	}
	
	//byte数组转int
	private static int byte2int(byte[] b){
		int targets = (b[0] & 0xff) | ((b[1] << 8) & 0xff00) | ((b[2] << 24) >>> 8) | (b[3] << 24);
	    return targets;//1305021700,1305021700
	}
	
	//byte数组转float
	private static float byte2float(byte[] b){
		int n = (b[0] & 0xff)|((b[1] & 0xff)<<8)|((b[2] & 0xff)<<16)|((b[3] & 0xff)<<24);
		return intBitsToFloat(n);
   
	}
	
	/**
	 * 整数转成浮点数
	 * @param bits
	 * @return
	 */
	public static float intBitsToFloat(int bits){
		int s = ((bits >> 31) == 0) ? 1 : -1;
		int e = ((bits >> 23) & 0xff);
		int m = (e == 0) ?
            (bits & 0x7fffff) << 1 :
            (bits & 0x7fffff) | 0x800000;
		return (float) (s*m*Math.pow(2,e-150));
	}

	/* (non-Javadoc)
	 * @see com.mip.tp.parser.IParser#getType()
	 */
	@Override
	public TPType getType() {
		return TPType.GASWATER;
	}

}
