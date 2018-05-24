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
import com.mip.tp.data.gaswater.Channel;
import com.mip.tp.data.gaswater.ChannelPeak;
import com.mip.tp.data.gaswater.GasWaterViewObject;
import com.mip.tp.util.ImageUtil;

/**
 * 油色谱及微水图谱文件图片生成器
 * @author longyz
 * @date 2012-4-20
 */
public class GasWaterImageGenerator implements ImageGenerator {
	
	private static final int DEFAULT_WIDTH = 1000;//默认宽度
	private static final int DEFAULT_HEIGHT = 200;//默认高度
	private static final int DEFAULT_X_PADDING = 80;//默认高度
	private static final int DEFAULT_Y_PADDING = 50;//默认高度
	private static final int DEFAULT_X_SEPARATECOUNT = 8;//x轴份数
	private static final int DEFAULT_Y_SEPARATECOUNT = 8;//y轴份数
	private static final Color DEFAULT_BG_COLOR = new Color(0x3B,0x9A,0x3C);//背景色
	
	private static final Color FONT_COLOR = Color.BLACK;

	private String title;
	
	private Color bgColor = DEFAULT_BG_COLOR;
	private int width = DEFAULT_WIDTH;
	private int height = DEFAULT_HEIGHT;
	private int channelWidth = 0;//通道曲线图的宽度
	private int channelHeight = 0;//通道曲线图的高度
	
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
	
	private double startXCoordinate;//起始X坐标
	
	private GasWaterViewObject vo;
	private Graphics2D g2d;
	private BufferedImage bi;
	
	private static final NumberFormat numberFormat = NumberFormat.getNumberInstance(java.util.Locale.CHINA);
	static{
		numberFormat.setMaximumFractionDigits(2);     //两位小数
	}
	
	private int currentChannelOrderNo = 0;//当前通道序号
	
	public GasWaterImageGenerator(){}
	
	public GasWaterImageGenerator(int width,int height) throws ImageGeneratorException{
		if(width < 0 || height < 0){
			throw new ImageGeneratorException("width和 hegiht不能小于0");
		}
		this.width = width;
		this.height = height;
	}
	
