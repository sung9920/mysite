package com.bit2025.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2025.mysite.repository.AdminRepository;
import com.bit2025.mysite.vo.AdminVo;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;

	public void addContents(AdminVo adminVo) {
		adminRepository.insert(adminVo);
	}

//	public List<AdminVo> getContentsList() {
//		return adminRepository.findAll();
//	}
}
