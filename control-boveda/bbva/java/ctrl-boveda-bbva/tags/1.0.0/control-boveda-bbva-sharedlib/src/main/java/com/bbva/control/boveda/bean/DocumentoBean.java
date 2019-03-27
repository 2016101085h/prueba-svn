package com.bbva.control.boveda.bean;

import java.io.Serializable;

public class DocumentoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id_renovacion;
	private String id_documento;
	private String fecha_proceso;
	private String corrida;
	private String id_usuario;
	private String maquina;
	private String cod_plaza;
	private String id_moneda;
	private String fecha_vencimiento;
	private String importe;
	private String estado_grabacion;
	private String archivo;
	private String hora_grabacion;
	private String estado_buzon;
	private String usr_grab;
	private String estado_marching;
	private String id_devolucion;
	
	
	public String getId_renovacion() {
		return id_renovacion;
	}
	public void setId_renovacion(String id_renovacion) {
		this.id_renovacion = id_renovacion;
	}
	public String getId_documento() {
		return id_documento;
	}
	public void setId_documento(String id_documento) {
		this.id_documento = id_documento;
	}
	public String getFecha_proceso() {
		return fecha_proceso;
	}
	public void setFecha_proceso(String fecha_proceso) {
		this.fecha_proceso = fecha_proceso;
	}
	public String getCorrida() {
		return corrida;
	}
	public void setCorrida(String corrida) {
		this.corrida = corrida;
	}
	public String getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getMaquina() {
		return maquina;
	}
	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}
	public String getCod_plaza() {
		return cod_plaza;
	}
	public void setCod_plaza(String cod_plaza) {
		this.cod_plaza = cod_plaza;
	}
	public String getId_moneda() {
		return id_moneda;
	}
	public void setId_moneda(String id_moneda) {
		this.id_moneda = id_moneda;
	}
	public String getFecha_vencimiento() {
		return fecha_vencimiento;
	}
	public void setFecha_vencimiento(String fecha_vencimiento) {
		this.fecha_vencimiento = fecha_vencimiento;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getEstado_grabacion() {
		return estado_grabacion;
	}
	public void setEstado_grabacion(String estado_grabacion) {
		this.estado_grabacion = estado_grabacion;
	}
	public String getArchivo() {
		return archivo;
	}
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	public String getHora_grabacion() {
		return hora_grabacion;
	}
	public void setHora_grabacion(String hora_grabacion) {
		this.hora_grabacion = hora_grabacion;
	}
	public String getEstado_buzon() {
		return estado_buzon;
	}
	public void setEstado_buzon(String estado_buzon) {
		this.estado_buzon = estado_buzon;
	}
	public String getUsr_grab() {
		return usr_grab;
	}
	public void setUsr_grab(String usr_grab) {
		this.usr_grab = usr_grab;
	}
	public String getEstado_marching() {
		return estado_marching;
	}
	public void setEstado_marching(String estado_marching) {
		this.estado_marching = estado_marching;
	}
	public String getId_devolucion() {
		return id_devolucion;
	}
	public void setId_devolucion(String id_devolucion) {
		this.id_devolucion = id_devolucion;
	}
	
}
