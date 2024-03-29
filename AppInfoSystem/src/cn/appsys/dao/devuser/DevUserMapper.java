package cn.appsys.dao.devuser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DevUser;

public interface DevUserMapper {

	/**
	 * 根据 devCode获取用户记录
	 * @param devCode
	 * @return 开发者对象
	 */
	DevUser getLoginUser(@Param("devCode")String devCode);
}
