package com.bbva.control.boveda.model.dao.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.bbva.control.boveda.bean.ControlDocBean;
import com.bbva.control.boveda.model.dao.TempTipo11DAO;
import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("deprecation")
@Repository("TempTipo11DAO")
public class SqlMapTempTipo11DAOImpl extends SqlMapClientDaoSupport implements TempTipo11DAO{
	
	@Autowired
	public SqlMapTempTipo11DAOImpl(@Qualifier("SqlMapClientBoveda") SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}

	@Override
	public void insert(ControlDocBean itemCtrlDoc) throws Exception {
		getSqlMapClientTemplate().insert("temp.insertarTempTipo", itemCtrlDoc);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ControlDocBean> listaTemp() throws Exception {
		return getSqlMapClientTemplate().queryForList("temp.obtenerTempTipo");
	}

	@Override
	public void delete() throws Exception {
		getSqlMapClientTemplate().delete("temp.borrarTempTipo");
	}

}
