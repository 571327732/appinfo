package cn.appsys.service.backenduser;

import java.util.List;


import cn.appsys.pojo.AppInfo;

public interface BackendUserAppService {

	/**
	 * 管理员获取未审核(状态)的 app集合
	 * 
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
	List<AppInfo> getAppInfolListByStatus(String softwareName, Integer status,
			Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer flatformId, Integer pageSize,
			Integer currentPageNo) throws Exception;

	/**
	 * 管理员获取未审核(状态)的 app数量
	 * 
	 * @param softwareName
	 * @param status
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param flatformId
	 * @return 未审核(状态)的 app数量
	 * @throws Exception
	 */
	int getAppInfoCountByStatus(String softwareName, Integer status,
			Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer flatformId) throws Exception;
}
