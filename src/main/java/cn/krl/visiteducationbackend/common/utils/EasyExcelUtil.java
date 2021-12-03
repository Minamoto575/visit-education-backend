package cn.krl.visiteducationbackend.common.utils;

import cn.krl.visiteducationbackend.common.listener.ExcelReaderListener;
import cn.krl.visiteducationbackend.model.dao.ExcelImportDAO;
import cn.krl.visiteducationbackend.model.dto.RecordDTO;
import cn.krl.visiteducationbackend.service.IRecordService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author kuang
 * @description EasyExcel工具类
 * @date 2021/12/1  18:21
 */
@Component
@Slf4j
public class EasyExcelUtil {
    @Autowired
    private IRecordService recordService;
    @Autowired
    private ExcelImportDAO excelImportDAO;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * @description: 临时存储上传的excel，uploadExcel接口是异步的。
     */
    // private MultipartFile tempFile;


    /**
     * @description: 读取并解析excel
     * @param: fileName
     * @param: doCheck
     * @param: multipartFile
     * @author kuang
     * @date: 2021/12/3
     */
    @Async
    public void readExcel(String fileName, Boolean doCheck, InputStream inputStream) {


        excelImportDAO.setDoCheck(doCheck);
        excelImportDAO.clearErrorList();

        //开始读取并解析
        try {
            // List<ReadSheet> readSheetList =
            //     EasyExcel.read(multipartFile.getInputStream())
            //         .build()
            //         .excelExecutor()
            //         .sheetList();
            // // 读每个sheet
            // for (ReadSheet readSheet : readSheetList) {
            //     // 对excel进行读取，在listener.RecordDTOLister被监听
            //     EasyExcel.read(
            //             multipartFile.getInputStream(),
            //             RecordDTO.class,
            //             new ExcelReaderListener(recordService, excelImportDAO))
            //         .sheet(readSheet.getSheetName())
            //         .doRead();
            // }
            EasyExcel.read(
                    inputStream,
                    RecordDTO.class,
                    new ExcelReaderListener(recordService, excelImportDAO))
                .sheet()
                .doRead();
        } catch (Exception e) {
            log.error("excel导入系统错误：" + e.getMessage());
            e.printStackTrace();
        }
        log.info(excelImportDAO.getErrorList().toString());
        //读完把结果存入redis
        String jsonStr = JSONObject.toJSONString(excelImportDAO.getErrorList());
        redisUtil.set(fileName, jsonStr, 120);
    }
}
