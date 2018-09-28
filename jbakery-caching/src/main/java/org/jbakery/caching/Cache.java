package org.jbakery.caching;

public interface Cache<T>
{
	void set(T item);
	T tryGet();
}
