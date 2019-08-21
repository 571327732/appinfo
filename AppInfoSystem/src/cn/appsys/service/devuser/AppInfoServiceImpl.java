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



	@Override
	public boolean addAppInfo(AppInfo appInfo) throws Exception {
		boolean flag=false;
		if(appInfoMapper.addAppInfo(appInfo)>0){
			flag=true;
		}
		return flag;
	}



	@Override
	public AppInfo getAppInfoById(Integer id) throws Exception {
		return appInfoMapper.getAppInfoById(id);
	}



	@Override
	public boolean updateAppInfo(AppInfo appInfo) throws Exception {
		boolean flag=false;
		if(appInfoMapper.updateAppInfo(appInfo)>0){
			flag=true;
		}
		return flag;
	}



	@Override
	public boolean delLogoPicById(Integer id) throws Exception {
		boolean flag=false;
		if(appInfoMapper.delLogoPicById(id)>0){
			flag=true;
		}
		return flag;
	}
	
}
