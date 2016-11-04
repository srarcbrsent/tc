package com.ysu.zyw.tc.web.item;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/item")
public class TcItemController {

    @RequestMapping(value = "/p/items")
    public ModelAndView go2items() {
        return new ModelAndView("/WEB-INF/templates/items/items.ftl");
    }

    @RequestMapping(value = "/p/item")
    public ModelAndView go2item() {
        return new ModelAndView("/WEB-INF/templates/items/items.ftl");
    }

}
