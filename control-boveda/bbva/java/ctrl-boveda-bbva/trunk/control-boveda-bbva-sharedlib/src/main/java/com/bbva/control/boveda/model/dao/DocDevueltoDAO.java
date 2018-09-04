package com.bbva.control.boveda.model.dao;

import java.util.List;
import java.util.Map;

import com.bbva.control.boveda.bean.DocDevueltoBean;

public interface DocDevueltoDAO {

	List<DocDevueltoBean> listDevueltos (Map<String, Object> map) throws Exception;

	void insertarRegistro(DocDevueltoBean itemDocDev) throws Exception;

	void deleteRegistro(DocDevueltoBean itemDocDev) throws Exception;
}
