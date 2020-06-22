package org.jacob.spring.log.mdc.thread;

import java.util.Map;
import java.util.function.Supplier;

import org.slf4j.MDC;

public class MDCSupplier<T> implements Supplier<T> {

	private transient final Map<String, String> _cm = MDC.getCopyOfContextMap();

	private final Supplier<T> supplier;

	public MDCSupplier(Supplier<T> supplier) {
		this.supplier = supplier;
	}

	@Override
	public T get() {
		if (_cm != null) {
			MDC.setContextMap(_cm);
		}
		try {
			return supplier.get();
		} finally {
			MDC.clear();
		}
	}

}