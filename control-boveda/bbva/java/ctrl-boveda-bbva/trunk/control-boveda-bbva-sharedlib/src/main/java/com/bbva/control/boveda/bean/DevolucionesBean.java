package com.bbva.control.boveda.bean;

import java.io.Serializable;

public class DevolucionesBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String numero_banco;
	private String fecha_vencimiento;
	private String estado;
	private double id_letra;
	private String id_lugar;
	
	public String getNumero_banco() {
		return numero_banco;
	}
	public void setNumero_banco(String numero_banco) {
		this.numero_banco = numero_banco;
	}
	public String getFecha_vencimiento() {
		return fecha_vencimiento;
	}
	public void setFecha_vencimiento(String fecha_vencimiento) {
		this.fecha_vencimiento = fecha_vencimiento;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getId_lugar() {
		return id_lugar;
	}
	public double getId_letra() {
		return id_letra;
	}
	public void setId_letra(double id_letra) {
		this.id_letra = id_letra;
	}
	public void setId_lugar(String id_lugar) {
		this.id_lugar = id_lugar;
	}

}
