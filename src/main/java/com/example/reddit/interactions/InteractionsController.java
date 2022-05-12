package com.example.reddit.interactions;

import com.example.reddit.interactions.entity.Interactions;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Interactions")
@RestController
@RequestMapping("/interactions")
class InteractionsController {

  private final InteractionService interactionService;
  private final InteractionsRepository interactionsRepository;
  private static final Logger log = LoggerFactory.getLogger(
    InteractionsController.class
  );

  @Autowired
  InteractionsController(
    InteractionService interactionService,
    InteractionsRepository interactionsRepository
  ) {
    this.interactionService = interactionService;
    this.interactionsRepository = interactionsRepository;
  }

  @GetMapping("test")
  public void getAll() {
    interactionService.testCase();
  }

  @PatchMapping("setNotificationChecked")
  public ResponseEntity setNotificationChecked(HttpSession session) {
    try {
      Long meId = (Long) session.getAttribute("userId");

      return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    } catch (Exception e) {
      log.error("error: ", e);
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Something's gone wrong...");
    }
  }

  @PatchMapping("setInteractionRead")
  public ResponseEntity setInteractionRead(HttpSession session) {
    try {
      Long meId = (Long) session.getAttribute("userId");

      return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    } catch (Exception e) {
      log.error("error: ", e);
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Something's gone wrong...");
    }
  }

  @PatchMapping("setAllInteractionRead")
  public ResponseEntity setAllInteractionRead(HttpSession session) {
    try {
      Long meId = (Long) session.getAttribute("userId");

      return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    } catch (Exception e) {
      log.error("error: ", e);
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Something's gone wrong...");
    }
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
  public ResponseEntity interact(
    @PathVariable("id") Long postId,
    Boolean value,
    String field,
    HttpSession session
  ) {
    try {
      Long meId = (Long) session.getAttribute("userId");
      if (meId == null) {
        return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body("You'll have to login first :)");
      }

      Boolean res = interactionService.interact(postId, meId, field, value);

      return ResponseEntity.status(HttpStatus.CREATED).body(res);
    } catch (Exception e) {
      log.error("error: ", e);
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Something's gone wrong...");
    }
  }

  @GetMapping("interactives")
  public ResponseEntity create(HttpSession session) {
    try {
      Long meId = (Long) session.getAttribute("userId");

      return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    } catch (Exception e) {
      log.error("error: ", e);
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Something's gone wrong...");
    }
  }

  @GetMapping("interact/comment/{id}")
  public ResponseEntity delete(
    @PathVariable("id") Long id,
    HttpSession session,
    @RequestParam(name = "value", required = true) Boolean value
  ) {
    try {
      Long meId = (Long) session.getAttribute("userId");

      return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    } catch (Exception e) {
      log.error("error: ", e);
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Something's gone wrong...");
    }
  }
}
