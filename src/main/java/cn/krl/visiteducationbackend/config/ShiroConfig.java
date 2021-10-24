package cn.krl.visiteducationbackend.config;

import cn.krl.visiteducationbackend.shiro.realm.AdminRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @description shiro配置类
 * @author kuang
 * @data 2021/10/24
 */
@Configuration
public class ShiroConfig {
    /**
     * @description ShiroFilter过滤所有请求
     * @param securityManager: 安全管理器
     * @return: org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @author kuang
     * @data 2021/10/24 21:21
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 给ShiroFilter配置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 配置系统受限资源
        // 配置系统公共资源
        Map<String, String> map = new HashMap<String, String>();
        // 表示这个为公共资源 一定是在受限资源上面
        map.put("/admin/login", "anon");
        map.put("/record/search", "anon");

        // 表示这个受限资源需要认证和授权
        map.put("/admin/logout", "authc");
        map.put("/admin/register", "authc");
        map.put("/record/delete", "authc");
        map.put("/record/post", "authc");
        map.put("/record/update", "authc");
        map.put("/record/upload", "authc");

        // 设置认证界面路径
        shiroFilterFactoryBean.setLoginUrl("/admin/login");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * @description 创建安全管理器
     * @param realm:
     * @return: org.apache.shiro.web.mgt.DefaultWebSecurityManager
     * @author kuang
     * @data 2021/10/24 21:19
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

    /**
     * @description 获取自定义的reaml
     * @return: org.apache.shiro.realm.Realm
     * @author kuang
     * @data 2021/10/24 21:20
     */
    @Bean
    public Realm getRealm() {
        AdminRealm realm = new AdminRealm();
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 设置使用MD5加密算法
        credentialsMatcher.setHashAlgorithmName("md5");
        // 散列次数
        credentialsMatcher.setHashIterations(1024);
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }
}
