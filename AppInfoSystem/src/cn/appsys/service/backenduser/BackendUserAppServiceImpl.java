package cn.appsys.service.backenduser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;

@Service("backendUserAppService")
public class BackendUserAppServiceImpl implements BackendUserAppService {

	@Resource
	private AppInfoMapper appInfoMapper;

	@Override
	public List<AppInfo> getAppInfolListByStatus(String softwareName,
			Integer status, Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer flatformId, Integer pageSize,
			Integer currentPageNo) throws Exception {
		// TODO Auto-generated method stub
		return appInfoMapper.getAppInfolListByStatus(softwareName, status, categoryLevel1, categoryLevel2, categoryLevel3, flatformId, pageSize, (currentPageNo-1)*pageSize);
	}

	@Override
	public int getAppInfoCountByStatus(String softwareName, Integer status,
			Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer flatformId) throws Exception {
		// TODO Auto-generated method stub
		return appInfoMapper.getAppInfoCountByStatus(softwareName, status, categoryLevel1, categoryLevel2, categoryLevel3, flatformId);
	}
	

}
