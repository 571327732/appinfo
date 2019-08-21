package cn.appsys.service.devuser;

import java.util.List;



import cn.appsys.pojo.AppVersion;

public interface AppVersionService {

	/**
	 * 根据 appId 查询版本集合
	 * @param appId
	 * @return 历史版本集合
	 */
	List<AppVersion>getAppVersionByVId(Integer appId);
}
