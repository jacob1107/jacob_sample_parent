package org.jacob.spring.log.mdc.thread;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class MDCTool {

	public static <T> MDCSupplier<T> supplier(Supplier<T> supplier) {
		return new MDCSupplier<>(supplier);
	}

	public static MDCRunnable runnable(Runnable runnable) {
		return new MDCRunnable(runnable);
	}

	public static MDCRunnable runnable(final Consumer<Void> consumer) {
		return new MDCRunnable(() -> {
			consumer.accept(null);
		});
	}

	public static <V> MDCCallable<V> callable(Callable<V> callable) {
		return new MDCCallable<>(callable);
	}

	public static <V> MDCCallable<V> callable(Supplier<V> supplier) {
		return new MDCCallable<>(() -> supplier.get());
	}

}