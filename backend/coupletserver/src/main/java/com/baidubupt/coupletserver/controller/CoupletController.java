package com.baidubupt.coupletserver.controller;

import com.baidubupt.coupletserver.entity.Couplet;
import com.baidubupt.coupletserver.service.CoupletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
public class CoupletController {

    @Autowired
    CoupletService coupletService;

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHomePage(Model model) {
        return "homepage";
    }

    /**
     * 用户输入
     * @param firstCouplet 比如:"宝鼎茶闲烟尚绿"
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/generate/{firstCouplet}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> generate(@PathVariable("firstCouplet") String firstCouplet) {
        Couplet couplet = null;
        Exception e = null;

        try {
            couplet = coupletService.generate(firstCouplet);
        } catch (Exception ex) {
            e = ex;
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("firstCouplet", firstCouplet);
        responseBody.put("secondCouplet", couplet.getSecondCouplet());
        responseBody.put("exception", e);
        responseBody.put("status", getStatusCode(couplet, e));

        return responseBody;
    }

    static int getStatusCode(Couplet couplet, Exception exception) {
        if (exception != null) {
            return 1;
        }

        if (couplet == null) {
            return 2;
        }

        if (couplet.getSecondCouplet() != null && couplet.getFirstCouplet() != null) { /** success **/
            return 0;
        }

        return 3; /** unknown **/
    }

}
