package com.bbva.control.boveda.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bbva.control.boveda.bean.ControlDocBean;
import com.bbva.control.boveda.bean.DetalleControlDocBean;
import com.bbva.control.boveda.bean.DevolucionesBean;
import com.bbva.control.boveda.bean.DocDevueltoBean;
import com.bbva.control.boveda.bean.DocRenovadoBean;
import com.bbva.control.boveda.bean.DocumentoBean;
import com.bbva.control.boveda.bean.LetraBean;
import com.bbva.control.boveda.model.dao.ControlDocDAO;
import com.bbva.control.boveda.model.dao.DetalleControlDocDAO;
import com.bbva.control.boveda.model.dao.DevolucionesDAO;
import com.bbva.control.boveda.model.dao.DocDevueltoDAO;
import com.bbva.control.boveda.model.dao.DocRenovadoDAO;
import com.bbva.control.boveda.model.dao.DocumentoRenovDAO;
import com.bbva.control.boveda.model.dao.LetraDAO;
import com.bbva.control.boveda.model.dao.ParametrosDAO;
import com.bbva.control.boveda.model.dao.TempCheqcarDAO;
import com.bbva.control.boveda.model.dao.TempTipo11DAO;
import com.bbva.control.boveda.util.Constantes;
import com.bbva.control.boveda.util.Util;

@Service("ProcesarBovedaService")
public class ProcesarBovedaServiceImpl implements ProcesarBovedaService{

	private static final Log log = LogFactory.getLog(ProcesarBovedaServiceImpl.class);
	
	public static int ini = 0;
	public static int fin = 0;
	public static List<Map<String, Object>> listCargaMap = null;
	public static Map<String, Object> mapItemCarga = null;
	public static List<ControlDocBean> listCargaBean = null; 
	
	@Autowired
	private ControlDocDAO controlDocDAO;
	
	@Autowired
	private LetraDAO letraDAO;
	
	@Autowired
	private DetalleControlDocDAO detalleControlDocDAO;
	
	@Autowired
	private DocRenovadoDAO docRenovadoDAO;
	
	@Autowired
	private DocumentoRenovDAO documentoRenovDAO;
	
	@Autowired
	private TempTipo11DAO tempTipo11DAO;
	
	@Autowired
	private TempCheqcarDAO tempCheqcarDAO;
	
	@Autowired
	private ParametrosDAO parametrosDAO;
	
	@Autowired
	private DocDevueltoDAO docDevueltoDAO;
	
	@Autowired
	private DevolucionesDAO devolucionesDAO;

	@Override
	public void cargaInicial() throws Exception{
		String msgLog = "[cargaInicial] - ";
		log.debug(msgLog + "INICIO");
		String cadena;
		//listCargaInicial = new ArrayList<Map<String,Object>>();
		listCargaBean = new ArrayList<ControlDocBean>();
		String logError = "";
		int cont = 1;
		try {
			logError = Constantes.PROC_INGR_EXCEP_01;
			String archivo = Constantes.RUTA_ARCHIVO_DSD.concat("CHEQBALV.dsd"); //Nombre de archivo de carga inicial
			
			FileReader f = new FileReader(archivo);
			BufferedReader b = new BufferedReader(f);
			logError = Constantes.PROC_INGR_EXCEP_05;
			while ((cadena = b.readLine()) != null) {
				log.debug(msgLog + "Linea Procesada: " + cont);
				procesarArchivoInicial(cadena);
				cont++;
			}
			b.close();
			String flag = "cargaInicial";
			registrarCtrlDoc(listCargaBean, msgLog, flag);
			registrarDetCtrlDoc(listCargaBean, msgLog, flag);
			
		} catch (Exception e) {
			crearLog(logError, "Linea Procesada: " + cont, "carga");
			log.error("ERROR: " + e);
			e.printStackTrace();
		}
		log.debug(msgLog + "FIN");
	}
	
	private void crearLog(String logError, String msg, String flag) {
		BufferedWriter output = null;
		File file = null;
		try {
        	
        	Date fecha = Calendar.getInstance().getTime();
	        DateFormat formatter = new SimpleDateFormat("ddMMyyyy");
	        String strFecha = formatter.format(fecha);
	        if(flag.equalsIgnoreCase("carga")){
	        	file = new File(Constantes.RUTA_ARCHIVO_LOG.concat(Constantes.LOG_ERROR_CARGAINI.concat(strFecha).concat(".log")));
	        }else if(flag.equalsIgnoreCase("ingreso")){
	        	file = new File(Constantes.RUTA_ARCHIVO_LOG.concat(Constantes.LOG_ERROR_INGRESO.concat(strFecha).concat(".log")));
	        }else if(flag.equalsIgnoreCase("salida")){
	        	file = new File(Constantes.RUTA_ARCHIVO_LOG.concat(Constantes.LOG_ERROR_SALIDA.concat(strFecha).concat(".log")));
	        }
            output = new BufferedWriter(new FileWriter(file));
            output.write("LOG DE ERROR EN EJECUCION");
            output.newLine();
            output.write("Fecha y Hora: " + fecha);
            output.newLine();
            output.write("Mensaje: " + logError);
            output.newLine();
            output.write("Detalle: " + msg);
            output.close();
        } catch ( IOException ioe ) {
        	log.error(ioe);
            ioe.printStackTrace();
        } 
	}

	private void registrarDetCtrlDoc(List<ControlDocBean> listCargaBean2,
			String msgLog, String flag) {
		log.debug(msgLog + "Se procede a registrar los datos en la tabla TM_DETALLE_CONTROL_DOC");
		try {
			log.debug(msgLog + "Cantidad de registros: " + listCargaBean2.size());
			for(ControlDocBean itemControl : listCargaBean2){
				DetalleControlDocBean detalleCtrl = new DetalleControlDocBean();
				
				detalleCtrl.setCod_cartera(itemControl.getNum_cuenta());
				detalleCtrl.setCod_juliano(itemControl.getCod_juliano());
				detalleCtrl.setFec_vencimiento(itemControl.getFec_vencimiento());
				detalleCtrl.setImp_importe(itemControl.getImp_importe().substring(1));
				detalleCtrl.setDes_nombre_titular(itemControl.getDes_nombre().trim());
				detalleCtrl.setInd_ingreso_salida(Constantes.EST_ACT_INGRESO);
				detalleCtrl.setFec_cargado(itemControl.getFec_proceso());
				detalleCtrl.setCod_observacion_ubica(itemControl.getDes_observacion_ubica());
				detalleCtrl.setCod_tipo_movimiento(itemControl.getCod_tipo_movimiento());
				detalleCtrl.setCod_plaza_actual(itemControl.getCod_agencia());
				detalleCtrl.setCod_plaza_cobro(itemControl.getCod_plaza());
				detalleControlDocDAO.insertarRegistro(detalleCtrl);
			}
		} catch (Exception e) {
			log.error("ERROR - registrarDetCtrlDoc: " + e);
			e.printStackTrace();
		}
		
	}

	private void registrarCtrlDoc(List<ControlDocBean> listCargaBean2, String msgLog, String flag) {
		log.debug(msgLog + "Se procede a registrar los datos en la tabla TM_CONTROL_DOC");
		try {
			log.debug(msgLog + "Cantidad de registros: " + listCargaBean2.size());
			for(ControlDocBean itemControl : listCargaBean2){
				controlDocDAO.insertar(itemControl);
			}
		} catch (Exception e) {
			if(flag.equalsIgnoreCase("cargaInicial")){
				log.error(msgLog + Constantes.PROC_CARG_INICIAL_EXCEP_05);
				//Crear archivo de error
				crearArchivoError(e, log, Constantes.LOG_ERROR_CARGAINI);
				
			}else if(flag.equalsIgnoreCase("procesarIngresos")){
				log.error(msgLog + Constantes.PROC_INGR_EXCEP_05);
				//Crear archivo de error
				crearArchivoError(e, log, Constantes.LOG_ERROR_INGRESO);
				
			}
			log.error("ERROR en registrarTMCtrlDoc: " + e);
			e.printStackTrace();
		}
	}

	private void crearArchivoError(Exception e, Log log2, String nomLog) {
        BufferedWriter output = null;
        try {
        	
        	Date fecha = Calendar.getInstance().getTime();
	        DateFormat formatter = new SimpleDateFormat("ddMMyyyy");
	        String strFecha = formatter.format(fecha);
        	
            File file = new File(nomLog.concat(strFecha).concat(".log"));
            output = new BufferedWriter(new FileWriter(file));
            output.write("ERROR ");
            output.newLine();
            output.write(log2.toString());
            output.newLine();
            output.write("Error causado por: " + e);
            output.close();
        } catch ( IOException ioe ) {
        	log.error("ERROR al crear el archivo");
            ioe.printStackTrace();
        } 
	}

	@SuppressWarnings("unused")
	public void procesarArchivoInicial(String cadena) throws Exception{
		ini = 0;
		fin = 0;
		
		ControlDocBean itemControl = new ControlDocBean();

		itemControl.setCod_operador(Constantes.COD_OPERADOR_BBVA);
		
		contIniFin(2);
		String tipo_documento = cadena.substring(ini, fin); // 2
		itemControl.setCod_clase_documento(tipo_documento);
		
		contIniFin(10);
		String num_cuenta = cadena.substring(ini, fin); // 10
		itemControl.setNum_cuenta(num_cuenta);
		
		contIniFin(2);
		String cod_servicio = cadena.substring(ini, fin); // 2
		itemControl.setCod_servicio(cod_servicio);
		
		contIniFin(10);
		String num_banco = cadena.substring(ini, fin); // 10
		itemControl.setCod_juliano(num_banco);
		
		contIniFin(8);
		String fec_vencim = cadena.substring(ini, fin); // 8
		itemControl.setFec_vencimiento(fec_vencim);
		
		contIniFin(4);
		String plaza_actual = cadena.substring(ini, fin); // 4
		//itemControl.setCod_agencia(plaza_actual);
		
		contIniFin(4);
		String plaza_cobro = cadena.substring(ini, fin); // 4
		itemControl.setCod_plaza(plaza_cobro);
		
		contIniFin(15);
		String importe_actual = cadena.substring(ini, fin); // 15
		itemControl.setImp_importe(importe_actual.substring(3,15));
		
		contIniFin(18);
		String nombre_girador = cadena.substring(ini, fin); // 18
		itemControl.setDes_nombre(nombre_girador);
		
		contIniFin(6);
		String tasa = cadena.substring(ini, fin); // 6
		itemControl.setNum_tasa_opcional(tasa);
		
		contIniFin(10);
		String cuenta_asociada = cadena.substring(ini, fin); // 10
		
		contIniFin(3);
		String funcionario = cadena.substring(ini, fin); // 3
		
		contIniFin(3);
		String propietario = cadena.substring(ini, fin); // 3
		itemControl.setCod_agencia(propietario);
		
		contIniFin(8);
		String ingreso_original = cadena.substring(ini, fin); // 8
		
		contIniFin(13);
		String importe_original = cadena.substring(ini, fin); // 13
		
		contIniFin(8);
		String fec_desembolso = cadena.substring(ini, fin); // 8
		itemControl.setFec_desembolso(fec_desembolso);
		
		contIniFin(8);
		String ingr_documentos = cadena.substring(ini, fin); // 8
		
		contIniFin(3);
		String num_renovacion = cadena.substring(ini, fin); // 3
		
		contIniFin(5);
		String por_renovacion = cadena.substring(ini, fin); // 5
		itemControl.setNum_porcentaje_renova(por_renovacion);
		
		contIniFin(5);
		String num_paquete = cadena.substring(ini, fin); // 5
		
		contIniFin(1);
		String tipo_credito = cadena.substring(ini, fin); // 1
		
		contIniFin(2);
		String cla_cliente = cadena.substring(ini, fin); // 2
		
		contIniFin(8);
		String cod_central = cadena.substring(ini, fin); // 8
		
		itemControl.setInd_ingreso_salida(Constantes.EST_ACT_INGRESO);
		itemControl.setCod_tipo_moneda(num_cuenta.substring(3,4));
		itemControl.setCod_tipo_movimiento(Constantes.COD_TIPO_MOVIMIENTO_11);
		itemControl.setDes_observacion_ubica(Constantes.COD_OBSERVACION_006);
		
		Date fecha = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String strFecha = formatter.format(fecha);
		itemControl.setFec_proceso(strFecha);
		
		listCargaBean.add(itemControl);
	}
	
	public void procesarArchivoDSD(String cadena) throws Exception{
		
		log.debug("[procesarArchivoDSD] - INICIO");
		ini = 0;
		fin = 0;
		ControlDocBean controlBean = new ControlDocBean();
		
		try {
			contIniFin(2);
			String operador = cadena.substring(ini, fin); // 2
			controlBean.setCod_operador(operador);
			
			contIniFin(10);
			String num_cuenta = cadena.substring(ini, fin); // 10
			controlBean.setNum_cuenta(num_cuenta);
			
			contIniFin(2);
			String id_servicio = cadena.substring(ini, fin); // 2
			controlBean.setCod_servicio(id_servicio);
			
			contIniFin(10);
			String id_documento = cadena.substring(ini, fin); // 10
			controlBean.setCod_juliano(id_documento);
			
			contIniFin(8);
			String fec_vencim = cadena.substring(ini, fin); // 8
			controlBean.setFec_vencimiento(fec_vencim);
			
			contIniFin(12);
			String importe = cadena.substring(ini, fin); // 12
			controlBean.setImp_importe(importe);
			
			contIniFin(2);
			String id_clas_docu = cadena.substring(ini, fin); // 2
			controlBean.setCod_clase_documento(id_clas_docu);
			
			contIniFin(4);
			String id_agencia = cadena.substring(ini, fin); // 4
			controlBean.setCod_agencia(id_agencia);
			
			contIniFin(4);
			String cod_plaza = cadena.substring(ini, fin); // 4
			controlBean.setCod_plaza(cod_plaza);
			
			contIniFin(12); 
			String num_clie = cadena.substring(ini, fin); // 10
			controlBean.setNum_cliente(num_clie.substring(0, 10));
			
			contIniFin(8);
			String fec_giro = cadena.substring(ini, fin); // 8
			controlBean.setFec_giro(fec_giro);
			
			contIniFin(8);
			String fec_dese = cadena.substring(ini, fin); // 8
			controlBean.setFec_desembolso(fec_dese);
			
			contIniFin(8);
			String fec_proc = cadena.substring(ini, fin); // 8
			controlBean.setFec_proceso(fec_proc);
			
			contIniFin(40);
			String nombre = cadena.substring(ini, fin); // 40
			controlBean.setDes_nombre(nombre.trim());
			
			contIniFin(60);
			String direccion = cadena.substring(ini, fin); // 60
			controlBean.setDes_direccion(direccion.trim());
			
			contIniFin(10);
			String cuenta = cadena.substring(ini, fin); // 10
			controlBean.setNum_cuenta_aceptante(cuenta);
			
			contIniFin(6);
			String tasa_opcion = cadena.substring(ini, fin); // 6
			controlBean.setNum_tasa_opcional(tasa_opcion);
			
			contIniFin(6);
			String por_renova = cadena.substring(ini, fin); // 6
			controlBean.setNum_porcentaje_renova(por_renova);
			
			contIniFin(2);
			String id_inst_cobro = cadena.substring(ini, fin); // 2
			controlBean.setInd_instruccion_cobro(id_inst_cobro);
			
			contIniFin(1);
			String exo_impu = cadena.substring(ini, fin); // 1
			controlBean.setInd_exoneracion_impuest(exo_impu);
			
			contIniFin(1);
			String tasa_garant = cadena.substring(ini, fin); // 1
			controlBean.setNum_tasa_garantia(tasa_garant);
			
			contIniFin(10);
			String id_planilla = cadena.substring(ini, fin); // 10
			controlBean.setCod_planilla(id_planilla);
			
			contIniFin(12);
			String cue_factor = cadena.substring(ini, fin); // 12
			controlBean.setNum_cuenta_factoring(cue_factor);
			
			contIniFin(2);
			id_servicio = cadena.substring(ini, fin); // 2 
			
			contIniFin(1);
			String id_tip_docu = cadena.substring(ini, fin); // 1
			controlBean.setCod_tipo_doc_identidad(id_tip_docu);
			
			contIniFin(11);
			String num_docum = cadena.substring(ini, fin); // 11
			controlBean.setNum_doc_identidad(num_docum);
			
			contIniFin(18);
			if(cadena.length() == 252){
				controlBean.setImp_prestamo_vinculado("");
			}else{
				System.out.println("registro mayor a 252 - juliano: " + controlBean.getCod_juliano());
				String pre_vinc = cadena.substring(ini, fin); // 18
				controlBean.setImp_prestamo_vinculado(pre_vinc);
			}
			
			controlBean.setInd_ingreso_salida(Constantes.EST_ACT_INGRESO);
			controlBean.setCod_tipo_moneda(num_cuenta.substring(3, 4));
			controlBean.setCod_tipo_movimiento(Constantes.COD_TIPO_MOVIMIENTO_11);
			controlBean.setDes_observacion_ubica(Constantes.COD_OBSERVACION_006);
			
			listCargaBean.add(controlBean);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw new Exception(e);
		}
		log.debug("[procesarArchivoDSD] - FIN");
		
	}

