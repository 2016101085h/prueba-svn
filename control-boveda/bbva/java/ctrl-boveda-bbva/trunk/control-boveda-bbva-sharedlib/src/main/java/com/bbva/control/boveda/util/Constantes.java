package com.bbva.control.boveda.util;

public class Constantes {
	
	//ESTADOS DE UN REGISTRO
	public static String EST_ACT_SALIDA = "0";
	public static String EST_ACT_INGRESO = "1";
	public static String EST_ACT_VIGENTE = "2";
	
	public static String DES_EST_ACT_SALIDA = "CANCELADO";
	public static String DES_EST_ACT_INGRESO = "INGRESO";
	public static String DES_EST_ACT_VIGENTE = "VIGENTE SIN FISICO";
	
	//RUTA DE ARCHIVO PLANO *.DSD
	public static String RUTA_ARCHIVO_DSD = "C://Users//FARAGON//Desktop//borrar//archivo//";
	public static String RUTA_ARCHIVO_REPORTE = "C://Users//FARAGON//Desktop//borrar//reporte//";
	public static String RUTA_ARCHIVO_LOG = "C://Users//FARAGON//Desktop//borrar//log//";
	
	//MENSAJES
	public static String PROC_INGR_EXITO = "El proceso ha culminado satisfactoriamente";
	public static String PROC_INGR_EXCEP_01 = "No se encontró el archivo plano, por favor verifique";
	public static String PROC_INGR_EXCEP_02 = "El archivo plano debe tener extensión .dsd, por favor verifique";
	public static String PROC_INGR_EXCEP_03 = "El archivo plano se encuentra vacío, por favor verifique";
	public static String PROC_INGR_EXCEP_04 = "El archivo plano no cumple con la estructura indicada, por favor verifique";
	public static String PROC_INGR_EXCEP_05 = "Se produjeron errores durante el proceso de registro de la información, por favor revisar el archivo log generado";
	
	public static String PROC_SALI_EXCEP_01 = "Algunos registros no fueron actualizados, por favor revise el archivo log generado";
	public static String PROC_SALI_EXCEP_02 = "Existen archivos Faltantes/Sobrantes en el cruce realizado, por favor revise el archivo log generado";
	public static String PROC_SALI_EXCEP_03 = "Se produjeron errores durante el proceso de registro de la información, por favor revisar el archivo log generado";
	
	public static String PROC_CARG_INICIAL_EXCEP_05 = "Se produjeron errores durante el proceso de carga de la información, por favor revisar el archivo log generado";
	
	//NOMBRE ARCHIVOS
	public static String LOG_ERROR_CARGAINI = "err_cargaini";
	public static String LOG_ERROR_INGRESO = "err_ingreso"; 
	public static String LOG_ERROR_SALIDA = "err_salida"; 
	
	public static String COD_TIPO_MOVIMIENTO_02 = "02";
	public static String COD_TIPO_MOVIMIENTO_11 = "11";
	public static String COD_TIPO_MOVIMIENTO_43 = "43";
	
	public static String COD_OPERADOR_BBVA = "11";
	public static String COD_OBSERVACION_001 = "001";
	public static String COD_OBSERVACION_002 = "002";
	public static String COD_OBSERVACION_003 = "003";
	public static String COD_OBSERVACION_004 = "004";
	public static String COD_OBSERVACION_005 = "005";
	public static String COD_OBSERVACION_006 = "006";
	public static String COD_OBSERVACION_007 = "007";
	public static String COD_OBSERVACION_008 = "008";
	public static String COD_OBSERVACION_009 = "009";
	public static String COD_OBSERVACION_010 = "010";
	public static String COD_OBSERVACION_011 = "011";
	public static String COD_OBSERVACION_012 = "012";
	public static String COD_OBSERVACION_013 = "013";
	public static String COD_OBSERVACION_014 = "014";
	public static String COD_OBSERVACION_015 = "015";
	public static String COD_OBSERVACION_016 = "016";
	
