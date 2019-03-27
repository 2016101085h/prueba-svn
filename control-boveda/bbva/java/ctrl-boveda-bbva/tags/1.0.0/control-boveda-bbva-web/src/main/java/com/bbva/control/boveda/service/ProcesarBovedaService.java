package com.bbva.control.boveda.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bbva.control.boveda.bean.ControlDocBean;
import com.bbva.control.boveda.bean.DetalleControlDocBean;

public interface ProcesarBovedaService {
	
	void cargaInicial() throws Exception;
	
	void procesarIngresos() throws Exception;
	
	void procesarSalidas(String strFecha) throws Exception;
	
	void procesarRenovados(String strFecha) throws Exception;
	
	List<ControlDocBean> consultaGeneral(Map<String, Object> param) throws Exception;

	List<ControlDocBean> consultaEspecifica(Map<String, Object> param) throws Exception;

	void generarReporte(Map<String, Object> parametros, HttpServletResponse response) throws Exception;
	
	List<DetalleControlDocBean> listDetalle(Map<String, Object> param) throws Exception;

	void generarReporteMasivo(List<ControlDocBean> listaDocumentos, HttpServletResponse response) throws Exception;

	void procesarProtestados(String strFecha) throws Exception;

	boolean generarReporteDocumentos(String tipoReporte, HttpServletResponse response) throws Exception;

	List<Map<String, Object>> listaAgencias() throws Exception;

	Map<String, Object> obtenerAgencia(String cod_agencia) throws Exception;

	void generarReportePorAnhio(Map<String, Object> mapRepo, List<ControlDocBean> listaIngresos, HttpServletResponse response) throws Exception;

	Map<String, String> consultaEspecificaMap(Map<String, Object> param) throws Exception;

	Map<String, Object> obtenerImportes(Map<String, Object> param) throws Exception;

	Map<String, Object> obtenerTotalRegistros(Map<String, Object> param) throws Exception;

	Map<String, Object> obtenerImpXIndXMoneda(Map<String, Object> param) throws Exception;
}
