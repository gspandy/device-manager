package com.yykj.base.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;
import com.yykj.base.exception.BusinessException;

public abstract class BeanUtil {

	private static final Log log = LogFactory.getLog(BeanUtil.class);

	public BeanUtil() {
	}

	/**
	 * 对象属性复制工具
	 * 
	 * @param to
	 *            目标拷贝对象
	 * @param from
	 *            拷贝源
	 */
	public static void copy(Object to, Object from) {
		try {
			PropertyUtils.copyProperties(to, from);
		} catch (IllegalAccessException e) {
			throw new BusinessException("拷贝时出错，用反射机制时属性或者方法不能被访问。", e);
		} catch (InvocationTargetException e) {
			throw new BusinessException("拷贝时出错，用反射机制调用方法时。", e);
		} catch (NoSuchMethodException e) {
			throw new BusinessException("拷贝时出错，用反射机制调用方法时方法不存在。", e);
		}
	}

	/**
	 * 对象属性复制工具
	 * 
	 * @param to
	 *            目标拷贝对象
	 * @param from
	 *            拷贝源
	 * @param ignore
	 *            需要忽略的属性
	 */
	public static void copy(Object to, Object from, String[] ignore) {
		List list = Arrays.asList(ignore);
		PropertyDescriptor[] descr = PropertyUtils.getPropertyDescriptors(to);
		for (int i = 0; i < descr.length; i++) {
			PropertyDescriptor d = descr[i];
			if (d.getWriteMethod() != null && !list.contains(d.getName()))
				try {
					Object value = PropertyUtils.getProperty(from, d.getName());
					PropertyUtils.setProperty(to, d.getName(), value);
				} catch (Exception e) {
					log.error((new StringBuilder("属性名：")).append(d.getName())
							.append(" 在实体间拷贝时出错").toString(), e);
					throw new BusinessException((new StringBuilder("属性名："))
							.append(d.getName()).append(" 在实体间拷贝时出错")
							.toString(), e);
				}
		}

	}

	/**
	 * <p>
	 * 将数据设定到实体对象bean中
	 * </p>
	 * 
	 * @param bean
	 * @param propertyName
	 * @param value
	 */
	public static void setDataToBean(Object bean, String propertyName,
			Object value) {
		try {
			PropertyUtils.setProperty(bean, propertyName, value);
		} catch (IllegalAccessException e) {
			throw new BusinessException((new StringBuilder("没有权限访问相应属性："))
					.append(propertyName).toString(), e);
		} catch (InvocationTargetException e) {
			throw new BusinessException((new StringBuilder("访问属性："))
					.append(propertyName).append("时出错").toString(), e);
		} catch (NoSuchMethodException e) {
			throw new BusinessException((new StringBuilder("属性："))
					.append(propertyName).append("没有相应的SET方法").toString(), e);
		}
	}

	/**
	 * 按照Map中的key和bean中的属性名一一对应，将Map中数据设定到实体对象bean中
	 * 
	 * @param bean
	 * @param attrsMap
	 */
	public static void setDataToBean(Object bean, Map map) {
		PropertyDescriptor[] descr = PropertyUtils.getPropertyDescriptors(bean);
		for (int i = 0; i < descr.length; i++) {

			PropertyDescriptor d = descr[i];
			/*
			 * 如果当前属性没有对应的可写的方法，则跳过到下一属性 如果Map的key值中无当前属性值，则跳过到下一属性
			 */
			if (d.getWriteMethod() == null || !map.containsKey(d.getName()))
				continue; /* Loop/switch isn't completed */
			Class clazz = d.getPropertyType();
			Object value = map.get(d.getName());
			/*
			 * 如果属性的类型不是java.lang.String且值为空，则不将map中该属性的值设置到实体中，
			 * 防止象Long型的guId，如果为空则值为null setProperty(....)会出错
			 */
			if ((java.lang.String.class == clazz || !"".equals(value))
					&& value != null) {
				try {
					if (java.util.Date.class == clazz
							&& java.lang.String.class == value.getClass())
						value = DateUtil.parse((String) value);
					if (clazz == value.getClass())
						PropertyUtils.setProperty(bean, d.getName(), value);
					else
						PropertyUtils.setProperty(
								bean,
								d.getName(),
								clazz.getConstructor(
										new Class[] { java.lang.String.class })
										.newInstance(new Object[] { value }));
				} catch (Exception e) {
					log.error((new StringBuilder("属性名：")).append(d.getName())
							.append(" 设置到实体中时出错").toString(), e);
					throw new BusinessException((new StringBuilder("属性名："))
							.append(d.getName()).append(" 设置到实体中时出错")
							.toString(), e);
				}
			}
		}
	}

