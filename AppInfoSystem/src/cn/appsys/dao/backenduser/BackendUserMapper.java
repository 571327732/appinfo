package cn.appsys.dao.backenduser;

import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.BackendUser;

public interface BackendUserMapper {

	BackendUser getLoginUser(@RequestParam("userCode")String userCode);
}
