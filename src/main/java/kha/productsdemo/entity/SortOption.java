package kha.productsdemo.entity;


public enum SortOption {
    PRICE_ASC("price-asc", " Price (Low to High)"),
    PRICE_DESC("price-desc", "Price (High to Low"),
    NAME_ASC("name-asc", "Price (A-Z)"),
    NAME_DESC("name-desc", "Price (Z-A)");

    private final String value;
    private final String displayName;

    SortOption(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public String getValue(){
        return value;
    }

    public String getDisplayName(){
        return displayName;
    }
}
