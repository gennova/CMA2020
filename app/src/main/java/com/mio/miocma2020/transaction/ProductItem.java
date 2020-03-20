package com.mio.miocma2020.transaction;

import java.io.Serializable;

public class ProductItem implements Serializable {
    String product_id,product_name;

    public ProductItem(String product_id, String product_name) {
        this.product_id = product_id;
        this.product_name = product_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
