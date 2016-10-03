package com.ysu.zyw.tc.web.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/order")
public class TcOrderController {

    @RequestMapping(value = "/p/orders")
    public ModelAndView go2orders() {
        return new ModelAndView("/WEB-INF/templates/order/orders.ftl");
    }

    @RequestMapping(value = "/p/order")
    public ModelAndView go2order() {
        return new ModelAndView("/WEB-INF/templates/order/order.ftl");
    }

}
