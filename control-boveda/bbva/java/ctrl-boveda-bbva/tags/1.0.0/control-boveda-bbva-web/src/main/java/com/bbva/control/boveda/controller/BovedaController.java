package com.bbva.control.boveda.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import pe.gob.sunat.framework.spring.util.conversion.SojoUtil;
import pe.gob.sunat.framework.spring.web.view.JsonView;

import com.bbva.control.boveda.bean.ControlDocBean;
import com.bbva.control.boveda.bean.DataJsonBean;
import com.bbva.control.boveda.bean.DetalleControlDocBean;
import com.bbva.control.boveda.service.ProcesarBovedaService;
import com.bbva.control.boveda.util.Constantes;
import com.bbva.control.boveda.util.Util;

public class BovedaController extends MultiActionController{
	
	private static final Log log = LogFactory.getLog(BovedaController.class);
	
	private JsonView jsonView;
	private ProcesarBovedaService procesarBovedaService;
	
	public ModelAndView mostrarAlertas(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView("Alertas");
		request.getSession().setAttribute("retorno", "false");
		log.debug("Inicio de metodo: " + BovedaController.class);
		return modelAndView;
	}
	
	public ModelAndView mostrarFormConsulta(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView("MenuConsultas");
		request.getSession().setAttribute("retorno", "false");
		log.debug("Inicio de metodo: " + BovedaController.class);
		return modelAndView;
	}
	
