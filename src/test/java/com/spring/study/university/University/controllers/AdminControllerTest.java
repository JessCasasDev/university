package com.spring.study.university.University.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.study.university.University.domain.Admin;
import com.spring.study.university.University.domain.Role;
import com.spring.study.university.University.enums.RoleEnum;
import com.spring.study.university.University.services.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AdminController.class)
class AdminControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  AdminService adminService;

  @InjectMocks
  AdminController adminController;
  @Autowired
  ObjectMapper objectMapper;
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    adminController = new AdminController(adminService);
  }

  @Test
  void createAdmin() throws Exception {
    Admin admin = getAdminInfo();

    when(adminService.addAdmin(admin))
        .thenReturn(admin);

    this.mockMvc
        .perform(post("/admin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(admin)))
        .andExpect(status().isCreated());
  }

  @Test
  void getAdmin() {
  }

  @Test
  void updateAdmin() {
  }

  @Test
  void deleteAdmin() {
  }

  private Admin getAdminInfo() {
    Admin admin = new Admin();
    admin.setName("Admin");
    admin.setLastName("Lastname");
    admin.setPhoneNumber(1234567L);
    admin.setEmail("admin@email.com");
    admin.setRole(new Role(RoleEnum.Admin));

    return admin;
  }
}