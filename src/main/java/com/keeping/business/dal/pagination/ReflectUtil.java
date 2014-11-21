package com.keeping.business.dal.pagination;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectUtil {
	 public static void setFieldValue(Object target, String fname, Class<?> ftype, Object fvalue) {
		    if ((target == null) || (fname == null) || ("".equals(fname)) || ((fvalue != null) && (!ftype.isAssignableFrom(fvalue.getClass())))) {
		      return;
		    }
		    Class clazz = target.getClass();
		    try {
		      Method method = clazz.getDeclaredMethod("set" + Character.toUpperCase(fname.charAt(0)) + fname.substring(1), new Class[] { ftype });
		      if (!Modifier.isPublic(method.getModifiers())) {
		        method.setAccessible(true);
		      }
		      method.invoke(target, new Object[] { fvalue });
		    }
		    catch (Exception e) {
		      try {
		        Field field = clazz.getDeclaredField(fname);
		        if (!Modifier.isPublic(field.getModifiers())) {
		          field.setAccessible(true);
		        }
		        field.set(target, fvalue);
		      } catch (Exception fe) {
//		        logger.error(fe.getMessage(), fe);
		      }
		    }
		  }

		  public static <T> Class<T> getSuperClassGenericType(Class clazz)
		  {
		    return getSuperClassGenricType(clazz, 0);
		  }

		  public static Class getSuperClassGenricType(Class clazz, int index)
		  {
		    Type genType = clazz.getGenericSuperclass();

		    if (!(genType instanceof ParameterizedType)) {
//		      logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
		      return Object.class;
		    }

		    Type[] params = ((ParameterizedType)genType).getActualTypeArguments();

		    if ((index >= params.length) || (index < 0)) {
//		      logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);

		      return Object.class;
		    }
		    if (!(params[index] instanceof Class)) {
//		      logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");

		      return Object.class;
		    }

		    return (Class)params[index];
		  }
}
