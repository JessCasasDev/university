package com.spring.study.university.University.controllers;

import com.spring.study.university.University.domain.Admin;
import com.spring.study.university.University.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("admin")
public class AdminController {
  @Autowired
  private final AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public Admin createAdmin(@RequestBody Admin admin){
    return adminService.addAdmin(admin);
  }

  @Operation(summary = "Get an admin by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found admin",
          content = { @Content(mediaType = "application/json",
              schema = @Schema(implementation = Admin.class)) }),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Admin not found",
          content = @Content) })
  @GetMapping("/{uuid}")
  public Admin getAdmin(@PathVariable UUID uuid){
    return adminService.getAdmin(uuid);
  }

  @PatchMapping("/{uuid}")
  public Admin updateAdmin(@PathVariable UUID uuid, @RequestBody Admin admin){
    return adminService.updateAdmin(uuid, admin);
  }

  @DeleteMapping("{uuid}")
  public void deleteAdmin(@PathVariable UUID uuid){
    adminService.deleteAdmin(uuid);
  }
}
