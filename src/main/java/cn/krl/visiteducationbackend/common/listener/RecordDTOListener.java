package cn.krl.visiteducationbackend.common.listener;


import cn.krl.visiteducationbackend.common.utils.ExcelCheckUtil;
import cn.krl.visiteducationbackend.dto.RecordDTO;
import cn.krl.visiteducationbackend.entity.Record;
import cn.krl.visiteducationbackend.service.IRecordService;
import cn.krl.visiteducationbackend.service.Impl.RecordServiceImpl;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 不能被spring管理,读取excel则触发
 */
public class RecordDTOListener extends AnalysisEventListener<RecordDTO>{

    private final char STAR = '*';

    /**
     * 每100条存储数据库，然后清空list
     * 服务器内存小  BATCH_COUNT不能过大
     */
    private static final int BATCH_COUNT=100;
    List<RecordDTO>  list= new ArrayList<RecordDTO>();

    /**
     * 不能使用@autowired
     */
    private IRecordService recordService;

    public RecordDTOListener(){
        recordService = new RecordServiceImpl();
    }

    /**
     * 带参构造导入spring注入的service，可以使用自定义的方法
     * @param recordService
     */
    public RecordDTOListener(IRecordService recordService){
        this.recordService = recordService;
    }

    /**
     * 每次解析到一条数据都会调用
     * @param recordDTO
     * @param analysisContext
     */
    @Override
    public void invoke(RecordDTO recordDTO, AnalysisContext analysisContext){

        //检查该记录
        try{
            recordDTO=ExcelCheckUtil.check(recordDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        //已存在数据库则不添加
        if (!recordService.exist(recordDTO)) {
            list.add(recordDTO);
        }
        if(list.size()>=BATCH_COUNT){
            saveData();
            list.clear();
        }
    }

    /**
     * 所有数据都解析完了
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    /**
     * 将读取的record存入数据库
     */
    public void saveData(){
        Collection<Record> records = new ArrayList<Record>();
        for(RecordDTO r:list){
            Record record = new Record();
            BeanUtils.copyProperties(r,record);
            record.setId(null);
            record.setGmtCreate(System.currentTimeMillis());
            record.setGmtModified(System.currentTimeMillis());
            records.add(record);
        }
        recordService.saveBatch(records);
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
//        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
//        // 如果是某一个单元格的转换异常 能获取到具体行号
//        // 如果要获取头的信息 配合invokeHeadMap使用
//        if (exception instanceof ExcelDataConvertException) {
//            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
//            log.error("第{}行，第{}列解析异常", excelDataConvertException.getLocalizedMessage());
//        }
    }
}