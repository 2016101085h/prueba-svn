package com.bbva.control.boveda.model.dao;

import java.util.List;
import java.util.Map;

public interface ParametrosDAO {
	
	List<Map<String,Object>> listAgencias() throws Exception;

	Map<String, Object> obtenerAgencia(Map<String, Object> param) throws Exception;

}
