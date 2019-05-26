package com.fushanconsumer.common.interceptor;
import com.fushanapi.common.utils.UserConstants;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 进入controller 时执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String basePath = request.getContextPath();
        String path = request.getRequestURI();
        if(!doLoginInterceptor(path, basePath) ){//是否进行登陆拦截
            return true;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute(UserConstants.LOGIN_USER.name()) == null){
            response.sendRedirect(request.getContextPath()+"login");
            return false;
        }
        return true;
    }
    /**
     * 是否进行登陆过滤
     * @param path
     * @param basePath
     * @return
     */
    private boolean doLoginInterceptor(String path,String basePath){
        path = path.substring(basePath.length());
        Set<String> notLoginPaths = new HashSet<>();
        notLoginPaths.add("/login");
        notLoginPaths.add("/js/**");
        notLoginPaths.add("/css/**");
        notLoginPaths.add("/fonts/**");
        notLoginPaths.add("/images/**");
        notLoginPaths.add("/login/check");
        if(notLoginPaths.contains(path)) return false;
        return true;
    }
}
