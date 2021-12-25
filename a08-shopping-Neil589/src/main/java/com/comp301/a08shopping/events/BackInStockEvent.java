package com.comp301.a08shopping.events;

import com.comp301.a08shopping.Product;
import com.comp301.a08shopping.Store;

public class BackInStockEvent implements StoreEvent {
  private final Store store;
  private final Product product;

  public BackInStockEvent(Product product, Store store) {
    // Constructor code goes here
    if (product == null || store == null) {
      throw new IllegalArgumentException();
    }
    this.store = store;
    this.product = product;
  }

  @Override
  public Product getProduct() {
    return product;
  }

  @Override
  public Store getStore() {
    return store;
  }
}
