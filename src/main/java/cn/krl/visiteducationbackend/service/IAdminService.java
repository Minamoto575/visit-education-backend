package cn.krl.visiteducationbackend.service;

import cn.krl.visiteducationbackend.model.dto.AdminQueryDTO;
import cn.krl.visiteducationbackend.model.dto.ChangePasswrodDTO;
import cn.krl.visiteducationbackend.model.vo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author kuang
 * @description 管理员服务层接口
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
     * @param id
     * @param pwd
     * @description 检查密码
     * @author kuang
     * @date 2021/12/27
     */
    boolean checkPassword(int id, String pwd);

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

    /**
     * @param id
     * @description
     * @author kuang
     * @date 2021/12/27
     */
    List<String> getRoleList(int id);

    /**
     * @description hash散列密码
     * @author kuang
     * @date 2021/12/27
     */
    String hashPwd(String pwd, String salt);
}
