package cn.appsys.service.devuser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appcategory.AppCategroyMapper;
import cn.appsys.pojo.AppCategory;

@Service("appCategroyService")
public class AppCategoryServiceImpl implements AppCategoryService{

	@Resource
	private AppCategroyMapper appCategroyMapper;
	
	@Override
	public List<AppCategory> getAppCategorieListById(Integer parentId)
			throws Exception {
		return appCategroyMapper.getAppCategorieListById(parentId);
	}

}