	private void contIniFin(int i) {
		ini = fin;
		fin = ini + i;
	}

	@Override
	public void procesarIngresos() throws Exception {
		String msgLog = "[procesarIngreso] - ";
		log.debug(msgLog + "INICIO");
		String rutaArchivo = "";
		//Validar la existencia del archivo
		try {
			rutaArchivo = crearRutaArchivo();
			log.debug(msgLog + "Ruta carpeta: " + rutaArchivo);
			
			//El nombre del archivo debe ser siempre el mismo
			String sFichero = rutaArchivo.concat("CHEQCAR1.dsd");
			
			File fichero = new File(sFichero);

			if (fichero.exists()){
				log.debug(msgLog + "El fichero " + sFichero + " existe - tamaño: " + fichero.length());
				if(fichero.length() != 0){
					if(validarEstructura(sFichero)){
						String flag = "procesarIngresos";
						registrarCtrlDoc(listCargaBean, msgLog, flag);
						registrarDetCtrlDoc(listCargaBean, msgLog, flag);
						registrarTmpCheqcar(listCargaBean, msgLog);
						log.debug(msgLog + Constantes.PROC_INGR_EXITO);
					}else{
						log.error(msgLog + Constantes.PROC_INGR_EXCEP_04);
						crearLog(msgLog + Constantes.PROC_INGR_EXCEP_04, "Ruta Archivo: " +sFichero, "ingreso");
						return;
					}
				}else{
					log.error(msgLog + Constantes.PROC_INGR_EXCEP_03);
					crearLog(msgLog + Constantes.PROC_INGR_EXCEP_03, "Ruta Archivo: " +sFichero, "ingreso");
					return;
				}
			}else{
				log.error(msgLog + Constantes.PROC_INGR_EXCEP_01);
				crearLog(msgLog + Constantes.PROC_INGR_EXCEP_01, "Ruta Archivo: " +sFichero, "ingreso");
				return;
			}
		} catch (Exception e) {
			crearLog(msgLog + Constantes.PROC_INGR_EXCEP_05, "Error  - " + e, "ingreso");
			log.error("ERROR: " + e);
			e.printStackTrace();
		} finally {
			log.debug(msgLog + "FIN");
		}
	}

	private void registrarTmpCheqcar(List<ControlDocBean> listCargaBean2,
			String msgLog) {
		log.debug(msgLog + "Se procede a registrar los datos en la tabla TMP_CHEQCAR1");
		try {
			log.debug(msgLog + "Cantidad de registros: " + listCargaBean2.size());
			for(ControlDocBean itemControl : listCargaBean2){
				tempCheqcarDAO.insertar(itemControl);
			}
		} catch (Exception e) {
			log.error("ERROR en registrarTmpCheqcar: " + e);
			e.printStackTrace();
		}
	}

	private boolean validarEstructura(String sFichero) {
		boolean respuesta = false;
		String msgLog = "[validarEstructura] - ";
		String cadena;
		log.debug(msgLog + "INICIO");
		int cont = 1;
		listCargaBean = new ArrayList<ControlDocBean>();
		try {
			FileReader f = new FileReader(sFichero);
			BufferedReader b = new BufferedReader(f);
			while ((cadena = b.readLine()) != null) {
				log.debug(msgLog + "Linea Procesada: " + cont);
				String newString = new String(cadena.getBytes(), "utf-8");
				procesarArchivoDSD(newString);
				cont++;
			}
			b.close();
			respuesta = true;
		} catch (Exception e) {
			respuesta = false;
			log.error("ERROR en la fila : " + cont);
			log.error("Detalle del error: " + e);
			e.printStackTrace();
		}
		
		return respuesta;
	}

	private String crearRutaArchivo() {
		String ruta = "";
		try {
			
			Date fecha = Calendar.getInstance().getTime();
	        DateFormat formatter = new SimpleDateFormat("ddMMyyyy");
	        String strFecha = formatter.format(fecha);
	        log.debug("FECHA DEL SISTEMA: " + strFecha);
	        ruta = Constantes.RUTA_ARCHIVO_DSD.concat(strFecha).concat("//");
	        
		} catch (Exception e) {
			log.error("ERROR: " + e);
			e.printStackTrace();
		}
		return ruta;
	}

	@Override
	public void procesarSalidas(String strFecha) throws Exception {
		String msgLog = "[procesarSalida] - ";
		log.debug(msgLog + "INICIO");
		try {
			
	        Map<String, Object> mapLetra = new HashMap<String, Object>();
	        mapLetra.put("fecha_cargado", strFecha);
	        
	        List<LetraBean> listLetras = letraDAO.obtenerDocumentos(mapLetra);
	        
	        for(LetraBean itemLetra : listLetras){
	        	ControlDocBean itemCtrlDoc = new ControlDocBean();
	        	Map<String, Object > param = new HashMap<String, Object>();
	        	param.put("numero_banco", itemLetra.getNumero_banco());
	        	param.put("fecha_vencimiento", itemLetra.getFecha_vencimiento());
	        	itemCtrlDoc = controlDocDAO.obtenerDocXLetra(param);
	        	if(itemCtrlDoc!= null){
	        		
	        		//“01-CANCELACION” o “07-CANCELACION POR RASTREO”
	        		if(itemLetra.getId_tipo_movimiento().trim().equals("01") || 
	        			itemLetra.getId_tipo_movimiento().trim().equals("07") ){
	        			
	        			DetalleControlDocBean detCtrl = cargaParamDetalle(itemLetra);
	        			detCtrl.setInd_ingreso_salida(Constantes.EST_ACT_SALIDA);
	        			detCtrl.setCod_observacion_ubica(Constantes.COD_OBSERVACION_001);
						detalleControlDocDAO.insertarRegistro(detCtrl);
						
						itemCtrlDoc.setInd_ingreso_salida(Constantes.EST_ACT_SALIDA);
						itemCtrlDoc.setDes_observacion_ubica(Constantes.COD_OBSERVACION_001);
						itemCtrlDoc.setNum_letra(itemLetra.getId_letra());
						itemCtrlDoc.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
						itemCtrlDoc.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
						itemCtrlDoc.setImp_nuevo_importe(itemLetra.getNuevo_importe());
						itemCtrlDoc.setCod_forma_pago(itemLetra.getId_forma_pago());
						itemCtrlDoc.setFec_movimiento(itemLetra.getFecha_movimiento());
						controlDocDAO.updateRegistro(itemCtrlDoc);
	        		
					//“02-RENOVACION” u “08-RENOVACION POR RASTREO”
	        		} else if(itemLetra.getId_tipo_movimiento().trim().equals("02") || 
		        			itemLetra.getId_tipo_movimiento().trim().equals("08") ){
	        			
	        			//Para CANCELACION
	        			DetalleControlDocBean detCtrl = cargaParamDetalle(itemLetra);
	        			detCtrl.setInd_ingreso_salida(Constantes.EST_ACT_SALIDA);
	        			detCtrl.setCod_observacion_ubica(Constantes.COD_OBSERVACION_002);
						detalleControlDocDAO.insertarRegistro(detCtrl);
						
						//Para RENOVACION
						detCtrl = cargaParamDetalle(itemLetra);
	        			detCtrl.setInd_ingreso_salida(Constantes.EST_ACT_VIGENTE);
	        			detCtrl.setCod_observacion_ubica(Constantes.COD_OBSERVACION_003);
						detalleControlDocDAO.insertarRegistro(detCtrl);
						
						itemCtrlDoc.setInd_ingreso_salida(Constantes.EST_ACT_VIGENTE); 
						itemCtrlDoc.setDes_observacion_ubica(Constantes.COD_OBSERVACION_003); 
						itemCtrlDoc.setNum_letra(itemLetra.getId_letra());
						itemCtrlDoc.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
						itemCtrlDoc.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
						itemCtrlDoc.setImp_nuevo_importe(itemLetra.getNuevo_importe());
						itemCtrlDoc.setCod_forma_pago(itemLetra.getId_forma_pago());
						itemCtrlDoc.setFec_movimiento(itemLetra.getFecha_movimiento());
						controlDocDAO.updateRegistro(itemCtrlDoc);
						
						DocRenovadoBean docRenov = new DocRenovadoBean();
						docRenov.setNum_letra(itemLetra.getId_letra());
						docRenov.setCod_juliano(itemLetra.getNumero_banco());
						docRenov.setFec_movimiento(itemLetra.getFecha_movimiento());
						docRenov.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
						docRenov.setCod_oficina_movimiento(itemLetra.getId_oficina_movimiento());
						docRenov.setCod_tipo_documento(itemLetra.getId_tipo_documento());
						docRenov.setCod_forma_pago(itemLetra.getId_forma_pago());
						docRenov.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
						docRenov.setImp_nuevo_importe(itemLetra.getNuevo_importe());
						docRenov.setFec_cargado(itemLetra.getFecha_cargado());
						docRenovadoDAO.insertarDocumento(docRenov);
						
					//“03-DEVUELTA” o “04-DEVUELTA PROTESTADA” o “06-DEVUELTA POR REASTREO”
	        		}else if(itemLetra.getId_tipo_movimiento().trim().equals("03") || 
		        			itemLetra.getId_tipo_movimiento().trim().equals("04") ||
		        			itemLetra.getId_tipo_movimiento().trim().equals("06") ){
	        			
	        			DetalleControlDocBean detCtrl = cargaParamDetalle(itemLetra);
	        			detCtrl.setInd_ingreso_salida(Constantes.EST_ACT_SALIDA);
	        			detCtrl.setCod_observacion_ubica(Constantes.COD_OBSERVACION_004);
						detalleControlDocDAO.insertarRegistro(detCtrl);
						
						itemCtrlDoc.setInd_ingreso_salida(Constantes.EST_ACT_SALIDA);
						itemCtrlDoc.setDes_observacion_ubica(Constantes.COD_OBSERVACION_004);
						itemCtrlDoc.setNum_letra(itemLetra.getId_letra());
						itemCtrlDoc.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
						itemCtrlDoc.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
						itemCtrlDoc.setImp_nuevo_importe(itemLetra.getNuevo_importe());
						itemCtrlDoc.setCod_forma_pago(itemLetra.getId_forma_pago());
						itemCtrlDoc.setFec_movimiento(itemLetra.getFecha_movimiento());
						controlDocDAO.updateRegistro(itemCtrlDoc);
						
					//“05-DESCARGO POR ERROR”
	        		}else if( itemLetra.getId_tipo_movimiento().trim().equals("05") ){
	        			
	        			DetalleControlDocBean detCtrl = cargaParamDetalle(itemLetra);
	        			detCtrl.setInd_ingreso_salida(Constantes.EST_ACT_SALIDA);
	        			detCtrl.setCod_observacion_ubica(Constantes.COD_OBSERVACION_005);
						detalleControlDocDAO.insertarRegistro(detCtrl);
						
						itemCtrlDoc.setInd_ingreso_salida(Constantes.EST_ACT_SALIDA);
						itemCtrlDoc.setDes_observacion_ubica(Constantes.COD_OBSERVACION_005);
						itemCtrlDoc.setNum_letra(itemLetra.getId_letra());
						itemCtrlDoc.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
						itemCtrlDoc.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
						itemCtrlDoc.setImp_nuevo_importe(itemLetra.getNuevo_importe());
						itemCtrlDoc.setCod_forma_pago(itemLetra.getId_forma_pago());
						itemCtrlDoc.setFec_movimiento(itemLetra.getFecha_movimiento());
						controlDocDAO.updateRegistro(itemCtrlDoc);
						
					//“11-INGRESOS POR CANJE”
	        		}else if( itemLetra.getId_tipo_movimiento().trim().equals("11") ){
	        			
	        			if(itemLetra.getId_tipo_documento().trim().equals("01")){
	        				
	        				DetalleControlDocBean detCtrl = cargaParamDetalle(itemLetra);
		        			detCtrl.setInd_ingreso_salida(Constantes.EST_ACT_VIGENTE);
		        			detCtrl.setCod_observacion_ubica(Constantes.COD_OBSERVACION_007);
							detalleControlDocDAO.insertarRegistro(detCtrl);
							
							itemCtrlDoc.setInd_ingreso_salida(Constantes.EST_ACT_VIGENTE);
							itemCtrlDoc.setDes_observacion_ubica(Constantes.COD_OBSERVACION_007);
							itemCtrlDoc.setNum_letra(itemLetra.getId_letra());
							itemCtrlDoc.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
							itemCtrlDoc.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
							itemCtrlDoc.setImp_nuevo_importe(itemLetra.getNuevo_importe());
							itemCtrlDoc.setCod_forma_pago(itemLetra.getId_forma_pago());
							itemCtrlDoc.setFec_movimiento(itemLetra.getFecha_movimiento());
							controlDocDAO.updateRegistro(itemCtrlDoc);
	        				
	        			}else{
	        				
	        				DetalleControlDocBean detCtrl = cargaParamDetalle(itemLetra);
		        			detCtrl.setInd_ingreso_salida(Constantes.EST_ACT_INGRESO);
		        			detCtrl.setCod_observacion_ubica(Constantes.COD_OBSERVACION_006);
							detalleControlDocDAO.insertarRegistro(detCtrl);
							
							itemCtrlDoc.setInd_ingreso_salida(Constantes.EST_ACT_INGRESO);
							itemCtrlDoc.setDes_observacion_ubica(Constantes.COD_OBSERVACION_006);
							itemCtrlDoc.setNum_letra(itemLetra.getId_letra());
							itemCtrlDoc.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
							itemCtrlDoc.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
							itemCtrlDoc.setImp_nuevo_importe(itemLetra.getNuevo_importe());
							itemCtrlDoc.setCod_forma_pago(itemLetra.getId_forma_pago());
							itemCtrlDoc.setFec_movimiento(itemLetra.getFecha_movimiento());
							controlDocDAO.updateRegistro(itemCtrlDoc);
							
							tempTipo11DAO.insert(itemCtrlDoc);
							
	        			}
	        		//“13-INGRESOS POR ERROR”.
	        		}else if( itemLetra.getId_tipo_movimiento().trim().equals("13") ){
	        			
	        			DetalleControlDocBean detCtrl = cargaParamDetalle(itemLetra);
	        			detCtrl.setInd_ingreso_salida(Constantes.EST_ACT_INGRESO);
	        			detCtrl.setCod_observacion_ubica(Constantes.COD_OBSERVACION_008);
						detalleControlDocDAO.insertarRegistro(detCtrl);
						
						itemCtrlDoc.setInd_ingreso_salida(Constantes.EST_ACT_INGRESO);
						itemCtrlDoc.setDes_observacion_ubica(Constantes.COD_OBSERVACION_008);
						itemCtrlDoc.setNum_letra(itemLetra.getId_letra());
						itemCtrlDoc.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
						itemCtrlDoc.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
						itemCtrlDoc.setImp_nuevo_importe(itemLetra.getNuevo_importe());
						itemCtrlDoc.setCod_forma_pago(itemLetra.getId_forma_pago());
						itemCtrlDoc.setFec_movimiento(itemLetra.getFecha_movimiento());
						controlDocDAO.updateRegistro(itemCtrlDoc);
	        			
					//“41-CAMBIOS A LA PENDIENTE”
	        		}else if( itemLetra.getId_tipo_movimiento().trim().equals("41") ){
	        			
	        			DetalleControlDocBean detCtrl = cargaParamDetalle(itemLetra);
	        			detCtrl.setInd_ingreso_salida(Constantes.EST_ACT_INGRESO);
	        			detCtrl.setCod_observacion_ubica(Constantes.COD_OBSERVACION_009);
						detalleControlDocDAO.insertarRegistro(detCtrl);
						
						itemCtrlDoc.setInd_ingreso_salida(Constantes.EST_ACT_INGRESO);
						itemCtrlDoc.setDes_observacion_ubica(Constantes.COD_OBSERVACION_009);
						itemCtrlDoc.setNum_letra(itemLetra.getId_letra());
						itemCtrlDoc.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
						itemCtrlDoc.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
						itemCtrlDoc.setImp_nuevo_importe(itemLetra.getNuevo_importe());
						itemCtrlDoc.setCod_forma_pago(itemLetra.getId_forma_pago());
						itemCtrlDoc.setFec_movimiento(itemLetra.getFecha_movimiento());
						controlDocDAO.updateRegistro(itemCtrlDoc);
	        			
					//“43-TRANSFERENCIA MANUAL”
	        		}else if( itemLetra.getId_tipo_movimiento().trim().equals("43") ){
	        			
	        			DetalleControlDocBean detCtrl = cargaParamDetalle(itemLetra);
	        			detCtrl.setInd_ingreso_salida(Constantes.EST_ACT_VIGENTE);
	        			detCtrl.setCod_observacion_ubica(Constantes.COD_OBSERVACION_010);
						detalleControlDocDAO.insertarRegistro(detCtrl);
						
						itemCtrlDoc.setInd_ingreso_salida(Constantes.EST_ACT_VIGENTE);
						itemCtrlDoc.setDes_observacion_ubica(Constantes.COD_OBSERVACION_010);
						itemCtrlDoc.setNum_letra(itemLetra.getId_letra());
						itemCtrlDoc.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
						itemCtrlDoc.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
						itemCtrlDoc.setImp_nuevo_importe(itemLetra.getNuevo_importe());
						itemCtrlDoc.setCod_forma_pago(itemLetra.getId_forma_pago());
						itemCtrlDoc.setFec_movimiento(itemLetra.getFecha_movimiento());
						controlDocDAO.updateRegistro(itemCtrlDoc);
						
					//“46-TRANSFERENCIA AUTOMATICA”
	        		}else if( itemLetra.getId_tipo_movimiento().trim().equals("46") ){
	        			
	        			if(itemLetra.getId_plaza_actual().substring(0, 1).equals("3")){
	        				
	        				DetalleControlDocBean detCtrl = cargaParamDetalle(itemLetra);
		        			detCtrl.setInd_ingreso_salida(Constantes.EST_ACT_VIGENTE);
		        			detCtrl.setCod_observacion_ubica(Constantes.COD_OBSERVACION_011);
							detalleControlDocDAO.insertarRegistro(detCtrl);
							
							itemCtrlDoc.setInd_ingreso_salida(Constantes.EST_ACT_VIGENTE);
							itemCtrlDoc.setDes_observacion_ubica(Constantes.COD_OBSERVACION_011);
							itemCtrlDoc.setNum_letra(itemLetra.getId_letra());
							itemCtrlDoc.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
							itemCtrlDoc.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
							itemCtrlDoc.setImp_nuevo_importe(itemLetra.getNuevo_importe());
							itemCtrlDoc.setCod_forma_pago(itemLetra.getId_forma_pago());
							itemCtrlDoc.setFec_movimiento(itemLetra.getFecha_movimiento());
							controlDocDAO.updateRegistro(itemCtrlDoc);
							
	        			}else{
	        				
	        				DetalleControlDocBean detCtrl = cargaParamDetalle(itemLetra);
		        			detCtrl.setInd_ingreso_salida(Constantes.EST_ACT_VIGENTE);
		        			detCtrl.setCod_observacion_ubica(Constantes.COD_OBSERVACION_012);
							detalleControlDocDAO.insertarRegistro(detCtrl);
							
							itemCtrlDoc.setInd_ingreso_salida(Constantes.EST_ACT_VIGENTE);
							itemCtrlDoc.setDes_observacion_ubica(Constantes.COD_OBSERVACION_012);
							itemCtrlDoc.setNum_letra(itemLetra.getId_letra());
							itemCtrlDoc.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
							itemCtrlDoc.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
							itemCtrlDoc.setImp_nuevo_importe(itemLetra.getNuevo_importe());
							itemCtrlDoc.setCod_forma_pago(itemLetra.getId_forma_pago());
							itemCtrlDoc.setFec_movimiento(itemLetra.getFecha_movimiento());
							controlDocDAO.updateRegistro(itemCtrlDoc);
	        				
	        			}
	        			
	        			DocDevueltoBean itemDocDev = new DocDevueltoBean();
						itemDocDev.setCod_forma_pago(itemLetra.getId_forma_pago());
						itemDocDev.setCod_juliano(itemLetra.getNumero_banco());
						itemDocDev.setCod_oficina_movimiento(itemLetra.getId_oficina_movimiento());
						itemDocDev.setCod_tipo_documento(itemLetra.getId_tipo_documento());
						itemDocDev.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
						itemDocDev.setFec_cargado(itemLetra.getFecha_cargado());
						itemDocDev.setFec_movimiento(itemLetra.getFecha_movimiento());
						itemDocDev.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
						itemDocDev.setFec_vencimiento(itemLetra.getFecha_vencimiento());
						itemDocDev.setImp_importe(itemLetra.getImporte());
						itemDocDev.setImp_nuevo_importe(itemLetra.getNuevo_importe());
						itemDocDev.setNum_letra(itemLetra.getId_letra());
						docDevueltoDAO.insertarRegistro(itemDocDev);
	        			
	        		//“49-MARCA PROTESTO”
	        		}else if( itemLetra.getId_tipo_movimiento().trim().equals("49") ){
	        			
	        			if(itemLetra.getId_plaza_cobro().equals("0902")){
	        			
	        				DetalleControlDocBean detCtrl = cargaParamDetalle(itemLetra);
		        			detCtrl.setInd_ingreso_salida(Constantes.EST_ACT_VIGENTE);
		        			detCtrl.setCod_observacion_ubica(Constantes.COD_OBSERVACION_013);
							detalleControlDocDAO.insertarRegistro(detCtrl);
							
							itemCtrlDoc.setInd_ingreso_salida(Constantes.EST_ACT_VIGENTE);
							itemCtrlDoc.setDes_observacion_ubica(Constantes.COD_OBSERVACION_013);
							itemCtrlDoc.setNum_letra(itemLetra.getId_letra());
							itemCtrlDoc.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
							itemCtrlDoc.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
							itemCtrlDoc.setImp_nuevo_importe(itemLetra.getNuevo_importe());
							itemCtrlDoc.setCod_forma_pago(itemLetra.getId_forma_pago());
							itemCtrlDoc.setFec_movimiento(itemLetra.getFecha_movimiento());
							controlDocDAO.updateRegistro(itemCtrlDoc);
	        				
	        			}else{
	        				
	        				DetalleControlDocBean detCtrl = cargaParamDetalle(itemLetra);
		        			detCtrl.setInd_ingreso_salida(Constantes.EST_ACT_VIGENTE);
		        			detCtrl.setCod_observacion_ubica(Constantes.COD_OBSERVACION_014);
							detalleControlDocDAO.insertarRegistro(detCtrl);
							
							itemCtrlDoc.setInd_ingreso_salida(Constantes.EST_ACT_VIGENTE);
							itemCtrlDoc.setDes_observacion_ubica(Constantes.COD_OBSERVACION_014);
							itemCtrlDoc.setNum_letra(itemLetra.getId_letra());
							itemCtrlDoc.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
							itemCtrlDoc.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
							itemCtrlDoc.setImp_nuevo_importe(itemLetra.getNuevo_importe());
							itemCtrlDoc.setCod_forma_pago(itemLetra.getId_forma_pago());
							itemCtrlDoc.setFec_movimiento(itemLetra.getFecha_movimiento());
							controlDocDAO.updateRegistro(itemCtrlDoc);
	        				
	        			}
	        			
	        			DocDevueltoBean itemDocDev = new DocDevueltoBean();
						itemDocDev.setCod_forma_pago(itemLetra.getId_forma_pago());
						itemDocDev.setCod_juliano(itemLetra.getNumero_banco());
						itemDocDev.setCod_oficina_movimiento(itemLetra.getId_oficina_movimiento());
						itemDocDev.setCod_tipo_documento(itemLetra.getId_tipo_documento());
						itemDocDev.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
						itemDocDev.setFec_cargado(itemLetra.getFecha_cargado());
						itemDocDev.setFec_movimiento(itemLetra.getFecha_movimiento());
						itemDocDev.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
						itemDocDev.setFec_vencimiento(itemLetra.getFecha_vencimiento());
						itemDocDev.setImp_importe(itemLetra.getImporte());
						itemDocDev.setImp_nuevo_importe(itemLetra.getNuevo_importe());
						itemDocDev.setNum_letra(itemLetra.getId_letra());
						docDevueltoDAO.insertarRegistro(itemDocDev);
	        		}
	        		log.debug(msgLog + Constantes.PROC_INGR_EXITO);
	        	}else{
	        		//Archivos que no se encuentran en TM_LETRA y TM_CONTROL_DOC
	        		crearLog("No se encontraron registros", "", "salida");
	        	}
	        }
	        
	        //Comparar Temporales
	        List<ControlDocBean> lstTempTipo = tempTipo11DAO.listaTemp();
	        List<ControlDocBean> lstTempCheqcar = tempCheqcarDAO.listaTempCh();
	        if(lstTempTipo.size()==lstTempCheqcar.size()){
	        	log.debug("Cantidad de registros iguales en tablas temporales");
	        }else{
	        	log.debug("Existen archivos Faltantes/Sobrantes en el cruce realizado, por favor revise el archivo log generado");
	        	List<ControlDocBean> nuevaList = new ArrayList<ControlDocBean>();
	        	List<ControlDocBean> iter = lstTempCheqcar;
	        	for(ControlDocBean elem: iter){
	                if(!lstTempTipo.contains(elem)){
	                	//Sobrantes
	                	nuevaList.add(elem);
	                }
	             }
	        	List<ControlDocBean> iter1 = lstTempTipo;
	        	for(ControlDocBean elem: iter1){
	                if(!lstTempCheqcar.contains(elem)){
	                	//Faltantes
	                	nuevaList.add(elem);
	                }
	             }
	        	crearLogFaltantes(nuevaList);
	        }
	        log.debug("Se procede a eliminar los datos de las tablas temporales");
			tempCheqcarDAO.delete();
			tempTipo11DAO.delete();
			log.debug("Datos eliminados de forma satisfactoria");
	        
		} catch (Exception e) {
			crearLog(Constantes.PROC_SALI_EXCEP_03, "Error - " + e, "salida");
			log.error(msgLog + e);
			e.printStackTrace();
		} finally {
			log.debug(msgLog + "FIN");
		}
		
	}

