package com.github.longdt.netz.socket.pool;

import java.util.function.Supplier;

public class SimplePool<T> implements Pool<T> {
    private Object[] objects;
    private int size;
    private final Supplier<T> objectSupplier;

    public SimplePool(Supplier<T> objectSupplier) {
        this(16, objectSupplier);
    }

    public SimplePool(int initCapacity, Supplier<T> objectSupplier) {
        objects = new Object[initCapacity];
        this.objectSupplier = objectSupplier;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get() {
        return size == 0 ? objectSupplier.get() : (T) objects[--size];
    }

    @Override
    public void release(T obj) {
        assert obj != null;
        if (size >= objects.length) {
            var newObjects = new Object[objects.length << 2];
            System.arraycopy(objects, 0, newObjects, 0, objects.length);
            objects = newObjects;
        }
        objects[size++] = obj;
    }

    @Override
    public int size() {
        return size;
    }
}
