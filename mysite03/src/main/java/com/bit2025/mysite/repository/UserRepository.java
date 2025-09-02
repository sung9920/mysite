package com.bit2025.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.bit2025.mysite.vo.UserVo;

@Repository
public class UserRepository {

	public int insert(UserVo vo) {
		int result = 0;

		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(
						" insert into user(name, email, password, gender, join_date) values (?, ?, password(?), ?, curdate())");) {
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return result;

	}

	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo result = null;

		try (Connection con = getConnection();
				PreparedStatement pstmt = con
						.prepareStatement("select id, name from user where email = ? and password = password(?);");) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);

				result = new UserVo();
				result.setId(id);
				result.setName(name);
			}

			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return result;
	}

	public UserVo findById(Long id) {
		UserVo result = null;

		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("select name, email, gender from user where id = ?;");) {
			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String gender = rs.getString(3);

				result = new UserVo();
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
			}

			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return result;
	}

	public int update(UserVo vo) {
		int result = 0;

		if (vo.getPassword() != "") {

			try (Connection con = getConnection();

					PreparedStatement pstmt = con.prepareStatement(
							" update user set name = ?, password = password(?), gender = ? where id = ?;");) {
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getPassword());
				pstmt.setString(3, vo.getGender());
				pstmt.setLong(4, vo.getId());

				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

			return result;
		} else {
			try (Connection con = getConnection();

					PreparedStatement pstmt = con.prepareStatement(
							" update user set name = ?, gender = ? where id = ?;");) {
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getGender());
				pstmt.setLong(3, vo.getId());

				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

			return result;
		}
	}

	private Connection getConnection() throws SQLException {
		Connection con = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.176:3306/webdb";
			con = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver Class Not Found");
		}

		return con;
	}
}
