package com.bit2025.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2025.mysite.repository.BoardRepository;
import com.bit2025.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	BoardRepository boardRepository;

	public void writeBoard(BoardVo vo) {
		boardRepository.insert(vo);
	}

	public void deleteBoard(Long id) {
		boardRepository.deleteById(id);
	}
}
