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
import com.mip.tp.factory.TPType;
import com.mip.tp.parser.IParser;
import com.mip.tp.parser.Main;
import com.mip.tp.util.ParserUtil;

/**
 * 
 * @author longyz
 * @date 2012-7-12
 */
public class ZGCBParser implements IParser {

	@Override
	public ViewObject parser_prps(InputStream is, String destFilePathTrain) throws IOException {
		return null;
	}
	@Override
	public ViewObject parser_prpd(InputStream is, String destFilePathTrain) throws IOException {
		return null;
	}

	/* (non-Javadoc)
         * @see com.mip.tp.parser.IParser#getType()
         */
	@Override
	public TPType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mip.tp.parser.IParser#parser(java.io.InputStream)
	 */
	@Override
	public ViewObject parser(InputStream is) throws IOException {
		DataInputStream stream = new DataInputStream(is);
		float version = stream.readFloat();
		System.out.println("文件版本号:" + version);
		int time = stream.readInt();
		System.out.println("波形记录时间:" + time);
		String devNO = ParserUtil.readString(stream, 50);
		System.out.println("被监测设备编号:" + devNO);
		
		String jg = ParserUtil.readString(stream, 1);
		System.out.println("断路器控制机构:" + jg);
		String type = ParserUtil.readString(stream, 1);
		System.out.println("波形类型:" + type);
		String u = ParserUtil.readString(stream, 1);
		System.out.println("电流单位类型:" + u);
		int count = stream.readInt();
		System.out.println("数据点数（k）:" + count);
		
		byte b = 01;
		char c = (char)b;
		System.out.println(c);
		
		return null;
	}

	public static void main(String[] args) throws IOException {
		InputStream data = Main.class.getResourceAsStream("/datafiles/zgcb/comtrade/A381_003_02_20120704140825.dat");
//		InputStream data = Main.class.getResourceAsStream("/datafiles/zgcb/comtrade/A381_003_03_20120704140825.dat");
//		InputStream data = Main.class.getResourceAsStream("/datafiles/zgcb/comtrade/A381_003_04_20120704140825.dat");
//		InputStream data = Main.class.getResourceAsStream("/datafiles/zgcb/comtrade/A381_003_06_20120704140825.dat");
		ZGCBParser parser = new ZGCBParser();
		parser.parser(data);
	}
}