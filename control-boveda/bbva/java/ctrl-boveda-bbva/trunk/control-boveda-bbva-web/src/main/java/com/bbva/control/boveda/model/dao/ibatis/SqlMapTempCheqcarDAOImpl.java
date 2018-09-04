package com.bbva.control.boveda.model.dao.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.bbva.control.boveda.bean.ControlDocBean;
import com.bbva.control.boveda.model.dao.TempCheqcarDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("deprecation")
public class SqlMapTempCheqcarDAOImpl extends SqlMapClientDaoSupport implements TempCheqcarDAO{

	@Autowired
	public SqlMapTempCheqcarDAOImpl(@Qualifier("SqlMapClientBoveda") SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}
	
	@Override
	public void insertar(ControlDocBean temp) throws Exception {
		getSqlMapClientTemplate().insert("temp.insertarTempCheqcar", temp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ControlDocBean> listaTempCh() throws Exception {
		return getSqlMapClientTemplate().queryForList("temp.obtenerTempCheqcar");
	}

	@Override
	public void delete() throws Exception {
		getSqlMapClientTemplate().delete("temp.borrarTempCheqcar");
	}

}
