package cn.appsys.controller.developer;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.appsys.service.devuser.DevUserService;
@Controller
@RequestMapping("/sys/dev")
public class DevUserController {
	
	private Logger logger=Logger.getLogger(DevUserController.class);
	@Resource
	private DevUserService devUserService;
	
	@RequestMapping(value="/main.html")
	public String main(){
		return "/developer/main";
	}
}
