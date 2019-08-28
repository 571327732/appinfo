package cn.appsys.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionMapper {

	/**
	 * 根据 appId 查询版本集合
	 * @param appId
	 * @return 版本集合
	 */
	List<AppVersion>getAppVersionByAId(@Param("appId")Integer appId);
	
	/**
	 * 添加新版本
	 * @param appVersion
	 * @return
	 */
	int addAppVersion(AppVersion appVersion)throws Exception;
	
	/**
	 * 根据id获取 AppVersion
	 * @param id
	 * @return AppVersion
	 * @throws Exception
	 */
	AppVersion getAppVersionById(@Param("id")Integer  id)throws Exception;
	
	/**
	 * 根据id获取  appVersion的数量
	 * @return  appVersion的数量
	 * @throws Exception
	 */
	int getAppVersionCountByAppId(@Param("appId")Integer appId)throws Exception;
	/**
	 * 根据appId 删除所有的版本信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	int delAppVersionByAppId(@Param("appId")Integer appId)throws Exception;
	
	/**
	 * 根据 id 删除apk文件
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int deleteApkFile(@Param("id")Integer id)throws Exception;
	
	/**
	 * 更新版本信息
	 * @param appVersion
	 * @return 影响行数
	 * @throws Exception
	 */
	int modify(AppVersion appVersion)throws Exception;
}
