/**
 * 
 */
package cn.appsys.service.devuser;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;

/**
 * @author 57132
 *
 */
@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService {

	@Resource
	private AppInfoMapper appInfoMapper;
	@Resource
	private AppVersionMapper appVersionMapper;

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



	@Override
	public boolean delAppInfoById(Integer id) throws Exception {
		boolean flag=false;
		Integer appId=id;
		List<AppVersion>list=null;
		//1..先删除版本 的信息
		if(appVersionMapper.getAppVersionCountByAppId(appId)>0){//版本数量大于0时
			//获取集合
			list=appVersionMapper.getAppVersionByAId(appId);
			for (AppVersion appVersion : list) {
				 if(appVersion.getApkLocPath()!=null&&!("").equals(appVersion.getApkLocPath())){//文件路径不为空时
					 File file=new File(appVersion.getApkLocPath());
					 if(file.exists()){//文件存在
						 if(!file.delete()){//没有删除成功  就抛异常
							 throw new Exception();
						 }
					 }
				 }
			}
			//进行删除	
			appVersionMapper.delAppVersionByAppId(appId);
		}
		//2..1appInfo信息(先删除服务器上的文件)
		AppInfo appInfo = appInfoMapper.getAppInfoById(appId);
		if(appInfo.getLogoLocPath()!=null&&!("").equals(appInfo.getLogoLocPath())){
			File file=new File(appInfo.getLogoLocPath());
			 if(file.exists()){//文件存在
				 if(!file.delete()){//没有删除成功  就抛异常
					 throw new Exception();
				 }
			 }
		}
		
		//2..2删除appInfo表信息
		if(appInfoMapper.delAppInfoById(appId)>0){
			flag=true;
		}
		return flag;
	}
	
}