	/* (non-Javadoc)
	 * @see com.mip.tp.image.ImageGenerator#generate(com.mip.tp.data.ViewObject)
	 */
	@Override
	public String generate(ViewObject vo,String saveImagePath) throws ImageGeneratorException {
		
		if(!(vo instanceof GasWaterViewObject)){
			throw new ImageGeneratorException("vo不是CBViewObject的对象");
		}
		this.vo = (GasWaterViewObject)vo;
		
		initGenerator();
		
		//背景色
        drawBG();
        g2d.translate(xOffset2center, yOffset2center);//平移坐标,使所有坐标居中
		draw();
		
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
	
	public void draw(){
		drawWave();
		//drawPeak();
	}
	
	public void drawWave(){
		Color[] linecolor = {
        		new Color(0xFF,0xFF,0xFF),
        		new Color(0xFF,0xFF,0xFF),
        		new Color(0xFF,0xFF,0xFF),
        		new Color(0xFF,0xFF,0xFF),
        		
        		new Color(0xFF,0xFF,0xFF),
        		new Color(0xFF,0xFF,0xFF),
        		new Color(0xFF,0xFF,0xFF),
        		
        		new Color(0xFF,0xFF,0xFF),
        		new Color(0xFF,0xFF,0xFF),
        		new Color(0xFF,0xFF,0xFF),
        	};
		
		/*Color[] linecolor = {
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
        	};*/
		
		for(int i=0; i<vo.channelCount; i++){
			currentChannelOrderNo = i+1;
			g2d.setColor(linecolor[i]);
			Channel channel = vo.channels[i];
			startXCoordinate = channelWidth * i;
			resetCoordinate(channel);
			drawXAxis(channel);
			drawYAxis(channel);
			
			drawTitle(channel);
			g2d.setColor(Color.WHITE);
			for(int j=0; j<channel.datacount-1; j++){
				int y1 = caculateYCoordinate(channel.datas[j]);
				int y2 = caculateYCoordinate(channel.datas[j+1]);
				int x = caculateXCoordinate(j);
				g2d.drawLine(x, y1, x, y2);
			}
			/*for(int j=0; j<channel.datacount; j++){
				int y = caculateYCoordinate(channel.datas[j]);
				int x = caculateXCoordinate(j);
				g2d.drawOval(x, y, 1, 1);
			}*/
			
			drawChannelPeak(channel);
		}
	}
	
	/**
	 * 重新设置坐标系统,关键是x的起点坐标需要变化
	 */
	private void resetCoordinate(Channel channel){
		xScale = (channelWidth-xPadding*2)/(channel.maxXCoordinate - channel.minXCoordinate);//x轴比例尺
        yScale = (channelHeight-yPadding*2)/(channel.maxYCoordinate - channel.minYCoordinate);//y轴比例尺
        
        xSeparate = (channelWidth-xPadding*2)/xSeparateCount;//等高线间距
        ySeparate = (channelHeight-yPadding*2)/ySeparateCount;//等高线间距
        xRealSeparate = xSeparate/xScale;//实际比例
        yRealSeparate = ySeparate/yScale;//实际比例
        
        xOffset2center = xPadding;//平移
        yOffset2center = yPadding;
        
        maxYCoordinate_yScale = channel.maxYCoordinate*yScale;
	}
	
	public void drawChannelPeak(Channel channel){
		for(int j=0;j<channel.peakcount;j++){
			g2d.setColor(Color.BLUE);
			ChannelPeak peak = channel.peaks[j];
			
			int y = caculateYCoordinate(peak.peakhigh);
			double peak_position = (peak.peaktime/channel.sampletime);//波峰所在位置
			int x = caculateXCoordinate(peak_position*channel.datacount);
			int r = 2;
			int d = 4;
			g2d.fillOval(x-r, y, d, d);
			
//				g2d.drawString("峰号"+peak.orderid+"名称"+peak.peakname+"时间"+peak.peaktime+"峰高"+peak.peakhigh, x+d, y+d);
			g2d.setColor(FONT_COLOR);
//			g2d.drawString(peak.orderid+":"+peak.peakname+","+peak.peakhigh, x+d, y+d);
			g2d.drawString(peak.orderid+":"+peak.peakname, x+d, y+d);
			
			int x1 = 0;
			int y1 = 0;
			int x2 = 0;
			int y2 = 0;
			
			double peak_position_temp = (peak.peakstarttime/channel.sampletime*channel.datacount);//确定点的位置
			x1 = caculateXCoordinate(peak_position_temp);
			y1 = caculateYCoordinate(channel.datas[(int)Math.round(peak_position_temp)]);
			
			g2d.setColor(Color.RED);
			peak_position_temp = (peak.peakendtime/channel.sampletime*channel.datacount);//确定点的位置
			x2 = caculateXCoordinate(peak_position_temp);
			y2 = caculateYCoordinate(channel.datas[(int)Math.round(peak_position_temp)]);
			g2d.drawLine(x1, y1, x2, y2);//开始时间线
			
		}
	}
	
	@Deprecated
	public void drawPeak(){
		for(int i=0; i<vo.channelCount; i++){
			g2d.setColor(Color.BLUE);
			Channel channel = vo.channels[i];
			for(int j=0;j<channel.peakcount;j++){
				ChannelPeak peak = channel.peaks[j];
				
				int y = caculateYCoordinate(peak.peakhigh);
				double peak_position = (peak.peaktime/channel.sampletime);//波峰所在位置
				int x = caculateXCoordinate(peak_position*channel.datacount);
				int r = 2;
				int d = 4;
				g2d.fillOval(x-r, y, d, d);
				
//				g2d.drawString("峰号"+peak.orderid+"名称"+peak.peakname+"时间"+peak.peaktime+"峰高"+peak.peakhigh, x+d, y+d);
				g2d.setColor(Color.WHITE);
				g2d.drawString(peak.orderid+":"+peak.peakname+","+peak.peakhigh, x+d, y+d);
				
				int x1 = 0;
				int y1 = 0;
				int x2 = 0;
				int y2 = 0;
				
				double peak_position_temp = (peak.peakstarttime/channel.sampletime*channel.datacount);//确定点的位置
				x1 = caculateXCoordinate(peak_position_temp);
				y1 = caculateYCoordinate(channel.datas[(int)Math.round(peak_position_temp)]);
				
				g2d.setColor(Color.RED);
				peak_position_temp = (peak.peakendtime/channel.sampletime*channel.datacount);//确定点的位置
				x2 = caculateXCoordinate(peak_position_temp);
				y2 = caculateYCoordinate(channel.datas[(int)Math.round(peak_position_temp)]);
				g2d.drawLine(x1, y1, x2, y2);//开始时间线
				
			}
		}
	}
	
	private void drawBG(){
		g2d.setColor(bgColor);
        g2d.fillRect(0, 0, width, height);
	}
	
	/**
	 * 绘制x轴坐标
	 */
	private void drawXAxis(Channel channel){
		int linex = caculateXCoordinate(0);
		int lineyMin = caculateYCoordinate(channel.minYCoordinate);
		int liney2 = caculateYCoordinate(channel.minYCoordinate-10);
		
		int lineyMax = caculateYCoordinate(channel.maxYCoordinate);
		
		//g2d.drawLine(0, lineyMin, 0, lineyMax);
		g2d.fillRect(caculateXCoordinate(0), lineyMax, 10, lineyMin);
		FontMetrics fm = g2d.getFontMetrics();//将要用到的FontMetrics类
		String str = null;
		int stringWidth;
        for(int i=1;i<xSeparateCount+1;i++){
        	g2d.setColor(new Color(0xBB,0xCC,0xDD));
        	linex = (int)caculateXCoordinate(xRealSeparate * i);
        	g2d.drawLine(linex, lineyMin, linex, liney2);
        	str = numberFormat.format(xRealSeparate*i);
        	stringWidth = fm.stringWidth(str);
        	g2d.setColor(FONT_COLOR);
        	g2d.drawString(str, linex-stringWidth/2, lineyMin+fm.getHeight());
        }
	}
	
	/**
	 * 绘制y轴坐标及等高线
	 */
	private void drawYAxis(Channel channel){
		//绘制等高线
        int linex1 = caculateXCoordinate(0);
        int liney = 0;
        int linex2 = caculateXCoordinate(channel.maxXCoordinate);
        FontMetrics fm = g2d.getFontMetrics();//将要用到的FontMetrics类
        String str = null;
        for(int i=0;i<ySeparateCount+1;i++){
        	liney = (int)(ySeparate * i);
        	g2d.setColor(new Color(0x99,0xCC,0xFF));
        	g2d.drawLine(linex1, liney, linex2, liney);
        	g2d.setColor(FONT_COLOR);
        	str = numberFormat.format((channel.maxYCoordinate - yRealSeparate*i));
        	g2d.drawString(str, linex1-fm.stringWidth(str)-10, liney);
        }
	}
	
	private void drawTitle(Channel channel){
		String str = "通道"+currentChannelOrderNo+" 创建时间:"+vo.createTime;
		FontMetrics fm = g2d.getFontMetrics();//将要用到的FontMetrics类
		int x = caculateXCoordinate((channel.maxXCoordinate-fm.stringWidth(str))/2);
		int y = (int)(height-yOffset2center)-10;
		
		g2d.drawString(str, x, y);
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
		/*
		xScale = (width-xPadding*2)/(vo.maxXCoordinate - vo.minXCoordinate);//x轴比例尺
        yScale = (height-yPadding*2)/(vo.maxYCoordinate - vo.minYCoordinate);//y轴比例尺
        
        xSeparate = (width-xPadding*2)/xSeparateCount;//等高线间距
        ySeparate = (height-yPadding*2)/ySeparateCount;//等高线间距
        xRealSeparate = xSeparate/xScale;//实际比例
        yRealSeparate = ySeparate/yScale;//实际比例
        
        xOffset2center = xPadding;
        yOffset2center = yPadding;
        
        maxYCoordinate_yScale = vo.maxYCoordinate*yScale;
        */
		
		xOffset2center = xPadding;
        yOffset2center = yPadding;
		channelWidth = width/vo.channelCount;
//		channelHeight = height/vo.channelCount;
		channelHeight = height;
	}
	
	
	/**
	 * 计算x坐标
	 * @param xvalue
	 * @return
	 */
	private int caculateXCoordinate(double xvalue){
		return (int)( startXCoordinate + (xvalue * xScale) );
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

}
