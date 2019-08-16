package cn.appsys.service.devuser;

import cn.appsys.pojo.DevUser;

public interface DevUserService {

	DevUser login(String devCode,String password);//登录
}
