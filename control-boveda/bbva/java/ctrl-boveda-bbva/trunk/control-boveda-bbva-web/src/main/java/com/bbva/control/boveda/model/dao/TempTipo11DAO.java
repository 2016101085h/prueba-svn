package com.bbva.control.boveda.model.dao;

import java.util.List;

import com.bbva.control.boveda.bean.ControlDocBean;

public interface TempTipo11DAO {
	
	void insert(ControlDocBean itemCtrlDoc) throws Exception;
	
	List<ControlDocBean> listaTemp() throws Exception;
	
	void delete() throws Exception;

}
