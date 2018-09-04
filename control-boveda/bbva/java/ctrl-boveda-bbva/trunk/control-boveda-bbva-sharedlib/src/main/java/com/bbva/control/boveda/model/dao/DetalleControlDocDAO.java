package com.bbva.control.boveda.model.dao;

import java.util.List;
import java.util.Map;

import com.bbva.control.boveda.bean.DetalleControlDocBean;

public interface DetalleControlDocDAO {

	void insertarRegistro(DetalleControlDocBean detalleCtrl) throws Exception;

	void updateRegistro(DetalleControlDocBean detalle) throws Exception;
	
	List<DetalleControlDocBean> obtenerLista(Map<String, Object> param) throws Exception;
}
