/**
 * Copyright (C) 2011 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.mip.tp.image;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import javax.imageio.ImageIO;

import com.mip.tp.data.ViewObject;
import com.mip.tp.data.circuitbreaker.CBDataValue;
import com.mip.tp.data.circuitbreaker.CBWaveData;
import com.mip.tp.data.partdischarge.PDViewObject;
import com.mip.tp.util.ImageUtil;

/**
 * 局放PRPD类型图谱文件图片生成器
 * @author longyz
 * @date 2012-4-23
 */
public class PDPrpdImageGenerator implements ImageGenerator {

	private static final int DEFAULT_WIDTH = 1000;//默认宽度
	private static final int DEFAULT_HEIGHT = 400;//默认高度
	private static final int DEFAULT_X_PADDING = 100;//默认高度
	private static final int DEFAULT_Y_PADDING = 70;//默认高度
	private static final int DEFAULT_X_SEPARATECOUNT = 4;//x轴份数
	private static final int DEFAULT_Y_SEPARATECOUNT = 8;//y轴份数
	private static final Color DEFAULT_BG_COLOR = new Color(0,0x99,0x44);//背景色
	
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
	
	private Graphics2D g2d;
	private BufferedImage bi;
	
	private static final NumberFormat numberFormat = NumberFormat.getNumberInstance(java.util.Locale.CHINA);
	static{
		numberFormat.setMaximumFractionDigits(2);     //两位小数
	}
	
	private String title = "PRPD";
	private static final Color[] colors = new Color[]{
		new Color(0xFFFE00),
		new Color(0xFFDD00),
		new Color(0xFFBD05),
		
		new Color(0xFF9A06),
		new Color(0xFE8000),
		new Color(0xFF3E00),
		
		new Color(0xFE1E00),
		new Color(0xFF1001),
		new Color(0xEA0000),
		
		new Color(0xAE02AC),
		new Color(0xAC00FF),
		new Color(0x5D01FD),

		new Color(0x7D80FC),
		new Color(0x9FA1F9),
		new Color(0xCFD1CA),

		new Color(0x60605D),
		new Color(0xFEFEFE)
	};
	
	private static Color[] reverseColors = new Color[colors.length];
	static{
		for(int i=0;i<reverseColors.length;i++){
			reverseColors[(reverseColors.length-1)-i] = colors[i];
		}
	}
	private PDViewObject vo;
	
