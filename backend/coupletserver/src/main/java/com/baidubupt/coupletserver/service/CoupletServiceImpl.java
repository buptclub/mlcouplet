package com.baidubupt.coupletserver.service;

import com.baidubupt.coupletserver.entity.Couplet;
import org.springframework.stereotype.Service;

@Service
public class CoupletServiceImpl implements CoupletService {

    @Override
    public Couplet generate(String firstCouplet) {

        try {
            // 模拟正在生成 Python 对联
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }

        Couplet couplet = new Couplet();
        couplet.setFirstCouplet(firstCouplet);
        couplet.setSecondCouplet("幽窗棋罢指犹凉");

        return couplet;
    }

}
