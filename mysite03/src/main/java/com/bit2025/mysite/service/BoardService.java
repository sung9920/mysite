package com.bit2025.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2025.mysite.repository.BoardRepository;
import com.bit2025.mysite.vo.BoardVo;

@Service
public class BoardService {
	private static final int PAGE_SIZE = 5;

	@Autowired
	BoardRepository boardRepository;

	public Map<String, Object> getBoardList(int currentPage, String keyword) {

				//1. 페이징을 위한 기본 데이터 계산
				int totalCount = boardRepository.getTotalCount(keyword);
				int startPage = ((currentPage-1) / 5 * 5 ) + 1;
				int endPage = (totalCount%5 == 0 ? totalCount/5 : (totalCount/5) + 1);

//				//2. 파라미터 page 값  검증
//				if(currentPage > pageCount) {
//					currentPage = pageCount;
//					currentBlock = (int)Math.ceil((double)currentPage / PAGE_SIZE);
//				}
//
//				if(currentPage < 1) {
//					currentPage = 1;
//					currentBlock = 1;
//				}
//
//				//3. view에서 페이지 리스트를 렌더링 하기위한 데이터 값 계산
//				int beginPage = currentBlock == 0 ? 1 : (currentBlock - 1) * PAGE_SIZE + 1;
//				int prevPage = (currentBlock > 1 ) ? (currentBlock - 1) * PAGE_SIZE : 0;
//				int nextPage = (currentBlock < blockCount) ? currentBlock * PAGE_SIZE + 1 : 0;
//				int endPage = (nextPage > 0) ? (beginPage - 1) + LIST_SIZE : pageCount;

				//4. 리스트 가져오기
				List<BoardVo> list = boardRepository.findAll(currentPage, keyword);

				//5. 리스트 정보를 맵에 저장
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("list", list);
				map.put("totalCount", totalCount);
				map.put("currentPage", currentPage);
				map.put("startPage", startPage);
				map.put("endPage", endPage);
				map.put("keyword", keyword);

				return map;
	}

	public void writeBoard(BoardVo vo) {
		boardRepository.insert(vo);
	}

	public void deleteBoard(Long id) {
		boardRepository.deleteById(id);
	}

	public BoardVo viewBoard(Long id) {
		boardRepository.updateHit(id);
		return boardRepository.findById(id);
	}

	public void updateBoard(BoardVo vo) {
		boardRepository.updateBoard(vo);
	}

}
