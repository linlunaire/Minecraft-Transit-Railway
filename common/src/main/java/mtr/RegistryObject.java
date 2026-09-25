package mtr;

import mtr.mappings.RegistrationContext;
import net.minecraft.resources.Identifier;

import java.util.Objects;
import java.util.function.Supplier;

public class RegistryObject<T> {

	private T object;
	private final Supplier<T> supplier;
	private final Identifier id;

	public RegistryObject(Supplier<T> supplier) {
		this.supplier = Objects.requireNonNull(supplier);
		id = null;
	}

	public RegistryObject(String path, Supplier<T> supplier) {
		this.supplier = Objects.requireNonNull(supplier);
		id = Identifier.fromNamespaceAndPath("mtr", path);
	}

	public T get() {
		if (object == null) {
			object = Objects.requireNonNull(id == null ? supplier.get() : RegistrationContext.construct(id, supplier));
		}
		return object;
	}
}
