package com.bbva.control.boveda.model.dao;

import java.util.List;
import java.util.Map;

import com.bbva.control.boveda.bean.ControlDocBean;

public interface ControlDocDAO {

	void insertar(ControlDocBean cargaInicialBean) throws Exception;
	
	void insertarCargaInicialMap(List<Map<String, Object>> listCargaInicial) throws Exception;
	
	void updateRegistro(ControlDocBean controlDoc) throws Exception;
	
	ControlDocBean obtenerDocXLetra(Map<String, Object> param) throws Exception;
	
	List<ControlDocBean> obtenerDocMap(Map<String, Object> param) throws Exception;

	List<ControlDocBean> listConsEspecifica(Map<String, Object> param) throws Exception;

	Map<String, Object> docuXServicio(Map<String, Object> mapParam) throws Exception;

	Map<String, String> listConsEspecificaMap(Map<String, Object> param) throws Exception;

	Map<String, Object> obtenerImportes(Map<String, Object> param) throws Exception;

	Map<String, Object> obtenerTotalRegistros(Map<String, Object> param) throws Exception;

	void updateRegistroRenov(ControlDocBean controlDoc) throws Exception;

	Map<String, Object> obtenerImpXIndXMoneda(Map<String, Object> param) throws Exception;
}
