/**
 * Copyright (C) 2011 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.mip.tp.parser.impl;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.io.input.SwappedDataInputStream;

import com.mip.tp.data.ViewObject;
import com.mip.tp.data.partdischarge.PDViewObject;
import com.mip.tp.factory.TPType;
import com.mip.tp.parser.IParser;
import com.mip.tp.util.ParserUtil;

/**
 * 
 * @author longyz
 * @date 2012-11-2
 */
public class DMSPDParser implements IParser {

	/* (non-Javadoc)
	 * @see com.mip.tp.parser.IParser#parser(java.lang.Object)
	 */
	@Override
	public ViewObject parser(InputStream is) throws IOException {
		PDViewObject vo = new PDViewObject();
		return vo;
	}
	public ViewObject parser_prps(InputStream is, String destFilePathTrain) throws IOException {
		PDViewObject vo = new PDViewObject();
		if(is == null){
			System.out.println("读取错误！！！");
			return vo;
		}
		try {

			SwappedDataInputStream stream = new SwappedDataInputStream(is);

			vo.version = stream.readFloat();
			vo.datetime = readDate(stream);
			vo.devnum = ParserUtil.readString(stream, 50);
			vo.addr = readAddress(stream);//
			vo.channelnum = stream.readInt();
			vo.devstatus = stream.readByte();
			vo.datastatus = stream.readByte();
			vo.type = stream.readByte();
			vo.alarm = stream.readByte();
			vo.probSt = stream.readByte();
			//10 种放电类型的概率

			for(int i=0;i<10;i++){
				vo.probs[i] = stream.readFloat();
			}
			vo.coeff = stream.readShort();
			vo.phaseNum = stream.readInt();
			vo.avg = stream.readInt();
			vo.cycNum = stream.readInt();
			vo.intens = stream.readFloat();
			vo.avgV = stream.readFloat();
			int maxint = 0;
			vo.fiftyHZ = stream.readFloat();
			vo.hundredHZ = stream.readFloat();
			vo.tptype = stream.readByte();
			vo.unit = stream.readByte();
			vo.minvalue = stream.readShort();
			vo.maxvalue = stream.readShort();

			int[][] datas = null;
			if(vo.tptype == 0){//PRPD型图谱,phaseNum窗数
				vo.maxXCoordinate = vo.phaseNum;
				vo.maxYCoordinate = vo.avg;
				datas = new int[vo.phaseNum][vo.avg];
				//m×n的二维数组。其中：m为相位窗数，n为幅值量化值，数组值为对应的放电频次。
				//m对应vo.phaseNum,n对应vo.avg
				for(int x=0;x<vo.phaseNum;x++){
					for(int y=0;y<vo.avg;y++){
						int value = stream.readShort();
						if(value!=0){
							maxint = Math.max(maxint, value);
							datas[x][y] = value;
							if(value > vo.maxZCoordinate){
								vo.maxZCoordinate = value;
							}
						}
					}
				}
			}else if(vo.tptype == 1){//PRPS型图谱,phaseNum窗数
				if(vo.unit==1){
					vo.maxXCoordinate = vo.phaseNum;
					vo.maxYCoordinate = vo.cycNum;
					datas = new int[vo.phaseNum][vo.cycNum];
					//m×L的二维数组。其中：m为相位窗数，L为工频周期数，数组值为对应的放电强度。
					//m对应vo.phaseNum,n对应vo.cycNum
					for(int x=0;x<vo.phaseNum;x++){
						for(int y=0;y<vo.cycNum;y++){
							int value = stream.readShort();
							if(value!=0){
								maxint = Math.max(maxint, value);
								datas[x][y] = value;
								if(value > vo.maxZCoordinate){
									vo.maxZCoordinate = value;
								}
							}
						}
					}
				}else if(vo.unit==2){
//				vo.maxXCoordinate = vo.cycNum;
//				vo.maxYCoordinate = vo.phaseNum;
//				datas = new int[vo.cycNum][vo.phaseNum];

					vo.maxXCoordinate = vo.phaseNum;
					vo.maxYCoordinate = vo.cycNum;
					datas = new int[vo.phaseNum][vo.cycNum];
					for(int x=0;x<vo.cycNum;x++){
						for(int y=0;y<vo.phaseNum;y++){
							int value = stream.readShort();
							if(value != 0){
								maxint = Math.max(maxint, value);
								datas[y][x] = value;
								if(value > vo.maxZCoordinate){
									vo.maxZCoordinate = value;
								}
							}
						}
					}
				}

			}
			vo.maxint = maxint;
			vo.datas = datas;



			if(vo.tptype == 1) {//PRPS型图谱,phaseNum窗数
				DataToCSV dataTrain = new DataToCSV();
//				String destFilePathTrain = "C:/Users/Administrator/Desktop/train.csv";
				File file = new File(destFilePathTrain);
				System.out.println(dataTrain.exportPRPSCsv(file, vo));
			}


		}catch (EOFException e){
			System.out.println("已经达到文件末尾");
		}
		return vo;
	}

