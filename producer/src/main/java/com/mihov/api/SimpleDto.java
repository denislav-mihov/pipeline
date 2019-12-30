package com.mihov.api;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SimpleDto simpleDto = (SimpleDto) o;
    return Objects.equals(getName(), simpleDto.getName()) &&
      Objects.equals(getType(), simpleDto.getType());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getType());
  }
}
