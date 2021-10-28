package cn.krl.visiteducationbackend.controller;

import cn.krl.visiteducationbackend.common.annotation.PassToken;
import cn.krl.visiteducationbackend.common.listener.ExcelReaderListener;
import cn.krl.visiteducationbackend.common.response.ResponseWrapper;
import cn.krl.visiteducationbackend.model.dao.ExcelImportDAO;
import cn.krl.visiteducationbackend.model.dto.DeleteDTO;
import cn.krl.visiteducationbackend.model.dto.ExcelErrorDTO;
import cn.krl.visiteducationbackend.model.dto.RecordDTO;
import cn.krl.visiteducationbackend.model.dto.RecordQueryDTO;
import cn.krl.visiteducationbackend.model.vo.Record;
import cn.krl.visiteducationbackend.service.IRecordService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @description 记录控制器，token的产生与验证由jwt控制，放在参数中是为了用swagger测试，控制器中不对token做校验
 * @author kuang
 * @data 2021/10/24
 */
@RestController
@Api(tags = "记录的api")
@RequestMapping("/record")
@Slf4j
public class RecordController {

    @Autowired private IRecordService recordService;

    @Autowired private ExcelImportDAO excelImportDAO;

    /**
     * 删除一条记录
     *
     * @param id 删除的id
     * @param token
     * @return
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除一条记录")
    public ResponseWrapper delete(@RequestParam Integer id, @RequestHeader("token") String token) {
        ResponseWrapper responseWrapper;
        if (recordService.removeById(id)) {
            responseWrapper = ResponseWrapper.markSuccess();
            log.info("删除记录：Id=" + id);
        } else {
            responseWrapper = ResponseWrapper.markParamError();
        }
        return responseWrapper;
    }

    /**
     * 更新一条记录
     *
     * @param recordDTO 记录传输对象
     * @param token
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("更新一条记录")
    public ResponseWrapper update(
            @RequestBody @Valid RecordDTO recordDTO, @RequestHeader("token") String token) {
        ResponseWrapper responseWrapper;
        Record record = new Record();
        BeanUtils.copyProperties(recordDTO, record);
        record.setGmtModified(System.currentTimeMillis());
        if (recordService.updateById(record)) {
            responseWrapper = ResponseWrapper.markSuccess();
            log.info("更新记录：" + recordDTO);
        } else {
            responseWrapper = ResponseWrapper.markParamError();
        }
        return responseWrapper;
    }

    /**
     * 增加一条记录
     *
     * @param recordDTO 被增加的记录
     * @param token
     * @return
     */
    @PostMapping("/post")
    @ApiOperation("增加一条记录")
    public ResponseWrapper post(
            @RequestBody @Valid RecordDTO recordDTO, @RequestHeader("token") String token) {
        ResponseWrapper responseWrapper;
        if (recordService.exist(recordDTO)) {
            responseWrapper = ResponseWrapper.markDataExisted();
            log.info("新增记录" + recordDTO);
        } else {
            int id = recordService.saveAndReturnId(recordDTO);
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("id", id);
        }
        return responseWrapper;
    }

    /**
     * 通过Excel批量导入记录
     *
     * @param multipartFile 输入的excel文件
     * @param token
     * @return
     */
    @PostMapping("/upload/excel")
    @ApiOperation("excel批量导入记录")
    public ResponseWrapper postByExcel(
            @RequestPart("file") MultipartFile multipartFile,
            @RequestHeader("token") String token,
            @RequestParam("doCheck") boolean doCheck) {
        ResponseWrapper responseWrapper;
        excelImportDAO.setDoCheck(doCheck);
        excelImportDAO.clearErrorList();
        try {
            log.info(doCheck == true ? "开始导入excel，并检查" : "开始导入excel，不检查");
            List<ReadSheet> readSheetList =
                    EasyExcel.read(multipartFile.getInputStream())
                            .build()
                            .excelExecutor()
                            .sheetList();
            // 读每个sheet
            for (ReadSheet readSheet : readSheetList) {
                // 对excel进行读取，在listener.RecordDTOLister被监听
                EasyExcel.read(
                                multipartFile.getInputStream(),
                                RecordDTO.class,
                                new ExcelReaderListener(recordService, excelImportDAO))
                        .sheet(readSheet.getSheetName())
                        .doRead();
            }
            responseWrapper = ResponseWrapper.markSuccess();
        } catch (Exception e) {
            responseWrapper = ResponseWrapper.markExcelOtherError();
            // 异常源码中被封装了一次 所以取Cause
            log.error("excel导入系统错误：" + e.getCause().getMessage());
        }
        List<ExcelErrorDTO> errorList = excelImportDAO.getErrorList();
        if (!errorList.isEmpty()) {
            responseWrapper = ResponseWrapper.markExcelCustomError();
            responseWrapper.setExtra("errors", errorList);
            log.error("excel导入自定义错误：" + JSON.toJSONString(errorList));
        }
        return responseWrapper;
    }

