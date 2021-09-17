package cn.krl.visiteducationbackend.controller;

import cn.krl.visiteducationbackend.dto.AdminDTO;
import cn.krl.visiteducationbackend.dto.RecordDTO;
import cn.krl.visiteducationbackend.entity.Record;
import cn.krl.visiteducationbackend.response.ResponseWrapper;
import cn.krl.visiteducationbackend.service.IAdminService;
import cn.krl.visiteducationbackend.service.IRecordService;
import cn.krl.visiteducationbackend.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@Api(tags = "管理者的api")
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IRecordService recordService;

    /**
     * 删除一条记录
     * @param recordDTO 被删除的记录
     * @return
     */
    @DeleteMapping("/")
    @ApiOperation("删除一条记录")
    public ResponseWrapper delete(@RequestBody @Valid RecordDTO recordDTO){
        ResponseWrapper responseWrapper;
        Record record = new Record();
        BeanUtils.copyProperties(recordDTO,record);
        if(recordService.removeById(record)){
            responseWrapper=ResponseWrapper.markSuccess();
        }else{
            responseWrapper=ResponseWrapper.markParamError();
        }
        return responseWrapper;
    }

    /**
     * 更新一条记录
     * @param recordDTO 被更新的记录
     * @return
     */
    @PutMapping("/")
    @ApiOperation("更新一条记录")
    public ResponseWrapper update(@RequestBody @Valid RecordDTO recordDTO){
        ResponseWrapper responseWrapper;
        Record record = new Record();
        BeanUtils.copyProperties(recordDTO,record);
        record.setGmtModified(System.currentTimeMillis());
        if(recordService.updateById(record)){
            responseWrapper=ResponseWrapper.markSuccess();
        }else {
            responseWrapper=ResponseWrapper.markParamError();
        }
        return responseWrapper;
    }

    /**
     * 增加一条记录
     * @param recordDTO 被增加的记录
     * @return
     */
    @PostMapping("/")
    @ApiOperation("增加一条记录")
    public ResponseWrapper post(@RequestBody @Valid RecordDTO recordDTO){
        ResponseWrapper responseWrapper;
        if(recordService.exist(recordDTO)){
            responseWrapper=ResponseWrapper.markDataExisted();
        }else {
            Record record = new Record();
            BeanUtils.copyProperties(recordDTO,record);
            record.setGmtModified(System.currentTimeMillis());
            record.setGmtCreate(System.currentTimeMillis());
            responseWrapper=ResponseWrapper.markSuccess();
        }
        return responseWrapper;
    }

    /**
     * 通过Excel批量导入record
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/excel/")
    @ApiOperation("excel批量导入记录")
    @Transactional
    public ResponseWrapper postByExcel(@RequestPart("file") MultipartFile file) throws IOException {
        ResponseWrapper responseWrapper;
        List<RecordDTO> records = ExcelUtil.importExcel(file,RecordDTO.class);

        //System.out.println(records);
        try {
            for(RecordDTO recordDTO:records){
                Record record = new Record();
                BeanUtils.copyProperties(recordDTO,record);
                record.setGmtCreate(System.currentTimeMillis());
                record.setGmtModified(System.currentTimeMillis());
                recordService.save(record);
            }
            responseWrapper=ResponseWrapper.markSuccess();
        }catch (Exception e){
            e.printStackTrace();
            responseWrapper=ResponseWrapper.markError();
        }
        return responseWrapper;

    }

    /**
     * 管理员注册
     * @param adminDTO
     * @return
     */
    @PostMapping("/resister")
    @ApiOperation("管理员注册")
    public ResponseWrapper register(@RequestBody AdminDTO adminDTO){
        ResponseWrapper responseWrapper;
        try {
            String name = adminDTO.getName();
            String password = adminDTO.getPassword();
            adminService.register(name,password);
            responseWrapper=ResponseWrapper.markSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper=ResponseWrapper.markError();
        }
        return responseWrapper;
    }

    /**
     * 账号退出
     * @return
     */
    @GetMapping("/logout")
    @ApiOperation("管理员退出")
    public  ResponseWrapper logout(){
        ResponseWrapper responseWrapper;
        Subject subject = null;
        try {
            subject = SecurityUtils.getSubject();
            subject.logout();
            responseWrapper=ResponseWrapper.markSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper=ResponseWrapper.markError();
        }
        return  responseWrapper;

    }


}
