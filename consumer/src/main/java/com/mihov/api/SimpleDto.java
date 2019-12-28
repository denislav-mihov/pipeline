package com.mihov.api;

/**
 * Created by Denis on 28-Dec-19.
 *
 * @author Denis
 */
public class SimpleDto{
  private String name;
  private String type;

  public SimpleDto() {}

  public SimpleDto(String name, String type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return '{' +
      "name='" + name + '\'' +
      ", type='" + type + '\'' +
      '}';
  }
}
