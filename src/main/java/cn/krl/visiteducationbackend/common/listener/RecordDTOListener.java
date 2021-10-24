package cn.krl.visiteducationbackend.common.listener;

import cn.krl.visiteducationbackend.common.utils.ExcelCheckUtil;
import cn.krl.visiteducationbackend.dao.ExcelImportDAO;
import cn.krl.visiteducationbackend.dto.RecordDTO;
import cn.krl.visiteducationbackend.entity.Record;
import cn.krl.visiteducationbackend.service.IRecordService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @description excel读取的监听器 发生excel读则触发 不能被spring管理,读取excel则触发
 * @author kuang
 * @data 2021/10/24
 */
public class RecordDTOListener extends AnalysisEventListener<RecordDTO> {

    /** 每100条存储数据库，然后清空list 服务器内存小 BATCH_COUNT不能过大 */
    private static final int BATCH_COUNT = 100;

    /** list缓冲区 */
    List<RecordDTO> list = new ArrayList<RecordDTO>();

    /** 不能使用@autowired */
    private IRecordService recordService;

    private ExcelImportDAO excelImportDAO;

    //    public RecordDTOListener(){
    //        recordService = new RecordServiceImpl();
    //        excelErrorDAO = new ExcelErrorDAO();
    //    }

    /**
     * 带参构造导入spring注入的service，可以使用自定义的方法
     *
     * @param recordService
     */
    public RecordDTOListener(IRecordService recordService, ExcelImportDAO excelImportDAO) {
        this.recordService = recordService;
        this.excelImportDAO = excelImportDAO;
    }

    /**
     * 每次解析到一条数据都会调用
     *
     * @param recordDTO
     * @param analysisContext
     */
    @Override
    public void invoke(RecordDTO recordDTO, AnalysisContext analysisContext) {
        // 检查该记录
        if (excelImportDAO.doCheck()) {
            recordDTO = ExcelCheckUtil.check(recordDTO);
        }
        // 已存在数据库则不添加
        if (!recordService.exist(recordDTO)) {
            list.add(recordDTO);
        }
        // 缓存装满了做一次数据库写
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }
    /**
     * 所有数据都解析完了
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    /** 将读取的record存入数据库 */
    public void saveData() {
        Collection<Record> records = new ArrayList<Record>();
        for (RecordDTO r : list) {
            Record record = new Record();
            BeanUtils.copyProperties(r, record);
            record.setId(null);
            record.setGmtCreate(System.currentTimeMillis());
            record.setGmtModified(System.currentTimeMillis());
            records.add(record);
        }
        recordService.saveBatch(records);
    }

    /**
     * excel处理中的异常捕获 不抛出异常则继续解析
     *
     * @param exception
     * @param context
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        System.out.println(exception.getMessage());
        // excel解析异常
        if (exception instanceof ExcelAnalysisException) {
            ExcelAnalysisException e = (ExcelAnalysisException) exception;
            String sheetName = context.readSheetHolder().getSheetName();
            int rowIndex = context.readRowHolder().getRowIndex();
            String error = sheetName + ",第" + rowIndex + "条记录:" + e.getMessage();
            excelImportDAO.addError(error);
        }
    }
}
