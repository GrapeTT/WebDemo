package com.demo.interceptor;

import com.demo.tools.SessionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author：涛
 * @Descripition：
 * @Date：2018/3/23 18:33
 */
public class LoginInterceptor implements HandlerInterceptor {
    //进入handler之前执行
    //用于身份认证、身份授权
    //比如身份认证，如果认证通过表示当前用户没有登陆，需要此方法拦截不再向下执行
    //return false表示拦截，不向下执行
    //return true表示放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取url
        String url = request.getRequestURI();
        //如果进行登陆、注册、找回密码、登出，放行
        if(url.equals("/") || url.endsWith("/login") || url.endsWith("/getRegisterPage") || url.startsWith("/password") || url.endsWith("/logout")){
            return true;
        }
        //取出用户身份信息
        String uid = SessionUtils.getUid(request.getSession());

        if(uid != null){
            if("register".equals(uid)) {
                //如果是注册，则只能放行注册需要的请求
                if(url.endsWith("/getValidateCode") || url.endsWith("/register") || url.endsWith("/getMajorList") || url.endsWith("/getClassList")) {
                    return true;
                } else {
                    //跳转注册页面
                    request.getRequestDispatcher("/user/getRegisterPage").forward(request, response);
                    return false;
                }
            } else {
                //身份存在，放行
                return true;
            }
        }
        //执行这里表示用户身份需要认证，跳转登陆页面
        request.getRequestDispatcher("/").forward(request, response);
        return false;
    }

    //进入handler方法之后，返回ModelAndView之前执行
    //应用场景从modelAndView出发：将公用的模型数据(比如菜单导航)在这里传到视图，也可以在这里统一指定视图
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    //handler执行完成之后执行
    //应用场景：统一异常处理，统一日志处理
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
