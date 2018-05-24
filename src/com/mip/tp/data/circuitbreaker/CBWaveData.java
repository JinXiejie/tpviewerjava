/**
 * Copyright (C) 2011 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.mip.tp.data.circuitbreaker;

import com.mip.tp.chart.Line;

/**
 * 
 * @author longyz
 * @date 2012-4-19
 */
public class CBWaveData extends Line{

	public int status;//波形数据状态
	public long wlength;//波形数据的长度
	public float value;//有效值
	public float xInterval;//x轴间隔
	public String xUnit;//x轴单位
	public String yUnit;//y轴单位
	public CBDataValue[] wdata;
	public CBWaveData(){}
}
