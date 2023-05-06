package com.github.longdt.netz.socket.pool;

/**
 * Created by naruto on 6/21/17.
 */
public interface Pool<T> {
  T get();

  void release(T obj);

  int size();
}
