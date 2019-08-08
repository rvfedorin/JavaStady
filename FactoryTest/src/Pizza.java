import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;


public abstract class Pizza {

    public final Set<Topping> addons;

    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> addons = EnumSet.noneOf(Topping.class);

        public T addAddons(Topping addTopping) {
            addons.add(Objects.requireNonNull(addTopping, "Topping is NULL"));
            return self();
        }

        abstract Pizza build();

        protected abstract T self();
    } // class Builder

    Pizza(Builder<?> builder) {
        addons = builder.addons.clone();
    }
}
