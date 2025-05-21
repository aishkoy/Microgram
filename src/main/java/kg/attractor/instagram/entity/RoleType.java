package kg.attractor.instagram.entity;

import lombok.Getter;

@Getter
public enum RoleType {
    USER("USER", 1L);

    private final String name;
    private final Long id;

    RoleType(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public static RoleType getById(Long id) {
        for (RoleType type : values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Нет AccountType с ID " + id);
    }

    @Override
    public String toString() {
        return name;
    }
}
