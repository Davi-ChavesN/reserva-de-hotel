package pro.orbolato.hotel.enums;

public enum Status {
    LIVRE("Livre"),
    OCUPADO("Ocupado");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
