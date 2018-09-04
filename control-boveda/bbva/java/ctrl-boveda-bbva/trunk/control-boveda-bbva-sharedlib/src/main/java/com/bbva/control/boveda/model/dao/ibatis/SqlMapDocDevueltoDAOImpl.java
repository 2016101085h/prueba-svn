package com.bbva.control.boveda.model.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.bbva.control.boveda.bean.DocDevueltoBean;
import com.bbva.control.boveda.model.dao.DocDevueltoDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("deprecation")
public class SqlMapDocDevueltoDAOImpl extends SqlMapClientDaoSupport implements DocDevueltoDAO{

	@Autowired
	public SqlMapDocDevueltoDAOImpl(@Qualifier("SqlMapClientBoveda") SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DocDevueltoBean> listDevueltos(Map<String, Object> map)
			throws Exception {
		return getSqlMapClientTemplate().queryForList("docDevuelto.listDevueltos", map);
	}

	@Override
	public void insertarRegistro(DocDevueltoBean itemDocDev) throws Exception {
		getSqlMapClientTemplate().insert("docDevuelto.insertar", itemDocDev);
	}

	@Override
	public void deleteRegistro(DocDevueltoBean itemDocDev) throws Exception {
		getSqlMapClientTemplate().delete("docDevuelto.borrar", itemDocDev);
	}

}
