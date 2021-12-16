package cn.krl.visiteducationbackend.service.Impl;

import cn.krl.visiteducationbackend.mapper.NoticeMapper;
import cn.krl.visiteducationbackend.model.vo.Notice;
import cn.krl.visiteducationbackend.service.INoticeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kuang
 * @description 通知服务层实现类
 * @date 2021/12/15  17:02
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public void insert(String content) {
        Notice notice = new Notice();
        notice.setContent(content);
        notice.setGmtCreate(System.currentTimeMillis());
        noticeMapper.insert(notice);
    }

    @Override
    public Notice getLatest() {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 1");
        return noticeMapper.selectOne(queryWrapper);
    }
}
