package cn.krl.visiteducationbackend.service;

import cn.krl.visiteducationbackend.model.vo.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author kuang
 * @description 通知的服务接口类
 * @date 2021/12/15  17:01
 */
public interface INoticeService extends IService<Notice> {
    /**
     * @param content
     * @description 上传一条通知
     * @author kuang
     * @date 2021/12/15
     */
    void insert(String content);

    /**
     * @description 获取最新的通知
     * @author kuang
     * @date 2021/12/15
     */
    Notice getLatest();
}
