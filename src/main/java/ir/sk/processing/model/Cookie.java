package ir.sk.processing.model;

import java.util.Objects;

/**
 * Created by sad.kayvanfar on 11/17/2021.
 */
public class Cookie {
    private String name;
    private String timestamp;

    public Cookie(String name, String timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie = (Cookie) o;
        return Objects.equals(name, cookie.name) &&
                Objects.equals(timestamp, cookie.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, timestamp);
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "name='" + name + '\'' +
                ", date=" + timestamp +
                '}';
    }
}
