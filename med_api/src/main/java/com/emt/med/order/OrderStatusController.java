package com.emt.med.order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderStatuses")
public class OrderStatusController {

    @GetMapping()
    public OrderStatus[] getAllOrderStatuses() {
        return OrderStatus.values();
    }
}

