package cn.krl.visiteducationbackend.controller;

import cn.krl.visiteducationbackend.annotation.PassToken;
import cn.krl.visiteducationbackend.dto.AdminDTO;
import cn.krl.visiteducationbackend.dto.ChangePasswrodDTO;
import cn.krl.visiteducationbackend.dto.LoginDTO;
import cn.krl.visiteducationbackend.entity.Admin;
import cn.krl.visiteducationbackend.response.ResponseWrapper;
import cn.krl.visiteducationbackend.service.IAdminService;
import cn.krl.visiteducationbackend.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @param loginDTO 登录信息传输对象，包括用户名和密码
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("管理员登录")
    @ResponseBody
    @PassToken
    public ResponseWrapper adminLogin(@RequestBody LoginDTO loginDTO){
        ResponseWrapper responseWrapper;

        String name = loginDTO.getName();
        String password = loginDTO.getPassword();
        Admin admin = adminService.getByName(name);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(new UsernamePasswordToken(name,password));
            String token = JwtUtil.createToken(Integer.toString(admin.getId()),name,admin.getType());
            responseWrapper=ResponseWrapper.markSuccess();
            responseWrapper.setExtra("id",admin.getId());
            responseWrapper.setExtra("name",name);
            responseWrapper.setExtra("type",admin.getType());
            responseWrapper.setExtra("token",token);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            responseWrapper=ResponseWrapper.markAccountError();
        } catch (IncorrectCredentialsException e){
            e.printStackTrace();
            responseWrapper=ResponseWrapper.markAccountError();
        }
        return responseWrapper;
    }


    /**
     * 管理员注册 只对超级管理员开发 只能注册普通管理员
     * @param adminDTO 登录注册传输对象，包括用户名和密码
     * @param token
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("管理员注册")
    public ResponseWrapper register(@RequestBody AdminDTO adminDTO,
                                    @RequestHeader("token")String token){
        ResponseWrapper responseWrapper;
        //int id = Integer.parseInt(JwtUtil.getAudience(token));
        String type = JwtUtil.getClaimByName(token,"type").asString();
        System.out.println(type);

        //超级管理员才有权限
        if(!SUPER.equals(type)){
            responseWrapper = ResponseWrapper.markApiNotPermission();
            return responseWrapper;
        }

        //用户名已被占用
        if(adminService.exist(adminDTO.getName())){
            responseWrapper = ResponseWrapper.markAdminExist();
            return responseWrapper;
        }

        try {
            String name = adminDTO.getName();
            String password = adminDTO.getPassword();
            adminService.register(name,password);
            responseWrapper=ResponseWrapper.markSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper=ResponseWrapper.markError();
        }

        return responseWrapper;
    }


    /**
     * 账号退出
     * @param token
     * @return
     */
    @GetMapping("/logout")
    @ApiOperation("管理员退出")
    public ResponseWrapper logout(@RequestHeader("token")String token){
        ResponseWrapper responseWrapper;
        Subject subject = null;
        try {
            subject = SecurityUtils.getSubject();
            subject.logout();
            responseWrapper=ResponseWrapper.markSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper=ResponseWrapper.markError();
        }
        return  responseWrapper;
    }


    /**
     * 修改密码
     * @param changePasswrodDTO 修改密码传输对象
     * @return
     */
    @PostMapping("/changePassword")
    @ApiOperation("修改密码")
    public ResponseWrapper changePassword(@RequestBody ChangePasswrodDTO changePasswrodDTO,
                                          @RequestHeader("token") String token){

        ResponseWrapper responseWrapper;
        int id = Integer.parseInt(JwtUtil.getAudience(token));
        String type = JwtUtil.getClaimByName(token,"type").toString();

        //普通管理员 密码验证正确且修改自己的密码
        boolean isOK = adminService.testPassword(changePasswrodDTO)&&(id==changePasswrodDTO.getId());
        //超级管理员直接修改
        if(SUPER.equals(type)||isOK){
            try{
                adminService.changePassword(changePasswrodDTO);
                responseWrapper = ResponseWrapper.markSuccess();
            }catch (Exception e){
                e.printStackTrace();
                responseWrapper = ResponseWrapper.markError();
            }
        }else{
            responseWrapper = ResponseWrapper.markPasswordError();
        }

        return responseWrapper;
    }

    /**
     * 删除管理员
     * @param deleteId
     * @param token
     * @return
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除管理员")
    public ResponseWrapper delete(@RequestParam Integer deleteId,@RequestHeader("token") String token){

        ResponseWrapper responseWrapper;
        int id = Integer.parseInt(JwtUtil.getAudience(token));

        //不能删除自己
        if(id==deleteId){
            responseWrapper = ResponseWrapper.markApiNotPermission();
            return responseWrapper;
        }

        String type = JwtUtil.getClaimByName(token,"type").toString();
        //超级管理员才有权限
        if(!SUPER.equals(type)){
            responseWrapper = ResponseWrapper.markApiNotPermission();
            return responseWrapper;
        }

        try{
            adminService.removeById(deleteId);
            responseWrapper = ResponseWrapper.markSuccess();
        }catch (Exception e){
            e.printStackTrace();
            responseWrapper = ResponseWrapper.markError();
        }

        return responseWrapper;
    }

    /**
     * 获取所有管理员列表(不包含salt和password)
     * @return
     */
    @GetMapping("/listall")
    @ApiOperation("列出所有管理员")
    public ResponseWrapper listall(){
        ResponseWrapper responseWrapper;
        try {
            List<Admin> admins = adminService.listall();
            responseWrapper=ResponseWrapper.markSuccess();
            responseWrapper.setExtra("admins",admins);
        } catch (Exception e) {
            e.printStackTrace();
            responseWrapper=ResponseWrapper.markError();
        }
        return responseWrapper;
    }


    /**
     * 测试管理员用户名是否被使用过
     * @param name 用户名
     * @return
     */
    @GetMapping("/testName")
    @ApiOperation("测试管理员用户名是否被使用过")
    public ResponseWrapper testName(@RequestParam String name){
        ResponseWrapper responseWrapper;
        if(adminService.exist(name)){
            responseWrapper=ResponseWrapper.markDefault(200,"used");
        }else{
            responseWrapper=ResponseWrapper.markDefault(200,"notused");
        }
        return responseWrapper;
    }

}
