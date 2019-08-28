/**
 * 
 */
package cn.appsys.service.devuser;

import java.util.List;



import cn.appsys.pojo.AppInfo;

/**
 * @author 57132
 * 
 */
public interface AppInfoService {

	/**
	 * 根据条件获取app数量
	 * @param id
	 * @param softwareName
	 * @param status
	 * @param categoryLeve1
	 * @param categoryLeve2
	 * @param categoryLeve3
	 * @param flatfromId
	 * @return app数量
	 */
	int getAppInfoCount(Integer id, String softwareName, Integer status,
			Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer flatformId)throws Exception;

	/**
	 * 根据条件获取app列表集合
	 * @param id
	 * @param softwareName
	 * @param status
	 * @param categoryLeve1
	 * @param categoryLeve2
	 * @param categoryLeve3
	 * @param flatfromId
	 * @param pageSieze
	 * @param currentPageNo
	 * @return
	 * @throws Exception
	 */
	List<AppInfo> getAppInfoList(Integer id, String softwareName,
			Integer status, Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer flatformId, Integer pageSieze,
			Integer currentPageNo) throws Exception;
	
	/**
	 * 根据apkName获取appInfo
	 * @param apkName
	 * @return appInfo
	 * @throws Exception
	 */
	AppInfo getAppInfoByApkName(String apkName)throws Exception;
	
	/**
	 * 添加appInfo
	 * @param appInfo
	 * @return 是否添加成功
	 * @throws Exception
	 */
	boolean addAppInfo(AppInfo appInfo)throws Exception;
	
	/**
	 * 根据id查找appInfo
	 * @param id
	 * @return appInfo
	 * @throws Exception
	 */
	AppInfo getAppInfoById(Integer id)throws Exception;
	
	/**
	 * 更改appInfo
	 * @param appInfo
	 * @return 是否删除成功
	 * @throws Exception
	 */
	boolean updateAppInfo(AppInfo appInfo)throws Exception;
	
	/**
	 * 根据id删除logo图片路径
	 * @param id
	 * @return 是否删除成功
	 * @throws Exception
	 */
	boolean delLogoPicById(Integer id)throws Exception;
	
	/**
	 * 根据id删除 appInfo
	 * @param id
	 * @return 是否删除成功
	 * @throws Exception
	 */
	boolean delAppInfoById(Integer id)throws Exception;
}
