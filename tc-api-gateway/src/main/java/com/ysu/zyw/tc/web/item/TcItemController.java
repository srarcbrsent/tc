package com.ysu.zyw.tc.web.item;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/item")
public class TcItemController {

    @RequestMapping(value = "/go2items")
    public ModelAndView go2items() {
        return new ModelAndView("/WEB-INF/templates/item/item_list.ftl");
    }

    @RequestMapping(value = "/go2item")
    public ModelAndView go2item() {
        return new ModelAndView("/WEB-INF/templates/item/item_detail.ftl");
    }

}
