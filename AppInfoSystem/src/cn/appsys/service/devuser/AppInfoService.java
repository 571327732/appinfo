/**
 * 
 */
package cn.appsys.service.devuser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
			Integer categoryLeve1, Integer categoryLeve2,
			Integer categoryLeve3, Integer flatfromId)throws Exception;

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
			Integer status, Integer categoryLeve1, Integer categoryLeve2,
			Integer categoryLeve3, Integer flatfromId, Integer pageSieze,
			Integer currentPageNo) throws Exception;
}
