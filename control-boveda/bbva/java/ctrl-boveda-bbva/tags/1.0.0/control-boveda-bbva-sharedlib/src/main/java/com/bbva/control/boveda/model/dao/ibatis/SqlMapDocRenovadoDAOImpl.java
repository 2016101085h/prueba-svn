package com.bbva.control.boveda.model.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.bbva.control.boveda.bean.DocRenovadoBean;
import com.bbva.control.boveda.model.dao.DocRenovadoDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("deprecation")
@Repository("DocRenovadoDAO")
public class SqlMapDocRenovadoDAOImpl extends SqlMapClientDaoSupport implements DocRenovadoDAO{

	@Autowired
	public SqlMapDocRenovadoDAOImpl(@Qualifier("SqlMapClientBoveda") SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}
	
	@Override
	public void insertarDocumento(DocRenovadoBean docRenov) throws Exception {
		getSqlMapClientTemplate().insert("docRenovado.insertar", docRenov);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocRenovadoBean> obtener(Map<String, Object> map) throws Exception {
		return getSqlMapClientTemplate().queryForList("docRenovado.obtener", map);
	}

	@Override
	public void deleteRegistro(DocRenovadoBean itemDocRen) throws Exception {
		getSqlMapClientTemplate().delete("docRenovado.borrar", itemDocRen);
		
	}

}
