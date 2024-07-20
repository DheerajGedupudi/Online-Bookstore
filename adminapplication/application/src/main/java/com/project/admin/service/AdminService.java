package com.project.admin.service;

import com.project.admin.dao.AdminRepository;
import com.project.admin.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    // Other methods as needed
}
