package cn.krl.visiteducationbackend.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.krl.visiteducationbackend.common.enums.AdminType;
import cn.krl.visiteducationbackend.common.response.ResponseWrapper;
import cn.krl.visiteducationbackend.model.dto.AdminDTO;
import cn.krl.visiteducationbackend.model.dto.AdminQueryDTO;
import cn.krl.visiteducationbackend.model.dto.ChangePasswrodDTO;
import cn.krl.visiteducationbackend.model.vo.Admin;
import cn.krl.visiteducationbackend.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kuang
 * @description 管理员控制器
 * @data 2021/10/24
 */
@RestController
@Api(tags = "管理者的api")
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final String SUPER = "super";
    private final String COMMON = "common";
    @Autowired
    private IAdminService adminService;

    /**
     * 管理员登录
     *
     * @param adminDTO 管理员传输对象，包括用户名和密码
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("管理员登录")
    @ResponseBody
    public ResponseWrapper adminLogin(@RequestBody AdminDTO adminDTO) {
        ResponseWrapper responseWrapper;
        String name = adminDTO.getName();
        String password = adminDTO.getPassword();
        Admin admin = adminService.getByName(name);
        int id = admin.getId();
        if (adminService.checkPassword(id, password)) {
            StpUtil.login(id);
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("id", admin.getId());
            responseWrapper.setExtra("name", name);
            responseWrapper.setExtra("type", admin.getType());
            responseWrapper.setExtra("token", StpUtil.getTokenValue());
        } else {
            log.error("密码错误");
            responseWrapper = ResponseWrapper.markPasswordError();
        }
        return responseWrapper;
    }


    /**
     * 管理员注册 只对超级管理员开发 只能注册普通管理员
     *
     * @param adminDTO 登录注册传输对象，包括用户名和密码
     * @return
     */
    @SaCheckRole(value = SUPER)
    @PostMapping("/register")
    @ApiOperation("管理员注册")
    public ResponseWrapper register(
        @RequestBody AdminDTO adminDTO) {
        ResponseWrapper responseWrapper;
        // 用户名已被占用
        if (adminService.exist(adminDTO.getName())) {
            responseWrapper = ResponseWrapper.markAdminExist();
            return responseWrapper;
        }
        try {
            String name = adminDTO.getName();
            String password = adminDTO.getPassword();
            adminService.register(name, password);
            responseWrapper = ResponseWrapper.markSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return responseWrapper;
    }

    /**
     * 账号退出
     *
     * @return
     */
    @SaCheckRole(
        value = {SUPER, COMMON},
        mode = SaMode.OR)
    @GetMapping("/logout")
    @ApiOperation("管理员退出")
    public ResponseWrapper logout() {
        ResponseWrapper responseWrapper;
        try {
            StpUtil.logout();
            responseWrapper = ResponseWrapper.markSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return responseWrapper;
    }

    /**
     * 修改密码
     *
     * @param changePasswrodDTO 修改密码传输对象
     * @return
     */
    @SaCheckRole(
        value = {SUPER, COMMON},
        mode = SaMode.OR)
    @PostMapping("/changePassword")
    @ApiOperation("修改密码")
    public ResponseWrapper changePassword(
        @RequestBody ChangePasswrodDTO changePasswrodDTO) {

        ResponseWrapper responseWrapper;
        int id = Integer.parseInt(StpUtil.getLoginId().toString());
        String type = adminService.getById(id).getType();
        String pwd = changePasswrodDTO.getOldPassword();

        int targetId = changePasswrodDTO.getId();
        boolean isOK;
        if (AdminType.SUPER_ADMIN.getType().equals(type)) {
            // 超级管理员：普通管理员直接修改  其他超管不能修改  自己验证后修改
            isOK =
                !adminService.isSuper(targetId)
                    || (id == targetId && adminService.checkPassword(id, pwd));
        } else {
            // 普通管理员：自己验证后修改
            isOK = adminService.checkPassword(id, pwd) && (id == targetId);
        }
        if (isOK) {
            try {
                adminService.changePassword(changePasswrodDTO);
                responseWrapper = ResponseWrapper.markSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                responseWrapper = ResponseWrapper.markError();
            }
        } else {
            responseWrapper = ResponseWrapper.markPasswordError();
        }
        return responseWrapper;
    }

    /**
     * 删除管理员
     *
     * @param deleteId
     * @return
     */
    @SaCheckRole(value = SUPER)
    @PostMapping("/delete")
    @ApiOperation("删除管理员")
    public ResponseWrapper delete(
        @RequestParam Integer deleteId) {
        ResponseWrapper responseWrapper;
        int id = Integer.parseInt(StpUtil.getLoginId().toString());
        // 不能删除自己  超级管理员不能删除
        if (id == deleteId || adminService.isSuper(deleteId)) {
            responseWrapper = ResponseWrapper.markApiNotPermission();
            return responseWrapper;
        }
        adminService.removeById(deleteId);
        responseWrapper = ResponseWrapper.markSuccess();
        return responseWrapper;
    }

    /**
     * 获取所有管理员列表(不包含salt和password)
     *
     * @return
     */
    @SaCheckRole(
        value = {SUPER, COMMON},
        mode = SaMode.OR)
    @PostMapping("/search/all")
    @ApiOperation("列出所有管理员")
    public ResponseWrapper listAll(@RequestBody AdminQueryDTO queryDTO) {
        ResponseWrapper responseWrapper;
        try {
            List<Admin> admins = adminService.listAll(queryDTO);
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("admins", admins);
            responseWrapper.setExtra("total", adminService.countAll());
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }
        return responseWrapper;
    }

    /**
     * 测试管理员用户名是否被使用过
     *
     * @param name 用户名
     * @return
     */
    @GetMapping("/testName")
    @ApiOperation("测试管理员用户名是否被使用过")
    public ResponseWrapper testName(@RequestParam String name) {
        ResponseWrapper responseWrapper;
        if (adminService.exist(name)) {
            responseWrapper = ResponseWrapper.markDefault(200, "used");
        } else {
            responseWrapper = ResponseWrapper.markDefault(200, "notused");
        }
        return responseWrapper;
    }
}
