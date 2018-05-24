/**
 * Copyright (C) 2011 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.mip.tp.data.circuitbreaker;

import java.util.ArrayList;
import java.util.List;

import com.mip.tp.data.ViewObject;

/**
 * 断路器视图数据对象
 * @author longyz
 * @date 2012-4-19
 */
public class CBViewObject implements ViewObject {
	/**
	 * 图谱的曲线条数,断路器是固定9个
	 */
	public static final int WAVE_COUNT = 9;
	/**
	 * 版本
	 */
	public float dataversion;
	/**
	 * 创建时间
	 */
	public double recordtime;
	/**
	 * 设备编号
	 */
	public String devnum;
	/**
	 * 波型类型
	 */
	public int wavetype;
	/**
	 * 电流单位类型
	 */
	public String unit;
	/**
	 * 数据点数
	 */
	public long datapointnum;
	/**
	 * 波形数据
	 */
//	public CBWaveData[] wavedatas = new CBWaveData[WAVE_COUNT];
	public List<CBWaveData> wavedatas = new ArrayList<CBWaveData>();
	
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
	
}
