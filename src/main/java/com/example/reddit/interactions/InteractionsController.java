package com.example.reddit.interactions;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Interactions")
@RestController
@RequestMapping("/interactions")
class InteractionsController {

  private final InteractionService interactionService;
  private static final Logger log = LoggerFactory.getLogger(
    InteractionsController.class
  );

  @Autowired
  InteractionsController(InteractionService interactionService) {
    this.interactionService = interactionService;
  }

  @GetMapping("test")
  public Object getAll() {
    return interactionService.testCase();
  }

  @PatchMapping("setNotificationChecked")
  public ResponseEntity<?> setNotificationChecked(HttpSession session) {
    Long meId = (Long) session.getAttribute("userId");

    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }

  @PatchMapping("setInteractionRead")
  public ResponseEntity<?> setInteractionRead(HttpSession session) {
    Long meId = (Long) session.getAttribute("userId");

    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }

  @PatchMapping("setAllInteractionRead")
  public ResponseEntity<?> setAllInteractionRead(HttpSession session) {
    Long meId = (Long) session.getAttribute("userId");

    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }

  @GetMapping("interact/post/{id}")
  @Parameter(
    name = "value",
    schema = @Schema(type = "boolean", allowableValues = { "true", "false" })
  )
  @Parameter(
    name = "field",
    schema = @Schema(
      type = "string",
      allowableValues = { "vote", "like", "laugh", "confused" }
    ),
    required = true
  )
  public ResponseEntity<?> interact(
    @PathVariable("id") Long postId,
    Boolean value,
    String field,
    HttpSession session
  ) {
    Long meId = (Long) session.getAttribute("userId");
    if (meId == null) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("You'll have to login first :)");
    }

    Boolean res = interactionService.interact(postId, meId, field, value);

    return ResponseEntity.status(HttpStatus.CREATED).body(res);
  }

  @GetMapping("interactives")
  public ResponseEntity<?> create(HttpSession session) {
    Long meId = (Long) session.getAttribute("userId");

    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }

  @GetMapping("interact/comment/{id}")
  public ResponseEntity<?> delete(
    @PathVariable("id") Long id,
    HttpSession session,
    @RequestParam(name = "value", required = true) Boolean value
  ) {
    Long meId = (Long) session.getAttribute("userId");

    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }
}
