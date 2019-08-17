package cn.appsys.controller.developer;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.devuser.AppInfoService;
import cn.appsys.service.devuser.DevUserService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/sys/dev")
public class DevUserController {

	private Logger logger = Logger.getLogger(DevUserController.class);
	@Resource
	private DevUserService devUserService;
	@Resource
	private AppInfoService appInfoService;

	@RequestMapping(value = "/main.html")
	public String main() {
		return "/developer/main";
	}

	@RequestMapping(value = "/list")
	public String appList(
			Model model,
			@RequestParam(value = "querySoftwareName", required = false) String softwareName,
			@RequestParam(value = "queryStatus", required = false) String status,
			@RequestParam(value = "queryFlatformId", required = false) String flatformId,
			@RequestParam(value = "queryCategoryLevel1", required = false) String categoryLevel1,
			@RequestParam(value = "queryCategoryLevel2", required = false) String categoryLevel2,
			@RequestParam(value = "queryCategoryLevel3", required = false) String categoryLevel3,
			@RequestParam(value = "pageIndex", required = false) String pageIndex,
			HttpSession session) {
		DevUser devUser = (DevUser) session
				.getAttribute(Constants.DEV_USER_SESSION);// 获取当前开发者
		Integer currentPageNo = 1;// 当前页
		Integer pageSize=Constants.pageSize;//页大小
		if (pageIndex != null && !pageIndex.equals("")) {
			currentPageNo = Integer.valueOf(pageIndex);
		}
		Integer _status = null;// 状态
		if (status != null && !status.equals("")) {
			_status = Integer.valueOf(status);
		}
		Integer _flatformId = null;// 平台
		if (flatformId != null && !flatformId.equals("")) {
			_flatformId = Integer.valueOf(flatformId);
		}
		Integer _categoryLevel1 = null;// 一级分类
		if (categoryLevel1 != null && !categoryLevel1.equals("")) {
			_categoryLevel1 = Integer.valueOf(categoryLevel1);
		}
		Integer _categoryLevel2 = null;// 二级分类
		if (categoryLevel2 != null && !categoryLevel2.equals("")) {
			_categoryLevel2 = Integer.valueOf(categoryLevel2);
		}
		Integer _categoryLevel3 = null;// 三级分类
		if (categoryLevel3 != null && !categoryLevel3.equals("")) {
			_categoryLevel3 = Integer.valueOf(categoryLevel3);
		}
		// 获取app列表信息
		int totalCount = 0;// 总记录数
		try {
			totalCount = appInfoService.getAppInfoCount(devUser.getId(), softwareName,
					_status, _categoryLevel1, _categoryLevel2, _categoryLevel3, _flatformId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<AppInfo> appInfoList = null;
		try {
			appInfoList = appInfoService.getAppInfoList(devUser.getId(), softwareName,
					_status, _categoryLevel1, _categoryLevel2,_categoryLevel3,_flatformId,pageSize,currentPageNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PageSupport pages = new PageSupport();
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		//页面控制
		if(currentPageNo<1){
			currentPageNo=1;
		}else if(currentPageNo>pages.getTotalPageCount()){
			currentPageNo=pages.getTotalPageCount();
		}
		
		pages.setCurrentPageNo(currentPageNo);
		model.addAttribute("pages",pages);
		model.addAttribute("appInfoList", appInfoList);
		model.addAttribute("querySoftwareName",softwareName);
		return "developer/appinfolist";
	}
	
}
