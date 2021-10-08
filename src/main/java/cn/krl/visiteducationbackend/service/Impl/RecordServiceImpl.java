package cn.krl.visiteducationbackend.service.Impl;

import cn.krl.visiteducationbackend.dto.RecordQueryDTO;
import cn.krl.visiteducationbackend.dto.RecordDTO;
import cn.krl.visiteducationbackend.entity.Record;
import cn.krl.visiteducationbackend.mapper.RecordMapper;
import cn.krl.visiteducationbackend.service.IRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper,Record> implements IRecordService {

    @Autowired
    private RecordMapper recordMapper;


    @Override
    public List<Record> listRecordsByCombination(RecordQueryDTO recordQueryDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page page  = new Page();
        queryWrapper.eq("projectName",recordQueryDTO.getProjectName());
        queryWrapper.eq("schoolName",recordQueryDTO.getSchoolName());
        queryWrapper.eq("subjectName",recordQueryDTO.getSubjectName());
        page.setCurrent(recordQueryDTO.getPage());
        page.setSize(recordQueryDTO.getLimit());
        List<Record> records=recordMapper.selectPage(page,queryWrapper).getRecords();
        return records;
    }

    @Override
    public int countByCombination(RecordQueryDTO recordQueryDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("projectName",recordQueryDTO.getProjectName());
        queryWrapper.eq("schoolName",recordQueryDTO.getSchoolName());
        queryWrapper.eq("subjectName",recordQueryDTO.getSubjectName());
        return recordMapper.selectList(queryWrapper).size();
    }


    @Override
    public List<Record> listRecordsByTeacher(RecordQueryDTO queryDTO) {
        //模糊查询
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("teacherName",queryDTO.getTeacherName());
        Page page = new Page();
        page.setCurrent(queryDTO.getPage());
        page.setSize(queryDTO.getLimit());
        List<Record> records = recordMapper.selectPage(page,queryWrapper).getRecords();
        return records;
    }

    @Override
    public int countByTeacher(RecordQueryDTO queryDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("teacherName",queryDTO.getTeacherName());
        return recordMapper.selectList(queryWrapper).size();
    }

    @Override
    public List<Record> listAll(RecordQueryDTO queryDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page page = new Page();
        page.setCurrent(queryDTO.getPage());
        page.setSize(queryDTO.getLimit());
        List<Record> records = recordMapper.selectPage(page,queryWrapper).getRecords();
        return records;
    }

    @Override
    public int countAll() {
        long count = recordMapper.selectCount(new QueryWrapper());
        return (int)count;
    }


    @Override
    public boolean exist(RecordDTO recordDTO) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("projectName",recordDTO.getProjectName());
        queryWrapper.eq("schoolName",recordDTO.getSchoolName());
        queryWrapper.eq("subjectName",recordDTO.getSubjectName());
        queryWrapper.eq("taskName",recordDTO.getTaskName());
        queryWrapper.eq("subjectCode",recordDTO.getSubjectCode());
        queryWrapper.eq("teacherName",recordDTO.getTeacherName());

        List<Record> records = recordMapper.selectList(queryWrapper);

        if(records.isEmpty()){
            return false;
        }
        return true;
    }


    @Override
    public List<String> listProjects() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("distinct projectName");
        return recordMapper.selectObjs(queryWrapper);
    }


    @Override
    public List<String> listSchoolsByProject(String project) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("distinct schoolName");
        queryWrapper.eq("projectName",project);
        return recordMapper.selectObjs(queryWrapper);
    }


    @Override
    public List<String> listSubjectByProjectAndSchool(String project, String school) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("distinct subjectName");
        queryWrapper.eq("projectName",project);
        queryWrapper.eq("schoolName",school);
        return recordMapper.selectObjs(queryWrapper);
    }

    @Override
    public int saveAndReturnId(RecordDTO recordDTO) {
        Record record = new Record();
        BeanUtils.copyProperties(recordDTO,record);
        record.setGmtModified(System.currentTimeMillis());
        record.setGmtCreate(System.currentTimeMillis());
        recordMapper.insert(record);
        System.out.println(record.getId());
        return record.getId();
    }


}
