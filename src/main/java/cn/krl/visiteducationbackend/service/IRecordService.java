package cn.krl.visiteducationbackend.service;

import cn.krl.visiteducationbackend.dto.RecordDTO;
import cn.krl.visiteducationbackend.dto.RecordQueryDTO;
import cn.krl.visiteducationbackend.entity.Record;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IRecordService extends IService<Record> {

    /**
     * 组合查询
     * @param recordQueryDTO
     * @return
     */
    List<Record> listRecordsByDTO(RecordQueryDTO recordQueryDTO);


    /**
     * 根据教师名称模糊查询
     * @param name
     * @return
     */
    List<Record> listRecordsByTeacherName(String name);

    /**
     * 查询 : 根据state状态查询Record列表，分页显示
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param state 状态
     * @return 分页对象
     */
    IPage<Record> selectRecordPage(Page<Record> page, Integer state);

    boolean exist(RecordDTO recordDTO);

}
