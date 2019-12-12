package mx.com.account.manager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.com.account.manager.domain.Property;
import mx.com.account.manager.exception.DaoException;
import mx.com.account.manager.util.Utils;

public class PropertyDaoImpl implements PropertyDao{

	@Override
	public String getPropertyValue(String property) throws DaoException {
		Connection connect = null;
		PreparedStatement st =null;
		
		try {
			Class.forName(Utils.JDBC_CLASS);
			connect = DriverManager.getConnection("jdbc:sqlite:"+Utils.DB_PATH);
			st = connect.prepareStatement(Property.QUERY_GET_BY_PROPERTY);
			st.setString(1, property);
			ResultSet result = st.executeQuery();
            while (result.next()) {
            	return result.getString("value");
            }	
            connect.close();
		} catch (SQLException e) {
			throw new DaoException(1,"Error en la consulta");
		} catch (ClassNotFoundException e) {
			throw new DaoException(2,"No se encontró la clase JDBC");
		} finally {
			if(connect!=null) {
				try {
					connect.close();
				} catch (SQLException e) {
					throw new DaoException(3,"Error al cerrar la conexión"); 
				}
			}
		}
		return null;
	}

}
