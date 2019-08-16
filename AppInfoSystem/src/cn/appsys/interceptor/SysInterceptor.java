package cn.appsys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;
import cn.appsys.tools.Constants;

/**
 * 拦截器
 * @author 57132
 *
 */
public class SysInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session=request.getSession();
		DevUser devUser=(DevUser)session.getAttribute(Constants.DEV_USER_SESSION);
		BackendUser backendUser=(BackendUser) session.getAttribute(Constants.USER_SESSION);
		
		if(null!=devUser){//开发者
			return true;
		}else if(null!=backendUser){//后台管理员
			return true;
		}else{
			response.sendRedirect(request.getContextPath()+"403.jsp");
			return false;
		}
	}
}