	private void crearLogFaltantes(List<ControlDocBean> lstTempCheqcar) {

		BufferedWriter output = null;
		File file = null;
		try {
        	
        	Date fecha = Calendar.getInstance().getTime();
	        DateFormat formatter = new SimpleDateFormat("ddMMyyyy");
	        String strFecha = formatter.format(fecha);
	        file = new File(System.getProperty("user.home").concat("/RegistrosFaltantes_".concat(strFecha).concat(".txt")));
            output = new BufferedWriter(new FileWriter(file));
            output.newLine();
            output.write(Constantes.PROC_SALI_EXCEP_02);
            output.newLine();
            output.write("Fecha y Hora: " + fecha);
            output.newLine();
            output.write("Mensaje: Se detallan a continuación los registros faltantes y/o sobrantes:");
            for(ControlDocBean item : lstTempCheqcar){
            	output.newLine();
            	output.write("Codigo Juliano: " + item.getCod_juliano() + " Fecha de Vencimiento: " + item.getFec_vencimiento() + " Importe: " + item.getImp_importe());
        	}
            output.close();
            enviarAlerta(file);
        } catch ( IOException ioe ) {
        	log.error(ioe);
            ioe.printStackTrace();
        } 
		
	}

	private DetalleControlDocBean cargaParamDetalle(LetraBean itemLetra) {
		
		DetalleControlDocBean detalle = new DetalleControlDocBean();
		detalle.setNum_letra(itemLetra.getId_letra());
		detalle.setCod_juliano(itemLetra.getNumero_banco());
		detalle.setCod_cartera(itemLetra.getCod_cartera().substring(0, 10));
		detalle.setFec_movimiento(itemLetra.getFecha_movimiento());
		detalle.setFec_vencimiento(itemLetra.getFecha_vencimiento());
		detalle.setCod_plaza_cobro(itemLetra.getId_plaza_cobro());
		detalle.setCod_plaza_actual(itemLetra.getId_plaza_actual());
		detalle.setCod_tipo_movimiento(itemLetra.getId_tipo_movimiento());
		detalle.setImp_importe(itemLetra.getImporte());
		detalle.setCod_oficina_movimiento(itemLetra.getId_oficina_movimiento());
		detalle.setCod_tipo_documento(itemLetra.getId_tipo_documento());
		detalle.setCod_forma_pago(itemLetra.getId_forma_pago());
		detalle.setFec_nuevo_vencimiento(itemLetra.getNuevo_vencimiento());
		detalle.setImp_nuevo_importe(itemLetra.getNuevo_importe());
		detalle.setNum_cuenta_corriente(itemLetra.getCuenta_corriente());
		detalle.setDes_nombre_titular(itemLetra.getNombre_titular());
		detalle.setDes_direccion_titular(itemLetra.getDireccion1_titular());
		detalle.setFec_cargado(itemLetra.getFecha_cargado());
		return detalle;
	}

	@Override
	public void procesarRenovados() throws Exception {
		List<DocRenovadoBean> listDocRenov = new ArrayList<DocRenovadoBean>();
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("tipo_movimiento", Constantes.COD_TIPO_MOVIMIENTO_02);
		listDocRenov = docRenovadoDAO.obtener(map);
		
		for(DocRenovadoBean itemDocRen : listDocRenov){
			List<DocumentoBean> listRenovaciones = new ArrayList<DocumentoBean>();
			Map<String, Object> mapRenov = new HashMap<String, Object>();
			mapRenov.put("cod_juliano", itemDocRen.getCod_juliano());
			mapRenov.put("nuevo_vencimiento", itemDocRen.getFec_nuevo_vencimiento());
			listRenovaciones = documentoRenovDAO.listDocRenovado(mapRenov);
			
			if(listRenovaciones!=null && !listRenovaciones.isEmpty()){
//				for(DocumentoBean itemDocumento: listRenovaciones){
					if(listRenovaciones.get(0).getId_devolucion().equals("0")  ||
							listRenovaciones.get(0).getId_devolucion().equals("2") ){
						
						ControlDocBean controlDoc = new ControlDocBean();
						controlDoc.setCod_juliano(itemDocRen.getCod_juliano());
						controlDoc.setFec_vencimiento(listRenovaciones.get(0).getFecha_vencimiento());
						controlDoc.setInd_ingreso_salida(Constantes.EST_ACT_INGRESO);
						controlDoc.setDes_observacion_ubica(Constantes.COD_OBSERVACION_015);
						controlDoc.setNum_letra(itemDocRen.getNum_letra());
						controlDoc.setCod_tipo_movimiento(itemDocRen.getCod_tipo_movimiento());
						controlDoc.setFec_nuevo_vencimiento(itemDocRen.getFec_nuevo_vencimiento());
						controlDoc.setImp_nuevo_importe(itemDocRen.getImp_nuevo_importe());
						controlDoc.setFec_movimiento(itemDocRen.getFec_movimiento());
						controlDocDAO.updateRegistroRenov(controlDoc );
						
						DetalleControlDocBean detalle = new DetalleControlDocBean();
//						detalle.setCod_cartera(listRenovaciones.get(0).get);
						detalle.setCod_forma_pago(itemDocRen.getCod_forma_pago());
						detalle.setCod_juliano(itemDocRen.getCod_juliano());
						detalle.setCod_observacion_ubica(Constantes.COD_OBSERVACION_015);
						detalle.setCod_oficina_movimiento(itemDocRen.getCod_oficina_movimiento());
						detalle.setCod_plaza_actual(listRenovaciones.get(0).getCod_plaza());
//						detalle.setCod_plaza_cobro(listRenovaciones.get(0).get);
						detalle.setCod_tipo_documento(itemDocRen.getCod_tipo_documento());
						detalle.setCod_tipo_movimiento(itemDocRen.getCod_tipo_movimiento());
//						detalle.setDes_direccion_titular(listRenovaciones.get(0).get);
//						detalle.setDes_nombre_titular(listRenovaciones.get(0).get);
						detalle.setFec_cargado(itemDocRen.getFec_cargado());
						detalle.setFec_movimiento(itemDocRen.getFec_movimiento());
						detalle.setFec_nuevo_vencimiento(itemDocRen.getFec_nuevo_vencimiento());
						detalle.setFec_vencimiento(listRenovaciones.get(0).getFecha_vencimiento());
//						detalle.setImp_importe(listRenovaciones.get(0).get);
						detalle.setImp_nuevo_importe(itemDocRen.getImp_nuevo_importe());
						detalle.setInd_ingreso_salida(Constantes.EST_ACT_INGRESO);
//						detalle.setNum_cuenta_corriente(listRenovaciones.get(0).get);
						detalle.setNum_letra(itemDocRen.getNum_letra());
						detalleControlDocDAO.insertarRegistro(detalle);
						
						docRenovadoDAO.deleteRegistro(itemDocRen);
					}
//				}
			}
		}
	}
	
