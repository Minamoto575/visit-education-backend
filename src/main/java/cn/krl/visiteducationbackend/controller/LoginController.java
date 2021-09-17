package cn.krl.visiteducationbackend.controller;

import cn.krl.visiteducationbackend.dto.AdminDTO;
import cn.krl.visiteducationbackend.dto.LoginDTO;
import cn.krl.visiteducationbackend.response.ResponseWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录的api")
@RequestMapping("/login")
public class LoginController {


    /**
     * 管理员登录
     * @param
     * @return
     */
    @GetMapping("/admin")
    @ApiOperation("管理员登录")
    public ResponseWrapper adminLogin(@RequestBody LoginDTO loginDTO){
        ResponseWrapper responseWrapper;

        String name = loginDTO.getName();
        String password = loginDTO.getPassword();
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(new UsernamePasswordToken(name,password));
            responseWrapper=ResponseWrapper.markSuccess();
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            responseWrapper=ResponseWrapper.markAccountError();
        } catch (IncorrectCredentialsException e){
            e.printStackTrace();
            responseWrapper=ResponseWrapper.markAccountError();
        }
        return responseWrapper;
    };


}
