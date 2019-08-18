package cn.appsys.dao.appcategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;

public interface AppCategroyMapper {

	/**
	 * 根据 parentId获取AppCategory集合
	 * @param parentId
	 * @return AppCategory集合
	 * @throws Exception
	 */
	List<AppCategory> getAppCategorieListById(@Param("parentId")Integer parentId)throws Exception;
}
