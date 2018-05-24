/**
 * Copyright (C) 2011 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.mip.tp.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author longyz
 * @date 2012-4-20
 */
public class CoordinateUtil {

	public static double[] c3Toc2(double x,double y,double z,double radian){
		/*
		 
		 P（x, y, z）→ P’(x’, y’)的转换公式为：
		 x’ = x + y*cosα 
		 y’ = z + y*sinα 
		 
		 */
//		double[] c2 = new double[2];
//		c2[0] = x + y*Math.cos(radian);
//		c2[1] = z + y*Math.sin(radian);
//		return c2;
		/*double[] c2 = new double[2];
		c2[0] = y + x*Math.cos(radian);
		c2[1] = z + x*Math.sin(radian);
		return c2;*/
		
//		double[] c2 = new double[2];
//		c2[0] = x + y*Math.cos(radian);
//		c2[1] = z + y*Math.sin(radian);
//		return c2;
		
		double[] c2 = new double[2];
		c2[0]=x+z*Math.cos(radian);
		c2[1]=y+z*Math.sin(radian);
		return c2;
	}
	
	public static double[] c3Toc2(double x,double y,double z,double f,double zerox,double zeroy){
		/*double x2d = f*x/z + zerox;
		double y2d = f*y/z + zeroy;
		return new double[]{x2d,y2d};*/
		double k = f/(f+z);
		double x2d = k*x;
		double y2d = k*y;
		return new double[]{x2d,y2d};
	}
	
