package cn.krl.visiteducationbackend.controller;

import cn.krl.visiteducationbackend.annotation.PassToken;
import cn.krl.visiteducationbackend.dto.AdminDTO;
import cn.krl.visiteducationbackend.dto.LoginDTO;
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

@RestController
@Api(tags = "管理者的api")
@RequestMapping("/admin")
@Slf4j
public class AdminController {

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
    @PassToken  //不验证token
    public ResponseWrapper adminLogin(@RequestBody LoginDTO loginDTO){
        ResponseWrapper responseWrapper;

        String name = loginDTO.getName();
        String password = loginDTO.getPassword();
        int id =adminService.getByName(name).getId();
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(new UsernamePasswordToken(name,password));
            String token = JwtUtil.createToken(Integer.toString(id),name);
            responseWrapper=ResponseWrapper.markSuccess();
            responseWrapper.setExtra("id",id);
            responseWrapper.setExtra("adminName",name);
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
     * 管理员注册
     * @param adminDTO 登录注册传输对象，包括用户名和密码
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("管理员注册")
    public ResponseWrapper register(@RequestBody AdminDTO adminDTO,@RequestHeader("token")String token){
        ResponseWrapper responseWrapper;
        if(!adminService.exist(adminDTO.getName())){
            try {
                String name = adminDTO.getName();
                String password = adminDTO.getPassword();
                adminService.register(name,password);
                responseWrapper=ResponseWrapper.markSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                responseWrapper=ResponseWrapper.markError();
            }
        }else {
            responseWrapper=ResponseWrapper.markAdminExist();
        }
        return responseWrapper;
    }


    /**
     * 账号退出
     * @return
     */
    @GetMapping("/logout")
    @ApiOperation("管理员退出")
    public  ResponseWrapper logout(@RequestHeader("token")String token){
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


}
