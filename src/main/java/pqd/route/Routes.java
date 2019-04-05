package pqd.route;

public enum Routes {
    PREPROCESS("direct:preprocess"),
    RESEQUENCER("direct:resequencer"),
    PERSIST_SERVICE("direct:persistService");

    private final String route;

    Routes(String  route) {
        this.route = route;
    }

    public String route() {
        return route;
    }
}
