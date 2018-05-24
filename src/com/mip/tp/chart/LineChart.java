/**
 * Copyright (C) 2011 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.mip.tp.chart;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import javax.imageio.ImageIO;

import com.mip.tp.image.ImageGeneratorException;

/**
 * 图
 * @author longyz
 * @date 2012-4-20
 */
public class LineChart {

	private static final int DEFAULT_WIDTH = 2400;//默认宽度
	private static final int DEFAULT_HEIGHT = 600;//默认高度
	private static final int DEFAULT_X_PADDING = 50;//默认高度
	private static final int DEFAULT_Y_PADDING = 30;//默认高度
	private static final int DEFAULT_X_SEPARATECOUNT = 20;//x轴份数
	private static final int DEFAULT_Y_SEPARATECOUNT = 10;//y轴份数
	private static final Color DEFAULT_BG_COLOR = new Color(0x3B,0x9A,0x3C);//背景色

	private String title;
	
	private Color bgColor = DEFAULT_BG_COLOR;
	private int width = DEFAULT_WIDTH;
	private int height = DEFAULT_HEIGHT;
	
	private double xSeparateCount = DEFAULT_X_SEPARATECOUNT;//x轴分隔份数
	private double ySeparateCount = DEFAULT_Y_SEPARATECOUNT;//x轴分隔份数
	private double xSeparate;//x轴分隔宽度
	private double ySeparate;//y轴分隔高度
	private double xRealSeparate;//x轴分隔实际宽度
	private double yRealSeparate;//y轴分隔实际高度
	
	private double xScale;//x轴比例尺
	private double yScale;//x轴比例尺
	
	private int xPadding = DEFAULT_X_PADDING;//x轴间隙
	private int yPadding = DEFAULT_Y_PADDING;//y轴间隙
	
	private double xOffset2center;//y轴居中的偏移量
	private double yOffset2center;//y轴居中的偏移量
	
	private double maxYCoordinate_yScale;//y轴最大值转换为坐标系后的值
	
	private DataProvider dataProvider;
	private Graphics2D g2d;
	private BufferedImage bi;
	
	private static final NumberFormat numberFormat = NumberFormat.getNumberInstance(java.util.Locale.CHINA);
	static{
		numberFormat.setMaximumFractionDigits(2);     //两位小数
	}
	
	public LineChart(){}
	
	public LineChart(int width,int height) throws ImageGeneratorException{
		if(width < 0 || height < 0){
			throw new ImageGeneratorException("width和 hegiht不能小于0");
		}
		this.width = width;
		this.height = height;
	}
	
