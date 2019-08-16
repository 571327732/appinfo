package cn.appsys.service.backenduser;

import cn.appsys.pojo.BackendUser;

public interface BackendUserService {

	BackendUser login(String userCode,String password);//后台登录
}
