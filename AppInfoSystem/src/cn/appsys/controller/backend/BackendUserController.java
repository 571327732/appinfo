package cn.appsys.controller.backend;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.backenduser.BackendUserAppService;
import cn.appsys.service.devuser.AppCategoryService;
import cn.appsys.service.devuser.DataDictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/sys/user")
public class BackendUserController {

	private Logger logger = Logger.getLogger(BackendUserController.class);

	@Resource
	private BackendUserAppService backendUserAppService;
	@Resource
    private DataDictionaryService dataDictionaryService;
	@Resource
	private AppCategoryService appCategoryService;
	@RequestMapping("/main.html")
	public String main() {
		return "/backend/main";
	}

	// 获取app信息集合
	@RequestMapping(value = "/list")
	public String getAppInfoList(
			Model model,
			@RequestParam(value = "querySoftwareName", required = false) String softwareName,
			@RequestParam(value = "queryFlatformId", required = false) String flatformId,
			@RequestParam(value = "queryCategoryLevel1", required = false) String categoryLevel1,
			@RequestParam(value = "queryCategoryLevel2",required=false) String categoryLevel2,
			@RequestParam(value = "queryCategoryLevel3", required = false) String categoryLevel3,
			@RequestParam(value = "pageIndex", required = false) String pageIndex) {
		Integer _flatformId = null;// 平台
		if (flatformId != null && !("").equals(flatformId)) {
			_flatformId = Integer.valueOf(flatformId);
		}
		Integer _categoryLevel1 = null;// 一级分类
		if (categoryLevel1 != null && !("").equals(categoryLevel1)) {
			_categoryLevel1 = Integer.valueOf(categoryLevel1);
		}
		Integer _categoryLevel2 = null;// 二级分类
		if (categoryLevel2 != null && !("").equals(categoryLevel2)) {
			_categoryLevel2 = Integer.valueOf(categoryLevel2);
		}
		Integer _categoryLevel3 = null;// 三级分类
		if (categoryLevel3 != null && !("").equals(categoryLevel3)) {
			_categoryLevel3 = Integer.valueOf(categoryLevel3);
		}
        //定义集合
		List<AppInfo>appInfoList=null;//app信息集合
		List<DataDictionary>flatFormList=null;//平台集合
		List<AppCategory>categoryLevel1List=null;//一级分类集合
		List<AppCategory>categoryLevel2List=null;//二级分类集合
		List<AppCategory>categoryLevel3List=null;//三级分类集合
		// 总记录数
		Integer totalCount = 0;
		Integer pageSize = Constants.pageSize;// 页面容量
		Integer currentPageNo = 1;// 当前页数
		if (pageIndex != null && !("").equals(pageIndex)) {
			currentPageNo = Integer.valueOf(pageIndex);
		}
		try {
			totalCount = backendUserAppService.getAppInfoCountByStatus(
					softwareName, 1, _categoryLevel1, _categoryLevel2,
					_categoryLevel3, _flatformId);
			flatFormList=dataDictionaryService.getDataDictionarieList("APP_FLATFORM");//获取平台信息集合
			categoryLevel1List=appCategoryService.getAppCategorieListById(null);//获取一级分类
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PageSupport pages = new PageSupport();
		pages.setPageSize(pageSize);
		pages.setCurrentPageNo(currentPageNo);
		pages.setTotalCount(totalCount);
		Integer totalPageCount = pages.getTotalPageCount();// 获取总页数

		// 页面控制
		if (currentPageNo < 1) {
			currentPageNo = 1;
		} else if (currentPageNo > totalPageCount) {
			currentPageNo = totalPageCount;
		}
		try {
			appInfoList=backendUserAppService.getAppInfolListByStatus(softwareName, 1,
					_categoryLevel1, _categoryLevel2, _categoryLevel3,
					_flatformId, pageSize, currentPageNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//设置二级分类和三级分类的回显
		if(_categoryLevel1!=null&&!("").equals(_categoryLevel1)){//一级分类有值时 给二级分类集合赋值
			try {
				categoryLevel2List=appCategoryService.getAppCategorieListById(_categoryLevel1);
				model.addAttribute("categoryLevel2List",categoryLevel2List);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(_categoryLevel2!=null&&!("").equals(_categoryLevel2)){//二级分类有值时 给三级分类集合赋值
			try {
				categoryLevel3List=appCategoryService.getAppCategorieListById(_categoryLevel2);
				model.addAttribute("categoryLevel3List",categoryLevel3List);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("app的数量为_____________________________________________________________???????"
				+ totalCount);
		model.addAttribute("pages", pages);
		model.addAttribute("querySoftwareName", softwareName);
		model.addAttribute("queryFlatformId", _flatformId);
		model.addAttribute("queryCategoryLevel1", _categoryLevel1);
		model.addAttribute("queryCategoryLevel2", _categoryLevel2);
		model.addAttribute("queryCategoryLevel3", _categoryLevel3);
		model.addAttribute("appInfoList", appInfoList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		return "/backend/applist";
	}
	
	//请求二级 三级 分类(重复利用)
	@RequestMapping(value="/categorylevellist.json")
	@ResponseBody
	public List<AppCategory> getCategoryList(@RequestParam("pid")String pid){
		if(pid!=null&&!("").equals(pid)){
			return getcaAppCategoryListByParentId(pid);
		}
		return null;
	}
	//请求二级 三级 分类(重复利用)
	public List<AppCategory>getcaAppCategoryListByParentId(String pid){
		List<AppCategory>appCategoryList=null;
		Integer parentId=Integer.valueOf(pid);
		try {
			appCategoryList=appCategoryService.getAppCategorieListById(parentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appCategoryList;
	}
}
