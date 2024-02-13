package com.spring.study.university.University.controllers;

import com.spring.study.university.University.domain.Professor;
import com.spring.study.university.University.services.ProfessorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/professor")
@AllArgsConstructor
public class ProfessorController {

  private final ProfessorService professorService;

  @PostMapping("")
  public Professor createProfessor(@RequestBody Professor professor){
    return professorService.createProfessor(professor);
  }

  @GetMapping("")
  public List<Professor> getProfessors() {
    return professorService.getProfessors();
  }

  @GetMapping("/{uuid}")
  public Professor getProfessor(@PathVariable UUID uuid) {
    return professorService.getProfessor(uuid);
  }

  @PatchMapping("/{uuid}")
  public Professor updateProfessor(@PathVariable UUID uuid, @RequestBody Professor professor){
    return professorService.editProfessor(uuid, professor);
  }

  @DeleteMapping("/{uuid}")
  public void deleteProfessor(@PathVariable UUID uuid){
    professorService.deleteProfessor(uuid);
  }
}
