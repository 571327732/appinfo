package cn.appsys.service.backenduser;

import java.util.List;

import org.apache.ibatis.annotations.Param;


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
	
	/**
	 * 审核 app 更改app状态
	 * @param status
	 * @param id
	 * @return 是否更改成功
	 * @throws Exception
	 */
	boolean updateAppInfoStatus(Integer status,Integer id)throws Exception;
}
