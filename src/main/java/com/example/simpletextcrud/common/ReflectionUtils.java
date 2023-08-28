package com.example.simpletextcrud.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <code>ReflectionUtils</code> - Reflection utilities holder.
 *
 */
public final class ReflectionUtils {

  /**
   * Prevent any instantiation.
   */
  private ReflectionUtils() {
    throw new UnsupportedOperationException("The " + getClass() + " is not instantiable!");
  }

  /**
   * Get the {@link Method} with the given signature (name and parameter types) belonging to
   * the provided Java {@link Class}.
   *
   * @param targetClass    target {@link Class}
   * @param methodName     method name
   * @param parameterTypes method parameter types
   * @return the {@link Method} matching the provided signature
   */
  @SuppressWarnings("unchecked")
  public static Method getMethod(Class targetClass, String methodName, Class... parameterTypes) {
    try {
      return targetClass.getDeclaredMethod(methodName, parameterTypes);
    } catch (NoSuchMethodException e) {
      try {
        return targetClass.getMethod(methodName, parameterTypes);
      } catch (NoSuchMethodException ignore) {
      }

      if (!targetClass.getSuperclass().equals(Object.class)) {
        return getMethod(targetClass.getSuperclass(), methodName, parameterTypes);
      } else {
        throw handleException(e);
      }
    }
  }

  /**
   * Get the {@link Method} with the given signature (name and parameter types) belonging to
   * the provided Java {@link Object} or {@code null} if no {@link Method} was found.
   *
   * @param targetClass    target {@link Class}
   * @param methodName     method name
   * @param parameterTypes method parameter types
   * @return return {@link Method} matching the provided signature or {@code null}
   */
  public static Method getMethodOrNull(Class targetClass, String methodName, Class... parameterTypes) {
    try {
      return getMethod(targetClass, methodName, parameterTypes);
    } catch (RuntimeException e) {
      return null;
    }
  }

  /**
   * Invoke the {@code static} {@link Method} with the provided parameters.
   *
   * @param method     target {@code static} {@link Method} to invoke
   * @param parameters parameters passed to the method call
   * @param <T>        return value object type
   * @return the value return by the method invocation
   */
  public static <T> T invokeStaticMethod(Method method, Object... parameters) {
    try {
      method.setAccessible(true);
      @SuppressWarnings("unchecked")
      T returnValue = (T) method.invoke(null, parameters);
      return returnValue;
    } catch (InvocationTargetException e) {
      throw handleException(e);
    } catch (IllegalAccessException e) {
      throw handleException(e);
    }
  }

  /**
   * Handle the {@link NoSuchMethodException} by rethrowing it as an {@link IllegalArgumentException}.
   *
   * @param e the original {@link NoSuchMethodException}
   * @return the {@link IllegalArgumentException} wrapping exception
   */
  private static IllegalArgumentException handleException(NoSuchMethodException e) {
    return new IllegalArgumentException(e);
  }

  /**
   * Handle the {@link IllegalAccessException} by rethrowing it as an {@link IllegalArgumentException}.
   *
   * @param e the original {@link IllegalAccessException}
   * @return the {@link IllegalArgumentException} wrapping exception
   */
  private static IllegalArgumentException handleException(IllegalAccessException e) {
    return new IllegalArgumentException(e);
  }

  /**
   * Handle the {@link InvocationTargetException} by rethrowing it as an {@link IllegalArgumentException}.
   *
   * @param e the original {@link InvocationTargetException}
   * @return the {@link IllegalArgumentException} wrapping exception
   */
  private static IllegalArgumentException handleException(InvocationTargetException e) {
    return new IllegalArgumentException(e);
  }
}

