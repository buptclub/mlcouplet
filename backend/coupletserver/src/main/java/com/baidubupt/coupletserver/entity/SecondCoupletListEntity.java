package com.baidubupt.coupletserver.entity;

import java.util.List;

/**
 * 下联列表
 */
public class SecondCoupletListEntity {

    private String firstCouplet;

    private List<SecondCouplet> secondCoupletList;

    public String getFirstCouplet() {
        return firstCouplet;
    }

    public void setFirstCouplet(String firstCouplet) {
        this.firstCouplet = firstCouplet;
    }

    public List<SecondCouplet> getSecondCoupletList() {
        return secondCoupletList;
    }

    public void setSecondCoupletList(List<SecondCouplet> secondCoupletList) {
        this.secondCoupletList = secondCoupletList;
    }
}