	@Override
	public void procesarProtestados() throws Exception{

		List<DocDevueltoBean> listDocDev = new ArrayList<DocDevueltoBean>();
		Map<String, Object> map = new HashMap<String, Object>();
		listDocDev = docDevueltoDAO.listDevueltos(map);
		
		for(DocDevueltoBean itemDocDev : listDocDev){
			DevolucionesBean itemDevolucion = new DevolucionesBean();
			Map<String, Object> mapDevol = new HashMap<String, Object>();
			mapDevol.put("cod_juliano", itemDocDev.getCod_juliano());
			mapDevol.put("fec_vencimiento", itemDocDev.getFec_vencimiento());
			mapDevol.put("id_letra", itemDocDev.getNum_letra().trim());
			itemDevolucion = devolucionesDAO.obtenerDevoluciones(mapDevol);
			
			if(itemDevolucion!=null){
				if(itemDevolucion.getEstado().equals("3") ){
					
					ControlDocBean controlDoc = new ControlDocBean();
					controlDoc.setCod_juliano(itemDocDev.getCod_juliano());
					controlDoc.setFec_vencimiento(itemDocDev.getFec_vencimiento());
					controlDoc.setInd_ingreso_salida("1");
					controlDoc.setDes_observacion_ubica(Constantes.COD_OBSERVACION_016);
					controlDoc.setNum_letra(itemDocDev.getNum_letra());
					controlDoc.setCod_tipo_movimiento(itemDocDev.getCod_tipo_movimiento());
					controlDoc.setFec_nuevo_vencimiento(itemDocDev.getFec_nuevo_vencimiento());
					controlDoc.setImp_nuevo_importe(itemDocDev.getImp_nuevo_importe());
					controlDoc.setFec_movimiento(itemDocDev.getFec_movimiento());
					controlDocDAO.updateRegistro(controlDoc );
					
					DetalleControlDocBean detalle = new DetalleControlDocBean();
//					detalle.setCod_cartera(itemDevolucion.get);
					detalle.setCod_forma_pago(itemDocDev.getCod_forma_pago());
					detalle.setCod_juliano(itemDocDev.getCod_juliano());
					detalle.setCod_observacion_ubica(Constantes.COD_OBSERVACION_016);
					detalle.setCod_oficina_movimiento(itemDocDev.getCod_oficina_movimiento());
//					detalle.setCod_plaza_actual(itemDevolucion.get);
//					detalle.setCod_plaza_cobro(itemDevolucion.get);
					detalle.setCod_tipo_documento(itemDocDev.getCod_tipo_documento());
					detalle.setCod_tipo_movimiento(itemDocDev.getCod_tipo_movimiento());
//					detalle.setDes_direccion_titular(itemDevolucion.get);
//					detalle.setDes_nombre_titular(itemDevolucion.get);
					detalle.setFec_cargado(itemDocDev.getFec_cargado());
					detalle.setFec_movimiento(itemDocDev.getFec_movimiento());
					detalle.setFec_nuevo_vencimiento(itemDocDev.getFec_nuevo_vencimiento());
					detalle.setFec_vencimiento(itemDocDev.getFec_vencimiento());
//					detalle.setImp_importe(itemDevolucion.get);
					detalle.setImp_nuevo_importe(itemDocDev.getImp_nuevo_importe());
					detalle.setInd_ingreso_salida("1");
//					detalle.setNum_cuenta_corriente(itemDevolucion.get);
					detalle.setNum_letra(itemDocDev.getNum_letra());
					detalleControlDocDAO.insertarRegistro(detalle);
					
					docDevueltoDAO.deleteRegistro(itemDocDev);
				}else{	
					docDevueltoDAO.deleteRegistro(itemDocDev);
				}
			}
		}
	
	}

