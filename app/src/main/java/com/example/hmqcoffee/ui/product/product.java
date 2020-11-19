package com.example.hmqcoffee.ui.product;


public class product {

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLinkimg() {
        return linkimg;
    }

    public void setLinkimg(String linkimg) {
        this.linkimg = linkimg;
    }

    public String image;
    public String linkimg;
    public String name;
    public String price;

    public product() {

    }

    public product(String image, String linkimg, String name, String price) {
        this.image = image;
        this.linkimg = linkimg;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