	public ViewObject parser_prpd(InputStream is, String destFilePathTrain) throws IOException {
		PDViewObject vo = new PDViewObject();
		if(is == null){
			System.out.println("读取错误！！！");
			return vo;
		}
		try {

			SwappedDataInputStream stream = new SwappedDataInputStream(is);

			vo.version = stream.readFloat();
			vo.datetime = readDate(stream);
			vo.devnum = ParserUtil.readString(stream, 50);
			vo.addr = readAddress(stream);//
			vo.channelnum = stream.readInt();
			vo.devstatus = stream.readByte();
			vo.datastatus = stream.readByte();
			vo.type = stream.readByte();
			vo.alarm = stream.readByte();
			vo.probSt = stream.readByte();
			//10 种放电类型的概率

			for(int i=0;i<10;i++){
				vo.probs[i] = stream.readFloat();
			}
			vo.coeff = stream.readShort();
			vo.phaseNum = stream.readInt();
			vo.avg = stream.readInt();
			vo.cycNum = stream.readInt();
			vo.intens = stream.readFloat();
			vo.avgV = stream.readFloat();
			int maxint = 0;
			vo.fiftyHZ = stream.readFloat();
			vo.hundredHZ = stream.readFloat();
			vo.tptype = stream.readByte();
			vo.unit = stream.readByte();
			vo.minvalue = stream.readShort();
			vo.maxvalue = stream.readShort();

			int[][] datas = null;
			if(vo.tptype == 0){//PRPD型图谱,phaseNum窗数
				vo.maxXCoordinate = vo.phaseNum;
				vo.maxYCoordinate = vo.avg;
				datas = new int[vo.phaseNum][vo.avg];
				//m×n的二维数组。其中：m为相位窗数，n为幅值量化值，数组值为对应的放电频次。
				//m对应vo.phaseNum,n对应vo.avg
				for(int x=0;x<vo.phaseNum;x++){
					for(int y=0;y<vo.avg;y++){
						int value = stream.readShort();
						if(value!=0){
							maxint = Math.max(maxint, value);
							datas[x][y] = value;
							if(value > vo.maxZCoordinate){
								vo.maxZCoordinate = value;
							}
						}
					}
				}
			}else if(vo.tptype == 1){//PRPS型图谱,phaseNum窗数
				if(vo.unit==1){
					vo.maxXCoordinate = vo.phaseNum;
					vo.maxYCoordinate = vo.cycNum;
					datas = new int[vo.phaseNum][vo.cycNum];
					//m×L的二维数组。其中：m为相位窗数，L为工频周期数，数组值为对应的放电强度。
					//m对应vo.phaseNum,n对应vo.cycNum
					for(int x=0;x<vo.phaseNum;x++){
						for(int y=0;y<vo.cycNum;y++){
							int value = stream.readShort();
							if(value!=0){
								maxint = Math.max(maxint, value);
								datas[x][y] = value;
								if(value > vo.maxZCoordinate){
									vo.maxZCoordinate = value;
								}
							}
						}
					}
				}else if(vo.unit==2){
//				vo.maxXCoordinate = vo.cycNum;
//				vo.maxYCoordinate = vo.phaseNum;
//				datas = new int[vo.cycNum][vo.phaseNum];

					vo.maxXCoordinate = vo.phaseNum;
					vo.maxYCoordinate = vo.cycNum;
					datas = new int[vo.phaseNum][vo.cycNum];
					for(int x=0;x<vo.cycNum;x++){
						for(int y=0;y<vo.phaseNum;y++){
							int value = stream.readShort();
							if(value != 0){
								maxint = Math.max(maxint, value);
								datas[y][x] = value;
								if(value > vo.maxZCoordinate){
									vo.maxZCoordinate = value;
								}
							}
						}
					}
				}

			}
			vo.maxint = maxint;
			vo.datas = datas;



			if(vo.tptype == 0) {//PRPD型图谱,phaseNum窗数
				for (int i=0;i<datas.length;i++){
					for (int j=0;j<datas[i].length;i++)
						System.out.print(datas[i][j] + " , ");
					System.out.println();
				}
				DataToCSV dataTrain = new DataToCSV();
//				String destFilePathTrain = "C:/Users/Administrator/Desktop/train.csv";
				File file = new File(destFilePathTrain);
				System.out.println(dataTrain.exportPRPDCsv(file, vo));
			}


		}catch (EOFException e){
			System.out.println("已经达到文件末尾");
		}
		return vo;
	}
	
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String readDate(SwappedDataInputStream stream) throws IOException{
        int t=stream.readInt();
        long ltime=((long)(t-(8*60*60)))*1000l;
        return df.format(new Date(ltime));
	}
	
	private static String readAddress(SwappedDataInputStream stream) throws IOException{
		StringBuffer addr = new StringBuffer();
		
		for(int i=0;i<4;i++){
			int b = (int)stream.readByte();
			if(b < 0){
				b += 256;
			}
			addr.append(b);
			if(i < 3){
				addr.append(".");
			}
		}
		return addr.toString();
	}

	/* (non-Javadoc)
	 * @see com.mip.tp.parser.IParser#getType()
	 */
	@Override
	public TPType getType() {
		return TPType.PD;
	}

}
