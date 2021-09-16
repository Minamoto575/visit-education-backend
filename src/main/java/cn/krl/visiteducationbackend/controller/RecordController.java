package cn.krl.visiteducationbackend.controller;

import cn.krl.visiteducationbackend.dao.RecordQueryDTO;
import cn.krl.visiteducationbackend.entity.Record;
import cn.krl.visiteducationbackend.service.IRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("记录的api")
@RequestMapping("/record")
@Slf4j
public class RecordController {

    @Autowired
    private IRecordService recordService;

    /**
     * 根据请求dto查询
     * @param recordQueryDTO 请求dto 包含项目、学校、学科名称
     * @return
     */
    @GetMapping("/")
    @ApiOperation("根据项目、学校、学科名称查询")
    public List<Record> listRecordsByDTO(@RequestBody RecordQueryDTO recordQueryDTO){
        List<Record> records = recordService.listRecordsByDTO(recordQueryDTO);
        return records;
    }

    /**
     * 根据老师名称进行模糊查询
     * @param name 老师的模糊名字
     * @return
     */
    @GetMapping("/{name}")
    @ApiOperation("根据老师名称模糊查询")
    public List<Record> listRecordsByTeacherName(@PathVariable String name){
        List<Record> records= recordService.listRecordsByTeacherName(name);
        return records;
    }

}
