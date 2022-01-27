package de.mydomain.drools;

public class Measurement {

    private final String id;
    private final String val;

    public Measurement(String id, String val) {
        this.id = id;
        this.val = val;
    }

    public String getId() {
        return id;
    }

    public String getVal() {
        return val;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Measurement [");

        if (id != null) {
            builder.append("id = ").append(id).append(", ");
        }
        if (val != null) {
            builder.append("val = ").append(val);
        }

        builder.append("]");

        return builder.toString();
    }
}
