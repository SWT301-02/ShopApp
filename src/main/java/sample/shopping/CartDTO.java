/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.shopping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lmao
 */
public class CartDTO {

    private Map<String, ProductDTO> cart = new HashMap<>();

    public boolean add(String id, ProductDTO product) {
        boolean check = false;
        try {
            if (this.cart == null) {
                this.cart = new HashMap<>();
            }
            if (this.cart.containsKey(product.getProductID())) {
                int currentQuantity = this.cart.get(id).getQuantity();
                product.setQuantity(currentQuantity + product.getQuantity());
            }
            this.cart.put(id, product);
            check = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return check;
    }

    public Map<String, ProductDTO> getCart() {
        return cart;
    }

    public void setCart(Map<String, ProductDTO> cart) {
        this.cart = cart;
    }

    public boolean change(String id, ProductDTO product) {
        boolean check = false;
        try {
            if (this.cart != null) {
                if (this.cart.containsKey(id)) {
                    if (product.getQuantity() == 0) {
                        return remove(id);
                    }
                    this.cart.put(id, product);
                    check = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return check;
    }

    public boolean remove(String id) {
        boolean check = false;
        try {
            if (this.cart != null) {
                if (this.cart.containsKey(id)) {
                    this.cart.remove(id);
                    check = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return check;
    }

    public int cartSize() {
        return this.cart.size();
    }

    public double getTotalPrice() {
        double sum = 0;
        for (ProductDTO product : cart.values()) {
            if (product != null) {
                sum += product.getQuantity() * product.getPrice();
            }
        }
        return sum;
    }

    public int getCartQuantity() {
        int sum = 0;
        for (ProductDTO product : cart.values()) {
            if (product != null) {
                sum += product.getQuantity();
            }
        }
        return sum;
    }

//    public OrderDTO createOrder(int orderID, String userID) {
//        double total = getTotalPrice();
//        List<OrderDetailDTO> orderDetails = cart.values().stream()
//                .map(product -> new OrderDetailDTO(orderID, product.getProductID(), product.getPrice(),
//                        product.getQuantity()))
//                .toList();
//        return new OrderDTO(orderID, userID, total, orderDetails);
//    }
}
