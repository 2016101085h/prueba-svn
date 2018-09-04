package com.bbva.control.boveda.model.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.bbva.control.boveda.bean.DocumentoBean;
import com.bbva.control.boveda.model.dao.DocumentoRenovDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("deprecation")
@Repository("DocumentoRenovDAO")
public class SqlMapDocumentoRenovDAOImpl extends SqlMapClientDaoSupport implements DocumentoRenovDAO{

	@Autowired
	public SqlMapDocumentoRenovDAOImpl(@Qualifier("SqlMapClientBoveda") SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoBean> listDocRenovado(Map<String, Object> mapRenov) throws Exception {
		return getSqlMapClientTemplate().queryForList("renovaciones.listaRenovados", mapRenov);
	}

}
