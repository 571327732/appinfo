package cn.appsys.dao.datadictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryMapper {

	/**
	 * 根据类型编码获取 类型集合
	 * @param typeCode
	 * @return 类型集合
	 */
	List<DataDictionary> getDataDictionarieList(@Param("typeCode")String typeCode)throws Exception;
}
