package cn.krl.visiteducationbackend.listener;


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

//不能被spring管理,读取excel则触发
public class RecordDTOListener extends AnalysisEventListener<RecordDTO>{

    //每2000条存储数据库，然后清空list
    private static final int BATCH_COUNT=2000;
    List<RecordDTO>  list= new ArrayList<RecordDTO>();

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
    public void invoke(RecordDTO recordDTO, AnalysisContext analysisContext) {
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
            record.setGmtCreate(System.currentTimeMillis());
            record.setGmtModified(System.currentTimeMillis());
            records.add(record);
        }
        recordService.saveBatch(records);
    }
}