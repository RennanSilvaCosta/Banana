package model;

public class LaunchType {

    private Integer id;
    private String type;

    public LaunchType() {}

    public LaunchType(Integer id, String name) {
        this.id = id;
        this.type = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

