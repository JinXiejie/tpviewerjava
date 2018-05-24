/**
 * Copyright (C) 2011 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.mip.tp.util;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

import org.apache.commons.io.input.SwappedDataInputStream;

/**
 * 
 * @author longyz
 * @date 2012-4-19
 */
public class ParserUtil {

	/**
	 * 从流中读取一个字符串
	 * @param dis 
	 * @param byteCount 字节数
	 * @return
	 * @throws IOException
	 */
	public static String readString(DataInputStream stream ,int byteCount) throws IOException{
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<byteCount; i++){
			sb.append((char)stream.readByte());
		}
		return sb.toString().trim();
	}
	
	public static String readString(SwappedDataInputStream stream ,int byteCount) throws IOException{
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<byteCount; i++){
			sb.append((char)stream.readByte());
		}
		return sb.toString().trim();
	}
	
	/**
	 * 低位在前高位在后读取
	 * @param ch1
	 * @param ch2
	 * @param ch3
	 * @param ch4
	 * @return
	 * @throws IOException
	 */
	public static int readInt(int ch1,int ch2,int ch3,int ch4) throws IOException {
        if ((ch1 | ch2 | ch3 | ch4) < 0){
        	throw new EOFException();
        }
        return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0));
    }
	/**
	 * 低位在前高位在后读取
	 * @param ch1
	 * @param ch2
	 * @return
	 * @throws IOException
	 */
	public static short readShort(int ch1,int ch2) throws IOException {
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (short)((ch2 << 8) + (ch1 << 0));
    }
}
