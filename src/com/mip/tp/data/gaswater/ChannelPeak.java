/**
 * Copyright (C) 2011 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.mip.tp.data.gaswater;

/**
 * 
 * @author longyz
 * @date 2012-4-20
 */
public class ChannelPeak {
	public int orderid;//峰号，从1开始
	public String peakname;//组成分名称
	public float peaktime;//峰点时间
	public float peakstarttime;//峰开始时间
	public float peakendtime;//峰结束时间
	public float peakhigh;//峰高
	public float peakarea;//峰面积
}
