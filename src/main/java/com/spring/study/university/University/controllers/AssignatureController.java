package com.spring.study.university.University.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.spring.study.university.University.domain.Assignature;
import com.spring.study.university.University.services.AssignatureService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("assignatures")
public class AssignatureController {
  private final AssignatureService assignatureService;

  @GetMapping("")
  public List<Assignature> getAssignatures() {
    return assignatureService.getAssignatures();
  }

  @PatchMapping("/{uuid}")
  public Assignature updateAssignature(@PathVariable UUID uuid, @RequestBody Assignature assignature) {
    return assignatureService.editAssignature(uuid, assignature);
  }

  @GetMapping("/{uuid}")
  public Assignature getAssignature(@PathVariable UUID uuid) {
    return assignatureService.getAssignature(uuid);
  }

  @DeleteMapping("/{uuid}")
  public void deleteAssignature(@PathVariable UUID uuid) {
    assignatureService.deleteAssignature(uuid);
  }

  @PostMapping("")
  public Assignature createAssignature(@RequestBody Assignature assignature) {
    return assignatureService.createAssignature(assignature);
  }

  @PatchMapping("/{uuid}/assign-professor")
  public Assignature assignProfessor(@PathVariable UUID uuid, @RequestBody JsonNode professor) {
    String professorUuid = professor.get("professor").asText();
    return assignatureService.assignProfessor(uuid, professorUuid);
  }

  @PatchMapping("/{uuid}/add-prerequisites")
  public Assignature addPrerequisites(@PathVariable UUID uuid, @RequestBody JsonNode body) {
    ArrayList<String> prerequisites = new ArrayList<>();

    Iterator<JsonNode> prerequisitesString = body.get("prerequisites").elements();

    while (prerequisitesString.hasNext()) {
      prerequisites.add(prerequisitesString.next().asText());
    }

    return assignatureService.addPrerequisite(uuid, prerequisites);
  }
}
