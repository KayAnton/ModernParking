package org.parking.model.buildings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Spot {

  private final int level;
  private final int spotNumber;
  private long timestamp;

}
