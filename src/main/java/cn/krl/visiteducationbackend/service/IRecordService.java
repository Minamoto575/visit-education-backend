package cn.krl.visiteducationbackend.service;

import cn.krl.visiteducationbackend.model.dto.DeleteDTO;
import cn.krl.visiteducationbackend.model.dto.RecordDTO;
import cn.krl.visiteducationbackend.model.dto.RecordQueryDTO;
import cn.krl.visiteducationbackend.model.vo.Record;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description 记录服务层接口
 * @author kuang
 * @data 2021/10/24
 */
public interface IRecordService extends IService<Record> {

    /**
     * 组合查询(分页查询)
     *
     * @param queryDTO
     * @return
     */
    List<Record> listRecordsByCombination(RecordQueryDTO queryDTO);

    /**
     * 符合组合查询的记录数
     *
     * @param queryDTO
     * @return
     */
    int countByCombination(RecordQueryDTO queryDTO);

    /**
     * 根据教师名称模糊查询(分页查询)
     *
     * @param queryDTO
     * @return
     */
    List<Record> listRecordsByTeacher(RecordQueryDTO queryDTO);

    /**
     * 符合教师查询的记录数
     *
     * @param queryDTO
     * @return
     */
    int countByTeacher(RecordQueryDTO queryDTO);

    /**
     * 列出所有记录(分页查询)
     *
     * @return
     */
    List<Record> listAll(RecordQueryDTO queryDTO);

    /**
     * 计算所有记录数
     *
     * @return
     */
    int countAll();

    /**
     * 检查数据库是否以及存在某条记录
     *
     * @param recordDTO record传输对象
     * @return
     */
    boolean exist(RecordDTO recordDTO);

    /**
     * 获取项目列表
     *
     * @return
     */
    List<String> listProjects();

    /**
     * 根据项目获取学校列表
     *
     * @param project 项目
     * @return
     */
    List<String> listSchoolsByProject(String project);

    /**
     * 根据项目、学校获取学科列表
     *
     * @param project 项目
     * @param school 学科
     * @return
     */
    List<String> listSubjectByProjectAndSchool(String project, String school);

    /**
     * 保存到数据库并返回自增的主键id
     *
     * @param recordDTO
     * @return
     */
    int saveAndReturnId(RecordDTO recordDTO);

    /**
     * @description 组合条件批量删除
     * @param deleteDTO:
     * @return: void
     * @data 2021/10/27
     */
    void deleteBatch(DeleteDTO deleteDTO);
}
