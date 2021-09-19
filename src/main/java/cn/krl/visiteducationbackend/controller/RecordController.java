package cn.krl.visiteducationbackend.controller;

import cn.krl.visiteducationbackend.dto.RecordDTO;
import cn.krl.visiteducationbackend.dto.RecordQueryDTO;
import cn.krl.visiteducationbackend.entity.Record;
import cn.krl.visiteducationbackend.listener.RecordDTOListener;
import cn.krl.visiteducationbackend.response.ResponseWrapper;
import cn.krl.visiteducationbackend.service.IRecordService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@Api(tags = "记录的api")
@RequestMapping("/record")
@Slf4j
public class RecordController {

    @Autowired
    private IRecordService recordService;

    /**
     * 删除一条记录
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除一条记录")
    public ResponseWrapper delete(@RequestParam Integer id){
        ResponseWrapper responseWrapper;
        if(recordService.removeById(id)){
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
    @PutMapping("/update")
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
    @PostMapping("/post")
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
            recordService.save(record);
            responseWrapper=ResponseWrapper.markSuccess();
    }
        return responseWrapper;
    }

    /**
     * 通过Excel批量导入记录
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/upload/excel")
    @ApiOperation("excel批量导入记录")
    public ResponseWrapper postByExcel(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        ResponseWrapper responseWrapper;
        try {
            EasyExcel.read(multipartFile.getInputStream(),RecordDTO.class,new RecordDTOListener(recordService)).sheet().doRead();
            responseWrapper=ResponseWrapper.markSuccess();
        } catch (IOException e) {
            responseWrapper=ResponseWrapper.markError();
            e.printStackTrace();
        }
        return responseWrapper;
    }

    @GetMapping("/search/project")
    @ApiOperation("获取所有项目的列表")
    public ResponseWrapper listProject(){
        ResponseWrapper responseWrapper;
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.select("distinct projectName");
            List<String> projects = recordService.listObjs(queryWrapper);
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("projects",projects);
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return  responseWrapper;
    }

    @GetMapping("/search/school/{project}")
    @ApiOperation("根据项目获取学校列表")
    public ResponseWrapper listSchoolByProject(@PathVariable String project){
        ResponseWrapper responseWrapper;
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.select("distinct schoolName");
            queryWrapper.eq("projectName",project);
            List<String> schools = recordService.listObjs(queryWrapper);
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("schools",schools);
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return responseWrapper;
    }

    @GetMapping("/search/subject/{project}&{school}")
    @ApiOperation("根据项目与学校获取学科列表")
    public ResponseWrapper listSchoolByProjectAndSchool(@PathVariable String project,@PathVariable String school){
        ResponseWrapper responseWrapper;
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.select("distinct subjectName");
            queryWrapper.eq("projectName",project);
            queryWrapper.eq("schoolName",school);
            List<String> subjects = recordService.listObjs(queryWrapper);
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("subjects",subjects);
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return responseWrapper;
    }

    /**
     * 根据请求dto查询记录
     * @param recordQueryDTO 请求dto 包含项目、学校、学科名称
     * @return
     */
    @PostMapping("/search/combination")
    @ApiOperation("根据项目、学校、学科名称查询")
    public ResponseWrapper listRecordsByDTO(@RequestBody RecordQueryDTO recordQueryDTO){
        ResponseWrapper responseWrapper;
        try {
            List<Record> records = recordService.listRecordsByDTO(recordQueryDTO);
            System.out.println(records);
            responseWrapper=ResponseWrapper.markSuccess();
            responseWrapper.setExtra("records",records);
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper=ResponseWrapper.markError();
        }
        return responseWrapper;
    }

    /**
     * 根据老师名称进行模糊查询
     * @param name 老师的模糊名字
     * @return
     */
    @GetMapping("/search/{name}")
    @ApiOperation("根据老师名称模糊查询")
    public ResponseWrapper listRecordsByTeacherName(@PathVariable String name){
        ResponseWrapper responseWrapper;
        try {
            List<Record> records= recordService.listRecordsByTeacherName(name);
            responseWrapper=ResponseWrapper.markSuccess();
            responseWrapper.setExtra("records",records);
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper=ResponseWrapper.markError();
        }
        return responseWrapper;
    }

}
