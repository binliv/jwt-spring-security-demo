package com.yuyan.web.dto;

import java.util.List;

public class UserListDTO {
    private List<UserDTO> items;
    private long total;

    public List<UserDTO> getItems() {
        return items;
    }

    public void setItems(List<UserDTO> items) {
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
