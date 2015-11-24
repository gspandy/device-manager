package com.yykj.base.util;


import com.yykj.system.services.IBusiBankCompareService;


public class Scheduler
{
    public void download()
    {
        IBusiBankCompareService busiBankCompareService = SpringContextUtil.getBean("busiBankCompareService", IBusiBankCompareService.class);
        busiBankCompareService.download();
    }
}
