package com.bbva.control.boveda.model.dao;

import java.util.Map;

import com.bbva.control.boveda.bean.DevolucionesBean;

public interface DevolucionesDAO {

	DevolucionesBean obtenerDevoluciones(Map<String, Object> mapDevol) throws Exception;

	void borrarRegistro(DevolucionesBean itemDevolucion) throws Exception;

}
