package cn.appsys.controller.developer;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.devuser.DevUserService;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping("/dev")
public class DeveloperLoginController {

	@Resource
	private DevUserService devUserService;
	
	@RequestMapping(value="/login")
	public String login(){
		return "devlogin";//登录页面跳转
	}
	
	@RequestMapping(value="dologin",method=RequestMethod.POST)
	public String doLogin(@RequestParam("devCode") String devCode,@RequestParam("devPassword")String password,
			HttpServletRequest request,HttpSession session){
		DevUser devUser=devUserService.login(devCode, password);
		if(devUser==null){
			request.setAttribute(Constants.ERROR, "账号或密码不正确,请重新填写!");
			return "devlogin";
		}
		session.setAttribute(Constants.DEV_USER_SESSION,devUser);
		return "redirect:/sys/dev/main.html";
	}
	
	    //注销
		@RequestMapping(value="/logout")
		public String logout(HttpSession session){
			session.removeAttribute(Constants.DEV_USER_SESSION);
			return "redirect:/dev/login";
		}
}
