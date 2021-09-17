package org.parking.model.vehicles;

import lombok.Data;
import org.parking.model.User;

@Data
public abstract class AbstractAutomobile implements Automobile {

  private User owner;

  public AbstractAutomobile(User user) {
    this.owner = user;
  }

  public String toString() {
    return String.format("Auto(owner: %s, type:%s)", owner.getUserId(), getType());
  }

}
