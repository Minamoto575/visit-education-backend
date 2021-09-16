package cn.krl.visiteducationbackend.service.Impl;

import cn.krl.visiteducationbackend.entity.Admin;
import cn.krl.visiteducationbackend.mapper.AdminMapper;
import cn.krl.visiteducationbackend.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;


}
