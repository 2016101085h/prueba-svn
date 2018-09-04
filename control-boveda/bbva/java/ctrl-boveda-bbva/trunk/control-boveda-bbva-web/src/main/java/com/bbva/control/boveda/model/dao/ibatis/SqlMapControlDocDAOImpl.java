package com.bbva.control.boveda.model.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.bbva.control.boveda.bean.ControlDocBean;
import com.bbva.control.boveda.model.dao.ControlDocDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("deprecation")
@Repository("ControlDocDAO")
public class SqlMapControlDocDAOImpl extends SqlMapClientDaoSupport  implements ControlDocDAO{

	
	@Autowired
	public SqlMapControlDocDAOImpl(@Qualifier("SqlMapClientBoveda") SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}
	
	@Override
	public void insertar(ControlDocBean cargaInicialBean) {
		getSqlMapClientTemplate().insert("controlDoc.insertar", cargaInicialBean);
	}
	
	@Override
	public void insertarCargaInicialMap(List<Map<String, Object>> listCargaInicial) {
		
	}

	@Override
	public void updateRegistro(ControlDocBean controlDoc) throws Exception {
		getSqlMapClientTemplate().update("controlDoc.actualizar", controlDoc);
	}

	@Override
	public ControlDocBean obtenerDocXLetra(Map<String, Object> param)
			throws Exception {
		return (ControlDocBean) getSqlMapClientTemplate().queryForObject("controlDoc.obtenerDocXLetra", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ControlDocBean> obtenerDocMap(Map<String, Object> param)
			throws Exception {
		return getSqlMapClientTemplate().queryForList("controlDoc.obtenerListDocMap", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ControlDocBean> listConsEspecifica(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("controlDoc.listConsEspecifica", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> docuXServicio(Map<String, Object> mapParam)
			throws Exception {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("controlDoc.docuXServicio", mapParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> listConsEspecificaMap(
			Map<String, Object> param) throws Exception {
		return (Map<String, String>) getSqlMapClientTemplate().queryForObject("controlDoc.listConsEspecificaMap", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> obtenerImportes(Map<String, Object> param)
			throws Exception {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("controlDoc.obtenerImportes", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> obtenerTotalRegistros(Map<String, Object> param) throws Exception {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("controlDoc.obtenerTotalRegistros", param);
	}

	@Override
	public void updateRegistroRenov(ControlDocBean controlDoc) throws Exception {
		getSqlMapClientTemplate().update("controlDoc.actualizarRenov", controlDoc);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> obtenerImpXIndXMoneda(Map<String, Object> param)
			throws Exception {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("controlDoc.obtenerImpXIndXMoneda", param);
	}

}
