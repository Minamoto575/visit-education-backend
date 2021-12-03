package cn.krl.visiteducationbackend.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;

/**
 * @author kuang
 * @description 文件工具类
 * @date 2021/12/2  17:16
 */


@Slf4j
public class FileUtil {
    public static void uploadFile(MultipartFile multipartFile, String fileName){
        String filePath = "C:\\Users\\91119\\Desktop\\temp\\";
        File dest = new File(filePath + fileName);
        try {
            multipartFile.transferTo(dest);
            log.info("成功存储在服务器");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @description: 将文件内容内容处理成Base64编码格式
     * @param: file
     * @author kuang
     * @date: 2021/12/3
     */
    public static String encodeFile(MultipartFile file) {
        byte[] fileBytes = null;
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return fileBytes == null ? null : encoder.encode(fileBytes);
    }



}
