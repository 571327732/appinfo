package cn.appsys.service.devuser;

import java.util.List;

import org.apache.ibatis.annotations.Param;



import cn.appsys.pojo.AppVersion;

public interface AppVersionService {

	/**
	 * 根据 appId 查询版本集合
	 * @param appId
	 * @return 历史版本集合
	 */
	List<AppVersion>getAppVersionByAId(Integer appId);
	
	/**
	 * 
	 * @param appVersion
	 * @return
	 * @throws Exception
	 */
	boolean addAppVersion(AppVersion appVersion)throws Exception;
	
	/**
	 * 根据id获取 AppVersion
	 * @param id
	 * @return AppVersion
	 * @throws Exception
	 */
	AppVersion getAppVersionById(Integer  id)throws Exception;
	
	/**
	 * 根据 id 删除apk文件
	 * @param id
	 * @return 是否删除成功
	 * @throws Exception
	 */
	boolean deleteApkFile(Integer id)throws Exception;
	/**
	 * 更新版本信息
	 * @param appVersion
	 * @return 是否更新成功
	 * @throws Exception
	 */
	boolean modify(AppVersion appVersion)throws Exception;
}
