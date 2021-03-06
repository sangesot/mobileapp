package edu.thu.component.jst;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import edu.thu.bean.XmlResult;
import edu.thu.icomponent.AbstractComponent;
import edu.thu.icomponent.ILoginComponent;

public class LoginComponent extends AbstractComponent implements ILoginComponent {

	@Override
	public void login(XmlResult xmlResult, HashMap<String, String> paramMap) {
		InitialContext context;
		try {
			context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/portal");
			Connection connection = dataSource.getConnection();
			String sql = "select * from portal.SYS_USER where userid='" + paramMap.get("userId") + "'";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				if (rs.getString("userpass").equalsIgnoreCase(paramMap.get("password"))) {
					onResultSucceed(xmlResult, "��¼�ɹ�", null);
				} else {
					onResultFail(xmlResult, "��¼ʧ��", null);
				}
			} else {
				onResultFail(xmlResult, "��¼ʧ��", null);
			}
			rs.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
			onResultException(xmlResult, "������߷���������", null);
		}
	}

}
