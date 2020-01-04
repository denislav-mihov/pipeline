package com.mihov.api;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by Denis on 05-Jan-20.
 *
 * @author Denis
 */
public class Vehicle{
  String     type;
  String     make;
  String     model;
  Integer    mileage;
  BigDecimal price;
  String     currency;

  public Vehicle() {
  }

  public Vehicle(String type, String make, String model, Integer mileage, BigDecimal price, String currency) {
    this.type = type;
    this.make = make;
    this.model = model;
    this.mileage = mileage;
    this.price = price;
    this.currency = currency;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Integer getMileage() {
    return mileage;
  }

  public void setMileage(Integer mileage) {
    this.mileage = mileage;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vehicle vehicle = (Vehicle) o;
    return Objects.equals(getType(), vehicle.getType()) &&
      Objects.equals(getMake(), vehicle.getMake()) &&
      Objects.equals(getModel(), vehicle.getModel()) &&
      Objects.equals(getMileage(), vehicle.getMileage()) &&
      Objects.equals(getPrice(), vehicle.getPrice()) &&
      Objects.equals(getCurrency(), vehicle.getCurrency());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getType(), getMake(), getModel(), getMileage(), getPrice(), getCurrency());
  }

  @Override
  public String toString() {
    return "Vehicle{" +
      "type='" + type + '\'' +
      ", make='" + make + '\'' +
      ", model='" + model + '\'' +
      ", mileage=" + mileage +
      ", price=" + price +
      ", currency='" + currency + '\'' +
      '}';
  }
}
