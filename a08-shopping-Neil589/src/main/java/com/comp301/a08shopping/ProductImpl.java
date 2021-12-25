package com.comp301.a08shopping;

public class ProductImpl implements Product {
  private final String name;
  private final double basePrice;
  private int noItems;
  private double percentoff;

  public ProductImpl(String name, double basePrice) {
    if (basePrice < 0.00) {
      throw new IllegalArgumentException();
    }
    this.name = name;
    this.basePrice = basePrice;
    this.noItems = 0;
    this.percentoff = 0;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public double getBasePrice() {
    return this.basePrice;
  }

  @Override
  public boolean equals(Object product) {
    return name.equals(((Product) product).getName());
  }

  public int getNoItems() {
    return this.noItems;
  }

  public void setNoItems(int noItems) {
    this.noItems = noItems;
  }

  public double getPercentoff() {
    return this.percentoff;
  }

  public void setPercentoff(double percentoff) {
    this.percentoff = percentoff;
  }

