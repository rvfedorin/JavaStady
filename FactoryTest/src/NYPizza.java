public class NYPizza extends Pizza {
    public int size;

    public static class Builder extends Pizza.Builder<Builder> {
        int size;

        public Builder(int size) {
            this.size = size;
        }

        @Override
        public NYPizza build() {
            return new NYPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private NYPizza(Builder builder) {
        super(builder);
        this.size = builder.size;
    }
}
