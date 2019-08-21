package cn.appsys.controller.backend;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.controller.basecontroller.BaseController;
import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backenduser.BackendUserService;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping("/manager")
public class BackendLoginController{

	private Logger logger=Logger.getLogger(BackendUserController.class);
	@Resource
	private BackendUserService backendUserService;
	
	@RequestMapping( value="/login")
	public String login(){
		return "backendlogin";
	}
	
	@RequestMapping(value="/dologin",method=RequestMethod.POST)
	public String doLogin(@RequestParam("userCode")String userCode,@RequestParam("userPassword")String password,
			HttpServletRequest request,HttpSession session){
		BackendUser backendUser=backendUserService.login(userCode, password);
		if(backendUser==null){
			request.setAttribute(Constants.ERROR,"账号或密码不正确,请重新填写!");
			return "backendlogin";
		}
		session.setAttribute(Constants.USER_SESSION, backendUser);//设置到session中 用来判断是否有权限
		return "redirect:/sys/user/main.html";//后台管理员主页面
	}
	//注销
	@RequestMapping(value="/logout")
	public String logout(HttpSession session){
		session.removeAttribute(Constants.USER_SESSION);
		return "redirect:/manager/login";
	}
}
