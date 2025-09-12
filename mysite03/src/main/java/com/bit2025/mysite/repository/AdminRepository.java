package com.bit2025.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit2025.mysite.vo.SiteVo;

@Repository
public class AdminRepository {

	@Autowired
	private SqlSession sqlSession;

	public int insert(SiteVo siteVo) {
		return sqlSession.insert("admin.insert", siteVo);
	}

//	public List<AdminVo> findAll() {
//		return sqlSession.selectList("admin.insert",adminvo);
//	}

}
