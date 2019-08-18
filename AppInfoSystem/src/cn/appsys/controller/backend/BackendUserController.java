package cn.appsys.controller.backend;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.appsys.pojo.AppInfo;
import cn.appsys.service.backenduser.BackendUserAppService;
import cn.appsys.service.backenduser.BackendUserService;

@Controller
@RequestMapping("/sys/user")
public class BackendUserController {

	private Logger logger=Logger.getLogger(BackendUserController.class);

	@Resource
	private BackendUserAppService backendUserAppService;
	@RequestMapping("/main.html")
	public String main(){
		return "/backend/main";
	}
	
	//获取app信息集合
	@RequestMapping(value="/list")
	public String getAppInfoList(){
		//获取未审核的app
		int count=0;
		try {
			count = backendUserAppService.getAppInfoCountByStatus(null, 1, null, null, null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("app的数量为_____________________________________________________________???????"+count);
		return "/backend/applist";
	}
}
