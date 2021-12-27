package cn.krl.visiteducationbackend.service.Impl;

import cn.krl.visiteducationbackend.common.enums.AdminType;
import cn.krl.visiteducationbackend.common.utils.SaltUtil;
import cn.krl.visiteducationbackend.mapper.AdminMapper;
import cn.krl.visiteducationbackend.model.dto.AdminQueryDTO;
import cn.krl.visiteducationbackend.model.dto.ChangePasswrodDTO;
import cn.krl.visiteducationbackend.model.vo.Admin;
import cn.krl.visiteducationbackend.service.IAdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kuang
 * @description 管理员服务层实现类
 * @data 2021/10/24
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public boolean exist(String name) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return !adminMapper.selectList(queryWrapper).isEmpty();
    }

    @Override
    public boolean register(String name, String password) {
        try {
            String salt = SaltUtil.getSalt(8);
            String hashedPwd = hashPwd(password, salt);
            Admin admin = new Admin();
            admin.setName(name);
            admin.setPassword(hashedPwd);
            admin.setSalt(salt);
            admin.setType("common");
            admin.setGmtModified(System.currentTimeMillis());
            admin.setGmtCreate(System.currentTimeMillis());
            adminMapper.insert(admin);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Admin getByName(String name) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return adminMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean checkPassword(int id, String pwd) {
        // 数据库存储的密码（经过salt hash散列）
        Admin admin = adminMapper.selectById(id);
        String password1 = admin.getPassword();
        // 前端发送的密码
        String salt = admin.getSalt();
        String password2 = hashPwd(pwd, salt);
        return password1.equals(password2);
    }

    @Override
    public boolean changePassword(ChangePasswrodDTO changePasswrodDTO) {
        try {
            // 获取需要更改的admin
            int id = changePasswrodDTO.getId();
            Admin admin = adminMapper.selectById(id);

            // 更新密码、修改时间
            String salt = admin.getSalt();
            String newPassword = changePasswrodDTO.getNewPassword();
            String hashedPwd = hashPwd(newPassword, salt);
            admin.setPassword(hashedPwd);
            admin.setGmtModified(System.currentTimeMillis());
            adminMapper.updateById(admin);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Admin> listAll(AdminQueryDTO queryDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page page = new Page();
        page.setSize(queryDTO.getLimit());
        page.setCurrent(queryDTO.getPage());
        queryWrapper.select("id", "name", "type", "gmtModified", "gmtCreate");
        return adminMapper.selectPage(page, queryWrapper).getRecords();
    }

    @Override
    public int countAll() {
        long count = adminMapper.selectCount(new QueryWrapper<>());
        return (int) count;
    }

    @Override
    public boolean isSuper(int id) {
        Admin admin = adminMapper.selectById(id);
        return AdminType.SUPER_ADMIN.getType().equals(admin.getType());
    }

    @Override
    public List<String> getRoleList(int id) {
        Admin admin = adminMapper.selectById(id);
        List<String> roles = new ArrayList<>();
        roles.add(admin.getType());
        return roles;
    }

    @Override
    public String hashPwd(String pwd, String salt) {
        String mixedStr = pwd + salt;
        return DigestUtils.md5DigestAsHex(mixedStr.getBytes());
    }
}
