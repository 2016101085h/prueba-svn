package com.bbva.control.boveda.model.dao;

import java.util.List;
import java.util.Map;

import com.bbva.control.boveda.bean.LetraBean;

public interface LetraDAO {
	
	 List<LetraBean> obtenerDocumentos(Map<String, Object> mapLetra) throws Exception;

}
