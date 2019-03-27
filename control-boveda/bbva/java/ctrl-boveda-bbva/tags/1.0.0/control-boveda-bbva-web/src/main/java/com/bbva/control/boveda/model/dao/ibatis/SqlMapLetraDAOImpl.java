package com.bbva.control.boveda.model.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.bbva.control.boveda.bean.LetraBean;
import com.bbva.control.boveda.model.dao.LetraDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("deprecation")
@Repository("LetraDAO")
public class SqlMapLetraDAOImpl extends SqlMapClientDaoSupport  implements LetraDAO{
	
	@Autowired
	public SqlMapLetraDAOImpl(@Qualifier("SqlMapClientBoveda") SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LetraBean> obtenerDocumentos(Map<String, Object> mapLetra) throws Exception {
		return getSqlMapClientTemplate().queryForList("letra.obtenerLetras", mapLetra);
	}
	
}
