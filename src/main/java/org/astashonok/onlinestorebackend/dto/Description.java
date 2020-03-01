package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceToRequiredObject;

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
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) throws NullReferenceToRequiredObject {
        if (product == null) {
            throw new NullReferenceToRequiredObject("Description has to belong to a specific product ");
        }

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

    public void setBattery(String battery) throws NullReferenceToRequiredObject {
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
}
