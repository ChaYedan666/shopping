package org.project01.domain;

import java.util.Collection;
import java.util.HashMap;

public class Cart {
    private HashMap<String,CartItem> items = new HashMap<>();
    private double total;

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public void setItems(HashMap<String, CartItem> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    // 购物车添加商品
    public void addItems(CartItem item){
        // 通过pid来判断这个新加的物品在购物车已经存在没有
        String pid = item.getProduct().getPid();

        if(items.containsKey(pid)){
            // 存在就更改数量
            CartItem cartItem = items.get(pid);
            int newCount = cartItem.getCount() + item.getCount();
            cartItem.setCount(newCount);
        }else {
            // 不存在就加入集合
            items.put(pid,item);
        }
        //更改总价
        total = total + item.getSubtotal();
    }

    // 删除某一项
    public void removeItem(String pid){
        CartItem remove = items.remove(pid);
        total = total- remove.getSubtotal();
    }

    // 清空购物车
    public void clear(){
        items.clear();
        total = 0.0;
    }

}
