package cn.appsys.controller.backend;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.appsys.service.backenduser.BackendUserService;

@Controller
@RequestMapping("/sys/user")
public class BackendUserController {

	private Logger logger=Logger.getLogger(BackendUserController.class);
	@Resource
	private BackendUserService backendUserService;
	@RequestMapping("/main.html")
	public String main(){
		return "/backend/main";
	}
}
