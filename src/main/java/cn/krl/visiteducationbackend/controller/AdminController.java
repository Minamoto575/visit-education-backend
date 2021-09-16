package cn.krl.visiteducationbackend.controller;

import ch.qos.logback.core.util.FileUtil;
import cn.krl.visiteducationbackend.dto.RecordDTO;
import cn.krl.visiteducationbackend.entity.Record;
import cn.krl.visiteducationbackend.service.Impl.AdminServiceImpl;
import cn.krl.visiteducationbackend.service.Impl.RecordServiceImpl;
import cn.krl.visiteducationbackend.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@Api(tags = "管理者的api")
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private RecordServiceImpl recordService;


    /**
     * 删除一条记录
     * @param recordDTO 被删除的记录
     * @return
     */
    @DeleteMapping("/")
    @ApiOperation("删除一条记录")
    public boolean delete(@RequestBody @Valid RecordDTO recordDTO){
        Record record = new Record();
        BeanUtils.copyProperties(recordDTO,record);
        return recordService.removeById(record);
    }



    /**
     * 更新一条记录
     * @param recordDTO 被更新的记录
     * @return
     */
    @PutMapping("/")
    @ApiOperation("更新一条记录")
    private boolean update(@RequestBody @Valid RecordDTO recordDTO){
        Record record = new Record();
        BeanUtils.copyProperties(recordDTO,record);
        record.setGmtModified(System.currentTimeMillis());
        return recordService.updateById(record);
    }

    /**
     * 增加一条记录
     * @param recordDTO 被增加的记录
     * @return
     */
    @PostMapping("/")
    @ApiOperation("增加一条记录")
    private boolean post(@RequestBody @Valid RecordDTO recordDTO){
        Record record = new Record();
        BeanUtils.copyProperties(recordDTO,record);
        record.setGmtModified(System.currentTimeMillis());
        record.setGmtCreate(System.currentTimeMillis());
        return recordService.save(record);
    }

    /**
     * 通过Excel批量导入record
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/")
    private boolean postByExcel(@RequestParam("file") MultipartFile file) throws IOException {
        List<RecordDTO> recordDTOS = ExcelUtils.importExcel(file,RecordDTO.class);
        try {
            for(RecordDTO recordDTO:recordDTOS){
                Record record = new Record();
                BeanUtils.copyProperties(record,recordDTO);
                record.setGmtCreate(System.currentTimeMillis());
                record.setGmtModified(System.currentTimeMillis());
                recordService.save(record);
            }
        }catch (Exception e){

        }finally {
            return true;
        }

    }
}
