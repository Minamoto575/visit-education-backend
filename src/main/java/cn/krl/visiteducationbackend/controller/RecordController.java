package cn.krl.visiteducationbackend.controller;

import cn.krl.visiteducationbackend.dto.RecordDTO;
import cn.krl.visiteducationbackend.dto.CombinationQueryDTO;
import cn.krl.visiteducationbackend.dto.TeacherQueryDTO;
import cn.krl.visiteducationbackend.entity.Record;
import cn.krl.visiteducationbackend.listener.RecordDTOListener;
import cn.krl.visiteducationbackend.response.ResponseWrapper;
import cn.krl.visiteducationbackend.service.IRecordService;
import com.alibaba.excel.EasyExcel;
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
     * @param id    记录id
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
     * @param multipartFile 输入的excel文件
     * @return
     * @throws IOException
     */
    @PostMapping("/upload/excel")
    @ApiOperation("excel批量导入记录")
    public ResponseWrapper postByExcel(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        ResponseWrapper responseWrapper;
        try {
            //对excel进行读取，在listern.RecordDTOLister被监听
            EasyExcel.read(multipartFile.getInputStream(),RecordDTO.class,new RecordDTOListener(recordService)).sheet().doRead();
            responseWrapper=ResponseWrapper.markSuccess();
        } catch (IOException e) {
            responseWrapper=ResponseWrapper.markError();
            e.printStackTrace();
        }
        return responseWrapper;
    }


    /**
     * 获取数据库中所有项目的列表
     * @return
     */
    @GetMapping("/search/project")
    @ApiOperation("获取所有项目的列表")
    public ResponseWrapper listProject(){
        ResponseWrapper responseWrapper;
        try {
            List<String> projects = recordService.listProjects();
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("projects",projects);
            responseWrapper.setExtra("total",projects.size());
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return  responseWrapper;
    }


    /**
     * 根据项目名称获取对应的学校列表
     * @param project   项目名称
     * @return
     */
    @GetMapping("/search/school/{project}")
    @ApiOperation("根据项目获取学校列表")
    public ResponseWrapper listSchoolByProject(@PathVariable String project){
        ResponseWrapper responseWrapper;
        try {
            List<String> schools = recordService.listSchoolsByProject(project);
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("schools",schools);
            responseWrapper.setExtra("total",schools.size());
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return responseWrapper;
    }


    /**
     * 根据项目名称和学校名称获取对应的学科列表
     * @param project   项目名称
     * @param school    学科名称
     * @return
     */
    @GetMapping("/search/subject/{project}&{school}")
    @ApiOperation("根据项目与学校获取学科列表")
    public ResponseWrapper listSchoolByProjectAndSchool(@PathVariable String project,@PathVariable String school){
        ResponseWrapper responseWrapper;
        try {
            List<String> subjects = recordService.listSubjectByProjectAndSchool(project,school);
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("subjects",subjects);
            responseWrapper.setExtra("total",subjects.size());
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return responseWrapper;
    }


    /**
     * 根据请求dto组合查询记录
     * @param queryDTO 请求dto 包含页码、页数、项目、学校、学科名称
     * @return
     */
    @PostMapping("/search/combination")
    @ApiOperation("根据项目、学校、学科名称查询")
    public ResponseWrapper listRecordsByDTO(@RequestBody CombinationQueryDTO queryDTO){
        ResponseWrapper responseWrapper;
        try {
            List<Record> records = recordService.listRecordsByCombination(queryDTO);
            responseWrapper=ResponseWrapper.markSuccess();
            responseWrapper.setExtra("records",records);
            responseWrapper.setExtra("total",records.size());
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper=ResponseWrapper.markError();
        }
        return responseWrapper;
    }


    /**
     * 根据老师名称进行模糊查询
     * @param queryDTO 老师的模糊名字
     * @return
     */
    @PostMapping("/search/teacher")
    @ApiOperation("根据老师名称模糊查询")
    public ResponseWrapper listRecordsByTeacherName(@RequestBody TeacherQueryDTO queryDTO){
        ResponseWrapper responseWrapper;
        try {
            List<Record> records= recordService.listRecordsByTeacher(queryDTO);
            responseWrapper=ResponseWrapper.markSuccess();
            responseWrapper.setExtra("records",records);
            responseWrapper.setExtra("total",records.size());
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper=ResponseWrapper.markError();
        }
        return responseWrapper;
    }

    /**
     * 获取所有记录
     * @return
     */
    @GetMapping("/search/all")
    @ApiOperation("获取所有记录")
    public ResponseWrapper listRecordsByTeacherName(){
        ResponseWrapper responseWrapper;
        try {
            List<Record> records= recordService.list();
            responseWrapper=ResponseWrapper.markSuccess();
            responseWrapper.setExtra("records",records);
            responseWrapper.setExtra("total",records.size());
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper=ResponseWrapper.markError();
        }
        return responseWrapper;
    }


}
