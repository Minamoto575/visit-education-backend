package cn.krl.visiteducationbackend.controller;

import cn.krl.visiteducationbackend.entity.Record;
import cn.krl.visiteducationbackend.service.Impl.AdminServiceImpl;
import cn.krl.visiteducationbackend.service.Impl.RecordServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("管理者的api")
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private RecordServiceImpl recordService;

    @ApiOperation("管理员登录")
    public int login(){
        return 0;
    }

    /**
     * 删除一条记录
     * @param record 被删除的记录
     * @return
     */
    @DeleteMapping("/")
    @ApiOperation("删除一条记录")
    public boolean delete(@RequestBody Record record){

        return recordService.removeById(record);
    }

    /**
     * 更新一条记录
     * @param record 被更新的记录
     * @return
     */
    @PutMapping("/")
    @ApiOperation("更新一条记录")
    private boolean update(@RequestBody Record record){
        return recordService.updateById(record);
    }

    /**
     * 增加一条记录
     * @param record 被增加的记录
     * @return
     */
    @PostMapping("/")
    @ApiOperation("增加一条记录")
    private boolean post(@RequestBody Record record){
        System.out.println(record);
        return recordService.save(record);
    }
}
