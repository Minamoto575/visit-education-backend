package cn.krl.visiteducationbackend.shiro.realm;

import cn.krl.visiteducationbackend.entity.Admin;
import cn.krl.visiteducationbackend.service.Impl.AdminServiceImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;


public class AdminRealm extends AuthorizingRealm {

    @Autowired
    private AdminServiceImpl adminService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        //读取数据库中的Admin
        Admin admin = adminService.getByName(principal);
        if (!ObjectUtils.isEmpty(admin)) {
            return new SimpleAuthenticationInfo(admin.getName(), admin.getPassword(), ByteSource.Util.bytes(admin.getSalt()), this.getName());
        }
        return null;
    }
}
