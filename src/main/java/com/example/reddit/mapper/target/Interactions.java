package com.example.reddit.mapper.target;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interactions {

  private Boolean voteStatus;
  private Boolean likeStatus;
  private Boolean laughStatus;
  private Boolean confusedStatus;
}
