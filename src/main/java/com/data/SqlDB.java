package com.testjava.data;

import java.util.ArrayList;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlDB
{
	private final String m_url = "jdbc:mysql://localhost:3306/test_java";
	private final String m_user = "root";
	private final String m_password = "root";

	private Connection m_con = null;
	private Statement m_stmt = null;
	private ResultSet m_rs;
	private int m_rowCount;

	public SqlDB()
	{
		try {
			m_con = DriverManager.getConnection(m_url, m_user, m_password);
			m_stmt = m_con.createStatement();

//			m_rs = m_stmt.executeQuery(query);
//			m_rs.last();
//			m_rowCount = m_rs.getRow();
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
	}

	public int checkRegistration(String login, String password)
	{
		if (m_con == null || m_stmt == null) {
			return 0;
		}

		String query = String.format("SELECT id FROM users WHERE login='%s' AND password='%s';", login, password);

		try {
			m_rs = null;
			m_rs = m_stmt.executeQuery(query);

			if (m_rs.next()) {
				return m_rs.getInt("id");
			}

			return 0;
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}

		return 0;
	}

	public void addMessage(int user_id, String text)
	{
		if (m_con == null || m_stmt == null) {
			return;
		}

		String query = String.format("INSERT INTO `messages` (`user_id`, `text`) values(%i, \"%s\");", user_id, text);

		try {
			m_stmt.executeQuery(query);
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
	}

	public ArrayList<String> getHstory()
	{
		ArrayList<String> result = new ArrayList<String>();

		if (m_con == null || m_stmt == null) {
			return result;
		}

		String query = "SELECT `text` FROM (SELECT `text`, `id` FROM `mesasges` ORDER BY `id` DESC LIMIT 10) t ORDER BY `id`;";

		try {
			m_rs = m_stmt.executeQuery(query);

			while (m_rs.next()) {
				result.add(m_rs.getString("text"));
			}
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}

		return result;
	}

	public ResultSet getRS()
	{
		return m_rs;
	}

	public int getRowsCount()
	{
		return m_rowCount;
	}

	public void close()
	{
		try { m_con.close(); } catch(SQLException se) { ; }
		try { m_stmt.close(); } catch(SQLException se) { ; }
		try { m_rs.close(); } catch(SQLException se) { ; }
	}
}
