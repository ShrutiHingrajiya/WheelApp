package test.practical.com.androidprojectsstructuredemo.Bean;

public class BeanCatagory {

    private String id;
    private String name;
    private String url;
    private String discount;
    private String toDisplay;

    public BeanCatagory() {
    }

    public BeanCatagory(String id, String name, String url, String discount, String toDisplay) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.discount = discount;
        this.toDisplay = toDisplay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getToDisplay() {
        return toDisplay;
    }

    public void setToDisplay(String toDisplay) {
        this.toDisplay = toDisplay;
    }
}
