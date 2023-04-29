package io.foldright.inspectablewrappers.utils;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.foldright.inspectablewrappers.Attachable;
import io.foldright.inspectablewrappers.Wrapper;
import io.foldright.inspectablewrappers.WrapperAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;


/**
 * Utility class for creating {@link WrapperAdapter} instances
 * without writing boilerplate code of creating a new adapter class.
 *
 * @author Jerry Lee (oldratlee at gmail dot com)
 */
public final class WrapperAdapterUtils {
    /**
     * Creates a {@link WrapperAdapter} instance of the given biz interface type
     * by the adapted/existed wrapper instance and the underlying instance that be wrapped.
     *
     * @param <T>          the type of instances that be wrapped
     * @param bizInterface the class of instances that be wrapped
     * @param underlying   the underlying instance that be wrapped, more info see {@link Wrapper#unwrap()}
     * @param adaptee      the adapted/existed wrapper instance, more info see {@link WrapperAdapter#adaptee()}
     * @return the new {@link WrapperAdapter} instance
     * @see Wrapper#unwrap()
     * @see WrapperAdapter#adaptee()
     */
    @NonNull
    public static <T> T createWrapperAdapter(Class<T> bizInterface, T underlying, T adaptee) {
        return createWrapperAdapter0(
                requireNonNull(bizInterface, "bizInterface is null"),
                requireNonNull(underlying, "underlying is null"),
                requireNonNull(adaptee, "adaptee is null"),
                null);
    }

    /**
     * Creates a {@link WrapperAdapter} instance of the given biz interface type with attachable interface
     * by the adapted/existed wrapper instance, the underlying instance that be wrapped and an attachable instance.
     *
     * @param <T>          the type of instances that be wrapped
     * @param bizInterface the class of instances that be wrapped
     * @param underlying   the underlying instance that be wrapped, more info see {@link Wrapper#unwrap()}
     * @param adaptee      the adapted/existed wrapper instance, more info see {@link WrapperAdapter#adaptee()}
     * @param attachable   the attachable instance, more info see {@link Attachable}
     * @return the new {@link WrapperAdapter} instance
     * @see Wrapper#unwrap()
     * @see WrapperAdapter#adaptee()
     * @see Attachable#getAttachment(Object)
     * @see Attachable#setAttachment(Object, Object)
     */
    @NonNull
    public static <T> T createWrapperAdapter(Class<T> bizInterface, T underlying, T adaptee, Attachable<?, ?> attachable) {
        return createWrapperAdapter0(
                requireNonNull(bizInterface, "bizInterface is null"),
                requireNonNull(underlying, "underlying is null"),
                requireNonNull(adaptee, "adaptee is null"),
                requireNonNull(attachable, "attachable is null"));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T> T createWrapperAdapter0(Class<T> bizInterface, T underlying, T adaptee, @Nullable Attachable<?, ?> attachable) {
        final InvocationHandler handler = (proxy, method, args) -> {
            if (isMethodUnwrap(method)) return underlying;
            if (isMethodAdaptee(method)) return adaptee;

            if (attachable != null && isMethodGetAttachment(method))
                return ((Attachable) attachable).getAttachment(args[0]);
            if (attachable != null && isMethodSetAttachment(method)) {
                ((Attachable) attachable).setAttachment(args[0], args[1]);
                return null;
            }

            return method.invoke(adaptee, args);
        };

        return (T) Proxy.newProxyInstance(
                adaptee.getClass().getClassLoader(),
                attachable == null
                        ? new Class[]{bizInterface, WrapperAdapter.class}
                        : new Class[]{bizInterface, WrapperAdapter.class, Attachable.class},
                handler);
    }

    private static boolean isMethodAdaptee(Method method) {
        return checkMethod(method, "adaptee");
    }

    private static boolean isMethodUnwrap(Method method) {
        return checkMethod(method, "unwrap");
    }

    private static boolean isMethodGetAttachment(Method method) {
        return checkMethod(method, "getAttachment", Object.class);
    }

    private static boolean isMethodSetAttachment(Method method) {
        return checkMethod(method, "setAttachment", Object.class, Object.class);
    }

    private static boolean checkMethod(Method method, String methodName, Class<?>... parameterTypes) {
        return method.getName().equals(methodName)
                && method.getParameterCount() == parameterTypes.length
                && Arrays.equals(method.getParameterTypes(), parameterTypes);
    }

    /**
     * NO need to create instance at all
     */
    private WrapperAdapterUtils() {
    }
}
