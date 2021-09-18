package cn.krl.visiteducationbackend.service;

import cn.krl.visiteducationbackend.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IAdminService extends IService<Admin> {
    /**
     * 根据用户名获取管理员
     * @param name 用户名
     * @return
     */
    List<Admin> getByName(String name);

    /**
     * 管理员注册
     * @param name
     * @param password
     * @return
     */
    boolean register(String name, String password);
}
