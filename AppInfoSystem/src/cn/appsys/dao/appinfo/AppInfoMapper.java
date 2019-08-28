/**
 * 
 */
package cn.appsys.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

/**
 * @author 57132
 *
 */
public interface AppInfoMapper {

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
     * @throws Exception
     */
	int getAppInfoCount(@Param("devId")Integer id,@Param("softwareName")String softwareName,@Param("status")Integer status,
			@Param("categoryLevel1")Integer categoryLevel1,@Param("categoryLevel2")Integer categoryLevel2,
			@Param("categoryLevel3")Integer categoryLevel3,@Param("flatformId")Integer flatformId)throws Exception;
	
	/**
	 * 根据条件获取app列表
	 * @param id
	 * @param softwareName
	 * @param status
	 * @param categoryLeve1
	 * @param categoryLeve2
	 * @param categoryLeve3
	 * @param flatfromId
	 * @param pageSieze
	 * @param currentPageNo
	 * @return app列表
	 * @throws Exception
	 */
	List<AppInfo> getAppInfoList(@Param("devId")Integer id,@Param("softwareName")String softwareName,@Param("status")Integer status,
			@Param("categoryLevel1")Integer categoryLevel1,@Param("categoryLevel2")Integer categoryLevel2,
			@Param("categoryLevel3")Integer categoryLevel3,@Param("flatformId")Integer flatformId,
			@Param("pageSize")Integer pageSieze,@Param("currentPageNo")Integer currentPageNo)throws Exception;
	
	
	
	/**
	 * 管理员获取未审核(状态)的 app集合
	 * @param softwareName
	 * @param status
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param flatformId
	 * @param pageSize
	 * @param currentPageNo
	 * @return 未审核(状态)的 app集合
	 * @throws Exception
	 */
	List<AppInfo>getAppInfolListByStatus(@Param("softwareName")String softwareName,@Param("status")Integer status,
			@Param("categoryLevel1")Integer categoryLevel1,@Param("categoryLevel2")Integer categoryLevel2,
			@Param("categoryLevel3")Integer categoryLevel3,@Param("flatformId")Integer flatformId,
			@Param("pageSize")Integer pageSize,@Param("currentPageNo")Integer currentPageNo)throws Exception;
	

	
	/**
	 * 管理员获取未审核(状态)的 app数量
	 * @param softwareName
	 * @param status
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param flatformId
	 * @return 未审核(状态)的 app数量
	 * @throws Exception
	 */
	int getAppInfoCountByStatus(@Param("softwareName")String softwareName,@Param("status")Integer status,
			@Param("categoryLevel1")Integer categoryLevel1,@Param("categoryLevel2")Integer categoryLevel2,
			@Param("categoryLevel3")Integer categoryLevel3,@Param("flatformId")Integer flatformId)throws Exception;
	/**
	 * 根据apkName获取appInfo
	 * @param apkName
	 * @return appInfo
	 * @throws Exception
	 */
	AppInfo getAppInfoByApkName(@Param("APKName")String apkName)throws Exception;
	
	/**
	 * 添加appInfo
	 * @param appInfo
	 * @return 影响行数
	 * @throws Exception
	 */
	int addAppInfo(AppInfo appInfo)throws Exception;
	
	/**
	 * 根据id查找appInfo
	 * @param id
	 * @return appInfo
	 * @throws Exception
	 */
	AppInfo getAppInfoById(@Param("id")Integer id)throws Exception;
	
	/**
	 * 更改appInfo
	 * @param appInfo
	 * @return 影响行数
	 * @throws Exception
	 */
	int updateAppInfo(AppInfo appInfo)throws Exception;
	
	/**
	 * 根据id删除logo图片路径
	 * @param id
	 * @return 影响行数
	 * @throws Exception
	 */
	int delLogoPicById(@Param("id")Integer id)throws Exception;
	
	/**
	 * 更新app的最新版本号
	 * @param versionId
	 * @param appId
	 * @return 影响行数
	 */
	int updateVesionId(@Param("versionId")Integer versionId,@Param("id")Integer appId)throws Exception;
	
	/**
	 * 根据id删除 appInfo
	 * @param id
	 * @return 影响行数
	 * @throws Exception
	 */
	int delAppInfoById(@Param("id")Integer id)throws Exception;
	
	/**
	 * 审核 app 更改app状态
	 * @param status
	 * @param id
	 * @return 影响行数
	 * @throws Exception
	 */
	int updateAppInfoStatus(@Param("status")Integer status,@Param("id")Integer id)throws Exception;
	
	
}