	/* (non-Javadoc)
	 * @see com.mip.tp.image.ImageGenerator#generate(com.mip.tp.data.ViewObject)
	 */
	public String generate(DataProvider dataProvider) throws ImageGeneratorException {
		this.dataProvider = dataProvider;
		
		initGenerator();
        
        //背景色
        drawBG();
        
        g2d.translate(xOffset2center, yOffset2center);//平移坐标,使所有坐标居中
        
        drawTitle();
        drawXAxis();
        drawYAxis();
        drawViewObject();
        
        release();
        
        try {
			ImageIO.write(bi, "jpg", new File("D:/a_new.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 初始化画笔
	 */
	private void initGraphics(){
//		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		g2d = bi.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        
	}
	
	/**
	 * 初始化属性
	 */
	private void initProperties(){
		xScale = (width-xPadding*2)/(dataProvider.maxXCoordinate - dataProvider.minXCoordinate);//x轴比例尺
        yScale = (height-yPadding*2)/(dataProvider.maxYCoordinate - dataProvider.minYCoordinate);//y轴比例尺
        
        xSeparate = (width-xPadding*2)/xSeparateCount;//等高线间距
        ySeparate = (height-yPadding*2)/ySeparateCount;//等高线间距
        xRealSeparate = xSeparate/xScale;//实际比例
        yRealSeparate = ySeparate/yScale;//实际比例
        
        xOffset2center = xPadding;
        yOffset2center = yPadding;
        
        maxYCoordinate_yScale = dataProvider.maxYCoordinate*yScale;
	}
	
	/**
	 * 初始化生成器
	 * @param cbvo
	 */
	private void initGenerator(){
		initGraphics();
		initProperties();
	}
	
	/**
	 * 释放资源
	 */
	private void release(){
		g2d.dispose();
	}
	
	private void drawTitle(){
		if(title!=null){
			g2d.setColor(Color.BLACK);
			g2d.drawString(title, 0, -10);
		}
	}
	
	private void drawBG(){
		g2d.setColor(bgColor);
        g2d.fillRect(0, 0, width, height);
	}
	
	/**
	 * 绘制x轴坐标
	 */
	private void drawXAxis(){
		int linex = 0;
		int lineyMin = caculateYCoordinate(dataProvider.minYCoordinate);
		int liney2 = caculateYCoordinate(dataProvider.minYCoordinate-10);
		
		int lineyMax = caculateYCoordinate(dataProvider.maxYCoordinate);
		
		g2d.setColor(new Color(0xBB,0xCC,0xDD));
		//g2d.drawLine(0, lineyMin, 0, lineyMax);
		g2d.fillRect(-10, lineyMax, 10, lineyMin);
		FontMetrics fm = g2d.getFontMetrics();//将要用到的FontMetrics类
		String str = null;
		int stringWidth;
        for(int i=1;i<xSeparateCount+1;i++){
        	linex = (int)(xSeparate * i);
        	g2d.drawLine(linex, lineyMin, linex, liney2);
        	str = numberFormat.format(xRealSeparate*i);
        	stringWidth = fm.stringWidth(str);
        	g2d.drawString(str, linex-stringWidth/2, lineyMin+fm.getHeight());
        }
	}
	
	/**
	 * 绘制y轴坐标及等高线
	 */
	private void drawYAxis(){
		//绘制等高线
        int linex1 = 0;
        int liney = 0;
        int linex2 = caculateXCoordinate(dataProvider.maxXCoordinate);
        g2d.setColor(new Color(255,255,255));
        FontMetrics fm = g2d.getFontMetrics();//将要用到的FontMetrics类
        String str = null;
        for(int i=0;i<ySeparateCount+1;i++){
        	liney = (int)(ySeparate * i);
        	g2d.drawLine(linex1, liney, linex2, liney);
        	str = numberFormat.format((dataProvider.maxYCoordinate - yRealSeparate*i));
        	g2d.drawString(str, linex1-fm.stringWidth(str)-10, liney);
        }
	}
	
	private void drawViewObject(){
		Color[] linecolor = {
        		new Color(0x00,0x00,0x00),
        		new Color(0xFF,0x00,0x00),
        		new Color(0x00,0x00,0xFF),
        		new Color(0xCC,0xFF,0x00),
        		
        		new Color(0x00,0xCC,0x00),
        		new Color(0xFF,0x66,0x00),
        		new Color(0x99,0xCC,0xFF),
        		
        		new Color(0x99,0xFF,0xCC),
        		new Color(0xFF,0x00,0xFF),
        		new Color(0x33,0x00,0x33)
        	};
		
		int wlength = dataProvider.getLength();
		for(int i=0; i<wlength; i++){
        	g2d.setColor(linecolor[i]);
        	Line line = dataProvider.getLines()[i];
        	drawLine(line);
        }
	}
	
	/**
	 * 计算x坐标
	 * @param xvalue
	 * @return
	 */
	private int caculateXCoordinate(double xvalue){
		return (int)(xvalue * xScale);
	}
	
	/**
	 * 计算y坐标
	 * @param yvalue
	 * @return
	 */
	private int caculateYCoordinate(double yvalue){
		//1)坐标反转
		return (int)( - (yvalue * yScale) + maxYCoordinate_yScale );
	}
	
	/**
	 * 绘制波形
	 */
	private void drawLine(Line line){
    	int wdlength = line.getLength();
    	
    	if(wdlength == 0){
    		return;
    	}
    	
    	Point point1 = null;
    	Point point2 = null;
    	for(int j=0; j<wdlength-1; j++){
    		point1 = line.getPoints()[j];
    		point2 = line.getPoints()[j+1];
    		int x1 = caculateXCoordinate(point1.xvalue);
        	int y1 = caculateYCoordinate(point1.yvalue);
    		int x2 = caculateXCoordinate(point2.xvalue);
        	int y2 = caculateYCoordinate(point2.yvalue);
    		g2d.drawLine(x1, y1, x2, y2);
    	}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
