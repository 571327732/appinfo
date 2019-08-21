package cn.appsys.service.devuser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {

	@Resource
	private AppVersionMapper appVersionMapper;
	@Override
	public List<AppVersion> getAppVersionByVId(Integer appId) {
		
		return appVersionMapper.getAppVersionByVId(appId);
	}

}
