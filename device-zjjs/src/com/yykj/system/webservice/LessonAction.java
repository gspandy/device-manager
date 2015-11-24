package com.yykj.system.webservice;

import org.springframework.stereotype.Service;

/**
 * @author QinShuJin
 * 2015��10��9�� ����1:51:18
 */
public class LessonAction {
	public int add( int a, int b){
        return a+b;
     }
	
	public String returnXml(String name){
		String xml="<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?> "+
		"	<Root>                                                       "+
		"		<Head>                                                   "+
		"			<TranCode>2015001</TranCode>                         "+
		"			<ChannelDate>2015-10-01 13:21:59</ChannelDate>       "+
		"		</Head>                                                  "+
		"		<Data>                                                   "+
		"			<DevNum>100001</DevNum>                              "+
		"			<SamNum>200002</SamNum>                              "+
		"			<SerialNum>at0001</SerialNum>                        "+
		"		</Data>                                                  "+
		"	</Root>                                                      "+name;
		return xml;
	}
}
