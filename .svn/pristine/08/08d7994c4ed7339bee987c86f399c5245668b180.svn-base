/**
 * Copyright (C) 2011 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.mip.tp.data.partdischarge;

import com.mip.tp.data.ViewObject;

/**
 * 局放视图数据对象,对应flash的PDViewObject
 * @author longyz
 * @date 2012-4-20
 */
public class PDViewObject implements ViewObject {

	/**
	 * 版本
	 */
	public float version;
	/**
	 * 图谱生成时间
	 */
	public String datetime;
	/**
	 * 被监测设备编号
	 */
	public String devnum;
	/**
	 * 装置地址
	 */
	public String addr;
	/**
	 * 通道编号
	 */
	public int channelnum;
	/**
	 * 被监测设备状态
	 */
	public int devstatus;
	/**
	 * 数据状态
	 */
	public int datastatus;
	/**
	 * 放电类型
	 */
	public int type;
	/**
	 * 报警等级
	 */
	public int alarm;
	/**
	 * 放电类型概率统计标志
	 */
	public int probSt;
	//10 种放电类型的概率
	public float[] probs = new float[10];
	/**
	 * 放电幅值标定系数
	 */
	public short coeff;
	/**
	 * 放电相位窗数
	 */
	public int phaseNum;
	/**
	 * 幅值量化值
	 */
	public int avg;
	/**
	 * 工频周期数
	 */
	public int cycNum;
	/**
	 * 放电量峰值
	 */
	public float intens;
	/**
	 * 放电量均值
	 */
	public float avgV;
	/**
	 * 50Hz相关性
	 */
	public float fiftyHZ;
	/**
	 * 100Hz相关性
	 */
	public float hundredHZ;
	/**
	 * 图谱类型标志
	 */
	public int tptype;
	/**
	 * 幅值单位标志
	 */
	public int unit;
	/**
	 * 幅值起始值
	 */
	public int minvalue;
	/**
	 * 幅值最大值
	 */
	public int maxvalue;
	/**
	 * 数据中的最大值，用来标志生成图谱的z轴（画图时候用到），图谱文件中不存在此值
	 */
	public int maxint;
	/**
	 * 数据,对应flash中的datavalue
	 */
	public int[][] datas;
	
	/**
	 * y轴最大坐标值
	 */
	public double maxYCoordinate = 0;
	/**
	 * y轴最小坐标值
	 */
	public double minYCoordinate = 0;
	/**
	 * x轴最小坐标值
	 */
	public double maxXCoordinate = 0;
	/**
	 * x轴最小坐标值
	 */
	public double minXCoordinate = 0;
	
	/**
	 * z轴最小坐标值
	 */
	public double maxZCoordinate = 0;
	/**
	 * z轴最小坐标值
	 */
	public double minZCoordinate = 0;
}
