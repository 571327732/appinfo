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
	List<AppVersion>getAppVersionByVId(@Param("appId")Integer appId);
}
