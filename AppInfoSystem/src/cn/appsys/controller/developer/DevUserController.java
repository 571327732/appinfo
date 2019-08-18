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
import org.springframework.web.bind.annotation.ResponseBody;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.devuser.AppInfoService;
import cn.appsys.service.devuser.AppCategoryService;
import cn.appsys.service.devuser.DataDictionaryService;
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
	@Resource
	private DataDictionaryService dataDictionaryService;
	@Resource
	private AppCategoryService appCategroyService;

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
		logger.info("querySoftwareName>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+softwareName);
		logger.info("queryStatus>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+status);
		logger.info("queryFlatformId>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+flatformId);
		logger.info("queryCategoryLevel1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+categoryLevel1);
		logger.info("queryCategoryLevel2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+categoryLevel2);
		logger.info("queryCategoryLevel3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+categoryLevel3);
		logger.info("pageIndex>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+pageIndex);
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
		List<AppInfo> appInfoList = null;//app信息集合
		List<DataDictionary>statusList=null;//状态集合
		List<DataDictionary>flatFormList=null;//平台集合
		List<AppCategory>categoryLevel1List=null;//一级分类集合
		List<AppCategory>categoryLevel2List=null;//二级分类集合
		List<AppCategory>categoryLevel3List=null;//三级分类集合
		try {
			appInfoList = appInfoService.getAppInfoList(devUser.getId(), softwareName,
					_status, _categoryLevel1, _categoryLevel2,_categoryLevel3,_flatformId,pageSize,currentPageNo);
			statusList=dataDictionaryService.getDataDictionarieList("APP_STATUS");
			flatFormList=dataDictionaryService.getDataDictionarieList("APP_FLATFORM");
			categoryLevel1List=appCategroyService.getAppCategorieListById(null);//先获取一级分类
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
		model.addAttribute("statusList", statusList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("queryCategoryLevel1", categoryLevel1);
		model.addAttribute("queryCategoryLevel2", categoryLevel2);
		model.addAttribute("queryCategoryLevel3", categoryLevel3);
		model.addAttribute("categoryLevel1List",categoryLevel1List);
		model.addAttribute("querySoftwareName",softwareName);
		model.addAttribute("queryStatus", status);
		model.addAttribute("queryFlatformId", flatformId);
		
		//二级 三级列表的 回显
		if(_categoryLevel2!=null&&!("").equals(_categoryLevel2)){//二级分类有值时
			try {
				categoryLevel2List=appCategroyService.getAppCategorieListById(_categoryLevel1);//拿一级的id 获取三级分类集合
				model.addAttribute("categoryLevel2List", categoryLevel2List);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(_categoryLevel3!=null&&!("").equals(_categoryLevel3)){//三级分类有值时
			try {
				categoryLevel3List=appCategroyService.getAppCategorieListById(_categoryLevel2);//拿二级的id 获取三级分类集合
				model.addAttribute("categoryLevel3List", categoryLevel3List);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "developer/appinfolist";
	}
	//根据parentId获取对应的子级(重复利用一级二级三级都可获取)
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<AppCategory> getAppCategoryList(@RequestParam("pid")String pid){
		if(("").equals(pid)){
			pid=null;
		}
		return getCategoryList(pid);
	}
	//根据parentId获取对应的子级(重复利用一级二级三级都可获取)
	public List<AppCategory>getCategoryList(String pid){
		List<AppCategory>appCategoryList=null;
		Integer parentId=Integer.valueOf(pid);
		try {
			 appCategoryList=appCategroyService.getAppCategorieListById(parentId);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return appCategoryList;
	}
	
}
