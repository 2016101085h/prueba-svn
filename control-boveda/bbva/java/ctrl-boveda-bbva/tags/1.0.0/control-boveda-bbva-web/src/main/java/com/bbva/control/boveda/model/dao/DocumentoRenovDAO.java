package com.bbva.control.boveda.model.dao;

import java.util.List;
import java.util.Map;

import com.bbva.control.boveda.bean.DocumentoBean;

public interface DocumentoRenovDAO {

	List<DocumentoBean> listDocRenovado(Map<String, Object> mapRenov) throws Exception;

}
