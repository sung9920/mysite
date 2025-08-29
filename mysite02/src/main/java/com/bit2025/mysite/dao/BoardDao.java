package com.bit2025.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bit2025.mysite.vo.BoardVo;

public class BoardDao {

	public int insert(BoardVo vo) {
		int result = 0;

		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(
						"insert into board values (null, ?, ?, 0, now(), ifnull((select max(a.g_no) from board a), 0) + 1, 1, 1, ?);");
				) {
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUser_id());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return result;

	}


	public BoardVo findById(Long boardId) {
		BoardVo result = null;

		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("select a.id, title, contents, g_no, o_no, depth, b.name "
															 + "from board a, user b "
															 + "where a.user_id = b.id "
															 + "and a.id = ?;");) {
			pstmt.setLong(1, boardId);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int g_no = rs.getInt(4);
				int o_no = rs.getInt(5);
				int depth = rs.getInt(6);
				String name = rs.getString(7);

				result = new BoardVo();
				result.setId(id);
				result.setTitle(title);
				result.setContents(contents);
				result.setG_no(g_no);
				result.setO_no(o_no);
				result.setDepth(depth);
				result.setName(name);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return result;

	}



	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<BoardVo>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Connection con = getConnection();

			String sql = "select a.id, title, contents, hit, date_format(reg_date, '%Y-%m-%d %h:%i:%s'), g_no, o_no, depth, user_id, b.name "
						+ "from board a, user b "
						+ "where a.user_id = b.id "
						+ "order by g_no desc, o_no asc;";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String reg_date = rs.getString(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);
				Long user_id = rs.getLong(9);
				String name = rs.getString(10);

				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(reg_date);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);
				vo.setUser_id(user_id);
				vo.setName(name);

				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return result;
	}

	public void updateHit(Long id) {

		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("update board set hit = hit+1 where id = ?;");) {
			pstmt.setLong(1, id);
			pstmt.executeQuery();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public int updateBoard(BoardVo vo) {
		int result = 0;

		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(
						"update board set title = ?, contents = ? where id = ? ");
				) {
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUser_id());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return result;
	}

	public int deleteById(Long id) {
		int result = 0;

		try (
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("delete from board where id = ?;");
		) {
			pstmt.setLong(1, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			 System.out.println("error:" + e);
		}

		return result;

	}


	public int insertReply(BoardVo vo) {
		int result = 0;

		try (Connection con = getConnection();
				PreparedStatement pstmt1 = con.prepareStatement("update board set o_no = o_no + 1 where g_no = ? and o_no > ?;");
				PreparedStatement pstmt2 = con.prepareStatement("insert into board values (null, ?, ?, 0, now(), ?, ?+1, ?+1, ?);");
				) {
			pstmt1.setInt(1, vo.getG_no());
			pstmt1.setInt(2, vo.getO_no());

			pstmt2.setString(1, vo.getTitle());
			pstmt2.setString(2, vo.getContents());
			pstmt2.setInt(3, vo.getG_no());
			pstmt2.setInt(4, vo.getO_no());
			pstmt2.setInt(5, vo.getDepth());
			pstmt2.setLong(6, vo.getUser_id());

			result = pstmt1.executeUpdate();
			result = pstmt2.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return result;

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