    /**
     * 获取数据库中所有项目的列表
     *
     * @return
     */
    @GetMapping("/search/project")
    @ApiOperation("获取所有项目的列表")
    @PassToken
    public ResponseWrapper listProject() {
        ResponseWrapper responseWrapper;
        try {
            List<String> projects = recordService.listProjects();
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("projects", projects);
            responseWrapper.setExtra("total", projects.size());
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return responseWrapper;
    }

    /**
     * 根据项目名称获取对应的学校列表 GET方法URL中文会乱码，这里使用POST
     *
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/search/school")
    @ApiOperation("根据项目获取学校列表")
    @PassToken
    public ResponseWrapper listSchoolByProject(@RequestBody RecordQueryDTO queryDTO) {
        String project = queryDTO.getProjectName();
        ResponseWrapper responseWrapper;
        try {
            List<String> schools = recordService.listSchoolsByProject(project);
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("schools", schools);
            responseWrapper.setExtra("total", schools.size());
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return responseWrapper;
    }

    /**
     * 根据项目名称和学校名称获取对应的学科列表 GET方法URL中文会乱码，这里使用POST
     *
     * @param queryDTO
     * @return
     */
    @PostMapping("/search/subject")
    @ApiOperation("根据项目与学校获取学科列表")
    @PassToken
    public ResponseWrapper listSchoolByProjectAndSchool(@RequestBody RecordQueryDTO queryDTO) {
        String project = queryDTO.getProjectName();
        String school = queryDTO.getSchoolName();
        ResponseWrapper responseWrapper;
        try {
            List<String> subjects = recordService.listSubjectByProjectAndSchool(project, school);
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("subjects", subjects);
            responseWrapper.setExtra("total", subjects.size());
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return responseWrapper;
    }

    /**
     * 根据请求dto组合查询记录
     *
     * @param queryDTO 请求dto 包含页码、页数、项目、学校、学科名称
     * @return
     */
    @PostMapping("/search/combination")
    @ApiOperation("根据项目、学校、学科名称组合查询")
    @PassToken
    public ResponseWrapper listRecordsByCombination(@RequestBody RecordQueryDTO queryDTO) {
        if (queryDTO.getProjectName() == "") {
            log.error("组合查询项目名称不能为空");
            return ResponseWrapper.markDefault(999, "组合查询项目名称不能为空");
        }
        ResponseWrapper responseWrapper;
        try {
            List<Record> records = recordService.listRecordsByCombination(queryDTO);
            int total = recordService.countByCombination(queryDTO);
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("records", records);
            responseWrapper.setExtra("total", total);
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return responseWrapper;
    }

    /**
     * @description 根据项目、学校、学科名称组合批量删除 学校、学科名称可以为空
     * @param deleteDTO:
     * @return: cn.krl.visiteducationbackend.common.response.ResponseWrapper
     * @data 2021/10/27
     */
    @PostMapping("/delete/combination")
    @ApiOperation("根据项目、学校、学科名称组合批量删除")
    @PassToken
    public ResponseWrapper deleteRecordsByCombination(@RequestBody DeleteDTO deleteDTO) {
        ResponseWrapper responseWrapper;
        if (deleteDTO.getProjectName() == "") {
            log.error("批量删除输入的项目名称为空");
            return ResponseWrapper.markDefault(999, "项目名称不能为空");
        }
        try {
            recordService.deleteBatch(deleteDTO);
            responseWrapper = ResponseWrapper.markSuccess();
            log.info("批量删除：" + deleteDTO);
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return responseWrapper;
    }

    /**
     * 根据老师名称进行模糊查询
     *
     * @param queryDTO 老师的模糊名字
     * @return
     */
    @PostMapping("/search/teacher")
    @ApiOperation("根据老师名称模糊查询")
    @PassToken
    public ResponseWrapper listRecordsByTeacherName(@RequestBody RecordQueryDTO queryDTO) {
        ResponseWrapper responseWrapper;
        try {
            List<Record> records = recordService.listRecordsByTeacher(queryDTO);
            int total = recordService.countByTeacher(queryDTO);
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("records", records);
            responseWrapper.setExtra("total", total);
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return responseWrapper;
    }

    /**
     * 获取所有记录
     *
     * @param token
     * @return
     */
    @PostMapping("/search/all")
    @ApiOperation("获取所有记录")
    public ResponseWrapper listAll(
            @RequestBody RecordQueryDTO queryDTO, @RequestHeader("token") String token) {
        ResponseWrapper responseWrapper;
        try {
            List<Record> records = recordService.listAll(queryDTO);
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("records", records);
            responseWrapper.setExtra("total", recordService.countAll());
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return responseWrapper;
    }
}
