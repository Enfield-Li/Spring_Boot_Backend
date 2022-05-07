package com.example.reddit.interactions;

import com.example.reddit.interactions.entity.Interactions;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Interactions")
@RestController
@RequestMapping("/interactions")
class InteractionsController {

  InteractionService interactionService;

  InteractionsController(InteractionService interactionService) {
    this.interactionService = interactionService;
  }

  @GetMapping
  public ResponseEntity<List<Interactions>> getAll() {
    return null;
  }

  @GetMapping("{id}")
  @Parameter(
    name = "value",
    schema = @Schema(type = "boolean", allowableValues = { "true", "false" })
  )
  @Parameter(
    name = "field",
    schema = @Schema(
      type = "string",
      allowableValues = { "vote", "like", "laugh", "confused" }
    )
  )
  public Boolean getById(
    @PathVariable("id") Long id,
    Boolean value,
    String field,
    HttpSession session
  ) {
    Long userId = (Long) session.getAttribute("userId");

    System.out.println(userId);
    if (userId == null) return null;

    return interactionService.interact(id, userId);
  }

  @PostMapping
  public ResponseEntity<Interactions> create(@RequestBody Interactions item) {
    return null;
  }

  @PutMapping("{id}")
  public ResponseEntity<Interactions> update(
    @PathVariable("id") Long id,
    @RequestBody Interactions item
  ) {
    return null;
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return null;
  }
}
