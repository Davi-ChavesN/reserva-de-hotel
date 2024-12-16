package pro.orbolato.hotel.enums;

public enum TipoUser {
    ADMIN("Administrador"),
    USER("Comum");

    private final String displayName;

    TipoUser(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
