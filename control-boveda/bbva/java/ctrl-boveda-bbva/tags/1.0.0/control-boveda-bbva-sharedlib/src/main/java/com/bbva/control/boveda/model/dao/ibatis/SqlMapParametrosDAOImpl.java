package com.bbva.control.boveda.model.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.bbva.control.boveda.model.dao.ParametrosDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("deprecation")
public class SqlMapParametrosDAOImpl extends SqlMapClientDaoSupport implements ParametrosDAO{

	@Autowired
	public SqlMapParametrosDAOImpl(@Qualifier("SqlMapClientBoveda") SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> listAgencias() {
		return getSqlMapClientTemplate().queryForList("parametros.listaAgencias");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> obtenerAgencia(Map<String, Object> param)
			throws Exception {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("parametros.obtenerAgencia", param);
	}

}
