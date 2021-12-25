package com.comp301.a08shopping;

import com.comp301.a08shopping.events.*;

import java.util.ArrayList;
import java.util.List;

public class CustomerImpl implements Customer {
  private final String cust_name;
  private final List<ReceiptItem> receiptitems;
  private double cust_budget;

  public CustomerImpl(String name, double budget) {
    if (name == null || budget < 0.00) {
      throw new IllegalArgumentException();
    }
    this.cust_name = name;
    this.cust_budget = budget;
    this.receiptitems = new ArrayList<>();
  }

  @Override
  public String getName() {
    return this.cust_name;
  }

  @Override
  public double getBudget() {
    return this.cust_budget;
  }

  @Override
  public void purchaseProduct(Product product, Store store) {
    if (product == null || store == null) {
      throw new IllegalArgumentException();
    }
    if (store.getSalePrice(product) > cust_budget) {
      throw new IllegalStateException();
    }
    cust_budget = cust_budget - store.getSalePrice(product);
    store.purchaseProduct(product);
  }

  @Override
  public List<ReceiptItem> getPurchaseHistory() {
    List<ReceiptItem> products2;
    products2 = List.copyOf(receiptitems);
    return products2;
  }

  @Override
  public void update(StoreEvent event) {
    if (event instanceof BackInStockEvent) {
      System.out.println(
          event.getProduct().getName() + " is back in stock at " + event.getStore().getName());
    }
    if (event instanceof OutOfStockEvent) {
      System.out.println(
          event.getProduct().getName() + " is now out of stock at " + event.getStore().getName());
    }
    if (event instanceof PurchaseEvent) {
      System.out.println(
          "Someone purchased "
              + event.getProduct().getName()
              + " at "
              + event.getStore().getName());
    }
    if (event instanceof SaleEndEvent) {
      System.out.println(
          "The sale for "
              + event.getProduct().getName()
              + " at "
              + event.getStore().getName()
              + " has ended");
    }
    if (event instanceof SaleStartEvent) {
      System.out.println(
          "New sale for "
              + event.getProduct().getName()
              + " at "
              + event.getStore().getName()
              + "!");
    }
  }
}
