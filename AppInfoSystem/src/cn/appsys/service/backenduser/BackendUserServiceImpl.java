package cn.appsys.service.backenduser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.backenduser.BackendUserMapper;
import cn.appsys.pojo.BackendUser;

@Service("backendUserService")
public class BackendUserServiceImpl implements BackendUserService {

	@Resource
	private BackendUserMapper backendUserMapper;
	@Override
	public BackendUser login(String userCode,String password) {
		BackendUser backendUser=null;
		backendUser=backendUserMapper.getLoginUser(userCode);
		if(backendUser!=null){
			if(backendUser.getUserPassword().equals(password)){
				return backendUser;//登录成功
			}
		}
		return null;
	}

}
