package cn.krl.visiteducationbackend.service.Impl;

import cn.krl.visiteducationbackend.entity.Admin;
import cn.krl.visiteducationbackend.mapper.AdminMapper;
import cn.krl.visiteducationbackend.service.IAdminService;
import cn.krl.visiteducationbackend.utils.SaltUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
