package com.comp301.a08shopping;

import com.comp301.a08shopping.events.*;
import com.comp301.a08shopping.exceptions.OutOfStockException;
import com.comp301.a08shopping.exceptions.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class StoreImpl implements Store {
  private final String name;
  private final List<StoreObserver> storeObservers;
  private final List<Product> products;
  // If you choose to store them in the StoreImpl class, keep
  // in mind that each product should have its own inventory and discount value.

  public StoreImpl(String name) {
    if (name == null) {
      throw new IllegalArgumentException();
    }
    this.storeObservers = new ArrayList<>();
    this.products = new ArrayList<>();
    this.name = name;
    // initialize any other data structure fields
    // that you use to store product and observer information.
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void addObserver(StoreObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException();
    }
    storeObservers.add(observer);
  }

  @Override
  public void removeObserver(StoreObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException();
    }
    storeObservers.remove(observer);
  }

  @Override
  public List<Product> getProducts() {
    List<Product> products2;
    products2 = List.copyOf(products);
    return products2;
  }

  @Override
  public Product createProduct(String name, double basePrice, int inventory) {
    if (name == null || basePrice < 0.01 || inventory < 0) {
      throw new IllegalArgumentException();
    }
    products.add(new ProductImpl(name, basePrice));
    return new ProductImpl(name, basePrice);
  }

  @Override
  public ReceiptItem purchaseProduct(Product product) {
    if (product == null) {
      throw new IllegalArgumentException();
    }
    int productindex = products.indexOf(product);
    if (productindex == -1) {
      throw new ProductNotFoundException();
    }
    ProductImpl singleproduct = (ProductImpl) products.get(productindex);
    if (singleproduct.getNoItems() == 0) {
      throw new OutOfStockException();
    }

    int singleproductqty = singleproduct.getNoItems();
    singleproduct.setNoItems(singleproductqty--);
    notify(new PurchaseEvent(product, this));
    if (singleproduct.getNoItems() <= 0) {
      singleproduct.setNoItems(0);
      notify(new OutOfStockEvent(product, this));
    }
    return new ReceiptItemImpl(singleproduct.getName(), singleproduct.getBasePrice(), name) {};
  }

  @Override
  public void restockProduct(Product product, int numItems) {
    if (product == null) {
      throw new IllegalArgumentException();
    }
    int index = products.indexOf(product);
    if (numItems < 0) {
      throw new IllegalArgumentException();
    }
    if (index == -1) {
      throw new ProductNotFoundException();
    }
    ProductImpl singleproduct = (ProductImpl) products.get(index);
    int singleproductqty = singleproduct.getNoItems();
    int singleproductqty2 = singleproduct.getNoItems();
    singleproductqty = singleproductqty + numItems; // incrementing the created variable
    int singleproductper = singleproduct.getNoItems();
    if (singleproductper != 0) {
      notify(new BackInStockEvent(product, this));
    }
  }

  @Override
  public void startSale(Product product, double percentOff) {
    if (product == null) {
      throw new IllegalArgumentException();
    }
    if (percentOff < 0.00 || percentOff > 1.00) {
      throw new IllegalArgumentException();
    }
    int index = products.indexOf(product);
    if (index == -1) {
      throw new ProductNotFoundException();
    }
    ((ProductImpl) products.get(index)).setPercentoff(percentOff);
    notify(new SaleStartEvent(product, this));
  }

  @Override
  public void endSale(Product product) {
    if (product == null) {
      throw new IllegalArgumentException();
    }
    int index = products.indexOf(product);
    if (index == -1) {
      throw new ProductNotFoundException();
    }
    if (getIsOnSale(product)) {
      ((ProductImpl) products.get(index)).setPercentoff(0);
      notify(new SaleEndEvent(product, this));
    }
  }

  @Override
  public int getProductInventory(Product product) {
    if (product == null) {
      throw new IllegalArgumentException();
    }
    int productindex = products.indexOf(product);
    if (productindex == -1) { // another way eli !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      throw new ProductNotFoundException();
    }
    ProductImpl singleproduct = (ProductImpl) products.get(productindex);
    int singleproductper = singleproduct.getNoItems();
    return singleproductper;
  }

  @Override
  public boolean getIsInStock(Product product) {
    if (product == null) {
      throw new IllegalArgumentException();
    }
    int productindex = products.indexOf(product);
    if (productindex == -1) {
      throw new ProductNotFoundException();
    }
    ProductImpl singleproduct = (ProductImpl) products.get(productindex);
    int singleproductper = singleproduct.getNoItems();
    return singleproductper > 0;
  }

  @Override
  public double getSalePrice(Product product) {
    if (product == null) {
      throw new IllegalArgumentException();
    }
    int index = products.indexOf(product);
    if (index == -1) { // another way eli !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      throw new ProductNotFoundException();
    }
    ProductImpl singleproduct = (ProductImpl) products.get(index);
    double singleproductper = singleproduct.getPercentoff();
    double saleprice = (product.getBasePrice() * (1.0 - singleproductper));
    double saleprice2 = Math.round(saleprice * 100.0) / 100.0;

    return saleprice2;
  }

  @Override
  public boolean getIsOnSale(Product product) {
    double saleprice = getSalePrice(product);
    if (product == null) {
      throw new IllegalArgumentException();
    }
    int index = products.indexOf(product);
    if (index == -1) {
      throw new ProductNotFoundException();
    }
    return saleprice < product.getBasePrice();
  }

  private void notify(StoreEvent x) {
    int i;
    for (i = 0; i < storeObservers.size(); i++) {
      storeObservers.get(i).update(x);
    }
  }
}
