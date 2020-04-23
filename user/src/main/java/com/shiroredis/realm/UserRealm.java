package com.shiroredis.realm;

import com.shiroredis.dao.UserMapper;
import com.shiroredis.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义realm
 * @author jianping.lu
 *
 */
public class UserRealm extends AuthorizingRealm{

    @Autowired
    private UserMapper userMapper;
    
    /**
     * 第一次访问@RequiresPermissions(value="/user/getUser") 这种带注解的方法时，会调用这个访问加载权限与角色
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        System.out.println("加载权限-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermission("/user/getUser");
        //authorizationInfo.setRoles(roles);
        //authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        // TODO Auto-generated method stub
        System.out.println("用户名与密码认证");
        //shiro判断逻辑
        UsernamePasswordToken user = (UsernamePasswordToken) arg0;
        User newUser = userMapper.selectUserByUsernameAndPassword(user.getUsername(),String.valueOf(user.getPassword()));
        if(newUser == null){
            //用户名错误
            return null;
        }
        return new SimpleAuthenticationInfo(newUser,newUser.getPassword(),"");
    }
}