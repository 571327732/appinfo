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
			@Param("categoryLeve1")Integer categoryLeve1,@Param("categoryLeve2")Integer categoryLeve2,
			@Param("categoryLeve3")Integer categoryLeve3,@Param("flatfromId")Integer flatfromId)throws Exception;
	
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
			@Param("categoryLeve1")Integer categoryLeve1,@Param("categoryLeve2")Integer categoryLeve2,
			@Param("categoryLeve3")Integer categoryLeve3,@Param("flatfromId")Integer flatfromId,
			@Param("pageSize")Integer pageSieze,@Param("currentPageNo")Integer currentPageNo)throws Exception;
}
