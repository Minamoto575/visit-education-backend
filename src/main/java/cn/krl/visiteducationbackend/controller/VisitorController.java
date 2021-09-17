package cn.krl.visiteducationbackend.controller;

import cn.krl.visiteducationbackend.dto.RecordQueryDTO;
import cn.krl.visiteducationbackend.entity.Record;
import cn.krl.visiteducationbackend.response.ResponseWrapper;
import cn.krl.visiteducationbackend.service.IRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "游客的api")
@RequestMapping("/visitor")
@Slf4j
public class VisitorController {

    @Autowired
    private IRecordService recordService;

    /**
     * 根据请求dto查询
     * @param recordQueryDTO 请求dto 包含项目、学校、学科名称
     * @return
     */
    @GetMapping("/search")
    @ApiOperation("根据项目、学校、学科名称查询")
    public ResponseWrapper listRecordsByDTO(@RequestBody RecordQueryDTO recordQueryDTO){
        ResponseWrapper responseWrapper;
        try {
            List<Record> records = recordService.listRecordsByDTO(recordQueryDTO);
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
