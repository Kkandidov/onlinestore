package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;

import java.util.Objects;

public class Description extends Entity {
    private Product product;
    private String screen;
    private String color;
    private String processor;
    private String frontCamera;
    private String rearCamera;
    private String capacity;
    private String battery;
    private String displayTechnology;
    private String graphics;
    private String wirelessCommunication;

    public Description() {
    }

    public Description(Product product, String screen, String color, String processor, String frontCamera,
                       String rearCamera, String capacity, String battery, String displayTechnology, String graphics,
                       String wirelessCommunication) {
        this.product = product;
        this.screen = screen;
        this.color = color;
        this.processor = processor;
        this.frontCamera = frontCamera;
        this.rearCamera = rearCamera;
        this.capacity = capacity;
        this.battery = battery;
        this.displayTechnology = displayTechnology;
        this.graphics = graphics;
        this.wirelessCommunication = wirelessCommunication;
        super.id = product.getId();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) throws NullReferenceException {
        if (product == null) {
            throw new NullReferenceException("Description has to belong to a specific product! ");
        }
        super.id = product.getId();
        this.product = product;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getFrontCamera() {
        return frontCamera;
    }

    public void setFrontCamera(String frontCamera) {
        this.frontCamera = frontCamera;
    }

    public String getRearCamera() {
        return rearCamera;
    }

    public void setRearCamera(String rearCamera) {
        this.rearCamera = rearCamera;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getDisplayTechnology() {
        return displayTechnology;
    }

    public void setDisplayTechnology(String displayTechnology) {
        this.displayTechnology = displayTechnology;
    }

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    public String getWirelessCommunication() {
        return wirelessCommunication;
    }

    public void setWirelessCommunication(String wirelessCommunication) {
        this.wirelessCommunication = wirelessCommunication;
    }

    @Override
    public String toString() {
        return "Description{" +
                "product=" + product +
                ", screen='" + screen + '\'' +
                ", color='" + color + '\'' +
                ", processor='" + processor + '\'' +
                ", frontCamera='" + frontCamera + '\'' +
                ", rearCamera='" + rearCamera + '\'' +
                ", capacity='" + capacity + '\'' +
                ", battery='" + battery + '\'' +
                ", displayTechnology='" + displayTechnology + '\'' +
                ", graphics='" + graphics + '\'' +
                ", wirelessCommunication='" + wirelessCommunication + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description that = (Description) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(screen, that.screen) &&
                Objects.equals(color, that.color) &&
                Objects.equals(processor, that.processor) &&
                Objects.equals(frontCamera, that.frontCamera) &&
                Objects.equals(rearCamera, that.rearCamera) &&
                Objects.equals(capacity, that.capacity) &&
                Objects.equals(battery, that.battery) &&
                Objects.equals(displayTechnology, that.displayTechnology) &&
                Objects.equals(graphics, that.graphics) &&
                Objects.equals(wirelessCommunication, that.wirelessCommunication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, screen, color, processor, frontCamera, rearCamera, capacity, battery, displayTechnology, graphics, wirelessCommunication);
    }
}
