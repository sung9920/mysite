package com.bit2025.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bit2025.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {

	public int deleteByIdAndPassword(Long id, String password) {
		int result = 0;

		try (
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("delete from guestbook where id = ? and password = ?;");
		) {
			pstmt.setLong(1, id);
			pstmt.setString(2, password);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			 System.out.println("error:" + e);
		}

		return result;
	}

	public int insert(GuestbookVo vo) {
		int result = 0;

		try (
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("insert into guestbook(name, password, message, reg_date) values (?, ?, ?, now())");
		){
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			 System.out.println("error:" + e);
		}

		return result;
	}

	public List<GuestbookVo> findAll() {
		List<GuestbookVo> result = new ArrayList<GuestbookVo>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();

			String sql = "select id, name, message, date_format(reg_date, '%Y-%m-%d %h:%i:%s') from guestbook order by id desc";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String message = rs.getString(3);
				String reg_date = rs.getString(4);

				GuestbookVo vo = new GuestbookVo();
				vo.setId(id);
				vo.setName(name);
				vo.setMessage(message);
				vo.setRegDate(reg_date);

				result.add(vo);
			}
		} catch (SQLException e) {
			 System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private Connection getConnection() throws SQLException {
		Connection con = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url  = "jdbc:mariadb://192.168.0.176:3306/webdb";
			con =  DriverManager.getConnection (url, "webdb", "webdb");
		} catch(ClassNotFoundException ex) {
			System.out.println("Driver Class Not Found");
		}

		return con;
	}

}