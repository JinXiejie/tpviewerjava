package com.mip.tp.parser;

import com.mip.tp.data.ViewObject;
import com.mip.tp.data.partdischarge.PDViewObject;
import com.mip.tp.factory.ImageGeneratorFactory;
import com.mip.tp.factory.ParserFactory;
import com.mip.tp.factory.TPType;
import com.mip.tp.image.ImageGenerator;
import com.mip.tp.image.ImageGeneratorException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Main2 {

    public static void main(String[] args) throws ImageGeneratorException {
        TPType type = TPType.PD;
        IParser parser = ParserFactory.getParser(type);

        String filePath = "E:\\JinXiejie\\UHF\\Putu\\PDMzzPdmSys_CouplerSPDC0003";
        File[] allFiles = new File(filePath).listFiles();

        String filetype = null;
        for(File file : allFiles){
            System.out.println("正在处理" + file.getAbsolutePath());
            InputStream stream = null;
            try{
                stream = new FileInputStream(file);
                ViewObject vo = parser.parser(stream);
                PDViewObject pdVO = (PDViewObject)vo;

                if(pdVO.tptype == 0){
                    filetype = "prpd";
                }else if(pdVO.tptype == 1){
                    filetype = "prps";
                }
                ImageGenerator generator = ImageGeneratorFactory.getImageGenerator(type);
                String saveImagePath = "D:/pngs/" + file.getName() + filetype + ".png";
                System.out.println(saveImagePath);
                generator.generate(vo,saveImagePath);
            }catch (Exception e){
                System.out.println("生成失败" + file.getAbsolutePath());
            }finally {
                if(stream != null){
                    try{
                        stream.close();
                    }catch (Exception e){}
                }
            }
        }

    }
}
