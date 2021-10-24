package cn.krl.visiteducationbackend.service;

import cn.krl.visiteducationbackend.dto.AdminQueryDTO;
import cn.krl.visiteducationbackend.dto.ChangePasswrodDTO;
import cn.krl.visiteducationbackend.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description 管理员服务层接口
 * @author kuang
 * @data 2021/10/24
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 根据用户名获取管理员
     *
     * @param name 用户名
     * @return
     */
    boolean exist(String name);

    /**
     * 管理员注册
     *
     * @param name
     * @param password
     * @return
     */
    boolean register(String name, String password);

    /**
     * 根据用户名获取管理员
     *
     * @param name
     * @return
     */
    Admin getByName(String name);

    /**
     * 校验密码是否正确
     *
     * @param changePasswrodDTO
     * @return
     */
    boolean testPassword(ChangePasswrodDTO changePasswrodDTO);

    /**
     * 修改密码
     *
     * @param changePasswrodDTO
     * @return
     */
    boolean changePassword(ChangePasswrodDTO changePasswrodDTO);

    /**
     * 列出所有管理员（不包含密码和salt）
     *
     * @return
     */
    List<Admin> listAll(AdminQueryDTO queryDTO);

    /**
     * 列出所有管理员数量
     *
     * @return
     */
    int countAll();

    /**
     * 是否是超级用户
     *
     * @param id
     * @return
     */
    boolean isSuper(int id);
}
