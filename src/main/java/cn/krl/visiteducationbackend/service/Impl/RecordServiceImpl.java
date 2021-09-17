package cn.krl.visiteducationbackend.service.Impl;

import cn.krl.visiteducationbackend.dto.RecordDTO;
import cn.krl.visiteducationbackend.dto.RecordQueryDTO;
import cn.krl.visiteducationbackend.entity.Record;
import cn.krl.visiteducationbackend.mapper.RecordMapper;
import cn.krl.visiteducationbackend.service.IRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper,Record> implements IRecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public List<Record> listRecordsByDTO(RecordQueryDTO recordQueryDTO) {
        String project = recordQueryDTO.getProjectName();
        String school = recordQueryDTO.getSchoolName();
        String subject = recordQueryDTO.getSubjectName();
        List<Record> records=recordMapper.listRecordsByQueryDTO(project,school,subject);
        return records;
    }

    @Override
    public List<Record> listRecordsByTeacherName(String name) {
        //模糊查询
        List<Record> records = recordMapper.selectList(new QueryWrapper<Record>().like("teacher",name));
        return records;
    }

    @Override
    public IPage<Record> selectRecordPage(Page<Record> page, Integer state) {
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
        // page.setOptimizeCountSql(false);
        // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
        // 要点!! 分页返回的对象与传入的对象是同一个
        return recordMapper.selectPageVo(page,state);
    }

    @Override
    public boolean exist(RecordDTO recordDTO) {
        String school = recordDTO.getSchoolName();
        String subject = recordDTO.getSubjectName();
        String code = recordDTO.getSubjectCode();
        String teacher = recordDTO.getTeacherName();
        String task = recordDTO.getTaskName();
        String project = recordDTO.getProjectName();
        List<Record> records = recordMapper.listTheSame(project,school,subject,code,teacher,task);
        if(records==null){
            return false;
        }else {
            return true;
        }
    }
}
