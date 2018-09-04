package com.bbva.control.boveda.model.dao;

import java.util.List;

import com.bbva.control.boveda.bean.ControlDocBean;

public interface TempCheqcarDAO {
	
	void insertar(ControlDocBean temp) throws Exception;
	
	List<ControlDocBean> listaTempCh() throws Exception;
	
	void delete() throws Exception;

}
