package cn.krl.visiteducationbackend.mapper;

import cn.krl.visiteducationbackend.model.vo.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * 记录mapper
 * @author kuang
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    /**
     * <p>
     * 查询 : 根据state状态查询Record列表，分页显示
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param state 状态
     * @return 分页对象
     */
    IPage<Record> selectPageVo(Page<?> page, Integer state);

}
