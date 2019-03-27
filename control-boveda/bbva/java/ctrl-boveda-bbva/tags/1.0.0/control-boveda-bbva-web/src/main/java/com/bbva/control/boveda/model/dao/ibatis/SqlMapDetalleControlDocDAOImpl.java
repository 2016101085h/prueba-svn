package com.bbva.control.boveda.model.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.bbva.control.boveda.bean.DetalleControlDocBean;
import com.bbva.control.boveda.model.dao.DetalleControlDocDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("deprecation")
@Repository("DetalleControlDocDAO")
public class SqlMapDetalleControlDocDAOImpl extends SqlMapClientDaoSupport  implements DetalleControlDocDAO{

	@Autowired
	public SqlMapDetalleControlDocDAOImpl(@Qualifier("SqlMapClientBoveda") SqlMapClient sqlMapClient) {
		setSqlMapClient(sqlMapClient);
	}
	
	@Override
	public void insertarRegistro(DetalleControlDocBean detalleCtrl)
			throws Exception {
		getSqlMapClientTemplate().insert("detalleControlDoc.insertar",detalleCtrl);
	}

	@Override
	public void updateRegistro(DetalleControlDocBean detalle) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetalleControlDocBean> obtenerLista(Map<String, Object> param)
			throws Exception {
		return getSqlMapClientTemplate().queryForList("detalleControlDoc.obtener", param);
	}

}