	public static String DES_OBSERVACION_001 = "CANCELADO";
	public static String DES_OBSERVACION_002 = "CANCELADO POR RENOVACION";
	public static String DES_OBSERVACION_003 = "VIGENTE SIN FISICO POR RENOVACION";
	public static String DES_OBSERVACION_004 = "SALIDA CON ESQUELA";
	public static String DES_OBSERVACION_005 = "SALIDA DESCARGO POR ERROR";
	public static String DES_OBSERVACION_006 = "INGRESO POR CANJE";
	public static String DES_OBSERVACION_007 = "SIN FISICO POR ACEPTAR";
	public static String DES_OBSERVACION_008 = "INGRESO DESCARGO POR ERROR";
	public static String DES_OBSERVACION_009 = "INGRESO POR ACEPTAR FIRMADO";
	public static String DES_OBSERVACION_010 = "VIGENTE SIN FISICO JUDICIAL";
	public static String DES_OBSERVACION_011 = "VIGENTE SIN FISICO EN BCP DE PROVINCIA";
	public static String DES_OBSERVACION_012 = "VIGENTE SIN FISICO EN PROVINCIA";
	public static String DES_OBSERVACION_013 = "VIGENTE SIN FISICO PROTESTADO EN CUSTODIA";
	public static String DES_OBSERVACION_014 = "VIGENTE SIN FISICO PROTESTADO EN PROVINCIA";
	public static String DES_OBSERVACION_015 = "INGRESO FISICO POR RENOVACION";
	public static String DES_OBSERVACION_016 = "INGRESO FISICO POR DOCUMENTO PROTESTADO";
	
	public static String DES_CLASE_DOC_1 = "Total Documentos Ingresados";
	public static String DES_CLASE_DOC_2 = "Total Documentos Cancelados";
	public static String DES_CLASE_DOC_3 = "Total Documentos Vigentes sin fisico";
	public static String DES_CLASE_DOC_4 = "Total documentos en Custodia";
	
	public static String COD_TIPO_SERVICIO_01 = "01";
	public static String DES_TIPO_SERVICIO_01 = "Cobranza libre";
	public static String COD_TIPO_SERVICIO_04 = "04";
	public static String DES_TIPO_SERVICIO_04 = "Cob Garantía - Ley Descont";
	public static String COD_TIPO_SERVICIO_05 = "05";
	public static String DES_TIPO_SERVICIO_05 = "Cob Garantía - Factura";
	public static String COD_TIPO_SERVICIO_07 = "07";
	public static String DES_TIPO_SERVICIO_07 = "Cob Garantía - Pagare";
	public static String COD_TIPO_SERVICIO_08 = "08";
	public static String DES_TIPO_SERVICIO_08 = "Cob Garantía - Cred. Doc";
	public static String COD_TIPO_SERVICIO_11 = "11";
	public static String DES_TIPO_SERVICIO_11 = "cobranza Letra Hipotecarias";
	public static String COD_TIPO_SERVICIO_12 = "12";
	public static String DES_TIPO_SERVICIO_12 = "Cob Garantía - NE";
	public static String COD_TIPO_SERVICIO_14 = "14";
	public static String DES_TIPO_SERVICIO_14 = "Cob Garantía - Aval";
	public static String COD_TIPO_SERVICIO_20 = "20";
	public static String DES_TIPO_SERVICIO_20 = "Cob Garantía - Fianzas";
	public static String COD_TIPO_SERVICIO_29 = "29";
	public static String DES_TIPO_SERVICIO_29 = "Cuenta Especial Prestamos";
	public static String COD_TIPO_SERVICIO_30 = "30";
	public static String DES_TIPO_SERVICIO_30 = "Factoring Especial Prestamos";
	public static String COD_TIPO_SERVICIO_50 = "50";
	public static String DES_TIPO_SERVICIO_50 = "Descuentos";
	public static String COD_TIPO_SERVICIO_55 = "55";
	public static String DES_TIPO_SERVICIO_55 = "Descuentos Por desembolsar";
	public static String COD_TIPO_SERVICIO_60 = "60";
	public static String DES_TIPO_SERVICIO_60 = "Facturas Negociables";
	
}
