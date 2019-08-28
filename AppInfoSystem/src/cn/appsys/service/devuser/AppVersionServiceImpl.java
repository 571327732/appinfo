package cn.appsys.service.devuser;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {

	@Resource
	private AppVersionMapper appVersionMapper;
	@Resource
	private AppInfoMapper appInfoMapper;
	@Override
	public List<AppVersion> getAppVersionByAId(Integer appId) {
		
		return appVersionMapper.getAppVersionByAId(appId);
	}
	@Override
	public boolean addAppVersion(AppVersion appVersion) throws Exception {
		boolean flag=false;
		Integer vId=null;
		if(appVersionMapper.addAppVersion(appVersion)>0){
			flag=true;
			vId=appVersion.getId();
		}
		if(appInfoMapper.updateVesionId(vId, appVersion.getAppId())> 0&& flag){
			flag=true;
		}
         return flag;
	}
	@Override
	public AppVersion getAppVersionById(Integer id) throws Exception {
		
		return appVersionMapper.getAppVersionById(id);
	}
	@Override
	public boolean deleteApkFile(Integer id) throws Exception {
		boolean flag = false;
		if(appVersionMapper.deleteApkFile(id) > 0){
			flag = true;
		}
		return flag;
		
	}
	@Override
	public boolean modify(AppVersion appVersion) throws Exception {
		boolean flag=false;
		if(appVersionMapper.modify(appVersion)>0){
			flag=true;
		}
		return flag;
	}

}
