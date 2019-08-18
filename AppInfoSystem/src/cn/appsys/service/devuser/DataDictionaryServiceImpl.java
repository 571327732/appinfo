package cn.appsys.service.devuser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.datadictionary.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;

@Service("dataDictionaryService")
public class DataDictionaryServiceImpl implements DataDictionaryService {

	@Resource
	private DataDictionaryMapper dataDictionaryMapper;
	@Override
	public List<DataDictionary> getDataDictionarieList(String typeCode)
			throws Exception {
		return dataDictionaryMapper.getDataDictionarieList(typeCode);
	}

}
