package material.quangduy.com.material.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductEntity implements Serializable {
    private int mProductID;

    private String mProductName;
    private float rate;
    private Boolean mProductStock;
    private int soluong = 0;
    private String mProductSymbol;
    private int image;
    private float mProductPrice;
    private String mProductDesciption;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private String linkImage;
    private ArrayList<String> list_image;

    public ArrayList<String> getList_image() {
        return list_image;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public void setList_image(ArrayList<String> list_image) {
        this.list_image = list_image;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    private ArrayList<ProductEntity> list_productRelated;

    public int getmProductID() {
        return mProductID;
    }

    public void setmProductID(int mProductID) {
        this.mProductID = mProductID;
    }

    public String getmProductName() {
        return mProductName;
    }

    public void setmProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public Boolean getmProductStock() {
        return mProductStock;
    }

    public void setmProductStock(Boolean mProductStock) {
        this.mProductStock = mProductStock;
    }

    public String getmProductSymboy() {
        return mProductSymbol;
    }

    public void setmProductSymboy(String mProductSymboy) {
        this.mProductSymbol = mProductSymboy;
    }

    public float getmProductPrice() {
        return mProductPrice;
    }

    public void setmProductPrice(float mProductPrice) {
        this.mProductPrice = mProductPrice;
    }

    public String getmProductDesciption() {
        return mProductDesciption;
    }

    public void setmProductDesciption(String mProductDesciption) {
        this.mProductDesciption = mProductDesciption;
    }

    public ArrayList<ProductEntity> getList_productRelated() {
        return list_productRelated;
    }

    public void setList_productRelated(
            ArrayList<ProductEntity> list_productRelated) {
        this.list_productRelated = list_productRelated;
    }

    public ProductEntity() {
        super();
    }


    public ProductEntity(int mProductID, String mProductName,
                         Boolean mProductStock, float rate, float mProductPrice,
                         int soluong, String linkImage) {
        super();
        this.mProductID = mProductID;
        this.mProductName = mProductName;
        this.rate = rate;
        this.mProductStock = mProductStock;
        this.soluong = soluong;
        this.mProductPrice = mProductPrice;
        this.linkImage = linkImage;
    }

    public ProductEntity(int mProductID, String mProductName, int image, float mProductPrice) {
        this.mProductID = mProductID;
        this.mProductName = mProductName;
        this.image = image;
        this.mProductPrice = mProductPrice;
    }
    public ProductEntity(int mProductID, String mProductName, String linkImage, float mProductPrice,int soluong) {
        this.mProductID = mProductID;
        this.mProductName = mProductName;
        this.linkImage = linkImage;
        this.mProductPrice = mProductPrice;
        this.soluong = soluong;
    }

}
