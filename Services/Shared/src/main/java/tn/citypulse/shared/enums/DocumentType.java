package tn.citypulse.shared.enums;

public enum DocumentType {
    BIRTH_CERTIFICATE(100L),
    FULL_BIRTH_CERTIFICATE(100L),
    MARRIAGE_CERTIFICATE(200L),
    DEATH_CERTIFICATE(100L),
    FAMILY_BOOK_EXTRACT(100L);
    private final Long price;
    DocumentType(Long price) {
        this.price = price;
    }
    Long getPrice() {
        return this.price;
    }
}
