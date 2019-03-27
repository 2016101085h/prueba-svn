package com.bbva.control.boveda.model.dao.ibatis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.bbva.control.boveda.bean.DevolucionesBean;
import com.bbva.control.boveda.model.dao.DevolucionesDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("deprecation")
public class SqlMapDevolucionesDAOImpl extends SqlMapClientDaoSupport implements DevolucionesDAO{

	@Autowired
	public SqlMapDevolucionesDAOImpl(@Qualifier("SqlMapClientBoveda") SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}
	
	@Override
	public DevolucionesBean obtenerDevoluciones(Map<String, Object> mapDevol)
			throws Exception {
		return (DevolucionesBean) getSqlMapClientTemplate().queryForObject("devoluciones.obtenerDevoluciones", mapDevol);
	}

	@Override
	public void borrarRegistro(DevolucionesBean itemDevolucion)
			throws Exception {
		getSqlMapClientTemplate().delete("devoluciones.borrar", itemDevolucion);
	}

}
