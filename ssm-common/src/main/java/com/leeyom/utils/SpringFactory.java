package com.leeyom.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * spring bean 工厂
 * @author lwang
 *
 */
public class SpringFactory implements BeanFactoryAware {

	private static BeanFactory beanFactory;

	@SuppressWarnings("static-access")
	public void setBeanFactory(BeanFactory factory) throws BeansException {
		this.beanFactory = factory;
	}

	/**
	 * description: 根据beanName名字取得bean
	 * author: leeyom
	 * company: leaderment.com
	 * date: 2017年5月9日 下午3:11:04
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		if (null != beanFactory) {
			return (T) beanFactory.getBean(beanName);
		}
		return null;
	}

}