	public static double[] c3Toc2_new(double x,double y,double z,double f){
		double k = f/(f+z);
		double x2d = k*x;
		double y2d = k*y;
		return new double[]{x2d,y2d};
	}
	
	
	public static void test2d(){
		double uint = 200;
		double foucs = 100;
		int width = 1000;
		int height = 1000;
		
		double[] c000 = CoordinateUtil.c3Toc2(0, 0, 0, foucs);
		double[] c111 = CoordinateUtil.c3Toc2(uint, uint, uint, foucs);
		double[] c_1_1_1 = CoordinateUtil.c3Toc2(-uint, -uint, -uint, foucs);
		
		double[] c100 = CoordinateUtil.c3Toc2(uint, 0, 0, foucs);
		double[] c010 = CoordinateUtil.c3Toc2(0, uint, 0, foucs);
		double[] c001 = CoordinateUtil.c3Toc2(0, 0, uint, foucs);
		
		double[] c_100 = CoordinateUtil.c3Toc2(-uint, 0, 0, foucs);
		double[] c0_10 = CoordinateUtil.c3Toc2(0, -uint, 0, foucs);
		double[] c00_1 = CoordinateUtil.c3Toc2(0, 0, -uint, foucs);
		
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2d = bi.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        
        g2d.translate(uint+200,uint+100);
        
        g2d.drawLine((int)c000[0], (int)c000[1], (int)c111[0], (int)c111[1]);
        g2d.drawLine((int)c000[0], (int)c000[1], (int)c_1_1_1[0], (int)c_1_1_1[1]);
        
        g2d.drawLine((int)c000[0], (int)c000[1], (int)c100[0], (int)c100[1]);
        g2d.drawLine((int)c000[0], (int)c000[1], (int)c010[0], (int)c010[1]);
        g2d.drawLine((int)c000[0], (int)c000[1], (int)c001[0], (int)c001[1]);
        
        g2d.drawLine((int)c000[0], (int)c000[1], (int)c_100[0], (int)c_100[1]);
        g2d.drawLine((int)c000[0], (int)c000[1], (int)c0_10[0], (int)c0_10[1]);
        g2d.drawLine((int)c000[0], (int)c000[1], (int)c00_1[0], (int)c00_1[1]);
        
        g2d.drawString("c000", (int)c000[0], (int)c000[1]);
        g2d.drawString("c-1-1-1", (int)c_1_1_1[0], (int)c_1_1_1[1]);
        g2d.drawString("c111", (int)c111[0], (int)c111[1]);
        
        g2d.drawString("c100", (int)c100[0], (int)c100[1]);
        g2d.drawString("c010", (int)c010[0], (int)c010[1]);
        g2d.drawString("c001", (int)c001[0], (int)c001[1]);
        
        g2d.drawString("c-100", (int)c_100[0], (int)c_100[1]);
        g2d.drawString("c0-10", (int)c0_10[0], (int)c0_10[1]);
        g2d.drawString("c00-1", (int)c00_1[0], (int)c00_1[1]);
        
        g2d.dispose();
        try {
			ImageIO.write(bi, "jpg", new File("D:/c3Toc2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void test2d2(){
		double uint = 200;
		double foucs = 800;
		int width = 1000;
		int height = 1000;
		
		double[] c000 = CoordinateUtil.c3Toc2_new(0, 0, 0, foucs);
		double[] c100 = CoordinateUtil.c3Toc2_new(uint, 0, 0, foucs);
		double[] c110 = CoordinateUtil.c3Toc2_new(uint, uint, 0, foucs);
		double[] c010 = CoordinateUtil.c3Toc2_new(0, uint, 0, foucs);
		
		double[] c001 = CoordinateUtil.c3Toc2_new(0, 0, uint, foucs);
		double[] c101 = CoordinateUtil.c3Toc2_new(uint, 0, uint, foucs);
		double[] c111 = CoordinateUtil.c3Toc2_new(uint, uint, uint, foucs);
		double[] c011 = CoordinateUtil.c3Toc2_new(0, uint, uint, foucs);
		
		
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2d = bi.createGraphics();
		g2d.setColor(new Color(0,0x99,0x44));
        g2d.fillRect(0, 0, width, height);
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        
        g2d.translate(uint+200,uint+100);
        
        int rule = AlphaComposite.SRC_OVER;
        float alpha = 0.5f;
        g2d.setComposite(AlphaComposite.getInstance(rule,alpha));

        g2d.setColor(new Color(0,0,0));
        g2d.fillRect(0+3, 0+3, 10, 100);
        
        g2d.setComposite(AlphaComposite.getInstance(rule));
        g2d.setColor(new Color(0,255,0));
        g2d.fillRect(0, 0, 10, 100);
        
//        g2d.setColor(new Color(0,255,0,9));
//        g2d.fillRect(0, 0, 100, 100);
//        g2d.fill3DRect(0, 0, 10, 100, true);
//        g2d.setColor(new Color(255,0,0,0));
//        g2d.fill3DRect(10, 0, 10, 100, true);
        
//        g2d.drawLine((int)c000[0], (int)c000[1], (int)c100[0], (int)c100[1]);
//        g2d.drawLine((int)c100[0], (int)c100[1], (int)c110[0], (int)c110[1]);
//        g2d.drawLine((int)c110[0], (int)c110[1], (int)c010[0], (int)c010[1]);
//        g2d.drawLine((int)c010[0], (int)c010[1], (int)c000[0], (int)c000[1]);
//        
//        g2d.drawLine((int)c001[0], (int)c001[1], (int)c101[0], (int)c101[1]);
//        g2d.drawLine((int)c101[0], (int)c101[1], (int)c111[0], (int)c111[1]);
//        g2d.drawLine((int)c111[0], (int)c111[1], (int)c011[0], (int)c011[1]);
//        g2d.drawLine((int)c011[0], (int)c011[1], (int)c001[0], (int)c001[1]);
//        
//        g2d.drawString("c000", (int)c000[0], (int)c000[1]);
//        g2d.drawString("c100", (int)c100[0], (int)c100[1]);
//        g2d.drawString("c110", (int)c110[0], (int)c110[1]);
//        g2d.drawString("c010", (int)c010[0], (int)c010[1]);
//        
//        g2d.drawString("c001", (int)c001[0], (int)c001[1]);
//        g2d.drawString("c101", (int)c101[0], (int)c101[1]);
//        g2d.drawString("c111", (int)c111[0], (int)c111[1]);
//        g2d.drawString("c011", (int)c011[0], (int)c011[1]);
        
        g2d.dispose();
        try {
			ImageIO.write(bi, "jpg", new File("D:/c3Toc2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
//		test2d();
		//test2d2();
		int width = 1000;
		int height = 1000;
		
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bi.createGraphics();
		//
		int unit = 100;
		g2d.translate(500, 500);
//		g2d.drawLine(0, 0, unit, 0);
//		g2d.drawLine(0, 0, 0, unit);
//		
//		g2d.drawLine(unit, 0, unit, unit);
		g2d.drawLine(0, 0, unit, unit);
		
		//定义好y轴起点
		
		g2d.dispose();
        try {
			ImageIO.write(bi, "jpg", new File("D:/c3Toc2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int caculateX(double x){
		
		return 0;
	}
	
	public static int caculateY(){
		return 0;
	}
	
	
}
