package coffeelog.util;

public class CBOption {
    private String key;
    private String value;

    public CBOption(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value; // Muestra el texto visible en el JComboBox
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CBOption option = (CBOption) obj;
        return key.equals(option.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}