	@Override
	public List<ControlDocBean> consultaGeneral(Map<String, Object> param)
			throws Exception {
		List<ControlDocBean> listaDocu = new ArrayList<ControlDocBean>();
		try {
			listaDocu = controlDocDAO.obtenerDocMap(param);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return listaDocu;
	}

	@Override
	public List<ControlDocBean> consultaEspecifica(Map<String, Object> param)
			throws Exception {
//		List<ControlDocBean> listaDocu = new ArrayList<ControlDocBean>();
//		try {
//			listaDocu = controlDocDAO.listConsEspecifica(param);
//		} catch (Exception e) {
//			log.error(e);
//			e.printStackTrace();
//		}
//		return listaDocu;
		return controlDocDAO.listConsEspecifica(param);
	}

	@SuppressWarnings({ "unused", "static-access" })
	@Override
	public void generarReporte(Map<String, Object> paramRepo, HttpServletResponse response) throws Exception {

		if (log.isDebugEnabled()) log.debug("*** INICIO generarReporte ***");
		
		List<DetalleControlDocBean> listaDetalleBean = new ArrayList<DetalleControlDocBean>();
		listaDetalleBean = detalleControlDocDAO.obtenerLista(paramRepo);
		
		try {

			String nomArchivoSalida = "ReporteDeMovimientos_".concat(paramRepo.get("cod_juliano").toString()).concat(".xls");
			FileOutputStream fileOut = null;
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Hoja1");
			sheet.getPrintSetup().setLandscape(true); //Orientacion Vertical
	        sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //TamaÃ±o papel A4
	        sheet.getPrintSetup().setScale((short)55); //Escala de impresion 75%
	        //RepeatingRowsAndColumns sheetIndex, startColumn, endColumn, startRow, endRow
	        wb.setRepeatingRowsAndColumns(0, 0, 19, 0, 3); //REPETIR FILAS EN EXTREMO SUPERIOR (IMPRESION)
	        sheet.setMargin(sheet.RightMargin, 0.7);
	        sheet.setMargin(sheet.LeftMargin, 0.7);
	        
//	        sheet.setColumnWidth(0,  2066); //NUM_LETRA          
//	        sheet.setColumnWidth(1,  2942); //COD_JULIANO 
//	        sheet.setColumnWidth(2,  3126); //COD_CARTERA  
//	        sheet.setColumnWidth(3,  2138); //FEC_MOVIMIENTO 
//	        sheet.setColumnWidth(4,  2685); //FEC_VENCIMIENTO 
//	        sheet.setColumnWidth(5,  3126); //COD_PLAZA_COBRO 
//	        sheet.setColumnWidth(6,  4990); //COD_PLAZA_ACTUAL 
//	        sheet.setColumnWidth(7,  4186); //COD_TIPO_MOVIMIENTO 
//	        sheet.setColumnWidth(8,  4186); //IMP_IMPORTE 
//	        sheet.setColumnWidth(9,  4150); //COD_OFICINA_MOVIMIENTO 
//	        sheet.setColumnWidth(10, 2760); //COD_TIPO_DOCUMENTO 
//	        sheet.setColumnWidth(11, 2760); //COD_FORMA_PAGO 
//	        sheet.setColumnWidth(12, 2284); //FEC_NUEVO_VENCIMIENTO 
//	        sheet.setColumnWidth(13, 2760); //IMP_NUEVO_IMPORTE 
//	        sheet.setColumnWidth(14, 2760); //NUM_CUENTA_CORRIENTE 
//	        sheet.setColumnWidth(15, 2284); //DES_NOMBRE_TITULAR     
//	        sheet.setColumnWidth(16, 2760); //DES_DIRECCION_TITULAR  
//	        sheet.setColumnWidth(17, 2284); //FEC_CARGADO 
//	        sheet.setColumnWidth(18, 2760); //IND_INGRESO_SALIDA 
//	        sheet.setColumnWidth(19, 2760); //COD_OBSERVACION_UBICA
	        
	        Row rowTitulo = sheet.createRow((short) 1);
	        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 19));
	        Cell cell = rowTitulo.createCell((short) 0);
	        cell.setCellType(cell.CELL_TYPE_STRING);
	        cell.setCellStyle(styleTitulo(wb));
	        cell.setCellValue("REPORTE DE MOVIMIENTOS DE DOCUMENTO");
	        
	        Row rowCuerpo = sheet.createRow((short)3);
	        Cell cellCab = rowCuerpo.createCell((short) 0);
	        cellCab.setCellType(cellCab.CELL_TYPE_STRING);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("NUM_LETRA");
	        
	        cellCab = rowCuerpo.createCell((short) 1);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("COD_JULIANO");
	        
	        cellCab = rowCuerpo.createCell((short) 2);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("COD_CARTERA");

	        cellCab = rowCuerpo.createCell((short) 3);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("FEC_MOVIMIENTO");
	        
	        cellCab = rowCuerpo.createCell((short) 4);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("FEC_VENCIMIENTO");
	        
	        cellCab = rowCuerpo.createCell((short) 5);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("COD_PLAZA_COBRO");

	        cellCab = rowCuerpo.createCell((short) 6);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("COD_PLAZA_ACTUAL");
	        
	        cellCab = rowCuerpo.createCell((short) 7);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("COD_TIPO_MOVIMIENTO");
	        
	        cellCab = rowCuerpo.createCell((short) 8);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("IMP_IMPORTE");
	        
	        cellCab = rowCuerpo.createCell((short) 9);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("COD_OFICINA_MOVIMIENTO");

	        cellCab = rowCuerpo.createCell((short) 10);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("COD_TIPO_DOCUMENTO");
	        
	        cellCab = rowCuerpo.createCell((short) 11);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("COD_FORMA_PAGO");
	        
	        cellCab = rowCuerpo.createCell((short) 12);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("FEC_NUEVO_VENCIMIENTO");
	        
	        cellCab = rowCuerpo.createCell((short) 13);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("IMP_NUEVO_IMPORTE");

	        cellCab = rowCuerpo.createCell((short) 14);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("NUM_CUENTA_CORRIENTE");
	        
	        cellCab = rowCuerpo.createCell((short) 15);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("DES_NOMBRE_TITULAR");
	        
	        cellCab = rowCuerpo.createCell((short) 16);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("DES_DIRECCION_TITULAR");
	        
	        cellCab = rowCuerpo.createCell((short) 17);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("FEC_CARGADO");

	        cellCab = rowCuerpo.createCell((short) 18);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("IND_INGRESO_SALIDA");
	        
	        cellCab = rowCuerpo.createCell((short) 19);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("COD_OBSERVACION_UBICA");
	        

	        sheet.createFreezePane(0, 4); //Fijar Cabecera 
	        
	        int rownum=4, contador=0;
	        
	        if (!listaDetalleBean.isEmpty()) {
	        	DetalleControlDocBean item = null;
	        	for (int i = 0; i < listaDetalleBean.size(); i++) {

					item = listaDetalleBean.get(i);
					
					rowCuerpo = sheet.createRow(rownum+i); //
	            	Cell cellCuerpo = null;
	            	
	            	//NUM_LETRA 
	            	cellCuerpo = rowCuerpo.createCell(0);  // 
	    	        cellCuerpo.setCellValue( item.getNum_letra() == null ? "" : item.getNum_letra());
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));

		    		//COD_JULIANO
		    		cellCuerpo = rowCuerpo.createCell(1);  // 
	    	        cellCuerpo.setCellValue( item.getCod_juliano() == null ? "" :  item.getCod_juliano());
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
	    	        
		    		//COD_CARTERA
		    		cellCuerpo = rowCuerpo.createCell(2);  // 
	    	        cellCuerpo.setCellValue(item.getCod_cartera() == null ? "" : item.getCod_cartera());
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));    	        
		    		
	    	        //FEC_MOVIMIENTO
		    		cellCuerpo = rowCuerpo.createCell(3);  // 
	    	        cellCuerpo.setCellValue(item.getFec_movimiento() == null ? "" : Util.formatearFecha(item.getFec_movimiento()));
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
					
	    	        //FEC_VENCIMIENTO
		    		cellCuerpo = rowCuerpo.createCell(4);  // 
	    	        cellCuerpo.setCellValue(item.getFec_vencimiento() == null ? "" : Util.formatearFecha(item.getFec_vencimiento()));
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
	    	        
	    	        //COD_PLAZA_COBRO
		    		cellCuerpo = rowCuerpo.createCell(5);  // 
	    	        cellCuerpo.setCellValue(item.getCod_plaza_cobro()== null ? "" :item.getCod_plaza_cobro() );
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
	    	        
	    	        //COD_PLAZA_ACTUAL
		    		cellCuerpo = rowCuerpo.createCell(6);  // 
	    	        cellCuerpo.setCellValue(item.getCod_plaza_actual()== null ? "" : item.getCod_plaza_actual());
	    	        //styleDetalle.setWrapText(true);
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
	    	        
	    	        //COD_TIPO_MOVIMIENTO
		    		cellCuerpo = rowCuerpo.createCell(7);  // 
	    	        cellCuerpo.setCellValue(item.getCod_tipo_movimiento()== null ? "" : item.getCod_tipo_movimiento());
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
	    	        
	    	        //IMP_IMPORTE
		    		cellCuerpo = rowCuerpo.createCell(8);  // 
	    	        cellCuerpo.setCellValue(item.getImp_importe()== null ? "" : Util.formatearImporte(item.getImp_importe()));
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
	    	        
	    	        //COD_OFICINA_MOVIMIENTO
		    		cellCuerpo = rowCuerpo.createCell(9);  // 
	    	        cellCuerpo.setCellValue(item.getCod_oficina_movimiento()== null ? "" : item.getCod_oficina_movimiento());
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
	    	        
	    	        //COD_TIPO_DOCUMENTO
		    		cellCuerpo = rowCuerpo.createCell(10);  // 
		    		cellCuerpo.setCellValue(item.getCod_tipo_documento()== null ? "" : item.getCod_tipo_documento());
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
	    	        
	    	        //COD_FORMA_PAGO
		    		cellCuerpo = rowCuerpo.createCell(11);  // 
		    		cellCuerpo.setCellValue(item.getCod_forma_pago()== null ? "" : item.getCod_forma_pago());
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
	    	        
	    	        //FEC_NUEVO_VENCIMIENTO
		    		cellCuerpo = rowCuerpo.createCell(12);  // 
	    	        cellCuerpo.setCellValue(item.getFec_nuevo_vencimiento()== null ? "" : Util.formatearFecha(item.getFec_nuevo_vencimiento()));
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));  
	    	        
		    	    //IMP_NUEVO_IMPORTE
	    	        cellCuerpo = rowCuerpo.createCell(13);  // 
	    	        cellCuerpo.setCellValue(item.getImp_nuevo_importe()== null ? "" : Util.formatearImporte(item.getImp_nuevo_importe()));
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
	    	        
		    	    //NUM_CUENTA_CORRIENTE
	    	        cellCuerpo = rowCuerpo.createCell(14);  // 
	    	        cellCuerpo.setCellValue(item.getNum_cuenta_corriente()== null ? "" : item.getNum_cuenta_corriente());
	    	        cellCuerpo.setCellStyle(styleDetalle(wb)); 
	    	        
		    	    //DES_NOMBRE_TITULAR
	    	        cellCuerpo = rowCuerpo.createCell(15);  // 
	    	        cellCuerpo.setCellValue(item.getDes_nombre_titular()== null ? "" : item.getDes_nombre_titular());
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
	    	        
		    	    //DES_DIRECCION_TITULAR
	    	        cellCuerpo = rowCuerpo.createCell(16);  // 
	    	        cellCuerpo.setCellValue(item.getDes_direccion_titular()== null ? "" : item.getDes_direccion_titular());
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
	    	        
		    	    //FEC_CARGADO
	    	        cellCuerpo = rowCuerpo.createCell(17);  // 
	    	        cellCuerpo.setCellValue(item.getFec_cargado()== null ? "" : Util.formatearFecha(item.getFec_cargado()));
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
	    	        
		    	    //IND_INGRESO_SALIDA
	    	        cellCuerpo = rowCuerpo.createCell(18);  // 
	    	        cellCuerpo.setCellValue(item.getInd_ingreso_salida()== null ? "" : Util.descripIndicador(item.getInd_ingreso_salida().trim()));
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
	    	        
		    	    //COD_OBSERVACION_UBICA
	    	        cellCuerpo = rowCuerpo.createCell(19);  // 
	    	        cellCuerpo.setCellValue(item.getCod_observacion_ubica()== null ? "" : Util.descripObservacion(item.getCod_observacion_ubica()));
	    	        cellCuerpo.setCellStyle(styleDetalle(wb));
	    	        
	    	        contador = rownum+i;
				}
	        }else{
	        	rowCuerpo = sheet.createRow(rownum); //
	        	Cell cellCuerpo = null;
	        	
	        	cellCuerpo = rowCuerpo.createCell(0);  // 
		        cellCuerpo.setCellValue("NO EXISTE INFORMACION PARA LA CONSULTA REALIZADA");
		        contador = rownum;
	        }
	       
	        //ESCRIBIENDO EL ARCHIVO DE SALIDA
	        fileOut = new FileOutputStream(System.getProperty("user.home").concat("/")+nomArchivoSalida);
	        wb.write(fileOut);
			
	        response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "inline; filename="+ nomArchivoSalida);
			byte[] archivo = null;
			archivo = filetoByteArray(System.getProperty("user.home").concat("/")+nomArchivoSalida);
			IOUtils.write(archivo, response.getOutputStream());
						
		} catch (Exception ex) {
			if (log.isDebugEnabled()) log.debug("Error - ProcesarBovedaServiceImpl.generarReporte");
			log.error(ex, ex);
			throw ex;
		} finally {
			if (log.isDebugEnabled()) log.debug("Final - ProcesarBovedaServiceImpl.generarReporte");
		}
	}
	
	public CellStyle styleTitulo(Workbook wb){
		CellStyle styleTitulo = wb.createCellStyle();
		Font font = wb.createFont();
 		font.setFontHeightInPoints((short) 12);
 		//font.setFontName("Calibri");
 		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
 		styleTitulo.setFont(font);
 		styleTitulo.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		return styleTitulo;
	}
	
	public CellStyle styleCabecera(Workbook wb){
		CellStyle styleCabecera = wb.createCellStyle();
		Font fontCab = wb.createFont();
        fontCab.setFontHeightInPoints((short) 9);
        //fontCab.setFontName("Arial");
        fontCab.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        
        styleCabecera.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        styleCabecera.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        styleCabecera.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        styleCabecera.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        styleCabecera.setFont(fontCab);
        styleCabecera.setWrapText(true);
        styleCabecera.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		return styleCabecera;
	}
	
	public CellStyle styleDetalle(Workbook wb){
		CellStyle styleDetalle = wb.createCellStyle();
		Font fontDet = wb.createFont();
        fontDet.setFontHeightInPoints((short) 9);
        //fontDet.setFontName("Calibri");
        styleDetalle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        styleDetalle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleDetalle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleDetalle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleDetalle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleDetalle.setWrapText(true);
//        styleDetalle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        styleDetalle.setFont(fontDet);
		return styleDetalle;
	}
	
	@Override
	public List<DetalleControlDocBean> listDetalle(Map<String, Object> param) throws Exception {
		List<DetalleControlDocBean> listaDetalle = new ArrayList<DetalleControlDocBean>();
		try {
			listaDetalle = detalleControlDocDAO.obtenerLista(param);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return listaDetalle;
	}

	@SuppressWarnings({ "static-access", "unused" })
	@Override
	public void generarReporteMasivo(List<ControlDocBean> listaDocu, HttpServletResponse response)
			throws Exception {
		
			if (log.isDebugEnabled()) log.debug("*** INICIO generarReporteMasivo ***");
			
			try{
	
				String nomArchivoSalida = "ReporteDeDocumentos.xlsx";
				FileOutputStream fileOut = null;
				
	//			HSSFWorkbook wb = new HSSFWorkbook();
//				XSSFWorkbook wb = new XSSFWorkbook();
				SXSSFWorkbook wb = new SXSSFWorkbook(4000);
//				XSSFSheet sheet = wb.createSheet("Hoja1") ;
	//			HSSFSheet sheet = wb.createSheet("Hoja1");
				Sheet sheet = wb.createSheet("Hoja1") ;
				sheet.getPrintSetup().setLandscape(true); //Orientacion Vertical
		        sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //TamaÃ±o papel A4
		        sheet.getPrintSetup().setScale((short)34); //Escala de impresion 75%
		        //RepeatingRowsAndColumns sheetIndex, startColumn, endColumn, startRow, endRow
		        //wb.setRepeatingRowsAndColumns(0, 0, 32, 0, 3); //REPETIR FILAS EN EXTREMO SUPERIOR (IMPRESION)
		        sheet.setMargin(sheet.RightMargin, 0.7);
		        sheet.setMargin(sheet.LeftMargin, 0.7);
		        
		        sheet.setColumnWidth(0,  2066);  //COD_OPERADOR 
		        sheet.setColumnWidth(1,  2942);  //NUM_CUENTA 
		        sheet.setColumnWidth(2,  2066);  //COD_SERVICIO 
		        sheet.setColumnWidth(3,  4150);  //COD_JULIANO 
		        sheet.setColumnWidth(4,  2760);  //FEC_VENCIMIENTO 
		        sheet.setColumnWidth(5,  3126);  //IMP_IMPORTE  
		        sheet.setColumnWidth(6,  2066);  //COD_CLASE_DOCUMENTO 
		        sheet.setColumnWidth(7,  2066);  //COD_AGENCIA 
		        sheet.setColumnWidth(8,  2066);  //COD_PLAZA 
		        sheet.setColumnWidth(9,  4150);  //NUM_CLIENTE 
		                                         //
		        sheet.setColumnWidth(10, 2760);  //FEC_GIRO 
		        sheet.setColumnWidth(11, 2760);  //FEC_DESEMBOLSO 
		        sheet.setColumnWidth(12, 2760);  //FEC_PROCESO 
		        sheet.setColumnWidth(13,  4990); //DES_NOMBRE 
//		        sheet.setColumnWidth(14,  4990); //DES_DIRECCION 
//		        sheet.setColumnWidth(15,  3126); //NUM_CUENTA_ACEPTANTE 
//		        sheet.setColumnWidth(16,  2138); //NUM_TASA_OPCIONAL 
//		        sheet.setColumnWidth(17,  2685); //NUM_PORCENTAJE_RENOVA 
//		        sheet.setColumnWidth(18,  2685); //IND_INSTRUCCION_COBRO 
//		        sheet.setColumnWidth(19,  4990); //IND_EXONERACION_IMPUEST 
//		                                         //
//		        sheet.setColumnWidth(20,  2685); //NUM_TASA_GARANTIA 
//		        sheet.setColumnWidth(21,  4186); //COD_PLANILLA 
//		        sheet.setColumnWidth(22,  4150); //NUM_CUENTA_FACTORING 
//		        sheet.setColumnWidth(23,  2760); //COD_TIPO_DOC_IDENTIDAD 
//		        sheet.setColumnWidth(24,  2760); //NUM_DOC_IDENTIDAD 
//		        sheet.setColumnWidth(25,  2284); //IMP_PRESTAMO_VINCULADO 
//		        sheet.setColumnWidth(26,  2066); //IND_INGRESO_SALIDA 
//		        sheet.setColumnWidth(27,  2942); //DES_OBSERVACION_UBICA 
//		        sheet.setColumnWidth(28,  2685); //COD_TIPO_MONEDA 
//		        sheet.setColumnWidth(29,  2138); //NUM_LETRA          
//		                                         //
//		        sheet.setColumnWidth(30,  2685); //COD_TIPO_MOVIMIENTO 
//		        sheet.setColumnWidth(31,  3126); //FEC_NUEVO_VENCIMIENTO 
//		        sheet.setColumnWidth(32,  2284); //IMP_NUEVO_IMPORTE
		        
		        Row rowTitulo = sheet.createRow((short) 1);
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 32));
		        Cell cell = rowTitulo.createCell((short) 0);
		        cell.setCellType(cell.CELL_TYPE_STRING);
		        cell.setCellStyle(styleTitulo(wb));
		        cell.setCellValue("REPORTE DE DOCUMENTOS EN BOVEDA");
		        
		        Row rowCuerpo = sheet.createRow((short)3);
		        Cell cellCab = rowCuerpo.createCell((short) 0);
		        cellCab.setCellType(cellCab.CELL_TYPE_STRING);
		        cellCab.setCellStyle(styleCabecera(wb));
		        cellCab.setCellValue("Juliano");
		        
		        cellCab = rowCuerpo.createCell((short) 1);
		        cellCab.setCellStyle(styleCabecera(wb));
		        cellCab.setCellValue("Planilla");
		        
		        cellCab = rowCuerpo.createCell((short) 2);
		        cellCab.setCellStyle(styleCabecera(wb));
		        cellCab.setCellValue("Cod Plaza");
		        
		        cellCab = rowCuerpo.createCell((short) 3);
		        cellCab.setCellStyle(styleCabecera(wb));
		        cellCab.setCellValue("Cod Agencia");
		        
		        cellCab = rowCuerpo.createCell((short) 4);
		        cellCab.setCellStyle(styleCabecera(wb));
		        cellCab.setCellValue("Cod Servicio");
		        
		        cellCab = rowCuerpo.createCell((short) 5);
		        cellCab.setCellStyle(styleCabecera(wb));
		        cellCab.setCellValue("Inst. Cobro");
		        
		        cellCab = rowCuerpo.createCell((short) 6);
		        cellCab.setCellStyle(styleCabecera(wb));
		        cellCab.setCellValue("FEc. Venc");
		        
		        cellCab = rowCuerpo.createCell((short) 7);
		        cellCab.setCellStyle(styleCabecera(wb));
		        cellCab.setCellValue("Nombre");
		        
		        cellCab = rowCuerpo.createCell((short) 8);
		        cellCab.setCellStyle(styleCabecera(wb));
		        cellCab.setCellValue("Moneda");
		        
		        cellCab = rowCuerpo.createCell((short) 9);
		        cellCab.setCellStyle(styleCabecera(wb));
		        cellCab.setCellValue("Importe");
		        
		        cellCab = rowCuerpo.createCell((short) 10);
		        cellCab.setCellStyle(styleCabecera(wb));
		        cellCab.setCellValue("Tipo Mov");
		        
		        cellCab = rowCuerpo.createCell((short) 11);
		        cellCab.setCellStyle(styleCabecera(wb));
		        cellCab.setCellValue("Nuevo Importe");
		        
		        cellCab = rowCuerpo.createCell((short) 12);
		        cellCab.setCellStyle(styleCabecera(wb));
		        cellCab.setCellValue("Nueva Fec Venc");
		        
		        cellCab = rowCuerpo.createCell((short) 13);
		        cellCab.setCellStyle(styleCabecera(wb));
		        cellCab.setCellValue("Fisico");
		        
//		        Cell cellCab = rowCuerpo.createCell((short) 0);
//		        cellCab.setCellType(cellCab.CELL_TYPE_STRING);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Cod Banco");
//		        
//		        cellCab = rowCuerpo.createCell((short) 1);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Num Cuenta");
//		        
//		        cellCab = rowCuerpo.createCell((short) 2);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Cod Servicio");
//		        
//		        cellCab = rowCuerpo.createCell((short) 3);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Cod Juliano");
//		        
//		        cellCab = rowCuerpo.createCell((short) 4);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Fec Venc");
//		        
//		        cellCab = rowCuerpo.createCell((short) 5);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Importe");
//		        
//		        cellCab = rowCuerpo.createCell((short) 6);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Clase Doc.");
//		        
//		        cellCab = rowCuerpo.createCell((short) 7);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Cod Agencia");
//		        
//		        cellCab = rowCuerpo.createCell((short) 8);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Cod Plaza");
//		        
//		        cellCab = rowCuerpo.createCell((short) 9);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Num Cliente");
//		        
//		        cellCab = rowCuerpo.createCell((short) 10);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Fec Giro");
//		        
//		        cellCab = rowCuerpo.createCell((short) 11);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Fec Desemb");
//		        
//		        cellCab = rowCuerpo.createCell((short) 12);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Fec Proc");
//		        
//		        cellCab = rowCuerpo.createCell((short) 13);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Nombre");
		        
//		        cellCab = rowCuerpo.createCell((short) 14);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Direccion");
//		        
//		        cellCab = rowCuerpo.createCell((short) 15);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Num Cuenta Acep");
//		        
//		        cellCab = rowCuerpo.createCell((short) 16);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Tasa Opcional");
//		        
//		        cellCab = rowCuerpo.createCell((short) 17);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Porcentaje Renov");
//		        
//		        cellCab = rowCuerpo.createCell((short) 18);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Instruc Cobro");
//		        
//		        cellCab = rowCuerpo.createCell((short) 19);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Exon Impuesto");
//		        
//		        cellCab = rowCuerpo.createCell((short) 20);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Tasa Garantia");
//		        
//		        cellCab = rowCuerpo.createCell((short) 21);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Cod Planilla");
//		        
//		        cellCab = rowCuerpo.createCell((short) 22);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Num Cuenta Fact");
//		        
//		        cellCab = rowCuerpo.createCell((short) 23);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Tipo Doc Iden");
//		        
//		        cellCab = rowCuerpo.createCell((short) 24);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Num Doc Iden");
//		        
//		        cellCab = rowCuerpo.createCell((short) 25);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Prestamo Vinc");
//		        
//		        cellCab = rowCuerpo.createCell((short) 26);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Fisico");
//		        
//		        cellCab = rowCuerpo.createCell((short) 27);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Obser Ubica");
//		        
//		        cellCab = rowCuerpo.createCell((short) 28);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Tipo Moneda");
//		        
//		        cellCab = rowCuerpo.createCell((short) 29);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Num Letra");
//		        
//		        cellCab = rowCuerpo.createCell((short) 30);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Tipo Movimiento");
//		        
//		        cellCab = rowCuerpo.createCell((short) 31);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Fec Nuevo Venc");
//		        
//		        cellCab = rowCuerpo.createCell((short) 32);
//		        cellCab.setCellStyle(styleCabecera(wb));
//		        cellCab.setCellValue("Nuevo Importe");
		        
		        sheet.createFreezePane(0, 4); //Fijar Cabecera 
		        
		        int rownum=4, contador=0;
		        Util.pasarGarbageCollector();
		        completarCampos2(listaDocu, rowCuerpo, sheet, rownum);
//		        if (!listaDocu.isEmpty()) {
//		        	ControlDocBean item = null;
////		        	System.out.println("Cantidad de filas: " + listaDocu.size());
//		        	int maxList = listaDocu.size();
//		        	long tiempoIni = System.nanoTime();
//		        	System.out.println(tiempoIni);
//		        	for (int i = 0; i < maxList; i++) {
//	
//						item = listaDocu.get(0);
//						
//						rowCuerpo = sheet.createRow(rownum+i);
//		            	Cell cellCuerpo = null;
//		            	
//		            	cellCuerpo = rowCuerpo.createCell(0);  //COD_OPERADOR 
//		    	        cellCuerpo.setCellValue( item.getCod_juliano());
//	
//			    		cellCuerpo = rowCuerpo.createCell(1);  //NUM_CUENTA 
//		    	        cellCuerpo.setCellValue( item.getCod_planilla());
//		    	        
//			    		cellCuerpo = rowCuerpo.createCell(2);  //COD_SERVICIO 
//		    	        cellCuerpo.setCellValue(item.getCod_plaza());
//			    		
//			    		cellCuerpo = rowCuerpo.createCell(3);  //COD_JULIANO 
//		    	        cellCuerpo.setCellValue(item.getCod_agencia());
//						
//			    		cellCuerpo = rowCuerpo.createCell(4);  //FEC_VENCIMIENTO 
//		    	        cellCuerpo.setCellValue(item.getCod_servicio());
//		    	        
//			    		cellCuerpo = rowCuerpo.createCell(5);  //IMP_IMPORTE 
//		    	        cellCuerpo.setCellValue(item.getInd_instruccion_cobro() );
//		    	        
//			    		cellCuerpo = rowCuerpo.createCell(6);  //COD_CLASE_DOCUMENTO 
//		    	        cellCuerpo.setCellValue(item.getFec_vencimiento());
//		    	        
//			    		cellCuerpo = rowCuerpo.createCell(7);  //COD_AGENCIA
//		    	        cellCuerpo.setCellValue(item.getDes_nombre());
//		    	        
//			    		cellCuerpo = rowCuerpo.createCell(8);  //COD_PLAZA
//		    	        cellCuerpo.setCellValue(item.getCod_tipo_moneda());
//		    	        
//			    		cellCuerpo = rowCuerpo.createCell(9);  //NUM_CLIENTE
//		    	        cellCuerpo.setCellValue(item.getImp_importe());
//		    	        
//			    		cellCuerpo = rowCuerpo.createCell(10);  //FEC_GIRO
//			    		cellCuerpo.setCellValue(item.getCod_tipo_movimiento());
//		    	        
//			    		cellCuerpo = rowCuerpo.createCell(11);  //FEC_DESEMBOLSO
//			    		cellCuerpo.setCellValue(item.getImp_nuevo_importe());
//		    	        
//			    		cellCuerpo = rowCuerpo.createCell(12);  //FEC_PROCESO 
//		    	        cellCuerpo.setCellValue(item.getFec_nuevo_vencimiento());
//		    	        
//		    	        cellCuerpo = rowCuerpo.createCell(13);  //DES_NOMBRE 
//		    	        cellCuerpo.setCellValue( item.getInd_ingreso_salida());
//		            	
////		            	cellCuerpo = rowCuerpo.createCell(0);  //COD_OPERADOR 
////		    	        cellCuerpo.setCellValue( item.getCod_operador() == null ? "" : item.getCod_operador());
////	
////			    		cellCuerpo = rowCuerpo.createCell(1);  //NUM_CUENTA 
////		    	        cellCuerpo.setCellValue( item.getNum_cuenta() == null ? "" :  item.getNum_cuenta());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(2);  //COD_SERVICIO 
////		    	        cellCuerpo.setCellValue(item.getCod_servicio() == null ? "" : item.getCod_servicio());
////			    		
////			    		cellCuerpo = rowCuerpo.createCell(3);  //COD_JULIANO 
////		    	        cellCuerpo.setCellValue(item.getCod_juliano() == null ? "" : item.getCod_juliano());
////						
////			    		cellCuerpo = rowCuerpo.createCell(4);  //FEC_VENCIMIENTO 
////		    	        cellCuerpo.setCellValue(item.getFec_vencimiento() == null ? "" : item.getFec_vencimiento());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(5);  //IMP_IMPORTE 
////		    	        cellCuerpo.setCellValue(item.getImp_importe()== null ? "" :item.getImp_importe() );
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(6);  //COD_CLASE_DOCUMENTO 
////		    	        cellCuerpo.setCellValue(item.getCod_clase_documento()== null ? "" : item.getCod_clase_documento());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(7);  //COD_AGENCIA
////		    	        cellCuerpo.setCellValue(item.getCod_agencia()== null ? "" : item.getCod_agencia());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(8);  //COD_PLAZA
////		    	        cellCuerpo.setCellValue(item.getCod_plaza()== null ? "" : item.getCod_plaza());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(9);  //NUM_CLIENTE
////		    	        cellCuerpo.setCellValue(item.getNum_cliente()== null ? "" : item.getNum_cliente());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(10);  //FEC_GIRO
////			    		cellCuerpo.setCellValue(item.getFec_giro()== null ? "" : item.getFec_giro());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(11);  //FEC_DESEMBOLSO
////			    		cellCuerpo.setCellValue(item.getFec_desembolso()== null ? "" : item.getFec_desembolso());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(12);  //FEC_PROCESO 
////		    	        cellCuerpo.setCellValue(item.getFec_proceso()== null ? "" : item.getFec_proceso());
////		    	        
////		    	        cellCuerpo = rowCuerpo.createCell(13);  //DES_NOMBRE 
////		    	        cellCuerpo.setCellValue( item.getDes_nombre() == null ? "" : item.getDes_nombre());
//	
////			    		cellCuerpo = rowCuerpo.createCell(14);  //DES_DIRECCION 
////		    	        cellCuerpo.setCellValue( item.getDes_direccion() == null ? "" :  item.getDes_direccion());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(15);  //NUM_CUENTA_ACEPTANTE 
////		    	        cellCuerpo.setCellValue(item.getNum_cuenta_aceptante() == null ? "" : item.getNum_cuenta_aceptante());
////			    		
////			    		cellCuerpo = rowCuerpo.createCell(16);  //NUM_TASA_OPCIONAL
////		    	        cellCuerpo.setCellValue(item.getNum_tasa_opcional() == null ? "" : item.getNum_tasa_opcional());
////						
////			    		cellCuerpo = rowCuerpo.createCell(17);  //NUM_PORCENTAJE_RENOVA
////		    	        cellCuerpo.setCellValue(item.getNum_porcentaje_renova() == null ? "" : item.getNum_porcentaje_renova());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(18);  //IND_INSTRUCCION_COBRO
////		    	        cellCuerpo.setCellValue(item.getInd_instruccion_cobro()== null ? "" :item.getInd_instruccion_cobro() );
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(19);  //IND_EXONERACION_IMPUEST
////		    	        cellCuerpo.setCellValue(item.getInd_exoneracion_impuest()== null ? "" : item.getInd_exoneracion_impuest());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(20);  //NUM_TASA_GARANTIA
////		    	        cellCuerpo.setCellValue(item.getNum_tasa_garantia()== null ? "" : item.getNum_tasa_garantia());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(21);  //COD_PLANILLA
////		    	        cellCuerpo.setCellValue(item.getCod_planilla()== null ? "" : item.getCod_planilla());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(22);  //NUM_CUENTA_FACTORING
////		    	        cellCuerpo.setCellValue(item.getNum_cuenta_factoring()== null ? "" : item.getNum_cuenta_factoring());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(23);  //COD_TIPO_DOC_IDENTIDAD
////			    		cellCuerpo.setCellValue(item.getCod_tipo_doc_identidad()== null ? "" : item.getCod_tipo_doc_identidad());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(24);  //NUM_DOC_IDENTIDAD
////			    		cellCuerpo.setCellValue(item.getNum_doc_identidad()== null ? "" : item.getNum_doc_identidad());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(25);  //IMP_PRESTAMO_VINCULADO
////		    	        cellCuerpo.setCellValue(item.getImp_prestamo_vinculado()== null ? "" : item.getImp_prestamo_vinculado());
////		    	        
////		    	        cellCuerpo = rowCuerpo.createCell(26);  //IND_INGRESO_SALIDA
////		    	        cellCuerpo.setCellValue( item.getInd_ingreso_salida() == null ? "" : item.getInd_ingreso_salida());
////	
////			    		cellCuerpo = rowCuerpo.createCell(27);  //DES_OBSERVACION_UBICA
////		    	        cellCuerpo.setCellValue( item.getDes_observacion_ubica() == null ? "" :  item.getDes_observacion_ubica());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(28);  //COD_TIPO_MONEDA
////		    	        cellCuerpo.setCellValue(item.getCod_tipo_moneda() == null ? "" : item.getCod_tipo_moneda());
////			    		
////			    		cellCuerpo = rowCuerpo.createCell(29);  //NUM_LETRA
////		    	        cellCuerpo.setCellValue(item.getNum_letra() == null ? "" : item.getNum_letra());
////						
////			    		cellCuerpo = rowCuerpo.createCell(30);  //COD_TIPO_MOVIMIENTO
////		    	        cellCuerpo.setCellValue(item.getCod_tipo_movimiento() == null ? "" : item.getCod_tipo_movimiento());
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(31);  //FEC_NUEVO_VENCIMIENTO
////		    	        cellCuerpo.setCellValue(item.getFec_nuevo_vencimiento()== null ? "" :item.getFec_nuevo_vencimiento() );
////		    	        
////			    		cellCuerpo = rowCuerpo.createCell(32);  //IMP_NUEVO_IMPORTE
////		    	        cellCuerpo.setCellValue(item.getImp_nuevo_importe()== null ? "" : item.getImp_nuevo_importe());
//		    	        
//		    	        contador = rownum+i;
//		    	        listaDocu.remove(item);
//	//	    	        Util.pasarGarbageCollector();
//					}
//		        	System.out.println(System.nanoTime() - tiempoIni);
//		        }else{
//		        	rowCuerpo = sheet.createRow(rownum);
//		        	Cell cellCuerpo = null;
//		        	
//		        	cellCuerpo = rowCuerpo.createCell(0);
//			        cellCuerpo.setCellValue("NO EXISTE INFORMACION PARA LA CONSULTA REALIZADA");
//			        contador = rownum;
//		        }
		        listaDocu.clear();
		        listaDocu = null;
		        fileOut = new FileOutputStream(System.getProperty("user.home").concat("/")+nomArchivoSalida);
		        wb.write(fileOut);
				wb = null;
		        response.setContentType("application/octet-stream");
				response.setHeader("Content-disposition", "inline; filename="+ nomArchivoSalida);
				byte[] archivo = null;
				archivo = filetoByteArray(System.getProperty("user.home").concat("/")+nomArchivoSalida);
				IOUtils.write(archivo, response.getOutputStream());
		        
				
		}catch (Exception ex) {
			if (log.isDebugEnabled()) log.debug("Error - MostrarFormServiceImpl.generarReporteMasivo");
			log.error(ex, ex);
			throw ex;
		} finally {
			if (log.isDebugEnabled()) log.debug("Final - MostrarFormServiceImpl.generarReporteMasivo");
			Util.pasarGarbageCollector();
		}
	}

	private int completarCampos2(List<ControlDocBean> listaDocu, Row rowCuerpo, Sheet sheet, int rownum) {
		int contador = 0;
		long tiempoIni = System.nanoTime();
		System.out.println(tiempoIni);
		try {
//			ControlDocBean item = new ControlDocBean();
			for (ControlDocBean item : listaDocu) {
//				item = listaDocu.get(i);
				
				rowCuerpo = sheet.createRow(rownum+contador);
            	Cell cellCuerpo = null;
            	
            	cellCuerpo = rowCuerpo.createCell(0);  //COD_OPERADOR 
    	        cellCuerpo.setCellValue( item.getCod_juliano());

	    		cellCuerpo = rowCuerpo.createCell(1);  //NUM_CUENTA 
    	        cellCuerpo.setCellValue( item.getCod_planilla());
    	        
	    		cellCuerpo = rowCuerpo.createCell(2);  //COD_SERVICIO 
    	        cellCuerpo.setCellValue(item.getCod_plaza());
	    		
	    		cellCuerpo = rowCuerpo.createCell(3);  //COD_JULIANO 
    	        cellCuerpo.setCellValue(item.getCod_agencia());
				
	    		cellCuerpo = rowCuerpo.createCell(4);  //FEC_VENCIMIENTO 
    	        cellCuerpo.setCellValue(item.getCod_servicio());
    	        
	    		cellCuerpo = rowCuerpo.createCell(5);  //IMP_IMPORTE 
    	        cellCuerpo.setCellValue(item.getInd_instruccion_cobro());
    	        
	    		cellCuerpo = rowCuerpo.createCell(6);  //COD_CLASE_DOCUMENTO 
    	        cellCuerpo.setCellValue(item.getFec_vencimiento());
    	        
	    		cellCuerpo = rowCuerpo.createCell(7);  //COD_AGENCIA
    	        cellCuerpo.setCellValue(item.getDes_nombre());
    	        
	    		cellCuerpo = rowCuerpo.createCell(8);  //COD_PLAZA
    	        cellCuerpo.setCellValue(item.getCod_tipo_moneda());
    	        
	    		cellCuerpo = rowCuerpo.createCell(9);  //NUM_CLIENTE
    	        cellCuerpo.setCellValue(item.getImp_importe());
    	        
	    		cellCuerpo = rowCuerpo.createCell(10);  //FEC_GIRO
	    		cellCuerpo.setCellValue(item.getCod_tipo_movimiento());
    	        
	    		cellCuerpo = rowCuerpo.createCell(11);  //FEC_DESEMBOLSO
	    		cellCuerpo.setCellValue(item.getImp_nuevo_importe());
    	        
	    		cellCuerpo = rowCuerpo.createCell(12);  //FEC_PROCESO 
    	        cellCuerpo.setCellValue(item.getFec_nuevo_vencimiento());
    	        
    	        cellCuerpo = rowCuerpo.createCell(13);  //DES_NOMBRE 
    	        cellCuerpo.setCellValue(item.getInd_ingreso_salida());
    	        
    	        contador++;
//    	        listaDocu.remove(item);

//	    		cellCuerpo = rowCuerpo.createCell(14);  //DES_DIRECCION 
//    	        cellCuerpo.setCellValue( item.getDes_direccion() == null ? "" :  item.getDes_direccion());
//    	        
//	    		cellCuerpo = rowCuerpo.createCell(15);  //NUM_CUENTA_ACEPTANTE 
//    	        cellCuerpo.setCellValue(item.getNum_cuenta_aceptante() == null ? "" : item.getNum_cuenta_aceptante());
//	    		
//	    		cellCuerpo = rowCuerpo.createCell(16);  //NUM_TASA_OPCIONAL
//    	        cellCuerpo.setCellValue(item.getNum_tasa_opcional() == null ? "" : item.getNum_tasa_opcional());
//				
//	    		cellCuerpo = rowCuerpo.createCell(17);  //NUM_PORCENTAJE_RENOVA
//    	        cellCuerpo.setCellValue(item.getNum_porcentaje_renova() == null ? "" : item.getNum_porcentaje_renova());
//    	        
//	    		cellCuerpo = rowCuerpo.createCell(18);  //IND_INSTRUCCION_COBRO
//    	        cellCuerpo.setCellValue(item.getInd_instruccion_cobro()== null ? "" :item.getInd_instruccion_cobro() );
//    	        
//	    		cellCuerpo = rowCuerpo.createCell(19);  //IND_EXONERACION_IMPUEST
//    	        cellCuerpo.setCellValue(item.getInd_exoneracion_impuest()== null ? "" : item.getInd_exoneracion_impuest());
//    	        
//	    		cellCuerpo = rowCuerpo.createCell(20);  //NUM_TASA_GARANTIA
//    	        cellCuerpo.setCellValue(item.getNum_tasa_garantia()== null ? "" : item.getNum_tasa_garantia());
//    	        
//	    		cellCuerpo = rowCuerpo.createCell(21);  //COD_PLANILLA
//    	        cellCuerpo.setCellValue(item.getCod_planilla()== null ? "" : item.getCod_planilla());
//    	        
//	    		cellCuerpo = rowCuerpo.createCell(22);  //NUM_CUENTA_FACTORING
//    	        cellCuerpo.setCellValue(item.getNum_cuenta_factoring()== null ? "" : item.getNum_cuenta_factoring());
//    	        
//	    		cellCuerpo = rowCuerpo.createCell(23);  //COD_TIPO_DOC_IDENTIDAD
//	    		cellCuerpo.setCellValue(item.getCod_tipo_doc_identidad()== null ? "" : item.getCod_tipo_doc_identidad());
//    	        
//	    		cellCuerpo = rowCuerpo.createCell(24);  //NUM_DOC_IDENTIDAD
//	    		cellCuerpo.setCellValue(item.getNum_doc_identidad()== null ? "" : item.getNum_doc_identidad());
//    	        
//	    		cellCuerpo = rowCuerpo.createCell(25);  //IMP_PRESTAMO_VINCULADO
//    	        cellCuerpo.setCellValue(item.getImp_prestamo_vinculado()== null ? "" : item.getImp_prestamo_vinculado());
//    	        
//    	        cellCuerpo = rowCuerpo.createCell(26);  //IND_INGRESO_SALIDA
//    	        cellCuerpo.setCellValue( item.getInd_ingreso_salida() == null ? "" : item.getInd_ingreso_salida());
//
//	    		cellCuerpo = rowCuerpo.createCell(27);  //DES_OBSERVACION_UBICA
//    	        cellCuerpo.setCellValue( item.getDes_observacion_ubica() == null ? "" :  item.getDes_observacion_ubica());
//    	        
//	    		cellCuerpo = rowCuerpo.createCell(28);  //COD_TIPO_MONEDA
//    	        cellCuerpo.setCellValue(item.getCod_tipo_moneda() == null ? "" : item.getCod_tipo_moneda());
//	    		
//	    		cellCuerpo = rowCuerpo.createCell(29);  //NUM_LETRA
//    	        cellCuerpo.setCellValue(item.getNum_letra() == null ? "" : item.getNum_letra());
//				
//	    		cellCuerpo = rowCuerpo.createCell(30);  //COD_TIPO_MOVIMIENTO
//    	        cellCuerpo.setCellValue(item.getCod_tipo_movimiento() == null ? "" : item.getCod_tipo_movimiento());
//    	        
//	    		cellCuerpo = rowCuerpo.createCell(31);  //FEC_NUEVO_VENCIMIENTO
//    	        cellCuerpo.setCellValue(item.getFec_nuevo_vencimiento()== null ? "" :item.getFec_nuevo_vencimiento() );
//    	        
//	    		cellCuerpo = rowCuerpo.createCell(32);  //IMP_NUEVO_IMPORTE
//    	        cellCuerpo.setCellValue(item.getImp_nuevo_importe()== null ? "" : item.getImp_nuevo_importe());
    	        
//    	        contador = rownum+i;
			}
			System.out.println(System.nanoTime() - tiempoIni);
		} catch (Exception e) {
			log.error("ERROR: " + e);
			e.printStackTrace();
		}
		return contador;
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public boolean generarReporteDocumentos(String tipoReporte, HttpServletResponse response) throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		Map<String, Object> mapResul = new HashMap<String, Object>();
		HSSFWorkbook wb = null;
		String descCabTitulo = "";
		String indIngSal = "";
		try {
			
			if(tipoReporte.equals("1")){
				descCabTitulo = Constantes.DES_CLASE_DOC_1;
				indIngSal = "1";
	        }else if(tipoReporte.equals("2")){
	        	descCabTitulo = Constantes.DES_CLASE_DOC_2;
	        	indIngSal = "0";
	        }else if(tipoReporte.equals("3")){
	        	descCabTitulo = Constantes.DES_CLASE_DOC_3;
	        	indIngSal = "2";
	        }else if(tipoReporte.equals("4")){
	        	descCabTitulo = Constantes.DES_CLASE_DOC_4;
	        }
			
			for(int i = 0; i < 14; i++){
				Map<String, Object> mapTemp = new HashMap<String, Object>();
				mapParam.put("cod_servicio", obtenerServicio(i).get("codigo").toString());
				if(!indIngSal.equals("")) {
					mapParam.put("ind_ingreso_salida", indIngSal);
				}
				mapTemp = controlDocDAO.docuXServicio(mapParam);
				mapResul.put(obtenerServicio(i).get("codigo").toString() ,mapTemp);
			}
			
			FileOutputStream fileOut = null;
			wb = new HSSFWorkbook();
			
			String nomArchivoSalida =  System.getProperty("user.home")+"/Resumen_Documentos.xls";
			HSSFSheet sheet = wb.createSheet("Hoja1");
			sheet.getPrintSetup().setLandscape(true); //Orientacion Vertical
	        sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //Tamaño papel A4
	        sheet.getPrintSetup().setScale((short)50); //Escala de impresion 75%
	        wb.setRepeatingRowsAndColumns(0, 0, 25, 0, 4); //REPETIR FILAS EN EXTREMO SUPERIOR (IMPRESION)
	        sheet.setMargin(sheet.RightMargin, 0.7);
	        sheet.setMargin(sheet.LeftMargin, 0.7);
	        
	        sheet.setColumnWidth(0,  2685); //
	        sheet.setColumnWidth(1,  6252); //
	        sheet.setColumnWidth(2,  2284); //
	        sheet.setColumnWidth(3,  2685); //
	        sheet.setColumnWidth(4,  2284); //
	        sheet.setColumnWidth(5,  2685); //
	        sheet.setColumnWidth(6,  2284); //
	        sheet.setColumnWidth(7,  2685); //
	        sheet.setColumnWidth(8,  2284); //
	        sheet.setColumnWidth(9,  2685); //
	        sheet.setColumnWidth(10, 2284); //
	        sheet.setColumnWidth(11, 2685); //
	        sheet.setColumnWidth(12, 2284); //
	        
	        sheet.setColumnWidth(13,  2685); //
	        sheet.setColumnWidth(14,  2284); //
	        sheet.setColumnWidth(15,  2685); //
	        sheet.setColumnWidth(16,  2284); //
	        sheet.setColumnWidth(17,  2685); //
	        sheet.setColumnWidth(18,  2284); //
	        sheet.setColumnWidth(19,  2685); //
	        sheet.setColumnWidth(20,  2284); //
	        sheet.setColumnWidth(21,  2685); //
//	        sheet.setColumnWidth(22,  2284); //
//	        sheet.setColumnWidth(23, 2685);  //
//	        sheet.setColumnWidth(24, 2284);  //
//	        sheet.setColumnWidth(25, 2685);  //
	        
	        Row row1 = sheet.createRow((short) 1);
	        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 21));
	        Cell cell = row1.createCell((short) 0);
	        cell.setCellStyle(styleTitulo(wb));
	        cell.setCellValue("RESUMEN DE DOCUMENTOS");
	        
	        Row row3 = sheet.createRow((short)3);
	        
	        Row row4 = sheet.createRow((short)4);
	        
	        CellRangeAddress region = new CellRangeAddress(3,4,0,0);
	        
	        HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, region, sheet, wb);
	        HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, region, sheet, wb);
	        HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, region, sheet, wb);
	        HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, region, sheet, wb);
	        
	        Cell cellCab = row3.createCell((short) 0);
	        //cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("Código de servicio");
	        sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 0));
	        
	        cellCab = row3.createCell((short) 1);
	        //cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue(descCabTitulo);
	       
	        sheet.addMergedRegion(new CellRangeAddress(3, 4, 1, 1));
	        
	        //LETRAS POR ACEPTAR
	        /*******************************************************/
	        sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 5));
	        cellCab = row3.createCell((short) 2);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("LETRA POR ACEPTAR");
	        
	        cellCab = row4.createCell((short) 2);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("CANTIDAD");
	        
	        cellCab = row4.createCell((short) 3);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("IMPORTE S/.");
	        
	        /*******************************************************/
	        cellCab = row4.createCell((short) 4);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("CANTIDAD");
	        
	        cellCab = row4.createCell((short) 5);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("IMPORTE $");
	        
	        //LETRAS ACEPTADAS
	        /*******************************************************/
	        sheet.addMergedRegion(new CellRangeAddress(3, 3, 6, 9));
	        cellCab = row3.createCell((short) 6);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("LETRA ACEPTADA");
	        
	        cellCab = row4.createCell((short) 6);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("CANTIDAD");
	        
	        cellCab = row4.createCell((short) 7);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("IMPORTE S/.");
	        
	        /*******************************************************/
	        cellCab = row4.createCell((short) 8);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("CANTIDAD");
	        
	        cellCab = row4.createCell((short) 9);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("IMPORTE $");
	        
	        //LETRAS RENOVADAS
	        /*******************************************************/
	        sheet.addMergedRegion(new CellRangeAddress(3, 3, 10, 13));
	        cellCab = row3.createCell((short) 10);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("FACTURA COMERCIAL FISICA");
	        
	        cellCab = row4.createCell((short) 10);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("CANTIDAD");
	        
	        cellCab = row4.createCell((short) 11);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("IMPORTE S/.");
	        
	        /*******************************************************/
	        cellCab = row4.createCell((short) 12);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("CANTIDAD");
	        
	        cellCab = row4.createCell((short) 13);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("IMPORTE $");
	        
	        
	        //FACTURA COMERCIAL FISICA
	        /*******************************************************/
	        sheet.addMergedRegion(new CellRangeAddress(3, 3, 14, 17));
	        cellCab = row3.createCell((short) 14);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("FACTURA NEGOCIABLE FISICA");
	        
	        cellCab = row4.createCell((short) 14);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("CANTIDAD");
	        
	        cellCab = row4.createCell((short) 15);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("IMPORTE S/.");
	        
	        /*******************************************************/
	        cellCab = row4.createCell((short) 16);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("CANTIDAD");
	        
	        cellCab = row4.createCell((short) 17);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("IMPORTE $");
	        
	        //FACTURA NEGOCIABLE FISICA
	        /*******************************************************/
	        sheet.addMergedRegion(new CellRangeAddress(3, 3, 18, 21));
	        cellCab = row3.createCell((short) 18);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("FACTURA COMERCIAL FISICA");
	        
	        cellCab = row4.createCell((short) 18);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("CANTIDAD");
	        
	        cellCab = row4.createCell((short) 19);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("IMPORTE S/.");
	        
	        /*******************************************************/
	        cellCab = row4.createCell((short) 20);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("CANTIDAD");
	        
	        cellCab = row4.createCell((short) 21);