	public ModelAndView formConsultaGeneral(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView("ConsultaGeneral");
		System.out.println("Inicio de metodo: formConsultaGeneral");
		log.debug("Inicio de metodo: " + BovedaController.class);
		return modelAndView;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelAndView formConsultaEspecifica(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView("ConsultaEspecifica");
		System.out.println("Inicio de metodo: formConsultaEspecifica");
		List<ControlDocBean> listaDocumentos = new ArrayList();
		Map<String, Object> resultado = new HashMap<String, Object>();
		String soles = "0.00";
		String dolares = "0.00";
		if (request.getSession().getAttribute("retorno")!=null && request.getSession().getAttribute("retorno").equals("true")) {
			listaDocumentos = (List<ControlDocBean>) request.getSession().getAttribute("listaDocumentos");
			soles = (String) request.getSession().getAttribute("soles");
			dolares = (String) request.getSession().getAttribute("dolares");
		}
		List<Map<String, Object>> listAgenciasMap = null;
		try {
			listAgenciasMap = procesarBovedaService.listaAgencias();
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR: " + e);
		}
		modelAndView.addObject("listaAgencias", SojoUtil.toJson(listAgenciasMap));
		modelAndView.addObject("listaDocumentos", SojoUtil.toJson(listaDocumentos));
		resultado.put("listaDocumentos", listaDocumentos);
		modelAndView.addObject("soles", SojoUtil.toJson(soles) );
		modelAndView.addObject("dolares", SojoUtil.toJson(dolares) );
		modelAndView.addObject("resultado", resultado);
		log.debug("Inicio de metodo: " + BovedaController.class);
		return modelAndView;
	}
	
	public ModelAndView consultarForm(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		
		ModelAndView model = null;
		Map<String, Object> resultado = new HashMap<String, Object>();
		List<ControlDocBean> listDocu = new ArrayList<ControlDocBean>();//request.getSession().getAttribute("listaDocumentos")
		request.getSession().removeAttribute("listaDocumentos");
		model = new ModelAndView(jsonView);
		
		try {
			String operador 	= ((String)request.getParameter("valOperador"));
			String estadoAct	= ((String)request.getParameter("valEstado"));
			String planilla		= ((String)request.getParameter("valPlanilla"));
			String plaza		= ((String)request.getParameter("valPlaza"));
			String cartera 		= ((String)request.getParameter("valCartera"));
			String servicio		= ((String)request.getParameter("valServicio"));
			String tipoMov		= ((String)request.getParameter("valTipoMov"));
			String agencia		= ((String)request.getParameter("valAgencia"));
			String insCobro		= ((String)request.getParameter("valInsCobro"));
			String tipoMoneda	= ((String)request.getParameter("valTipoMone"));
			String anhioVenc	= ((String)request.getParameter("valAnhioVenc"));
			String tipoDocu 	= ((String)request.getParameter("valTipoDocu"));
			String resumen		= ((String)request.getParameter("valResumen"));
			String formaPago  	= ((String)request.getParameter("valFormaPago"));
			String fecProcIni	= ((String)request.getParameter("valFecProcIni"));
			String fecProcFin	= ((String)request.getParameter("valFecProcFin"));
			String juliIni 		= ((String)request.getParameter("valJuliIni"));
			//String juliFin 		= ((String)request.getParameter("valJuliFin"));
			String letraIni		= ((String)request.getParameter("valLetraIni"));
			String letraFin		= ((String)request.getParameter("valLetraFin"));
			
			Map<String, Object> param = new HashMap<String, Object>();
				param.put("operador", operador);
			if(estadoAct.equals("1")){
				param.put("estadoActSi", Constantes.EST_ACT_INGRESO);
			}else if(estadoAct.equals("0")){
				param.put("estadoActNo", Constantes.EST_ACT_SALIDA);
			}
			
			if(planilla!=null && !planilla.equals("")){
				param.put("planilla", planilla);
			}
			
			if(plaza!=null && !plaza.equals("")){
				param.put("plaza", plaza);
			}
			
			if(cartera!=null && !cartera.equals("")){
				param.put("cartera", cartera);
			}
			
			if(!servicio.equals("00")){
				param.put("servicio", servicio);
			}
			
			if(!tipoMov.equals("00")){
				param.put("tipoMov", tipoMov);
			}
			
			if(!agencia.equals("00")){
				param.put("agencia", agencia);
			}
			
			if(!insCobro.equals("00")){
				param.put("insCobro", insCobro);
			}
			
			if(!tipoMoneda.equals("00")){
				param.put("tipoMoneda", tipoMoneda);
			}
			
			if(!anhioVenc.equals("")){
				param.put("anhioVenc", anhioVenc);
				param.put("anhioVencIni", anhioVenc.concat("0101"));
				param.put("anhioVencFin", anhioVenc.concat("1231"));
			}
			
			if(!tipoDocu.equals("00")){
				param.put("tipoDocumento", tipoDocu);
			}
				
			if(!resumen.equals("00")){
				param.put("ind_ingreso_salida", resumen);
			}
			
			if(!formaPago.equals("00")){
				param.put("formaPago", formaPago);
			}
			
			if(!fecProcIni.equals("") && !fecProcFin.equals("")){
				String[] spFecIni = fecProcIni.split("/");
				String[] spFecFin = fecProcFin.split("/");
				param.put("fecProcIni", spFecIni[2].concat(spFecIni[1].concat(spFecIni[0])) );
				param.put("fecProcFin", spFecFin[2].concat(spFecFin[1].concat(spFecFin[0])) );
				param.put("consFecProc", "ok");
			}
			
			if(!juliIni.equals("") ){
				param.put("consJuli", "ok");
				param.put("juliIni", juliIni);
			}
			if(!letraIni.equals("") && !letraFin.equals("")){
				param.put("consLetra", "ok");
				param.put("letraIni", letraIni);
				param.put("letraFin", letraFin);
			}
			listDocu = procesarBovedaService.consultaEspecifica(param);
			int cantidad = listDocu.size();
			
			Map<String, Object> mapImportes = null;
			mapImportes = procesarBovedaService.obtenerImportes(param);
			if(listDocu.size()<20000){
				model.addObject("listaDocumentos", SojoUtil.toJson(listDocu.subList(0, cantidad)));
			}else{
				model.addObject("listaDocumentos", SojoUtil.toJson(listDocu.subList(0, 20000)));
			}
			if(mapImportes!=null){
				if(tipoMoneda.equals("00")){
					resultado.put("soles", Util.formatearImporte(mapImportes.get("soles")==null ? "0" : mapImportes.get("soles").toString() ));
					resultado.put("dolares", Util.formatearImporte(mapImportes.get("dolares")==null ? "0" : mapImportes.get("dolares").toString()));
				}else if(tipoMoneda.equals("1")){
					resultado.put("soles", Util.formatearImporte(mapImportes.get("soles")==null ? "0" : mapImportes.get("soles").toString() ));
					resultado.put("dolares", Util.formatearImporte("0"));					
				}else if(tipoMoneda.equals("2")){
					resultado.put("soles", Util.formatearImporte("0"));
					resultado.put("dolares", Util.formatearImporte(mapImportes.get("dolares")==null ? "0" : mapImportes.get("dolares").toString()));
				}
				
			}else{
				resultado.put("soles", Util.formatearImporte("0"));
				resultado.put("dolares", Util.formatearImporte("0"));
			}
			
			resultado.put("cantidad", cantidad);
			model.addObject("resultado", resultado);
			request.getSession().setAttribute("listaDocumentos", listDocu);
			mapImportes = null;
			request.getSession().setAttribute("soles", resultado.get("soles"));
			request.getSession().setAttribute("dolares", resultado.get("dolares"));
			Util.pasarGarbageCollector();
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return model;
	}
	
	public ModelAndView limpiarCache(HttpServletRequest request, HttpServletResponse response){
		ModelAndView model = null;
		model = new ModelAndView(jsonView);
		Util.pasarGarbageCollector();
		return model;
	}
	
	@SuppressWarnings("unused")
	private List<ControlDocBean> completarDatosLista(List<ControlDocBean> listDocu) {
		List<ControlDocBean> newLista= new ArrayList<ControlDocBean>();
		ControlDocBean newControl = new ControlDocBean();
		try {
			for(ControlDocBean item: listDocu){
				newControl = new ControlDocBean();
				newControl.setCod_agencia(item.getCod_agencia() == null ? "" : item.getCod_agencia());
				newControl.setCod_clase_documento(item.getCod_clase_documento() == null ? "" : item.getCod_clase_documento());
				newControl.setCod_juliano(item.getCod_juliano() == null ? "" : item.getCod_juliano());
				newControl.setCod_operador(item.getCod_operador() == null ? "" : item.getCod_operador());
				newControl.setCod_planilla(item.getCod_planilla() == null ? "" : item.getCod_planilla() );
				newControl.setCod_plaza(item.getCod_plaza() == null ? "" : item.getCod_plaza());
				newControl.setCod_servicio(item.getCod_servicio() == null ? "" : item.getCod_servicio());
//				newControl.setCod_tipo_doc_identidad(item.getCod_tipo_doc_identidad()  == null ? "" : item.getCod_tipo_doc_identidad() );
				newControl.setCod_tipo_moneda(item.getCod_tipo_moneda()  == null ? "" : item.getCod_tipo_moneda());
//				newControl.setDes_direccion(item.getDes_direccion()  == null ? "" : item.getDes_direccion());
				newControl.setDes_nombre(item.getDes_nombre()  == null ? "" : item.getDes_nombre());
				newControl.setDes_observacion_ubica(item.getDes_observacion_ubica()  == null ? "" : item.getDes_observacion_ubica());
//				newControl.setFec_desembolso(Util.formatearFecha(item.getFec_desembolso()));
//				newControl.setFec_giro(Util.formatearFecha(item.getFec_giro()) );
				newControl.setFec_proceso(Util.formatearFecha(item.getFec_proceso()) );
				newControl.setFec_vencimiento(Util.formatearFecha(item.getFec_vencimiento()));
				newControl.setImp_importe(formatearImporteSumar(item.getImp_importe(), item.getCod_tipo_moneda()) );
//				newControl.setImp_prestamo_vinculado(Util.formatearImporte(item.getImp_prestamo_vinculado()) );
//				newControl.setInd_exoneracion_impuest(item.getInd_exoneracion_impuest()  == null ? "" : item.getInd_exoneracion_impuest() );
				newControl.setInd_ingreso_salida(Util.descripIndicador(item.getInd_ingreso_salida()) );
				newControl.setInd_instruccion_cobro(item.getInd_instruccion_cobro()  == null ? "" : item.getInd_instruccion_cobro());
//				newControl.setNum_cliente(item.getNum_cliente()  == null ? "" : item.getNum_cliente() );
				newControl.setNum_cuenta(item.getNum_cuenta()  == null ? "" : item.getNum_cuenta());
//				newControl.setNum_cuenta_aceptante(item.getNum_cuenta_aceptante()  == null ? "" : item.getNum_cuenta_aceptante());
//				newControl.setNum_cuenta_factoring(item.getNum_cuenta_factoring()  == null ? "" : item.getNum_cuenta_factoring());
//				newControl.setNum_doc_identidad(item.getNum_doc_identidad()  == null ? "" : item.getNum_doc_identidad() );
//				newControl.setNum_porcentaje_renova(item.getNum_porcentaje_renova()  == null ? "" : item.getNum_porcentaje_renova());
//				newControl.setNum_tasa_garantia(item.getNum_tasa_garantia()  == null ? "" : item.getNum_tasa_garantia() );
//				newControl.setNum_tasa_opcional(item.getNum_tasa_opcional()  == null ? "" : item.getNum_tasa_opcional());
				
				newControl.setNum_letra(item.getNum_letra()  == null ? "" : item.getNum_letra());
				newControl.setCod_tipo_movimiento(item.getCod_tipo_movimiento()  == null ? "" : item.getCod_tipo_movimiento());
				newControl.setFec_nuevo_vencimiento(Util.formatearFecha(item.getFec_nuevo_vencimiento()) );
				newControl.setImp_nuevo_importe(Util.formatearImporte(item.getImp_nuevo_importe()) );
				newLista.add(newControl);
				
			}
			
			listDocu.clear();
			listDocu = null;
			Util.pasarGarbageCollector();
		
//		for (ControlDocBean obj : newLista) {
//        	jsonObj.add(SojoUtil.toJson((ControlDocBean) obj));
//        	System.out.println(jsonObj);
//        }
//		newLista.clear();
//		newLista = null;
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return newLista;
	}
	
	private String formatearImporteSumar(String imp_importe, String tipo_moneda) {
		String formatNum = "";
		double soles = 0;
		double dolares = 0;
		if(imp_importe!=null && !imp_importe.equals("")){
			try {
				double douNum = new Double(imp_importe);
				douNum = douNum / 100;
				if(tipo_moneda.equals("1")){
					soles = soles + douNum;
				}else{
					dolares = dolares + douNum;
				}
				double valor = new Double(douNum);
		        
		        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
			    simbolo.setDecimalSeparator('.');
			    simbolo.setGroupingSeparator(',');
		        
		        DecimalFormat formatter = new DecimalFormat("#,##0.00", simbolo);
		        formatNum = formatter.format(valor);
			} catch (Exception e) {
				log.error("ERROR: " + e);
			}
		}
		
		return formatNum;
	}
	
	public ModelAndView consultaGeneral(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		ModelAndView model = null;
		Map<String, Object> resultado = new HashMap<String, Object>();
		DataJsonBean dataJSON = new DataJsonBean();
//		List<ControlDocBean> listIngresos = new ArrayList<ControlDocBean>();
//		List<ControlDocBean> listSalidas = new ArrayList<ControlDocBean>();
		List<ControlDocBean> listVigSinF = new ArrayList<ControlDocBean>();
		Map<String, Object> param = new HashMap<String, Object>();
		String anhioIni = "";
		String anhioFin = "";
		
		model = new ModelAndView(jsonView);
		
		try {
			param.put("ind_ingreso_salida", "0");
			param.put("cod_tipo_moneda", "1");
//			listSalidas = procesarBovedaService.consultaGeneral(param);
			Map<String, Object> mapSalSol = procesarBovedaService.obtenerImpXIndXMoneda(param);
			
			param.put("cod_tipo_moneda", "2");
			Map<String, Object> mapSalDol = procesarBovedaService.obtenerImpXIndXMoneda(param);
			
			double impIngSol = 0, impIngDol = 0;
			double impSalSol = 0, impSalDol = 0;
			double impVigSol = 0, impVigDol = 0;
			int cantSolesIng = 0, cantDolaresIng = 0;
			int cantSolesSal = 0, cantDolaresSal = 0;
			int cantSolesVig = 0, cantDolaresVig = 0;
			
//			for(ControlDocBean item : listSalidas){
//				if(item.getCod_tipo_moneda().equals("1")){ //Soles
//					impSalSol+=  new Double(item.getImp_importe());
//					cantSolesSal++;
//				}else if(item.getCod_tipo_moneda().equals("2")){
//					impSalDol+=  new Double(item.getImp_importe());
//					cantDolaresSal++;
//				}
//			}
			
			impSalSol = new Double(mapSalSol.get("imp_importe").toString()) / 100;
			cantSolesSal = Integer.parseInt(mapSalSol.get("cantidad").toString());
			impSalDol = new Double(mapSalDol.get("imp_importe").toString()) / 100;
			cantDolaresSal = Integer.parseInt(mapSalDol.get("cantidad").toString());
			
			System.out.println("solSal: " + impSalSol);
			System.out.println("dolSal: " + impSalDol);
			
			param.put("ind_ingreso_salida", "1");
//			listIngresos = procesarBovedaService.consultaGeneral(param);
			param.put("cod_tipo_moneda", "1");
			Map<String, Object> mapIngSol = procesarBovedaService.obtenerImpXIndXMoneda(param);
			
			param.put("cod_tipo_moneda", "2");
			Map<String, Object> mapIngDol = procesarBovedaService.obtenerImpXIndXMoneda(param);
			
//			for(ControlDocBean item : listIngresos){
//				if(item.getCod_tipo_moneda().equals("1")){ //Soles
//					impIngSol+=  new Double(item.getImp_importe());
//					cantSolesIng++;
//				}else if(item.getCod_tipo_moneda().equals("2")){
//					impIngDol+=  new Double(item.getImp_importe());
//					cantDolaresIng++;
//				}
//			}
			
//			impIngSol = impIngSol / 100;
//			impIngDol = impIngDol / 100;
			
			impIngSol = new Double(mapIngSol.get("imp_importe").toString()) / 100;
			cantSolesIng = Integer.parseInt(mapIngSol.get("cantidad").toString());
			impIngDol = new Double(mapIngDol.get("imp_importe").toString()) / 100;
			cantDolaresIng = Integer.parseInt(mapIngDol.get("cantidad").toString());
			
			System.out.println("solIng: " + Math.abs(impIngSol));
			System.out.println("dolIng: " + Math.abs(impIngDol));
			
			param.put("ind_ingreso_salida", "2");
			listVigSinF = procesarBovedaService.consultaGeneral(param);
			for(ControlDocBean item : listVigSinF){
				if(item.getCod_tipo_moneda().equals("1")){ //Soles
					impVigSol+=  new Double(item.getImp_importe());
					cantSolesVig++;
				}else if(item.getCod_tipo_moneda().equals("2")){
					impVigDol+=  new Double(item.getImp_importe());
					cantDolaresVig++;
				}
			}
			
			impVigSol = impVigSol / 100;
			impVigDol = impVigDol / 100;
			
			System.out.println("solVig: " + impVigSol);
			System.out.println("dolVig: " + impVigDol);
			
			DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
		    simbolo.setDecimalSeparator('.');
		    simbolo.setGroupingSeparator(',');
		    DecimalFormat formatter = new DecimalFormat("#,##0.00", simbolo); 
			
			
			resultado.put("estado", "OK");
//			resultado.put("contTot", listIngresos.size() - listSalidas.size() - listVigSinF.size());
			resultado.put("contTot", 
					(Integer.parseInt(mapIngSol.get("cantidad").toString()) + Integer.parseInt(mapIngDol.get("cantidad").toString())) - 
					(Integer.parseInt(mapSalSol.get("cantidad").toString()) + Integer.parseInt(mapSalDol.get("cantidad").toString())) - 
					listVigSinF.size());
			resultado.put("contTotSol", formatter.format(impIngSol - impSalSol - impVigSol));
			resultado.put("contTotDol", formatter.format(impIngDol - impSalDol - impVigDol));
			resultado.put("cantSolTot", cantSolesIng - cantSolesSal - cantSolesVig);
			resultado.put("cantDolTot", cantDolaresIng - cantDolaresSal - cantDolaresVig);
			
//			resultado.put("contIng", listIngresos.size());
			resultado.put("contIng", Integer.parseInt(mapIngSol.get("cantidad").toString()) + Integer.parseInt(mapIngDol.get("cantidad").toString()));
			resultado.put("contIngSol", formatter.format(impIngSol));
			resultado.put("contIngDol",formatter.format(impIngDol));
			resultado.put("cantSolesIng", cantSolesIng);
			resultado.put("cantDolaresIng", cantDolaresIng);
			
//			resultado.put("contSal", listSalidas.size());
			resultado.put("contSal", Integer.parseInt(mapSalSol.get("cantidad").toString()) + Integer.parseInt(mapSalDol.get("cantidad").toString()) );
			resultado.put("contSalSol", formatter.format(impSalSol));
			resultado.put("contSalDol", formatter.format(impSalDol));
			resultado.put("cantSolesSal", cantSolesSal);
			resultado.put("cantDolaresSal", cantDolaresSal);
			
			resultado.put("contVig", listVigSinF.size());
			resultado.put("contVigSol", formatter.format(impVigSol));
			resultado.put("contVigDol", formatter.format(impVigDol));
			resultado.put("cantSolesVig", cantSolesVig);
			resultado.put("cantDolaresVig", cantDolaresVig);
			
			resultado.put("anhioIni", anhioIni);
			resultado.put("anhioFin", anhioFin);
			request.getSession().setAttribute("datosGeneral", resultado);
//			request.getSession().setAttribute("listaVigentes", listVigSinF);
//			request.getSession().setAttribute("listaSalidas", listSalidas);
//			request.getSession().setAttribute("listaIngresos", listIngresos);
			
			dataJSON.setRespuesta("ok", null, resultado);  
			model.addObject("dataJSON", dataJSON);
			
			Util.pasarGarbageCollector();
			
		} catch (Exception e) {
			log.error("ERROR: " + e);
			e.printStackTrace();
		}
		return model;
	}
	
	
	
	public ModelAndView procesarCarga(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelo = null;
		try {
			log.debug("INICIO - procesarCarga");
			procesarBovedaService.cargaInicial();
			modelo =  new ModelAndView("Exito");
			log.debug("FIN - procesarCarga");
			
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return modelo;
	}
	
	public ModelAndView procesarIngresos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelo = null;
		try {
			log.debug("INICIO - procesarIngresos");
			procesarBovedaService.procesarIngresos();
			modelo =  new ModelAndView("Exito");
			log.debug("FIN - procesarIngresos");
			
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return modelo;
	}
	public ModelAndView procesarSalidas(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelo = null;
		try {
			log.debug("INICIO - procesarSalidas");
			Date fecha = Calendar.getInstance().getTime();
	        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	        String strFecha = formatter.format(fecha);
			procesarBovedaService.procesarSalidas("20171003");
			modelo =  new ModelAndView("Exito");
			log.debug("FIN - procesarSalidas");
			
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return modelo;
	}
	
	public ModelAndView procesarRenovados(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelo = null;
		try {
			log.debug("INICIO - procesarRenovados");
			procesarBovedaService.procesarRenovados("");
			modelo =  new ModelAndView("Exito");
			log.debug("FIN - procesarRenovados");
			
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return modelo;
	}
	
	public ModelAndView procesarProtestados(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelo = null;
		try {
			log.debug("INICIO - procesarProtestados");
			procesarBovedaService.procesarProtestados("");
			modelo =  new ModelAndView("Exito");
			log.debug("FIN - procesarProtestados");
			
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return modelo;
	}
	
	public ModelAndView verDetalle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = null;
		Map<String, Object> resultado = new HashMap<String, Object>();
		DataJsonBean dataJSON = new DataJsonBean();
		List<ControlDocBean> listDocu = new ArrayList<ControlDocBean>();
		List<DetalleControlDocBean> listDetalle =  new ArrayList<DetalleControlDocBean>();
		List<DetalleControlDocBean> listCompletaDetalle =  new ArrayList<DetalleControlDocBean>();
		Map<String,Object> mapAgencia = new HashMap<String,Object>();
		model = new ModelAndView(jsonView);
		log.debug("INICIO - verDetalle");
		try {
			
			String juliano 	= (String)request.getParameter("cod_juliano");
			request.setAttribute("cod_juliano", juliano);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("juliano", juliano);
			
			listDocu = procesarBovedaService.consultaEspecifica(param);
			
			resultado.put("listaDocumentos", listDocu);
			
			param.put("cod_juliano", juliano);
			listDetalle = procesarBovedaService.listDetalle(param);
			
			listCompletaDetalle = completarDatosMovimiento(listDetalle);
			
			mapAgencia = procesarBovedaService.obtenerAgencia(listDocu.get(0).getCod_agencia());
			
			request.getSession().setAttribute("listDetalle", listCompletaDetalle);
			
			request.getSession().setAttribute("operador", "BBVA - BANCO CONTINENTAL");
			request.getSession().setAttribute("agencia", listDocu.get(0).getCod_agencia());
			request.getSession().setAttribute("desAgencia", mapAgencia == null ? "" : mapAgencia.get("desAgencia").toString());
			request.getSession().setAttribute("claseDocu", listDocu.get(0).getCod_clase_documento());
			request.getSession().setAttribute("nombre", listDocu.get(0).getDes_nombre());
			request.getSession().setAttribute("direccion", listDocu.get(0).getDes_direccion() == null ? "" : listDocu.get(0).getDes_direccion());
			request.getSession().setAttribute("juliano", listDocu.get(0).getCod_juliano());
			request.getSession().setAttribute("estado", Util.descripObservacion(listDocu.get(0).getDes_observacion_ubica()) );
			request.getSession().setAttribute("servicio", listDocu.get(0).getCod_servicio());
			dataJSON.setRespuesta("ok", null, resultado);  
			model.addObject("dataJSON", dataJSON);
			
			log.debug("FIN - verDetalle");
			
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		}
		return model;
	}
	
	private List<DetalleControlDocBean> completarDatosMovimiento(List<DetalleControlDocBean> listDetalle) {
		List<DetalleControlDocBean> newLista= new ArrayList<DetalleControlDocBean>();
		DetalleControlDocBean newDetalle = new DetalleControlDocBean();
		try {
			for(DetalleControlDocBean item: listDetalle){
				newDetalle = new DetalleControlDocBean();
				newDetalle.setNum_letra(item.getNum_letra()  == null ? "" : item.getNum_letra());
				newDetalle.setCod_juliano(item.getCod_juliano());
				newDetalle.setCod_cartera(item.getCod_cartera() == null ? "" : item.getCod_cartera());
				newDetalle.setFec_movimiento(Util.formatearFecha(item.getFec_movimiento()));
				newDetalle.setFec_vencimiento(Util.formatearFecha(item.getFec_vencimiento()));
				newDetalle.setCod_plaza_cobro(item.getCod_plaza_cobro() == null ? "" : item.getCod_plaza_cobro());
				newDetalle.setCod_plaza_actual(item.getCod_plaza_actual() == null ? "" : item.getCod_plaza_actual());
				newDetalle.setCod_tipo_movimiento(item.getCod_tipo_movimiento()  == null ? "" : item.getCod_tipo_movimiento());
				newDetalle.setImp_importe(Util.formatearImporte(item.getImp_importe()) );
				newDetalle.setCod_oficina_movimiento(item.getCod_oficina_movimiento() == null ? "" : item.getCod_oficina_movimiento());
				newDetalle.setCod_tipo_documento(item.getCod_tipo_documento() == null ? "" : item.getCod_tipo_documento());
				newDetalle.setCod_forma_pago(item.getCod_forma_pago() == null ? "" : item.getCod_forma_pago());
				newDetalle.setFec_nuevo_vencimiento(Util.formatearFecha(item.getFec_nuevo_vencimiento()) );
				newDetalle.setImp_nuevo_importe(Util.formatearImporte(item.getImp_nuevo_importe()) );
				newDetalle.setNum_cuenta_corriente(item.getNum_cuenta_corriente() == null ? "" : item.getNum_cuenta_corriente());
				newDetalle.setDes_nombre_titular(item.getDes_nombre_titular() == null ? "" : item.getDes_nombre_titular());
				newDetalle.setDes_direccion_titular(item.getDes_direccion_titular() == null ? "" : item.getDes_direccion_titular());
				newDetalle.setFec_cargado(Util.formatearFecha(item.getFec_cargado() == null ? "" : item.getFec_cargado()) );
				newDetalle.setInd_ingreso_salida(Util.descripIndicador(item.getInd_ingreso_salida().trim()) );
				newDetalle.setCod_observacion_ubica(Util.descripObservacion(item.getCod_observacion_ubica()) );
				
				newLista.add(newDetalle);
				
			}
		} catch (Exception e) {
			log.error(e);
		}
		return newLista;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView mostrarListaDetalle(HttpServletRequest request, HttpServletResponse response) throws Exception{

		ModelAndView modelAndView = new ModelAndView("DetalleConsulta");
		DataJsonBean dataJSON = new DataJsonBean();
		Map<String, Object> resultado = new HashMap<String, Object>();
		List<DetalleControlDocBean> listDetalle =  new ArrayList<DetalleControlDocBean>();
		
		System.out.println("Inicio de metodo: mostrarListaDetalle");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cod_juliano",  request.getParameter("juliano") );
		listDetalle = (List<DetalleControlDocBean>) request.getSession().getAttribute("listDetalle");
		
		dataJSON.setRespuesta("ok", null, resultado);  
		modelAndView.addObject("listDetalle", SojoUtil.toJson(listDetalle));
		modelAndView.addObject("operador", 	SojoUtil.toJson(request.getSession().getAttribute("operador")) );
		modelAndView.addObject("agencia", 	SojoUtil.toJson(request.getSession().getAttribute("agencia")) );
		modelAndView.addObject("desAgencia", SojoUtil.toJson(request.getSession().getAttribute("desAgencia")) );
		modelAndView.addObject("claseDocu", SojoUtil.toJson(request.getSession().getAttribute("claseDocu")) );
		modelAndView.addObject("nombre", 	SojoUtil.toJson(request.getSession().getAttribute("nombre")) );
		modelAndView.addObject("direccion", SojoUtil.toJson(request.getSession().getAttribute("direccion")) );
		modelAndView.addObject("juliano", 	SojoUtil.toJson(request.getSession().getAttribute("juliano")) );
		modelAndView.addObject("estado", 	SojoUtil.toJson(request.getSession().getAttribute("estado")) );
		modelAndView.addObject("servicio", 	SojoUtil.toJson(request.getSession().getAttribute("servicio")) );
		
		modelAndView.addObject("dataJSON", dataJSON);
		
		request.getSession().setAttribute("retorno", "true");
		
		log.debug("Inicio de metodo: " + BovedaController.class);
		return modelAndView;
			
	}
	
	public ModelAndView generarReporteMovimientos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> parametros = new HashMap<String, Object>();
		try {
			
			parametros.put("cod_juliano", request.getParameter("juliano") == null ? "" : request.getParameter("juliano"));			
			procesarBovedaService.generarReporte(parametros, response);
			
		}
		catch (Exception ex) {
			log.error(ex);
			ex.printStackTrace();
		} 
		return null;
	} 
	
	@SuppressWarnings({ "unused", "unchecked" })
	public ModelAndView generarReporteMasivo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> parametros = new HashMap<String, Object>();
		try {
			
			List<ControlDocBean> listaDocumentos = new ArrayList<ControlDocBean>();
			listaDocumentos = (List<ControlDocBean>) request.getSession().getAttribute("listaDocumentos");
			
			procesarBovedaService.generarReporteMasivo(listaDocumentos, response);
			Util.pasarGarbageCollector();
			return null;
			
		}
		catch (Exception ex) {
			log.error(ex);
			ex.printStackTrace();
			return null;
		} 
	}
	
	public ModelAndView resumenDocumentos(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try {
			String tipoReporte 	= ((String)request.getParameter("tipo"));
			procesarBovedaService.generarReporteDocumentos(tipoReporte, response);
			
		} catch (Exception e) {
			log.error("ERROR: " + e);
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView generarReportePorAnhio(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			Map<String, Object> mapRepo = new HashMap<String, Object>();
			mapRepo = (Map<String, Object>) request.getSession().getAttribute("datosGeneral");
			
			List<ControlDocBean> listaIngresos = (List<ControlDocBean>) request.getSession().getAttribute("listaIngresos");
			String anhioIni = mapRepo.get("anhioIni").toString();
			//String anhioFin = mapRepo.get("anhioFin").toString();
			
			Map<String, Object> mapExpo = new HashMap<String, Object>();
//			soles = 0;
//			dolares = 0;
			for(ControlDocBean item : listaIngresos){
				if(anhioIni.equals(item.getFec_proceso().substring(0, 4))){
					mapExpo.put(anhioIni, formatearImporteSumar(item.getImp_importe(), item.getCod_tipo_moneda()))	;
				}
			}
//			System.out.println(soles);
//			System.out.println(dolares);
			procesarBovedaService.generarReportePorAnhio(mapRepo, listaIngresos, response);
		} catch (Exception e) {
			log.error("ERROR: " +e);
		}
		return null;
	}

	public ProcesarBovedaService getProcesarBovedaService() {
		return procesarBovedaService;
	}

	public void setProcesarBovedaService(ProcesarBovedaService procesarBovedaService) {
		this.procesarBovedaService = procesarBovedaService;
	}

	public JsonView getJsonView() {
		return jsonView;
	}

	public void setJsonView(JsonView jsonView) {
		this.jsonView = jsonView;
	}
	
}
