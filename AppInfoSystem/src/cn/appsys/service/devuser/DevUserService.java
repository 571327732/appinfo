package cn.appsys.service.devuser;


import cn.appsys.pojo.DevUser;

public interface DevUserService {

	//登录
	DevUser login(String devCode,String password);
}
