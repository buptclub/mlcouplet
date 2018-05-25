package com.baidubupt.coupletserver.service;

import com.baidubupt.coupletserver.entity.SecondCoupletListEntity;

public interface CoupletService {

    SecondCoupletListEntity generate(String firstCouplet) throws Exception;

}
