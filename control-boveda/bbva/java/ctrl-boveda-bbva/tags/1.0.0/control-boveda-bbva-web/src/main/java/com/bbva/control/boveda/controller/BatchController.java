package com.bbva.control.boveda.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.bbva.control.boveda.service.ProcesarBovedaService;

public class BatchController extends MultiActionController{
	
	private static final Log log = LogFactory.getLog(BatchController.class);
	
	private ProcesarBovedaService procesarBovedaService;
	
	public void procesarCarga() {
		try {
			log.debug("INICIO - procesarCarga");
			procesarBovedaService.cargaInicial();
			log.debug("FIN - procesarCarga");
			
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
	}
	
	public void procesarIngresos() {
		try {
			System.out.println("Ingresos");
			log.debug("INICIO - procesarIngresos");
			procesarBovedaService.procesarIngresos();
			log.debug("FIN - procesarIngresos");
			
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
	}
	
	public void procesarSalidas(){
		try {
			System.out.println("Salidas");
			log.debug("INICIO - procesarSalidas");
			Date fecha = Calendar.getInstance().getTime();
	        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	        String strFecha = formatter.format(fecha);
			procesarBovedaService.procesarSalidas(strFecha);
			
			procesarBovedaService.procesarRenovados(strFecha);
			
			procesarBovedaService.procesarProtestados(strFecha);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR: " +e);
		}
	}
	
	public ProcesarBovedaService getProcesarBovedaService() {
		return procesarBovedaService;
	}

	public void setProcesarBovedaService(ProcesarBovedaService procesarBovedaService) {
		this.procesarBovedaService = procesarBovedaService;
	}

	
}