	/**
	 * 根据对象的class名，创建相应的Class对象
	 * 
	 * @param className
	 * @return
	 */
	public static Class createClassByName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new BusinessException((new StringBuilder("实体: "))
					.append(className).append(" 无法加载").toString(), e);
		}
	}

	/**
	 * 根据对象的class名，创建相应的对象
	 * 
	 * @param className
	 * @return
	 */
	public static Object newInstanceByName(String className) {
		try {
			return Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			throw new BusinessException((new StringBuilder("实例化失败：")).append(
					className).toString(), e);
		} catch (IllegalAccessException e) {
			throw new BusinessException((new StringBuilder("没有合适的构造函数，实例化失败："))
					.append(className).toString(), e);
		} catch (ClassNotFoundException e) {
			throw new BusinessException((new StringBuilder("类文件无法找到，实例化失败："))
					.append(className).toString(), e);
		}
	}

	/**
	 * <p>
	 * 通过带参数的构造函数实例化对象
	 * </p>
	 * 
	 * @param className
	 *            String
	 * @param clazzes
	 *            Class[]
	 * @param args
	 *            Object[]
	 * @return Object
	 */
	public static Object newInstanceByName(String className, Class[] clazzes,
			Object[] args) {
		Class clazz = createClassByName(className);
		try {
			return clazz.getConstructor(clazzes).newInstance(args);
		} catch (IllegalArgumentException e) {
			throw new BusinessException((new StringBuilder("非法参数类型，实例化失败："))
					.append(className).toString(), e);
		} catch (SecurityException e) {
			throw new BusinessException((new StringBuilder("安全性限制，实例化失败："))
					.append(className).toString(), e);
		} catch (InstantiationException e) {
			throw new BusinessException((new StringBuilder("实例化失败：")).append(
					className).toString(), e);
		} catch (IllegalAccessException e) {
			throw new BusinessException((new StringBuilder("没有相应的构造函数，实例化失败："))
					.append(className).toString(), e);
		} catch (InvocationTargetException e) {
			throw new BusinessException((new StringBuilder("非法调用，实例化失败："))
					.append(className).toString(), e);
		} catch (NoSuchMethodException e) {
			throw new BusinessException((new StringBuilder("没有相应的构造函数，实例化失败："))
					.append(className).toString(), e);
		}
	}

	/**
	 * <p>
	 * 通过带参数的构造函数实例化对象
	 * </p>
	 * 
	 * @param className
	 *            String
	 * @param clazzes
	 *            Class[]
	 * @param args
	 *            Object[]
	 * @return Object
	 */
	public static Object newInstanceByName(Class clazz, Class[] clazzes,
			Object[] args) {
		try {
			return clazz.getConstructor(clazzes).newInstance(args);
		} catch (IllegalArgumentException e) {
			throw new BusinessException((new StringBuilder("非法参数类型，实例化失败："))
					.append(clazz.getName()).toString(), e);
		} catch (SecurityException e) {
			throw new BusinessException((new StringBuilder("安全性限制，实例化失败："))
					.append(clazz.getName()).toString(), e);
		} catch (InstantiationException e) {
			throw new BusinessException((new StringBuilder("实例化失败：")).append(
					clazz.getName()).toString(), e);
		} catch (IllegalAccessException e) {
			throw new BusinessException((new StringBuilder("没有相应的构造函数，实例化失败："))
					.append(clazz.getName()).toString(), e);
		} catch (InvocationTargetException e) {
			throw new BusinessException((new StringBuilder("非法调用，实例化失败："))
					.append(clazz.getName()).toString(), e);
		} catch (NoSuchMethodException e) {
			throw new BusinessException((new StringBuilder("没有相应的构造函数，实例化失败："))
					.append(clazz.getName()).toString(), e);
		}
	}

	/**
	 * 根据Class对象创建Object对象
	 * 
	 * @param clazz
	 * @return
	 */
	public static Object newInstance(Class clazz) {
		Object o = null;
		try {
			o = clazz.newInstance();
		} catch (InstantiationException e1) {
			throw new BusinessException((new StringBuilder("实例化失败：")).append(
					clazz.getName()).toString());
		} catch (IllegalAccessException e1) {
			throw new BusinessException((new StringBuilder("实例化失败：")).append(
					clazz.getName()).toString());
		}
		return o;
	}

	public static List newInstanceList(Object[] classNames) {
		return newInstanceList(Arrays.asList(classNames));
	}

	public static List newInstanceList(List classNames) {
		List result = new ArrayList();
		for (int i = 0, n = classNames.size(); i < n; i++) {
			String className = (String) classNames.get(i);
			if (PublicUtil.isNotNull(className))
				result.add(newInstanceByName(className));
		}
		return result;
	}

	/**
	 * <p>
	 * 获取实体对象的所有属性
	 * </p>
	 * 
	 * @param bean
	 *            实体对象
	 * @return Map 属性集合
	 */
	public static Map getProperties(Object bean) {
		return getProperties(bean, new HashSet());
	}

	/**
	 * <p>
	 * 获取实体对象的某些属性
	 * </p>
	 * 
	 * @param bean
	 *            实体对象
	 * @param ignores
	 *            Set 忽略的属性名集合
	 * @return Map 属性集合
	 */
	public static Map getProperties(Object bean, Set ignores) {
		Map map = new HashMap();
		PropertyDescriptor[] descr = PropertyUtils.getPropertyDescriptors(bean);
		for (int i = 0; i < descr.length; i++) {
			PropertyDescriptor d = descr[i];
			if (d.getReadMethod() != null) {
				String name = d.getName();
				if (!ignores.contains(name))
					map.put(name, getPropertyValueByName(bean, name));
			}
		}
		return map;
	}

	/**
	 * 获取对象中指定属性的值
	 * 
	 * @param obj
	 * @param name
	 * @return
	 */
	public static Object getPropertyValueByName(Object bean, String propertyName) {
		Object obj;
		try {
			obj = PropertyUtils.getProperty(bean, propertyName);
		} catch (IllegalAccessException e) {
			throw new BusinessException((new StringBuilder("没有权限访问相应属性："))
					.append(propertyName).toString(), e);
		} catch (InvocationTargetException e) {
			throw new BusinessException((new StringBuilder("访问属性："))
					.append(propertyName).append("时出错").toString(), e);
		} catch (NoSuchMethodException e) {
			throw new BusinessException((new StringBuilder("属性："))
					.append(propertyName).append("没有相应的GET方法").toString(), e);
		}
		return obj;
	}

	/**
	 * 判断属性是否在实体中
	 * 
	 * @param bean
	 * @param propertyName
	 * @return
	 */
	public static boolean isPropertyInBean(Object bean, String propertyName) {
		PropertyDescriptor[] descr = PropertyUtils.getPropertyDescriptors(bean);
		for (int i = 0; i < descr.length; i++) {
			PropertyDescriptor d = descr[i];
			if (propertyName.equals(d.getName()))
				return true;
		}

		return false;
	}

	/**
	 * 判断对象是否继承某个接口
	 * 
	 * @param clazz
	 * @param interfaceClazz
	 * @return true/false
	 */
	public static boolean isImplInterface(Class clazz, Class interfaceClazz) {
		return Arrays.asList(clazz.getInterfaces()).contains(interfaceClazz);
	}

	/**
	 * 判定指定的 Object 是否与此 Class 所表示的对象赋值兼容
	 * 
	 * @param clazz
	 * @param obj
	 * @return
	 */
	public static boolean isInstance(Object obj, Class clazz) {
		return clazz.isInstance(obj);
	}

	/**
	 * <p>
	 * 将对象格式化为XML字符串
	 * </p>
	 * 
	 * @param bean
	 *            Object Java对象
	 * @return String XML字符串
	 */
	public static String toXml(Object bean) {
		XStream xs = new XStream();
		return xs.toXML(bean);
	}

	/**
	 * 获取某个实体的全部属性 以map的形式展现 包括嵌套属性 以 id.value的形式体现
	 * 
	 * @param obj
	 *            Object Java对象
	 * @return map
	 */
	public static Map<String, Object> getAllProperties(Object obj, String pName) {
		Map<String, Object> map = new HashMap<String, Object>();
		PropertyDescriptor[] descr = PropertyUtils.getPropertyDescriptors(obj);
		Class clazz = obj.getClass();
		for (PropertyDescriptor d : descr) {
			if (d.getReadMethod() != null && d.getWriteMethod() != null) {
				String keyName = new String();
				if (pName != null && pName.length() > 1)
					keyName = (new StringBuilder(String.valueOf(pName)))
							.append(".").append(d.getName()).toString();
				else
					keyName = d.getName();
				try {
					java.lang.reflect.Field field = clazz.getDeclaredField(d
							.getName());
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}

				/*
				 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~/ Class propertyType =
				 * getPropertyType(clazz, d.getName()); if
				 * (propertyType.equals(Long.class) ||
				 * propertyType.equals(Double.class)) {
				 * 
				 * return null; } /~~~~~~~~~~~~~~~~~~~~~~~~~~~
				 */

				map.put(keyName, getPropertyValueByName(obj, d.getName()));
			}
		}
		return map;
	}

	/**
	 * 获取某个实体的全部属性 以map的形式展现 包括嵌套属性 以 id.value的形式体现
	 * 
	 * @param obj
	 *            Object Java对象
	 * @return map
	 */
	public static Map getPMap(Object obj) {
		Map map = null;
		try {
			map = getAllProperties(obj, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取某个实体的全部属性值 放入list中
	 * 
	 * @param obj
	 *            Object Java对象
	 * @return list
	 */
	public static List getVList(Object obj) {
		List list = new ArrayList();
		Map map = getPMap(obj);
		for (Iterator iter = map.keySet().iterator(); iter.hasNext(); list
				.add(map.get(iter.next())))
			;
		return list;
	}

	/**
	 * 将map转化为对象实体
	 * 
	 * @param map
	 *            数据
	 * @param cls
	 *            对象类名
	 * @return Object
	 */
	public static Object map2Object(Map<String, Object> map, String cls) {
		Object o = null;
		try {
			o = map2Object(map, Class.forName(cls));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * 将map转化为对象实体
	 * 
	 * @param map
	 *            数据
	 * @param cls
	 *            对象类名
	 * @return Object
	 */
	public static Object map2Object(Map<String, Object> map, Class cls) {
		Object obj = newInstance(cls);
		for (String key : map.keySet()) {
			key = key.toLowerCase();
			Object o = map.get(key);
			if (o instanceof JSONObject) {
				JSONObject j = (JSONObject) o;
				if (!j.isNullObject()) {
					Class clz = getPropertyType(obj.getClass(), key);
					Object sub = map2Object(((Map) (j)), clz);
					setProp(obj, key, sub);
				}
			} else {
				setProp(obj, key, map.get(key));
			}
		}
		return obj;
	}

	/**
	 * 获取对象属性的类型 可包含 id.value的嵌套属性
	 * 
	 * @param clazz
	 *            类名
	 * @param pKey
	 *            属性名
	 * @return Class
	 */
	public static Class getPropertyType(Class clazz, String pKey) {
		int i = pKey.indexOf(".");
		Object obj = newInstance(clazz);
		try {
			if (i <= 0) {
				return PropertyUtils.getPropertyType(newInstance(clazz), pKey);
			} else {
				return PropertyUtils.getPropertyType(obj, pKey.substring(0, i));
			}
		} catch (IllegalAccessException e) {
			return null;
		} catch (InvocationTargetException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 * 设置对象属性
	 * 
	 * @param obj
	 *            对象
	 * @param key
	 *            属性名
	 * @param value
	 *            属性值
	 * @return Class
	 */
	public static void setProp(Object obj, String key, Object value) {
		if (value == null)
			return;
		Class clazz;
		if ("null".equals(value))
			value = null;
		int i = key.indexOf(".");
		if (i > 0) {
			Class _class;
			try {
				_class = PropertyUtils
						.getPropertyType(obj, key.substring(0, i));
				Object subObj = PropertyUtils.getProperty(obj,
						key.substring(0, i));
				if (subObj == null)
					subObj = newInstance(_class);
				setProp(subObj, key.substring(i + 1, key.length()), value);
				PropertyUtils.setProperty(obj, key.substring(0, i), subObj);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		clazz = getPropertyType(obj.getClass(), key);
		if (clazz == null
				|| net.sf.json.JSONNull.class.equals(value.getClass()))
			return;
		try {
			if ("".equals(value)) {
				if (clazz.equals(java.lang.String.class))
					PropertyUtils.setNestedProperty(obj, key, value);
			} else if (clazz.equals(java.lang.Long.class)
					&& isInstance(value, java.lang.Number.class))
				PropertyUtils.setProperty(obj, key,
						new Long(String.valueOf(value)));
			else if (clazz.equals(java.lang.Long.class)
					&& isInstance(value, java.lang.String.class))
				PropertyUtils.setProperty(obj, key,
						new Long(String.valueOf(value)));
			else if (clazz.equals(java.util.Date.class) && value != null)
				PropertyUtils.setProperty(obj, key,
						DateUtil.parse(String.valueOf(value)));
			else if (clazz.equals(java.lang.Double.class) && value != null)
				PropertyUtils.setProperty(obj, key,
						new Double(String.valueOf(value)));
			else if (clazz.equals(java.util.List.class) && value != null)
				PropertyUtils.setProperty(obj, key,
						JsonParser.json2List(String.valueOf(value)));
			else
				PropertyUtils.setNestedProperty(obj, key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map compareEnt(Object from, Object to) {
		Map map = new HashMap();
		if (!from.getClass().equals(to.getClass()))
			return null;
		try {
			Map fMap = getPMap(from);
			Map tMap = getPMap(to);
			for (Iterator iter = fMap.keySet().iterator(); iter.hasNext();) {
				Object key = iter.next();
				if (!objectEq(fMap.get(key), tMap.get(key)))
					map.put(key, tMap.get(key));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	private static boolean objectEq(Object from, Object to) {
		if (from == null && to == null)
			return true;
		return from != null && to != null && from.equals(to);
	}

	/**
	 * Bean方法的调用
	 * 
	 * @param bean
	 *            对象
	 * @param method
	 *            方法名
	 * @param para
	 *            参数
	 * @return Object
	 */
	public static Object invoke(Object bean, String method, Object para) {
		Object obj = null;
		try {
			obj = MethodUtils.invokeMethod(bean, method, new Object[] { para });
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static void main(String args1[]) {
	}

}
