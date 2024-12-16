package pro.orbolato.hotel.enums;

public enum StatusReserva {
    AGENDADO("Agendado"),
    UTILIZANDO("Utilizando"),
    PAGO("Pago");

    private final String displayName;

    StatusReserva(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
