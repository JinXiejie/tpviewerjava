/**
 * Copyright (C) 2011 Guangzhou JHComn Technologies Ltd.
 *
 * 本代码版权归广州佳和立创科技发展有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.mip.tp.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author longyz
 * @date 2012-4-22
 */
public class ImageUtil {

	/**
	 * 返回base64编码的字符串
	 * @throws IOException 
	 */
	public static String convert2Base64(BufferedImage bi) throws IOException {
		ByteArrayOutputStream baos=new ByteArrayOutputStream(1024);
		ImageIO.write(bi, "png", baos);
		baos.flush();
        String base64String=new sun.misc.BASE64Encoder().encode(baos.toByteArray());
        baos.close();
        return base64String;
	}
}
