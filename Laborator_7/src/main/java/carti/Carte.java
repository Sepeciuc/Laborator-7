package carti;

import java.util.Objects;

public record Carte(String titlu, String autor, int anulAparitiei) {
    @Override
    public String toString() {
        return "Carte{" +
                "titlu='" + titlu + '\'' +
                ", autor='" + autor + '\'' +
                ", anulAparitiei=" + anulAparitiei +
                '}';
    }
}
