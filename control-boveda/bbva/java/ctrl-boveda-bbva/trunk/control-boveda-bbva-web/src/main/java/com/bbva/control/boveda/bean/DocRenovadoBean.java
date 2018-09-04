package com.bbva.control.boveda.bean;

import java.io.Serializable;

public class DocRenovadoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String num_letra;
	private String cod_juliano;
	private String fec_movimiento;
	private String cod_tipo_movimiento;
	private String cod_oficina_movimiento;
	private String cod_tipo_documento;
	private String cod_forma_pago;
	private String fec_nuevo_vencimiento;
	private String imp_nuevo_importe;
	private String fec_cargado;
	
	public String getNum_letra() {
		return num_letra;
	}
	public void setNum_letra(String num_letra) {
		this.num_letra = num_letra;
	}
	public String getCod_juliano() {
		return cod_juliano;
	}
	public void setCod_juliano(String cod_juliano) {
		this.cod_juliano = cod_juliano;
	}
	public String getFec_movimiento() {
		return fec_movimiento;
	}
	public void setFec_movimiento(String fec_movimiento) {
		this.fec_movimiento = fec_movimiento;
	}
	public String getCod_tipo_movimiento() {
		return cod_tipo_movimiento;
	}
	public void setCod_tipo_movimiento(String cod_tipo_movimiento) {
		this.cod_tipo_movimiento = cod_tipo_movimiento;
	}
	public String getCod_oficina_movimiento() {
		return cod_oficina_movimiento;
	}
	public void setCod_oficina_movimiento(String cod_oficina_movimiento) {
		this.cod_oficina_movimiento = cod_oficina_movimiento;
	}
	public String getCod_tipo_documento() {
		return cod_tipo_documento;
	}
	public void setCod_tipo_documento(String cod_tipo_documento) {
		this.cod_tipo_documento = cod_tipo_documento;
	}
	public String getCod_forma_pago() {
		return cod_forma_pago;
	}
	public void setCod_forma_pago(String cod_forma_pago) {
		this.cod_forma_pago = cod_forma_pago;
	}
	public String getFec_nuevo_vencimiento() {
		return fec_nuevo_vencimiento;
	}
	public void setFec_nuevo_vencimiento(String fec_nuevo_vencimiento) {
		this.fec_nuevo_vencimiento = fec_nuevo_vencimiento;
	}
	public String getImp_nuevo_importe() {
		return imp_nuevo_importe;
	}
	public void setImp_nuevo_importe(String imp_nuevo_importe) {
		this.imp_nuevo_importe = imp_nuevo_importe;
	}
	public String getFec_cargado() {
		return fec_cargado;
	}
	public void setFec_cargado(String fec_cargado) {
		this.fec_cargado = fec_cargado;
	}
	
}
