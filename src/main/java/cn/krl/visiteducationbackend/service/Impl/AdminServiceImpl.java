package cn.krl.visiteducationbackend.service.Impl;

import cn.krl.visiteducationbackend.dto.AdminQueryDTO;
import cn.krl.visiteducationbackend.dto.ChangePasswrodDTO;
import cn.krl.visiteducationbackend.entity.Admin;
import cn.krl.visiteducationbackend.common.enums.AdminType;
import cn.krl.visiteducationbackend.mapper.AdminMapper;
import cn.krl.visiteducationbackend.service.IAdminService;
import cn.krl.visiteducationbackend.common.utils.SaltUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 管理员服务层实现类
 * @author kuang
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;


    @Override
    public boolean exist(String name) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",name);
        return !adminMapper.selectList(queryWrapper).isEmpty();
    }


    @Override
    public boolean register(String name, String password) {
        try {
            String salt = SaltUtil.getSalt(8);
            Md5Hash md5Hash =new Md5Hash(password,salt,1024);
            Admin admin = new Admin();
            admin.setName(name);
            admin.setPassword(md5Hash.toHex());
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
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",name);
        return adminMapper.selectOne(queryWrapper);
    }


    @Override
    public boolean testPassword(ChangePasswrodDTO changePasswrodDTO) {

        //数据库存储的密码（经过salt hash散列）
        int id = changePasswrodDTO.getId();
        Admin admin = adminMapper.selectById(id);
        String password1 = admin.getPassword();

        //前端发送的密码
        String oldPassword = changePasswrodDTO.getOldPassword();
        String salt = admin.getSalt();
        Md5Hash md5Hash = new Md5Hash(oldPassword,salt,1024);
        String password2 = md5Hash.toHex();
        System.out.println(password1);
        System.out.println(password2);
        System.out.println(password1.equals(password2));
        return password1.equals(password2);

    }


    @Override
    public boolean changePassword(ChangePasswrodDTO changePasswrodDTO) {
        try {
            //获取需要更改的admin
            int id = changePasswrodDTO.getId();
            Admin admin = adminMapper.selectById(id);

            //更新密码、修改时间
            String salt = admin.getSalt();
            String newPassword = changePasswrodDTO.getNewPassword();
            Md5Hash md5Hash =new Md5Hash(newPassword,salt,1024);
            admin.setPassword(md5Hash.toHex());
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
        queryWrapper.select("id","name","type","gmtModified","gmtCreate");
        return adminMapper.selectPage(page,queryWrapper).getRecords();
    }

    @Override
    public int countAll() {
        long count = adminMapper.selectCount(new QueryWrapper<>());
        return (int)count;
    }

    @Override
    public boolean isSuper(int id) {
        Admin admin = adminMapper.selectById(id);
        return AdminType.SUPER_ADMIN.getType().equals(admin.getType());
    }
}