	/* (non-Javadoc)
	 * @see com.mip.tp.image.ImageGenerator#generate(com.mip.tp.data.ViewObject, java.lang.String)
	 */
	@Override
	public String generate(ViewObject vo, String saveImagePath) throws ImageGeneratorException {
		if(!(vo instanceof PDViewObject)){
			throw new ImageGeneratorException("vo不是PDViewObject的对象");
		}
		this.vo = (PDViewObject)vo;
		
		initGenerator();
        
        //背景色
        drawBG();
        
        g2d.translate(xOffset2center, yOffset2center);//平移坐标,使所有坐标居中
        drawColorBar();
        drawTitle();
        drawXAxis();
        drawYAxis();
        drawViewObject();
        
        release();
        
        try {
        	if(saveImagePath != null){
        		ImageIO.write(bi, "png", new File(saveImagePath));
        	}
			return ImageUtil.convert2Base64(bi);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ImageGeneratorException("图片生成失败",e);
		}
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
		xScale = (width-xPadding*2)/(vo.maxXCoordinate - vo.minXCoordinate);//x轴比例尺
        yScale = (height-yPadding*2)/(vo.maxYCoordinate - vo.minYCoordinate);//y轴比例尺
        
        ySeparateCount = vo.maxYCoordinate/10;
        xSeparate = (width-xPadding*2)/xSeparateCount;//等高线间距
        ySeparate = (height-yPadding*2)/ySeparateCount;//等高线间距
        xRealSeparate = xSeparate/xScale;//实际比例
        yRealSeparate = ySeparate/yScale;//实际比例
        
        xOffset2center = xPadding;
        yOffset2center = yPadding;
        
        maxYCoordinate_yScale = vo.maxYCoordinate*yScale;
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
	 * 绘制颜色条
	 */
	private void drawColorBar(){
		for (int c = 0; c < colors.length; c++) {
			g2d.setColor(colors[c]);
			g2d.fillRect(-50, c * 10, 20, 10);
		}
		
	}
	
	/**
	 * 绘制x轴坐标
	 */
	private void drawXAxis(){
		int linex = caculateXCoordinate(0);
		int lineyMin = caculateYCoordinate(vo.minYCoordinate);
		int liney2 = caculateYCoordinate(vo.minYCoordinate-10);
		
		int lineyMax = caculateYCoordinate(vo.maxYCoordinate);
		int avgX = (int)(360/xSeparateCount);
		g2d.setColor(Color.BLACK);
		g2d.drawString("相位(º)", caculateXCoordinate(vo.maxXCoordinate/2), caculateYCoordinate(-10));
		g2d.setColor(new Color(0xBB,0xCC,0xDD));
		//g2d.drawLine(0, lineyMin, 0, lineyMax);
		g2d.fillRect(-10, lineyMax, 10, lineyMin);
		FontMetrics fm = g2d.getFontMetrics();//将要用到的FontMetrics类
		String str = null;
		int stringWidth;
        for(int i=1;i<xSeparateCount+1;i++){
        	linex = (int)caculateXCoordinate(xRealSeparate * i);
        	g2d.drawLine(linex, 0, linex, lineyMin);
        	str = numberFormat.format(avgX*i);
//        	str = numberFormat.format(xRealSeparate*i);
        	stringWidth = fm.stringWidth(str);
        	g2d.drawString(str, linex, lineyMin+fm.getHeight());
        }
	}
	
	/**
	 * 绘制y轴坐标及等高线
	 */
	private void drawYAxis(){
		//绘制等高线
        int linex1 = caculateXCoordinate(0);
        int liney = 0;
        int linex2 = caculateXCoordinate(vo.maxXCoordinate);
        FontMetrics fm = g2d.getFontMetrics();//将要用到的FontMetrics类
        String str = null;
        g2d.setColor(Color.BLACK);
        g2d.drawString("幅值", caculateXCoordinate(-5), caculateYCoordinate(20));
        g2d.setColor(new Color(255,255,255));
        for(int i=0;i<ySeparateCount+1;i++){
        	liney = (int)(ySeparate * i);
        	g2d.drawLine(linex1, liney, linex2, liney);
        	str = numberFormat.format((vo.maxYCoordinate - yRealSeparate*i));
        	g2d.drawString(str, linex1-fm.stringWidth(str)-10, liney);
        }
	}
	
	private void drawViewObject(){
		double avgValue = vo.maxvalue/colors.length;//幅值平均值
		g2d.setColor(Color.RED);
		if(null==vo.datas){//临时文件解析，数据可能为空。
			return;
		}
		int rowCount = vo.datas.length;
		
		int rectWidth = (width-2*xPadding)/rowCount;
		int rectHeight = 2;
		
		for(int x=0;x<rowCount;x++){
			int[] row = vo.datas[x];
			int colCount = row.length;
			for(int y=0;y<colCount;y++){
				int value = row[y];
				if(value == 0){
					continue;
				}
				double colorIdx = value/avgValue;//当前颜色索引
				int x1 = caculateXCoordinate(x);
				int y1 = caculateYCoordinate(y);
				
				g2d.setColor(reverseColors[(int)Math.round(colorIdx)]);
				g2d.fillRect(x1-1, y1, rectWidth, rectHeight);
			}
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
	private void drawWave(CBWaveData waveData){
    	int wdlength = waveData.wdata.length;
    	
    	if(wdlength == 0){
    		return;
    	}
    	
    	CBDataValue dv1 = null;
    	CBDataValue dv2 = null;
    	for(int j=0; j<wdlength-1; j++){
    		dv1 = waveData.wdata[j];
    		dv2 = waveData.wdata[j+1];
    		int x1 = caculateXCoordinate(dv1.xvalue);
        	int y1 = caculateYCoordinate(dv1.yvalue);
    		int x2 = caculateXCoordinate(dv2.xvalue);
        	int y2 = caculateYCoordinate(dv2.yvalue);
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
