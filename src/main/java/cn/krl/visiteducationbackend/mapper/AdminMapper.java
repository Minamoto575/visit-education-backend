package cn.krl.visiteducationbackend.mapper;

import cn.krl.visiteducationbackend.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    /**
     * 根据用户名获取管理员
     * @param name 用户名
     * @return
     */
    List<Admin> getByName(@Param("name") String name);
}