//	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("IMPORTE $");
	        
	        //FACTURA NEGOCIABLE ELECTRONICA
	        /*******************************************************/
//	        sheet.addMergedRegion(new CellRangeAddress(3, 3, 22, 25));
//	        cellCab = row3.createCell((short) 22);
//	        cellCab.setCellStyle(styleCabecera(wb));
//	        cellCab.setCellValue("FACTURA COMERCIAL FISICA");
//	        
//	        cellCab = row4.createCell((short) 22);
//	        cellCab.setCellStyle(styleCabecera(wb));
//	        cellCab.setCellValue("CANTIDAD");
//	        
//	        cellCab = row4.createCell((short) 23);
//	        cellCab.setCellStyle(styleCabecera(wb));
//	        cellCab.setCellValue("IMPORTE S/.");
//	        
//	        /*******************************************************/
//	        cellCab = row4.createCell((short) 24);
//	        cellCab.setCellStyle(styleCabecera(wb));
//	        cellCab.setCellValue("CANTIDAD");
//	        
//	        cellCab = row4.createCell((short) 25);
//	        cellCab.setCellStyle(styleCabecera(wb));
//	        cellCab.setCellValue("IMPORTE $");
	        
//	        sheet.createFreezePane(0, 5); //Fijar Cabecera 
	        
	        int rownum=5, cont = 0 ;
	        
