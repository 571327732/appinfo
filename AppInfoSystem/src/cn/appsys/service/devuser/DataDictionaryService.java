package cn.appsys.service.devuser;

import java.util.List;


import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryService {

	/**
	 * 根据类型编码获取类型集合
	 * @param typeCode
	 * @return 类型集合
	 * @throws Exception
	 */
	List<DataDictionary> getDataDictionarieList(String typeCode)throws Exception;
}
