package com.bbva.control.boveda.model.dao;

import java.util.List;
import java.util.Map;

import com.bbva.control.boveda.bean.DocRenovadoBean;

public interface DocRenovadoDAO {
	
	void insertarDocumento(DocRenovadoBean docRenov) throws Exception;

	List<DocRenovadoBean> obtener(Map<String, Object> map) throws Exception;

	void deleteRegistro(DocRenovadoBean itemDocRen) throws Exception;

}
