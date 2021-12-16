package cn.krl.visiteducationbackend.mapper;


import cn.krl.visiteducationbackend.model.vo.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author kuang
 * @description 通知接口
 * @date 2021/12/15  17:00
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
}
