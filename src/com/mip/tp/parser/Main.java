/**
 * Copyright (C) 2011 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.mip.tp.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.mip.tp.data.ViewObject;
import com.mip.tp.data.partdischarge.PDViewObject;
import com.mip.tp.factory.ImageGeneratorFactory;
import com.mip.tp.factory.ParserFactory;
import com.mip.tp.factory.TPType;
import com.mip.tp.image.ImageGenerator;
import com.mip.tp.image.ImageGeneratorException;

/**
 * http://tech.ccidnet.com/art/3739/20051117/521859_1.html
 * @author longyz
 * @date 2012-4-19
 */
public class Main {

	private static final String SAVEIMAGEPATH = "D:/a_new.png";
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ImageGeneratorException 
	 */
	public static void main(String[] args) throws IOException, ImageGeneratorException {
		long start = System.currentTimeMillis();
//		testCBParser();
//		testGasWaterParser();
//		testPDPrpsParser();

//        testPDPrpdParser();

		testDMSPrpsParser();

//		testDMSPrpsParserSin(); //测试绘制sin曲线的图像效果


//		testNewDMSPrpsParser();
		System.out.println("用时:" + (System.currentTimeMillis() - start));
	}
	
	public static void testCBParser() throws IOException, ImageGeneratorException{
		TPType type = TPType.CB;
		IParser parser = ParserFactory.getParser(type);
//		InputStream data = Main.class.getResourceAsStream("/datafiles/close_ciWave.dat");//合闸
//		InputStream data = Main.class.getResourceAsStream("/datafiles/open_ciWave.dat");//开闸
		InputStream data = Main.class.getResourceAsStream("/datafiles/cb/EDS1-CB15-SCBR1_02_20120627084323.dat");//开闸
		ViewObject vo =parser.parser(data);
		ImageGenerator generator = ImageGeneratorFactory.getImageGenerator(type);
		generator.generate(vo,SAVEIMAGEPATH);
	}
	public static void testGasWaterParser() throws IOException, ImageGeneratorException{
		TPType type = TPType.GASWATER;
		IParser parser = ParserFactory.getParser(type);
		InputStream data = Main.class.getResourceAsStream("/datafiles/0301B15000003A2982000_105_07_20110510100145.dat");
		ViewObject vo =parser.parser(data);
		ImageGenerator generator = ImageGeneratorFactory.getImageGenerator(type);
		generator.generate(vo,SAVEIMAGEPATH);
	}
	public static void testPDPrpsParser() throws IOException, ImageGeneratorException{
		TPType type = TPType.PD;
		IParser parser = ParserFactory.getParser(type);
		InputStream data = Main.class.getResourceAsStream("/datafiles/pd_floating.dat");
//		InputStream data = Main.class.getResourceAsStream("/datafiles/cb/EDS1-PD05-SPDC3_01_20120625081426.dat");
		ViewObject vo =parser.parser(data);
		ImageGenerator generator = ImageGeneratorFactory.getImageGenerator(type);
		generator.generate(vo,SAVEIMAGEPATH);
	}
	
	public static void testDMSPrpsParser() throws IOException, ImageGeneratorException{
		TPType type = TPType.PD;
		IParser parser = ParserFactory.getParser(type);
//		InputStream data = Main.class.getResourceAsStream("/datafiles/pdm/0001__001_01__20121101081434.dat");
//		InputStream data = Main.class.getResourceAsStream("/data/PDMSystem-PdmSys-CouplerSPDC0002_01_20140611233322.dat");

		ViewObject vo = new PDViewObject();
		int idx = 1;
//		for (long i = 20140611233322L;i <= 20160306121631L;i++){
//			String filePath = "/data/PDMSystemPdmSys_CouplerSPDC0002/PDMSystem-PdmSys-CouplerSPDC0002_01_" + String.valueOf(i) + ".dat";
//			InputStream data = Main.class.getResourceAsStream(filePath);
//			if(data != null){
//				String destFilePathTrain = "C:/Users/Administrator/Desktop/TrainData/train" + String.valueOf(idx) + ".csv";
//				vo = parser.parser_jin(data,destFilePathTrain);
//				idx++;
//				data.close();
//			}
//
//		}

//		for (int t = 1;t < 26;t++){
//			String filePath = "E:\\JinXiejie\\data\\MetaData-PDMSystemPdmSys_CouplerSPDC-Channel_2\\PDMSystemPdmSys_CouplerSPDC000" + String.valueOf(t);
//			File file = new File(filePath);
//			File[] lists = file.listFiles();
//			if(lists != null){
//				for (File f : lists){
//					InputStream data = new FileInputStream(f);
//					String destFilePathTrain = "E:/JinXiejie/data/PRPS/PDMSystemPdmSys_CouplerSPDC-Channel_2_" + String.valueOf(t) +"/prps_train" + String.valueOf(idx) + ".csv";
//					vo = parser.parser_prps(data,destFilePathTrain);
//					idx++;
//					data.close();
//				}
//			}
//		}

		String filePath = "E:\\JinXiejie\\data\\MetaData-PDMSystemPdmSys_CouplerSPDC-Channel_2\\PDMSystemPdmSys_CouplerSPDC00010";
		File file = new File(filePath);
		File[] lists = file.listFiles();
		if(lists != null){
			System.out.println("正在执行PRPS。。。");
			for (File f : lists){
//			InputStream data = Main.class.getResourceAsStream(filePath);
				InputStream data = new FileInputStream(f);
				String destFilePathTrain = "E:/JinXiejie/data/PRPS/PDMSystemPdmSys_CouplerSPDC-Channel_2_10/prps_train" + String.valueOf(idx) + ".csv";
				vo = parser.parser_prps(data,destFilePathTrain);
				idx++;
				data.close();
			}
		}


//		InputStream data = Main.class.getResourceAsStream("/data/PDMSystem-PdmSys-CouplerSPDC0002_01_20140611233322.dat");
//		String destFilePathTrain = "C:/Users/Administrator/Desktop/TrainData/train.csv";
//		ViewObject vo =parser.parser_jin(data,destFilePathTrain);
		ImageGenerator generator = ImageGeneratorFactory.getImageGenerator(type);
		generator.generate(vo,SAVEIMAGEPATH);
	}
	
