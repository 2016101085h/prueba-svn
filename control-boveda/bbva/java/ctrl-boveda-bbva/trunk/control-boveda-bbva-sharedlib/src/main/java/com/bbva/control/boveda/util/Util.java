package com.bbva.control.boveda.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Util {
	
	private static final Log log = LogFactory.getLog(Util.class);

	public static String descripIndicador(String ind_ingreso_salida) {
		String descrip = "";
		switch ( Integer.parseInt(ind_ingreso_salida) ) {
		case 0:
			descrip = "NO";//Constantes.DES_EST_ACT_SALIDA;
			break;
		case 1:
			descrip = "SI";//Constantes.DES_EST_ACT_INGRESO;
			break;
		case 2:
			descrip = "NO";//Constantes.DES_EST_ACT_VIGENTE;
			break;
		default:
			break;
		}
		return descrip;
	}
	
	public static String descripObservacion(String des_observacion_ubica) {
		String observacion = "";
		switch (Integer.parseInt(des_observacion_ubica)) {
		case 1:
			observacion = Constantes.DES_OBSERVACION_001;
			break;
		case 2:
			observacion = Constantes.DES_OBSERVACION_002;
			break;
		case 3:
			observacion = Constantes.DES_OBSERVACION_003;
			break;
		case 4:
			observacion = Constantes.DES_OBSERVACION_004;
			break;
		case 5:
			observacion = Constantes.DES_OBSERVACION_005;
			break;
		case 6:
			observacion = Constantes.DES_OBSERVACION_006;
			break;
		case 7:
			observacion = Constantes.DES_OBSERVACION_007;
			break;
		case 8:
			observacion = Constantes.DES_OBSERVACION_008;
			break;
		case 9:
			observacion = Constantes.DES_OBSERVACION_009;
			break;
		case 10:
			observacion = Constantes.DES_OBSERVACION_010;
			break;
		case 11:
			observacion = Constantes.DES_OBSERVACION_011;
			break;
		case 12:
			observacion = Constantes.DES_OBSERVACION_012;
			break;
		case 13:
			observacion = Constantes.DES_OBSERVACION_013;
			break;
		case 14:
			observacion = Constantes.DES_OBSERVACION_014;
			break;
		case 15:
			observacion = Constantes.DES_OBSERVACION_015;
			break;
		default:
			break;
		}
		return observacion;
	}
	
	public static String formatDouble(double dolares2) {
		String formateado = "";
		DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
	    simbolo.setDecimalSeparator('.');
	    simbolo.setGroupingSeparator(',');
        
        DecimalFormat formatter = new DecimalFormat("#,##0.00", simbolo);
        formateado = formatter.format(dolares2);
		return formateado;
	}
	
	public static String formatearImporte(String imp_importe) {
		String formatNum = "";
		if(imp_importe!=null && !imp_importe.trim().equals("")){
			try {
				double douNum = new Double(imp_importe);
				douNum = douNum / 100;
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
	
	public static String formatearFecha(String fec_vencimiento) {
		SimpleDateFormat fromUser = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
		String reformattedStr = "";
		try {
			if(fec_vencimiento!= null && !fec_vencimiento.trim().equals("")){
				if(fec_vencimiento.equals("00000000")){
					reformattedStr = "00/00/0000";
				}else{
					reformattedStr = myFormat.format(fromUser.parse(fec_vencimiento));
				}
			}
		} catch (ParseException e) {
			log.error("ERROR: " + e);
			e.printStackTrace();
		}
		return reformattedStr;
	}
	
	public static void pasarGarbageCollector(){
		 
        Runtime garbage = Runtime.getRuntime();
//        System.out.println("Memoria libre antes de limpieza: "+ garbage.freeMemory());
        garbage.gc();
//        System.out.println("Memoria libre tras la limpieza : "+ garbage.freeMemory());
	}
}