//	        if (!mapResul.isEmpty()) {
	        
	        	for (int i = 0; i < 14; i++) {
	        		
	        		Map<String, Object> mapTemp = new HashMap<String, Object>();
	        		mapTemp = (Map<String, Object>) mapResul.get(obtenerServicio(i).get("codigo").toString());
	        		
	        		if(mapTemp!=null){
	        			row4 = sheet.createRow(rownum + cont);
	        			cont++;
		            	Cell cellCuerpo = null;
		            	
		            	cellCuerpo = row4.createCell(0); 
		    	        cellCuerpo.setCellValue(obtenerServicio(i).get("codigo").toString());
	
			    		cellCuerpo = row4.createCell(1); 
		    	        cellCuerpo.setCellValue(obtenerServicio(i).get("documento").toString());
	        		

			    		cellCuerpo = row4.createCell(2);  // CANTIDAD
			    	    cellCuerpo.setCellValue(mapTemp == null ? "0" : mapTemp.get("CANTSOL_LPA") == null ? "0" : mapTemp.get("CANTSOL_LPA").toString());
			    		
			    		cellCuerpo = row4.createCell(3);  // IMPORTE SOLES
			    	    cellCuerpo.setCellValue(mapTemp == null ? "0.00" : mapTemp.get("SUMSOL_LPA") == null ? "0.00" : Util.formatearImporte(mapTemp.get("SUMSOL_LPA").toString()));
						
			    		cellCuerpo = row4.createCell(4);  // IMPORTE DOLARES
			    		cellCuerpo.setCellValue(mapTemp == null ? "0" : mapTemp.get("CANTDOL_LPA") == null ? "0" : mapTemp.get("CANTDOL_LPA").toString());
		    	        
			    		cellCuerpo = row4.createCell(5);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0.00" : mapTemp.get("SUMDOL_LPA") == null ? "0.00" : Util.formatearImporte(mapTemp.get("SUMDOL_LPA").toString()));
		    	        
			    		cellCuerpo = row4.createCell(6);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0" : mapTemp.get("CANTSOL_LA") == null ? "0" : mapTemp.get("CANTSOL_LA").toString());
		    	        
			    		cellCuerpo = row4.createCell(7);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0.00" : mapTemp.get("SUMSOL_LA") == null ? "0.00" : Util.formatearImporte(mapTemp.get("SUMSOL_LA").toString()));
		    	        
			    		cellCuerpo = row4.createCell(8);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0" : mapTemp.get("CANTDOL_LA") == null ? "0" : mapTemp.get("CANTDOL_LA").toString());
		    	        
			    		cellCuerpo = row4.createCell(9);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0.00" : mapTemp.get("SUMDOL_LA") == null ? "0.00" : Util.formatearImporte(mapTemp.get("SUMDOL_LA").toString()));
		    	        
			    		cellCuerpo = row4.createCell(10);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0" : mapTemp.get("CANTSOL_FCF") == null ? "0" : mapTemp.get("CANTSOL_FCF").toString());
			    		
			    		cellCuerpo = row4.createCell(11);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0.00" : mapTemp.get("SUMSOL_FCF") == null ? "0.00" : Util.formatearImporte(mapTemp.get("SUMSOL_FCF").toString()));
			    		
			    		cellCuerpo = row4.createCell(12);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0" : mapTemp.get("CANTDOL_FCF") == null ? "0" : mapTemp.get("CANTDOL_FCF").toString());
			    		
			    		cellCuerpo = row4.createCell(13);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0.00" : mapTemp.get("SUMDOL_FCF") == null ? "0.00" : Util.formatearImporte(mapTemp.get("SUMDOL_FCF").toString()));
			    		
			    		cellCuerpo = row4.createCell(14);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0" : mapTemp.get("CANTSOL_FNF") == null ? "0" : mapTemp.get("CANTSOL_FNF").toString());
			    		
			    		cellCuerpo = row4.createCell(15);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0.00" : mapTemp.get("SUMSOL_FNF") == null ? "0.00" : Util.formatearImporte(mapTemp.get("SUMSOL_FNF").toString()));
			    		
			    		cellCuerpo = row4.createCell(16);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0" : mapTemp.get("CANTDOL_FNF") == null ? "0" : mapTemp.get("CANTDOL_FNF").toString());
			    		
			    		cellCuerpo = row4.createCell(17);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0.00" : mapTemp.get("SUMDOL_FNF") == null ? "0.00" : Util.formatearImporte(mapTemp.get("SUMDOL_FNF").toString()));
			    		
			    		cellCuerpo = row4.createCell(18);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0" : mapTemp.get("CANTSOL_FNE") == null ? "0" : mapTemp.get("CANTSOL_FNE").toString());
			    		
			    		cellCuerpo = row4.createCell(19);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0.00" : mapTemp.get("SUMSOL_FNE") == null ? "0.00" : Util.formatearImporte(mapTemp.get("SUMSOL_FNE").toString()));
			    		
			    		cellCuerpo = row4.createCell(20);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0" : mapTemp.get("CANTDOL_FNE") == null ? "0" : mapTemp.get("CANTDOL_FNE").toString());
			    		
			    		cellCuerpo = row4.createCell(21);  //
			    		cellCuerpo.setCellValue(mapTemp == null ? "0.00" : mapTemp.get("SUMDOL_FNE") == null ? "0.00" : Util.formatearImporte(mapTemp.get("SUMDOL_FNE").toString()));
	        		}
	    	        
				}
	        	
	        	row4 = sheet.createRow(rownum + cont + 1);
            	Cell cellCuerpo = null;
            	
	    		cellCuerpo = row4.createCell(1); 
    	        cellCuerpo.setCellValue("TOTAL DOCUMENTOS");
    	        
    	        Cell cellFormula = null;
    	        cellFormula = row4.createCell(2); 
    	        cellFormula.setCellFormula(generarFormula(cont,"C"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(3); 
    	        cellFormula.setCellFormula(generarFormula(cont,"D"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(4); 
    	        cellFormula.setCellFormula(generarFormula(cont,"E"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(5); 
    	        cellFormula.setCellFormula(generarFormula(cont,"F"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(6); 
    	        cellFormula.setCellFormula(generarFormula(cont,"G"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(7); 
    	        cellFormula.setCellFormula(generarFormula(cont,"H"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(8); 
    	        cellFormula.setCellFormula(generarFormula(cont,"I"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(9); 
    	        cellFormula.setCellFormula(generarFormula(cont,"J"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(10); 
    	        cellFormula.setCellFormula(generarFormula(cont,"K"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(11); 
    	        cellFormula.setCellFormula(generarFormula(cont,"L"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(12); 
    	        cellFormula.setCellFormula(generarFormula(cont,"M"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(13); 
    	        cellFormula.setCellFormula(generarFormula(cont,"N"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(14); 
    	        cellFormula.setCellFormula(generarFormula(cont,"O"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(15); 
    	        cellFormula.setCellFormula(generarFormula(cont,"P"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(16); 
    	        cellFormula.setCellFormula(generarFormula(cont,"Q"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(17); 
    	        cellFormula.setCellFormula(generarFormula(cont,"R"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(18); 
    	        cellFormula.setCellFormula(generarFormula(cont,"S"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(19); 
    	        cellFormula.setCellFormula(generarFormula(cont,"T"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(20); 
    	        cellFormula.setCellFormula(generarFormula(cont,"U"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
    	        cellFormula = row4.createCell(21); 
    	        cellFormula.setCellFormula(generarFormula(cont,"V"));
    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
    	        
//    	        cellFormula = row4.createCell(22); 
//    	        cellFormula.setCellFormula(generarFormula(cont,"W"));
//    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
//    	        
//    	        cellFormula = row4.createCell(23); 
//    	        cellFormula.setCellFormula(generarFormula(cont,"X"));
//    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
//    	        
//    	        cellFormula = row4.createCell(24); 
//    	        cellFormula.setCellFormula(generarFormula(cont,"Y"));
//    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
//    	        
//    	        cellFormula = row4.createCell(25); 
//    	        cellFormula.setCellFormula(generarFormula(cont,"Z"));
//    	        cellFormula.setCellType(Cell.CELL_TYPE_FORMULA);
	        	
//	        }
	       
	        //ESCRIBIENDO EL ARCHIVO DE SALIDA
	        fileOut = new FileOutputStream(nomArchivoSalida);
	        wb.write(fileOut);
	        
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "inline; filename="+ "Resumen_Documentos.xls");
			byte[] archivo = null;
			archivo = filetoByteArray(nomArchivoSalida);
			IOUtils.write(archivo, response.getOutputStream());
	        
		} catch (Exception e) {
			log.error("ERROR: " + e);
			e.printStackTrace();
		}
		return true;
	}
	
	@SuppressWarnings({ "unused", "static-access" })
	@Override
	public void generarReportePorAnhio(Map<String, Object> mapRepo, List<ControlDocBean> listaIngresos,
			HttpServletResponse response) {
		try {
			String nomArchivoSalida =  System.getProperty("user.home")+"/reporteGeneral.xls";
			FileOutputStream fileOut = null;
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Hoja1");
			sheet.getPrintSetup().setLandscape(true); //Orientacion Vertical
	        sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //TamaÃ±o papel A4
	        sheet.getPrintSetup().setScale((short)90); //Escala de impresion 75%
	        //RepeatingRowsAndColumns sheetIndex, startColumn, endColumn, startRow, endRow
	        wb.setRepeatingRowsAndColumns(0, 0, 25, 0, 4); //REPETIR FILAS EN EXTREMO SUPERIOR (IMPRESION)
	        sheet.setMargin(sheet.RightMargin, 0.7);
	        sheet.setMargin(sheet.LeftMargin, 0.7);
	        
	        sheet.setColumnWidth(0,  3126); //JULIANO
	        sheet.setColumnWidth(1,  3126); //NUMERO CUENTA
	        sheet.setColumnWidth(2,  3126); //NUMERO LETRA
	        sheet.setColumnWidth(3,  3126); //SERVICIO
	        sheet.setColumnWidth(4,  3126); //FECHA VENCIMIENTO
	        sheet.setColumnWidth(5,  3126); //CLASE DOC
	        sheet.setColumnWidth(6,  3126); //AGENCIA
	        sheet.setColumnWidth(7,  3126); //FECHA GIRO
	        sheet.setColumnWidth(8,  3126); //FECHA DESEMBOLSO
	        sheet.setColumnWidth(9,  3126); //FECHA PROCESO
	        
	        String cellAddr="$A$4:$J$5";
	        
	        Row row1 = sheet.createRow((short) 1);
	        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));
	        Cell cell = row1.createCell((short) 0);
	        cell.setCellType(cell.CELL_TYPE_STRING);
	        cell.setCellStyle(styleTitulo(wb));
	        cell.setCellValue("RESUMEN DE DOCUMENTOS");
	        
	        Row row3 = sheet.createRow((short)3);
	        
	        Row row4 = sheet.createRow((short)4);
	        
	        CellRangeAddress region = new CellRangeAddress(3,4,0,0);
	        
	        HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, region, sheet, wb);
	        HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, region, sheet, wb);
	        HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, region, sheet, wb);
	        HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, region, sheet, wb);
	        
	        Cell cellCab = row3.createCell((short) 0);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("AÑO VENCIMIENTO");
	        sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 0));
	        
	        
	        //LETRAS POR ACEPTAR
	        /*******************************************************/
	        sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 4));
	        cellCab = row3.createCell((short) 1);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("LETRAS");
	        
	        cellCab = row4.createCell((short) 1);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("Cantidad");
	        
	        cellCab = row4.createCell((short) 2);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("Importe S/.");
	        
	        cellCab = row4.createCell((short) 3);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("Cantidad");
	        
	        cellCab = row4.createCell((short) 4);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("Importe $");
	        
	        //LETRAS ACEPTADAS
	        /*******************************************************/
	        sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 8));
	        cellCab = row3.createCell((short) 5);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("FACTURAS");
	        
	        cellCab = row4.createCell((short) 5);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("Cantidad");
	        
	        cellCab = row4.createCell((short) 6);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("Importe S/.");
	        
	        cellCab = row4.createCell((short) 7);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("Cantidad");
	        
	        cellCab = row4.createCell((short) 8);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("Importe $");
	        
	        cellCab = row3.createCell((short) 9);
	        cellCab.setCellStyle(styleCabecera(wb));
	        cellCab.setCellValue("TOTAL DOCUMENTOS");
	        sheet.addMergedRegion(new CellRangeAddress(3, 4, 9, 9));
	        

	        setRegionBorderWithMedium(CellRangeAddress.valueOf(cellAddr), sheet);
	        
	        sheet.createFreezePane(0, 5); //Fijar Cabecera 
	        
	        int rownum=5, contador=0;
	        
			if (!listaIngresos.isEmpty()) {
	        	ControlDocBean item = null;
	        	for (int i = 0; i < 14; i++) {

					item = listaIngresos.get(0);
					
					row4 = sheet.createRow(rownum + i); //POSICION DE LA FILA
	            	Cell cellCuerpo = null;
	            	
	            	//JULIANO
	            	cellCuerpo = row4.createCell(0);  //, POSICION DE LA CELDA
	    	        cellCuerpo.setCellValue(item.getCod_tipo_movimiento() == null ? "" : item.getCod_tipo_movimiento());

		    		//NUMERO CUENTA
		    		cellCuerpo = row4.createCell(1);  //, POSICION DE LA CELDA
	    	        cellCuerpo.setCellValue(item.getCod_tipo_movimiento() == null ? "" : item.getCod_tipo_movimiento());
	    	        
		    		//NUMERO LETRA
		    		cellCuerpo = row4.createCell(2);  //, POSICION DE LA CELDA
	    	        cellCuerpo.setCellValue(item.getCod_tipo_movimiento() == null ? "" : item.getCod_tipo_movimiento());
		    		
	    	        //SERVICIO
		    		cellCuerpo = row4.createCell(3);  //, POSICION DE LA CELDA
	    	        cellCuerpo.setCellValue(item.getCod_tipo_movimiento() == null ? "" : item.getCod_tipo_movimiento());
					
	    	        //FECHA VENCIMIENTO
		    		cellCuerpo = row4.createCell(4);  //, POSICION DE LA CELDA
	    	        cellCuerpo.setCellValue(item.getCod_tipo_movimiento() == null ? "" : item.getCod_tipo_movimiento());
	    	        
	    	        //CLASE DOCUMENTO
		    		cellCuerpo = row4.createCell(5);  //, POSICION DE LA CELDA
	    	        cellCuerpo.setCellValue(item.getCod_tipo_movimiento() == null ? "" : item.getCod_tipo_movimiento());
	    	        
	    	        //AGENCIA
		    		cellCuerpo = row4.createCell(6);  //, POSICION DE LA CELDA
	    	        cellCuerpo.setCellValue(item.getCod_tipo_movimiento() == null ? "" : item.getCod_tipo_movimiento());
	    	        //styleDetalle.setWrapText(true);
	    	        
	    	        //FECHA GIRO
		    		cellCuerpo = row4.createCell(7);  //, POSICION DE LA CELDA
	    	        cellCuerpo.setCellValue(item.getCod_tipo_movimiento() == null ? "" : item.getCod_tipo_movimiento());
	    	        
	    	        //FECHA DESEMBOLSO
		    		cellCuerpo = row4.createCell(8);  //, POSICION DE LA CELDA
	    	        cellCuerpo.setCellValue(item.getFec_vencimiento()== null ? "" : item.getFec_vencimiento());
	    	        
	    	        //FECHA PROCESO
		    		cellCuerpo = row4.createCell(9);  //, POSICION DE LA CELDA
	    	        cellCuerpo.setCellValue(item.getFec_nuevo_vencimiento()== null ? "" : item.getFec_nuevo_vencimiento());
	    	        
	    	        contador = rownum+i;
				}
	        }else{
	        	row4 = sheet.createRow(rownum); //POSICION DE LA FILA
	        	Cell cellCuerpo = null;
	        	
	        	cellCuerpo = row4.createCell(0);  //, POSICION DE LA CELDA
		        cellCuerpo.setCellValue("NO EXISTE INFORMACION PARA LA CONSULTA REALIZADA");
		        contador = rownum;
		        
	        }
	       
	        //ESCRIBIENDO EL ARCHIVO DE SALIDA
			fileOut = new FileOutputStream(nomArchivoSalida);
	        wb.write(fileOut);
	        
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "inline; filename="+ "Reporte_General_Por_Año.xls");
			byte[] archivo = null;
			archivo = filetoByteArray(nomArchivoSalida);
			IOUtils.write(archivo, response.getOutputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void setRegionBorderWithMedium(CellRangeAddress region, Sheet sheet) {
        Workbook wb = sheet.getWorkbook();
        RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, region, sheet, wb);
        RegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, region, sheet, wb);
        RegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, region, sheet, wb);
        RegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, region, sheet, wb);
    }
	
	private String generarFormula(int cont, String letra){
		String formula ="";
		try {
			for(int i = 0; i<cont; i++){
				formula = formula + letra.concat(String.valueOf(6 + i)).concat("+");
			}
			formula = formula.substring(0, formula.length()-1);
		} catch (Exception e) {
			log.error("ERROR: " + e);
			e.printStackTrace();
		}
		return formula;
	}
	
	
	@SuppressWarnings("resource")
	private byte[] filetoByteArray(String path) {
		File file = new File(path);

        byte[] b = new byte[(int) file.length()];
        try {
              FileInputStream fileInputStream = new FileInputStream(file);
              fileInputStream.read(b);
              for (int i = 0; i < b.length; i++) {
                          //System.out.print((char)b[i]);
               }
         } catch (FileNotFoundException e) {
        	 e.printStackTrace();
         }
         catch (IOException e1) {
        	 e1.printStackTrace();
         }
		return b;
	}

	private Map<String, Object> obtenerServicio(int i) {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		switch (i) {
		case 0:
			mapParam.put("codigo", Constantes.COD_TIPO_SERVICIO_01);
			mapParam.put("documento", Constantes.DES_TIPO_SERVICIO_01);
			break;
		case 1:
			mapParam.put("codigo", Constantes.COD_TIPO_SERVICIO_04);
			mapParam.put("documento", Constantes.DES_TIPO_SERVICIO_04);
			break;
		case 2:
			mapParam.put("codigo", Constantes.COD_TIPO_SERVICIO_05);
			mapParam.put("documento", Constantes.DES_TIPO_SERVICIO_05);
			break;
		case 3:
			mapParam.put("codigo", Constantes.COD_TIPO_SERVICIO_07);
			mapParam.put("documento", Constantes.DES_TIPO_SERVICIO_07);
			break;
		case 4:
			mapParam.put("codigo", Constantes.COD_TIPO_SERVICIO_08);
			mapParam.put("documento", Constantes.DES_TIPO_SERVICIO_08);
			break;
		case 5:
			mapParam.put("codigo", Constantes.COD_TIPO_SERVICIO_11);
			mapParam.put("documento", Constantes.DES_TIPO_SERVICIO_11);
			break;
		case 6:
			mapParam.put("codigo", Constantes.COD_TIPO_SERVICIO_12);
			mapParam.put("documento", Constantes.DES_TIPO_SERVICIO_12);
			break;
		case 7:
			mapParam.put("codigo", Constantes.COD_TIPO_SERVICIO_14);
			mapParam.put("documento", Constantes.DES_TIPO_SERVICIO_14);
			break;
		case 8:
			mapParam.put("codigo", Constantes.COD_TIPO_SERVICIO_20);
			mapParam.put("documento", Constantes.DES_TIPO_SERVICIO_20);
			break;
		case 9:
			mapParam.put("codigo", Constantes.COD_TIPO_SERVICIO_29);
			mapParam.put("documento", Constantes.DES_TIPO_SERVICIO_29);
			break;
		case 10:
			mapParam.put("codigo", Constantes.COD_TIPO_SERVICIO_30);
			mapParam.put("documento", Constantes.DES_TIPO_SERVICIO_30);
			break;
		case 11:
			mapParam.put("codigo", Constantes.COD_TIPO_SERVICIO_50);
			mapParam.put("documento", Constantes.DES_TIPO_SERVICIO_50);
			break;
		case 12:
			mapParam.put("codigo", Constantes.COD_TIPO_SERVICIO_55);
			mapParam.put("documento", Constantes.DES_TIPO_SERVICIO_55);
			break;
		case 13:
			mapParam.put("codigo", Constantes.COD_TIPO_SERVICIO_60);
			mapParam.put("documento", Constantes.DES_TIPO_SERVICIO_60);
			break;
		default:
			break;
		}
		return mapParam;
	}

	@Override
	public List<Map<String, Object>> listaAgencias() throws Exception {
		List<Map<String, Object>> listaAgencias = new ArrayList<Map<String,Object>>();
		try {
			listaAgencias = parametrosDAO.listAgencias();
		} catch (Exception e) {
			log.error("ERROR: " +e);
			e.printStackTrace();
		}
		return listaAgencias;
	}

	@Override
	public Map<String, Object> obtenerAgencia(String cod_agencia)
			throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id_oficina_movimiento", cod_agencia);
		Map<String, Object> mapAgencia = new HashMap<String, Object>();
		try {
			mapAgencia = parametrosDAO.obtenerAgencia(param);
		} catch (Exception e) {
			log.error("ERROR: " +e);
			e.printStackTrace();
		}
		return mapAgencia;
	}
	
	public void enviarAlerta(File file){
		try{
			//ENVIO DE CORREO
			String destinatario  = "correo_destinatario@gmail.com";

            String asunto = "";//Constantes.ASUNTO_POR_DEFECTO;
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("<html>");
            mensaje.append("Estimado usuario: ");
            mensaje.append("<div>&nbsp;</div>");
            mensaje.append("<div>&nbsp;</div>");
        	asunto = "ALERTA DE INCOMPATIBILIDAD DE REGISTROS ";
        	mensaje.append("Se ha encontrado incompatibilidad con los archivos cargados (cheqcar1.dsd) y ");
        	mensaje.append("los procesados con el dia.txt. \n");
        	mensaje.append("Se adjunta archivo con mayor detalle.");
            mensaje.append("<div>&nbsp;</div>");
            mensaje.append("</br></br>");
            mensaje.append("</html>");
			
			if(log.isDebugEnabled())log.debug(" >> Enviando CORREO ELECTRONICO a: " + destinatario);
			
			JavaMailSenderImpl sender = new JavaMailSenderImpl();
			sender.setHost("localhost");
			sender.setPort(26); //TODO comentar para produccion
			sender.setUsername("username@bbva.com.pe");
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			try {
				helper.setFrom( "from@bbva.com.pe", "from@bbva.com.pe" );
				helper.setTo(destinatario);
				helper.setSubject(asunto);
				helper.setText(mensaje.toString(), true); //true = habilita HTML
				helper.addAttachment("archivo.txt",  new ByteArrayResource(IOUtils.toByteArray(new FileInputStream(file.getAbsolutePath()))));
				sender.send(message);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("ERROR: " + e);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("ERROR: " + e);
		}
	
	}

	@Override
	public Map<String, String> consultaEspecificaMap(
			Map<String, Object> param) throws Exception {
		return controlDocDAO.listConsEspecificaMap(param);
	}

	@Override
	public Map<String, Object> obtenerImportes(Map<String, Object> param)
			throws Exception {
		return controlDocDAO.obtenerImportes(param);
	}

	@Override
	public Map<String, Object> obtenerTotalRegistros(Map<String, Object> param) throws Exception {
		return controlDocDAO.obtenerTotalRegistros(param);
	}

}
