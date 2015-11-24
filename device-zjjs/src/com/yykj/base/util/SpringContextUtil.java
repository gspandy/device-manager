package com.yykj.base.util;

import java.lang.reflect.Field;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.util.ReflectionUtils;


/**
 * Spring工具类
 * @author QinShuJin
 * 2015年7月10日 上午10:02:39
 */
public class SpringContextUtil implements BeanFactoryAware {
	private static BeanFactory beanFactory;

	public void setBeanFactory(final BeanFactory beanFactory) {
		Field field = ReflectionUtils.findField(SpringContextUtil.class, "beanFactory", BeanFactory.class);
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, this, beanFactory);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(final String beanName, final Class<T> requiredType) {
		return (T) SpringContextUtil.beanFactory.getBean(beanName);
	}
}
