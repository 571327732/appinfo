package cn.appsys.service.devuser;

import java.util.List;


import cn.appsys.pojo.AppCategory;

public interface AppCategoryService {

	/**
	 * 根据 parentId获取AppCategory集合
	 * @param parentId
	 * @return AppCategory集合
	 * @throws Exception
	 */
	List<AppCategory> getAppCategorieListById(Integer parentId)throws Exception;
}
