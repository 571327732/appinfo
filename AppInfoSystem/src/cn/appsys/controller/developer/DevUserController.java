package cn.appsys.controller.developer;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;

import sun.net.www.content.audio.aiff;

import cn.appsys.controller.basecontroller.BaseController;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.devuser.AppInfoService;
import cn.appsys.service.devuser.AppCategoryService;
import cn.appsys.service.devuser.AppVersionService;
import cn.appsys.service.devuser.DataDictionaryService;
import cn.appsys.service.devuser.DevUserService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/sys/dev")
public class DevUserController{

	private Logger logger = Logger.getLogger(DevUserController.class);
	
	@Resource
	private AppInfoService appInfoService;
	@Resource
	private DataDictionaryService dataDictionaryService;
	@Resource
	private AppCategoryService appCategroyService;
	@Resource
    private AppVersionService appVersionService;
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
		logger.info("querySoftwareName>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ softwareName);
		logger.info("queryStatus>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + status);
		logger.info("queryFlatformId>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + flatformId);
		logger.info("queryCategoryLevel1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ categoryLevel1);
		logger.info("queryCategoryLevel2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ categoryLevel2);
		logger.info("queryCategoryLevel3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ categoryLevel3);
		logger.info("pageIndex>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + pageIndex);
		DevUser devUser = (DevUser) session
				.getAttribute(Constants.DEV_USER_SESSION);// 获取当前开发者
		Integer currentPageNo = 1;// 当前页
		Integer pageSize = Constants.pageSize;// 页大小
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
			totalCount = appInfoService.getAppInfoCount(devUser.getId(),
					softwareName, _status, _categoryLevel1, _categoryLevel2,
					_categoryLevel3, _flatformId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<AppInfo> appInfoList = null;// app信息集合
		List<DataDictionary> statusList = null;// 状态集合
		List<DataDictionary> flatFormList = null;// 平台集合
		List<AppCategory> categoryLevel1List = null;// 一级分类集合
		List<AppCategory> categoryLevel2List = null;// 二级分类集合
		List<AppCategory> categoryLevel3List = null;// 三级分类集合
		try {
			appInfoList = appInfoService.getAppInfoList(devUser.getId(),
					softwareName, _status, _categoryLevel1, _categoryLevel2,
					_categoryLevel3, _flatformId, pageSize, currentPageNo);
			statusList = dataDictionaryService
					.getDataDictionarieList("APP_STATUS");
			flatFormList = dataDictionaryService
					.getDataDictionarieList("APP_FLATFORM");
			categoryLevel1List = appCategroyService
					.getAppCategorieListById(null);// 先获取一级分类
		} catch (Exception e) {
			e.printStackTrace();
		}

		PageSupport pages = new PageSupport();
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		// 页面控制
		if (currentPageNo < 1) {
			currentPageNo = 1;
		} else if (currentPageNo > pages.getTotalPageCount()) {
			currentPageNo = pages.getTotalPageCount();
		}

		pages.setCurrentPageNo(currentPageNo);
		model.addAttribute("pages", pages);
		model.addAttribute("appInfoList", appInfoList);
		model.addAttribute("statusList", statusList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("queryCategoryLevel1", categoryLevel1);
		model.addAttribute("queryCategoryLevel2", categoryLevel2);
		model.addAttribute("queryCategoryLevel3", categoryLevel3);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("querySoftwareName", softwareName);
		model.addAttribute("queryStatus", status);
		model.addAttribute("queryFlatformId", flatformId);

		// 二级 三级列表的 回显
		if (_categoryLevel1 != null && !("").equals(_categoryLevel1)) {// 一级分类有值时
			try {
				categoryLevel2List = appCategroyService
						.getAppCategorieListById(_categoryLevel1);// 拿一级的id
																	// 获取二级分类集合
				model.addAttribute("categoryLevel2List", categoryLevel2List);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (_categoryLevel2 != null && !("").equals(_categoryLevel2)) {// 二级分类有值时
			try {
				categoryLevel3List = appCategroyService
						.getAppCategorieListById(_categoryLevel2);// 拿二级的id
																	// 获取三级分类集合
				model.addAttribute("categoryLevel3List", categoryLevel3List);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "developer/appinfolist";
	}

	// 根据parentId获取对应的子级(重复利用一级二级三级都可获取)
	@RequestMapping(value = "/categorylevellist.json", method = RequestMethod.GET)
	@ResponseBody
	public List<AppCategory> getAppCategoryList(@RequestParam("pid") String pid) {
		if (("").equals(pid)) {
			pid = null;
		}
		return getCategoryList(pid);
	}

	// 根据parentId获取对应的子级(重复利用一级二级三级都可获取)
	public List<AppCategory> getCategoryList(String pid) {
		List<AppCategory> appCategoryList = null;
		Integer parentId = null;
		if (pid != null) {
			parentId = Integer.valueOf(pid);
		}
		try {
			appCategoryList = appCategroyService
					.getAppCategorieListById(parentId);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return appCategoryList;
	}

	// 添加新的app页面
	@RequestMapping(value = "/appinfoadd")
	public String addAppInfo(@ModelAttribute AppInfo appInfo) {
		return "/developer/appinfoadd";
	}

	// 保存新的app信息
	@RequestMapping("/appinfoaddsave")
	public String saveAppInfo(
			AppInfo appInfo,
			HttpServletRequest request,
			HttpSession session,
			@RequestParam(value = "a_logoPicPath", required = false) MultipartFile attach) {
		String logoPicPath = null;
		String logoLocPath = null;// 文件服务器的位置
		boolean flag = true;
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles");
			String oldFileName = attach.getOriginalFilename();// 获取文件的原名称
			String suffix = FilenameUtils.getExtension(oldFileName);// 获取文件的前缀
			int size = 500000;// 500kb
			if (attach.getSize() > size) {// 文件大小判断分支
				flag = false;
				request.setAttribute("fileUploadError",
						Constants.FILEUPLOAD_ERROR_4);// 文件过大
			} else if (suffix.equalsIgnoreCase("png")
					|| suffix.equalsIgnoreCase("peng")
					|| suffix.equalsIgnoreCase("jpg")
					|| suffix.equalsIgnoreCase("jpeg")) {// 后缀判断分支
				String fileName = appInfo.getAPKName() + ".jpg";// 使用apkName作为唯一区分
				File targetFile = new File(path, fileName);
				if (!targetFile.exists()) {// 文件不存在
					targetFile.mkdirs();// 创建文件
				}
				try {
					attach.transferTo(targetFile);// 将原文件输入到新的文件中
				} catch (Exception e) {
					e.printStackTrace();
					flag = false;
					request.setAttribute("fileUploadError",
							Constants.FILEUPLOAD_ERROR_2);// 文件上传失败
				}
				logoPicPath = request.getContextPath() + File.separator
						+ "statics" + File.separator + "uploadfiles"
						+ File.separator + fileName;// LOGO图片url路径
				logoLocPath = path + File.separator + fileName;// 服务器路径
			} else {// 后缀判断分支(不符合要求)
				flag = false;
				request.setAttribute("fileUploadError",
						Constants.FILEUPLOAD_ERROR_3);// 文件格式不正确
				/* return "/developer/appinfoadd"; */
			}
		} else {// 文件为空分支
			flag = false;
			request.setAttribute("fileUploadError", "文件为空");
		}
		// 文件符合标准时
		if (flag) {
			// 需获取创建者id 创建日期 文件路径在项目中的位置 和保存到服务器上的文件路径
			DevUser devUser = (DevUser) session
					.getAttribute(Constants.DEV_USER_SESSION);// 获取创建者信息
			appInfo.setDevId(devUser.getId());// 开发者者赋值
			appInfo.setCreationDate(new Date());// 创建日期
			appInfo.setLogoPicPath(logoPicPath);
			appInfo.setLogoLocPath(logoLocPath);
			appInfo.setCreatedBy(devUser.getId());// 创建者
			// 进行保存操作
			try {
				if (appInfoService.addAppInfo(appInfo)) {
					return "redirect:/sys/dev/list";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "/developer/appinfoadd";
	}

	// 获取平台
	@RequestMapping("/datadictionarylist.json")
	@ResponseBody
	public List<DataDictionary> getDataDictionaryList(
			@RequestParam("tcode") String typeCode) {
		List<DataDictionary> dataDictionaryList = null;
		try {
			dataDictionaryList = dataDictionaryService
					.getDataDictionarieList(typeCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataDictionaryList;
	}

	// 校验Apk名称(必须唯一)
	@RequestMapping(value = "/apkexist.json")
	@ResponseBody
	public Map<String, Object> getApkNameIsExist(
			@RequestParam("APKName") String apkName) {
		Map<String, Object> map = new HashMap<String, Object>();
		AppInfo appInfo = null;
		if (apkName == null || ("").equals(apkName)) {
			map.put("APKName", "empty");
		} else {
			try {
				appInfo = appInfoService.getAppInfoByApkName(apkName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (appInfo != null) {
				map.put("APKName", "exist");
			} else {
				map.put("APKName", "noexist");
			}
		}
		return map;
	}

	// 查看 app信息
	@RequestMapping(value = "/appview/{id}", method = RequestMethod.GET)
	public String view(@PathVariable String id, Model model) {
		AppInfo appInfo = this.getAppInfoById(id);// 根据id获取到appInfo
		List<AppVersion>appVersionList=appVersionService.getAppVersionByAId(appInfo.getId());
		model.addAttribute("appInfo", appInfo);
		model.addAttribute("appVersionList", appVersionList);
		return "/developer/appinfoview";
	}

	// 根据id获取app信息(查看和修改共用一个查询方法)
	public AppInfo getAppInfoById(String id) {
		Integer appId = null;
		if (id != null && !("").equals(id)) {
			appId = Integer.valueOf(id);
		}
		AppInfo appInfo = null;
		try {
			appInfo = appInfoService.getAppInfoById(appId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appInfo;
	}

	// 更改 app信息页面
	@RequestMapping(value = "/appinfomodify")
	public String appInfoModify(@RequestParam("id") String id, Model model) {
		AppInfo appInfo = this.getAppInfoById(id);
		model.addAttribute("appInfo", appInfo);
		return "/developer/appinfomodify";
	}

	// 保存app信息
	@RequestMapping(value = "/appinfomodifysave")
	public String saveAppInfoModify(
			AppInfo appInfo,
			HttpServletRequest request,
			HttpSession session,
			@RequestParam(value = "attach", required = false) MultipartFile attach) {
		String logoPicPath = null;
		String logoLocPath = null;
		boolean flag = true;// 文件是否成功的标识
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles");// 获取路径
			String olderFileName = attach.getOriginalFilename();// 原文件名称
			String suffixName = FilenameUtils.getExtension(olderFileName);// 文件后缀
			int size = 500000;// 文件大小
			if (attach.getSize() > size) {// 文件过大
				flag = false;
				request.setAttribute("fileUploadError",
						Constants.FILEUPLOAD_ERROR_4);
			} else if (suffixName.equalsIgnoreCase("jpg")
					|| suffixName.equalsIgnoreCase("jpeg")
					|| suffixName.equalsIgnoreCase("peg")
					|| suffixName.equalsIgnoreCase("peng")) {// 后缀满足这样的
				// 图片路径
				String fileName = appInfo.getAPKName() + ".jpg";
				File targetFile = new File(path, fileName);
				if (!targetFile.exists()) {// 文件不存在
					targetFile.mkdir();// 就创建
				}
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					flag = false;
					request.setAttribute("fileUploadError",
							Constants.FILEUPLOAD_ERROR_2);
				}
				// LOGO图片url路径
				logoPicPath = request.getContextPath() + File.separator
						+ "statics" + File.separator + "uploadfiles"
						+ File.separator + fileName;
				// 服务器上的位置
				logoLocPath = path + File.separator + fileName;
			} else {
				flag = false;// 不满足 文件格式
				request.setAttribute("fileUploadError",
						Constants.FILEUPLOAD_ERROR_3);
			}
		}
		if (flag) {
			appInfo.setModifyBy(((DevUser) (session
					.getAttribute(Constants.DEV_USER_SESSION))).getId());// 设置更改者
			appInfo.setModifyDate(new Date());// 设置更改时间
			//如果更改图片 就进行赋值操作
			if(logoPicPath!=null&&!("").equals(logoPicPath)){
				appInfo.setLogoPicPath(logoPicPath);
			}
             if(logoLocPath!=null&&!("").equals(logoLocPath)){
				appInfo.setLogoLocPath(logoLocPath);
			}
			try {
				if (appInfoService.updateAppInfo(appInfo)) {
					return "redirect:/sys/dev/list";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return "/developer/appinfomodify";
	}

/*	// 删除图片操作
	@RequestMapping("/delfile.json")
	@ResponseBody
	public Map<String, Object> delLogoPicPath(@RequestParam("id") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer iId = null;
		if (id != null && !("").equals(id)) {
			iId = Integer.valueOf(id);
		}
		try {
			// 先获取图片的路径
			String logoLocPath = appInfoService.getAppInfoById(iId)
					.getLogoLocPath();
			File file = new File(logoLocPath);
			if (file.exists()) {// 文件存在
				if (file.delete()) {// 删除成功
					if (appInfoService.delLogoPicById(iId)) {// 进行appInfo的路径删除(修改为null)
						map.put("result", "success");
					} else {
						map.put("result", "failed");
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}*/
	//删除 appInfoLogo/appVersion的APK文件 共用一个方法
	@RequestMapping(value = "/delfile",method=RequestMethod.GET)
	@ResponseBody
	public Object delFile(@RequestParam(value="flag",required=false) String flag,
						 @RequestParam(value="id",required=false) String id){
		HashMap<String, String> map = new HashMap<String, String>();
		String fileLocPath = null;
		if(flag == null || flag.equals("") ||
			id == null || id.equals("")){
			map.put("result", "failed");
		}else if(flag.equals("logo")){//删除logo图片（操作app_info）
			try {
				fileLocPath = (appInfoService.getAppInfoById(Integer.valueOf(id))).getLogoLocPath();//获取logo文件路径
				File file = new File(fileLocPath);
			    if(file.exists())
			    	if (file.delete()) {// 删除成功
						if (appInfoService.delLogoPicById(Integer.valueOf(id))) {// 进行appInfo的路径删除(修改为null)
							map.put("result", "success");
						} else {
							map.put("result", "failed");
						}
					}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(flag.equals("apk")){//删除apk文件（操作app_version）
			try {
				fileLocPath = (appVersionService.getAppVersionById(Integer.parseInt(id))).getApkLocPath();
				File file = new File(fileLocPath);
			    if(file.exists())
			     if(file.delete()){//删除服务器存储的物理文件
						if(appVersionService.deleteApkFile(Integer.parseInt(id))){//更新表
							map.put("result", "success");
						 }
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	//新增版本
	@RequestMapping(value="/appversionadd")
	public String appVersionAdd(@RequestParam("id")String id,AppVersion appVersion,Model model,@RequestParam(value="error",required=false)
	String fileUploadError){
		if(null!=fileUploadError&&fileUploadError.equals("error1")){
			fileUploadError=Constants.FILEUPLOAD_ERROR_1;
		}else if(null!=fileUploadError&&fileUploadError.equals("error2")){
			fileUploadError=Constants.FILEUPLOAD_ERROR_2;
		}else if(null!=fileUploadError&&fileUploadError.equals("error3")){
			fileUploadError=Constants.FILEUPLOAD_ERROR_3;
		}
		Integer appId=null;
		if(id!=null&&!("").equals(id)){
			appId=Integer.valueOf(id);
		}
		//根据id获取app的历史版本信息
		List<AppVersion> appVersionList = appVersionService.getAppVersionByAId(appId);
	    model.addAttribute("appVersionList", appVersionList);
	    appVersion.setAppId(appId);
	    model.addAttribute("fileUploadError", fileUploadError);
	    model.addAttribute("appVersion",appVersion);
		return "/developer/appversionadd";
	}
	//新增版本保存
	@RequestMapping(value="/addversionsave")
	public String addVersionSave(AppVersion appVersion,HttpServletRequest request,HttpSession session,
			@RequestParam("a_downloadLink")MultipartFile attach){
		String downloadLink=null;
		String apkLocPath=null;
		String apkFileName =null;
		boolean flag = true;
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles");
			String oldFileName = attach.getOriginalFilename();// 获取文件的原名称
			String suffix = FilenameUtils.getExtension(oldFileName);// 获取文件的前缀
			int size = 500000000;// 500Mb
			if (attach.getSize() > size) {// 文件大小判断分支
				flag = false;
				request.setAttribute("fileUploadError",
						Constants.FILEUPLOAD_ERROR_4);// 文件过大
			} else if (suffix.equalsIgnoreCase("apk")) {// 后缀判断分支
				//获取 appid(用来你获取apkName)
				Integer appId=appVersion.getAppId();
				String apkName=null;
				try {
					apkName = appInfoService.getAppInfoById(appId).getAPKName();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				apkFileName =apkName+"-"+appVersion.getVersionNo()+".apk";// apkName+ 版本号 + .apk
				File targetFile = new File(path, apkFileName);
				if (!targetFile.exists()) {// 文件不存在
					targetFile.mkdirs();// 创建文件
				}
				try {
					attach.transferTo(targetFile);// 将原文件输入到新的文件中
				} catch (Exception e) {
					e.printStackTrace();
					flag = false;
					return "redirect:/sys/dev/appversionadd?id="+appVersion.getAppId()+"&error=error2";// 文件上传失败
				}
				downloadLink = request.getContextPath() + File.separator
						+ "statics" + File.separator + "uploadfiles"
						+ File.separator + apkFileName;// apk文件url路径
				apkLocPath = path + File.separator + apkFileName;// 服务器路径
			} else {// 后缀判断分支(不符合要求)
				flag = false;
				return "redirect:/sys/dev/appversionadd?id="+appVersion.getAppId()+"&error=error3";// 文件格式不正确
			}
		} else {// 文件为空分支
			flag = false;
			request.setAttribute("fileUploadError", "文件为空");
		}
		// 文件符合标准时
		if (flag) {
			// 需获取创建者id 创建日期 文件路径在项目中的位置 和保存到服务器上的文件路径
			DevUser devUser = (DevUser) session
					.getAttribute(Constants.DEV_USER_SESSION);// 获取创建者信息
			appVersion.setApkFileName(apkFileName);//文件名称
			appVersion.setCreatedBy(devUser.getId());//创建者
			appVersion.setApkLocPath(apkLocPath);//服务器路径
			appVersion.setDownloadLink(downloadLink);//下载路径
			appVersion.setCreationDate(new Date());//创建日期
			// 进行保存操作
			try {
				if (appVersionService.addAppVersion(appVersion)) {
					return "redirect:/sys/dev/list";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/sys/dev/appversionadd?id="+appVersion.getAppId();
	}
	
	//修改版本页面
	@RequestMapping(value="/appversionmodify")
	public String appVersionModify(@RequestParam("vid")String vid,@RequestParam("aid")String aId,Model model){
		Integer appId=null;
		Integer vId=null;
		//获取历史版本
		if(aId!=null&&!("").equals(aId)){
			appId=Integer.valueOf(aId);
		}
		if(vid!=null&&!("").equals(vid)){
			vId=Integer.valueOf(vid);
		}
		List<AppVersion> appVersionList = appVersionService.getAppVersionByAId(appId);
		AppVersion appVersion=null;//显示的要修改的版本
		try {
			appVersion = appVersionService.getAppVersionById(vId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute("appVersion", appVersion);
		return "/developer/appversionmodify";
	}
	
	//修改版本保存
	@RequestMapping(value="/appversionmodifysave",method=RequestMethod.POST)
	public String modifyAppVersionSave(AppVersion appVersion,HttpSession session,HttpServletRequest request,
					@RequestParam(value="attach",required= false) MultipartFile attach){	
		
		String downloadLink =  null;
		String apkLocPath = null;
		String apkFileName = null;
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			logger.info("uploadFile path: " + path);
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
			if(prefix.equalsIgnoreCase("apk")){//apk文件命名：apk名称+版本号+.apk
				 String apkName = null;
				 try {
					apkName = appInfoService.getAppInfoById(appVersion.getAppId()).getAPKName();
				 } catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				 }
				 if(apkName == null || "".equals(apkName)){
					 return "redirect:/dev/appversionmodify?vid="+appVersion.getId()
							 +"&aid="+appVersion.getAppId()
							 +"&error=error1";
				 }
				 apkFileName = apkName + "-" +appVersion.getVersionNo() + ".apk";
				 File targetFile = new File(path,apkFileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "redirect:/dev/flatform/app/appversionmodify?vid="+appVersion.getId()
							 +"&aid="+appVersion.getAppId()
							 +"&error=error2";
				} 
				downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
				apkLocPath = path+File.separator+apkFileName;
			}else{
				return "redirect:/dev/flatform/app/appversionmodify?vid="+appVersion.getId()
						 +"&aid="+appVersion.getAppId()
						 +"&error=error3";
			}
		}
		appVersion.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appVersion.setModifyDate(new Date());
		appVersion.setDownloadLink(downloadLink);
		appVersion.setApkLocPath(apkLocPath);
		appVersion.setApkFileName(apkFileName);
		try {
			if(appVersionService.modify(appVersion)){
				return "redirect:/sys/dev/list";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "developer/appversionmodify";
	}
	
	//删除app(包括所有版本)
	@RequestMapping(value="/delapp.json")
	@ResponseBody
	public Map<String,Object> delAppInfo(@RequestParam("id")String id){
		Map<String,Object>map=new HashMap<String, Object>();
		Integer aId=null;
		if(StringUtils.isNullOrEmpty(id)){
			map.put("delResult","notexist");
		}else{
			//根据id删除app
			aId=Integer.valueOf(id);
			try {
				if(appInfoService.delAppInfoById(aId)){
					map.put("delResult","true");
				}else{
					map.put("delResult","false");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return map;
	}
	/*//上架 下架
	@RequestMapping(value="/{appid}/sale",method=RequestMethod.PUT)
	@ResponseBody
	public Object sale(@PathVariable String appid,HttpSession session){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Integer appIdInteger = 0;
		try{
			appIdInteger = Integer.parseInt(appid);
		}catch(Exception e){
			appIdInteger = 0;
		}
		resultMap.put("errorCode", "0");
		resultMap.put("appId", appid);
		if(appIdInteger>0){
			try {
				DevUser devUser = (DevUser)session.getAttribute(Constants.DEV_USER_SESSION);//获取开发者
				AppInfo appInfo = new AppInfo();
				appInfo.setId(appIdInteger);//
				appInfo.setModifyBy(devUser.getId());//更改者
				if(appInfoService.appsysUpdateSaleStatusByAppId(appInfo)){
					resultMap.put("resultMsg", "success");
				}else{
					resultMap.put("resultMsg", "success");
				}		
			} catch (Exception e) {
				resultMap.put("errorCode", "exception000001");
			}
		}else{
			//errorCode:0为正常
			resultMap.put("errorCode", "param000001");
		}
		
		return resultMap;
	}*/
}
