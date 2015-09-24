/**
 * 
 */
package com.verify.main.resolvers;

/**
 * @author esimome
 *
 */
public interface Resolver<I, T> {
	T resolve(I i, T t);
}