	public static void testDMSPrpsParserSin() throws IOException, ImageGeneratorException{
		while(true){
			TPType type = TPType.PD;
			IParser parser = ParserFactory.getParser(type);
			InputStream data = Main.class.getResourceAsStream("/datafiles/pdm/0001__001_01__20121101081434.dat");
			ViewObject vo =parser.parser(data);
			GenSin((PDViewObject)vo);
			ImageGenerator generator = ImageGeneratorFactory.getImageGenerator(type);
			generator.generate(vo,SAVEIMAGEPATH);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	static int a = 0;
	public static void GenSin(PDViewObject vo){
		int[][] datas = vo.datas;
		
		int[] sin = new int[64];
		
		int max = Integer.MIN_VALUE;
		for(int colIdx = 0; colIdx < 64; colIdx++){
			for(int rowIdx = 0; rowIdx < 50; rowIdx++){
				max = Math.max(max, datas[colIdx][rowIdx]);
				datas[colIdx][rowIdx] = 0;
			}
		}
		
		double s = 360.0 / 64;
		double t = (Math.PI / 180.0);
		for(int colIdx = 0; colIdx < 64; colIdx++){
			sin[colIdx] = (int)(max * Math.sin(a + t * s * colIdx  ));
			sin[colIdx] = Math.abs(sin[colIdx]);
		}
		
		
		for(int colIdx = 0; colIdx < 64; colIdx+=2){
			for(int rowIdx = 0; rowIdx < 50; rowIdx+=1){
				datas[colIdx][rowIdx] = sin[colIdx];
			}
		}
		a+=10;
	}
	
	public static void testNewDMSPrpsParser() throws IOException, ImageGeneratorException{
		TPType type = TPType.NPD;
		IParser parser = ParserFactory.getParser(type);
		InputStream data = Main.class.getResourceAsStream("/datafiles/pdm/0301B13000005A3830616_004_01_20140817143145.dat");
		//	InputStream data = Main.class.getResourceAsStream("/datafiles/pdm/0301B13000005A3830600_004_01_20140801235959.dat");//临时文件
		ViewObject vo =parser.parser(data);
		ImageGenerator generator = ImageGeneratorFactory.getImageGenerator(type);
		generator.generate(vo,SAVEIMAGEPATH);
	}
	
	public static void testPDPrpdParser() throws IOException, ImageGeneratorException{
//		TPType type = TPType.PD;
//		IParser parser = ParserFactory.getParser(type);
////		InputStream data = Main.class.getResourceAsStream("/datafiles/pd_floating.dat");
//		InputStream data = Main.class.getResourceAsStream("/datafiles/cb/EDS1-PD05-SPDC3_01_20120625081426.dat");
//		ViewObject vo =parser.parser(data);
//		PDViewObject pdvo = (PDViewObject)vo;
//		pdvo.tptype = 0;
//		ImageGenerator generator = ImageGeneratorFactory.getImageGenerator(type);
//		generator.generate(vo,SAVEIMAGEPATH);
		TPType type = TPType.PD;
		IParser parser = ParserFactory.getParser(type);

		ViewObject vo = new PDViewObject();
		int idx = 1;
//		String filePath = "E:\\JinXiejie\\data\\MetaData-PDMSystemPdmSys_CouplerSPDC-Channel_2\\PDMSystemPdmSys_CouplerSPDC0004";
		String filePath = "E:\\JinXiejie\\UHF\\Putu\\PDMzzPdmSys_CouplerSPDC0003";
		File file = new File(filePath);
		File[] lists = file.listFiles();
		if(lists != null){
			System.out.println("正在执行PRPD。。。");
			for (File f : lists){
//			InputStream data = Main.class.getResourceAsStream(filePath);
				InputStream data = new FileInputStream(f);
//				String destFilePathTrain = "E:/JinXiejie/data/PRPD/PDMSystemPdmSys_CouplerSPDC-Channel_2_3/prpd_train" + String.valueOf(idx) + ".csv";
				String destFilePathTrain = "C:/Users/Administrator/Desktop/PRPD/prpd_train" + String.valueOf(idx) + ".csv";
				vo = parser.parser_prpd(data,destFilePathTrain);
				idx++;
				data.close();
			}
		}
		ImageGenerator generator = ImageGeneratorFactory.getImageGenerator(type);
		generator.generate(vo,SAVEIMAGEPATH);
	}

}
