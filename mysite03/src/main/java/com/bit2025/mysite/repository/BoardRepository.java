package com.bit2025.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit2025.mysite.vo.BoardVo;

@Repository
public class BoardRepository {

	@Autowired
	private SqlSession sqlsession;

	public int insert(BoardVo boardVo) {
		return sqlsession.insert("board.insert", boardVo);
	}

	public List<BoardVo> findAllByPageAndKeword(String keyword, Integer page, Integer size) {
		return sqlsession.selectList("board.findAllByPageAndKeword", Map.of("keyword", keyword, "page", page, "size", size));
	}

	public int update(BoardVo boardvo) {
		return sqlsession.update("board.update", boardvo);
	}

	public int delete(Long id, Long userId) {
		return sqlsession.delete("board.delete", Map.of("id", id, "userId", userId));
	}

	public BoardVo findById(Long boardId) {
		return sqlsession.selectOne("board.findById", boardId);
	}

	public BoardVo findByIdAndUserId(Long boardId, Long userId) {
		return sqlsession.selectOne("board.findByIdAndUserId", Map.of("boardId", boardId, "userId", userId));
	}

	public int updateHit(Long id) {
		return sqlsession.update("board.updateHit", id);
	}

	public int updateOrderNo(Integer groupNo, Integer orderNo) {
		return sqlsession.update("board.updateOrderNo", Map.of("groupNo", groupNo, "orderNo", orderNo));
	}

	public int getTotalCount(String keyword) {
		return sqlsession.selectOne("board.getTotalCount", keyword);
	}
}