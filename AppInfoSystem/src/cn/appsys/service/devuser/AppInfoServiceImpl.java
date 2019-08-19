/**
 * 
 */
package cn.appsys.service.devuser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;

/**
 * @author 57132
 *
 */
@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService {

	@Resource
	private AppInfoMapper appInfoMapper;

	

	@Override
	public List<AppInfo> getAppInfoList(Integer id, String softwareName,
			Integer status, Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer flatformId, Integer pageSieze,
			Integer currentPageNo) throws Exception {
		return  appInfoMapper.getAppInfoList(id, softwareName, status, categoryLevel1, categoryLevel2, categoryLevel3, flatformId, pageSieze, (currentPageNo-1)*pageSieze);
	}



	@Override
	public int getAppInfoCount(Integer id, String softwareName, Integer status,
			Integer categoryLeve1, Integer categoryLeve2,
			Integer categoryLeve3, Integer flatfromId) throws Exception {
		int count=0;
		try {
			count=appInfoMapper.getAppInfoCount(id, softwareName, status, categoryLeve1, categoryLeve2, categoryLeve3, flatfromId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}



	@Override
	public AppInfo getAppInfoByApkName(String apkName) throws Exception {
		return appInfoMapper.getAppInfoByApkName(apkName);
	}
	
}
