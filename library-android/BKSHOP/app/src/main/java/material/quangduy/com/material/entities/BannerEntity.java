package material.quangduy.com.material.entities;

/**
 * Created by Quang Duy on 22-Oct-15.
 */
public class BannerEntity {
   private int image_banner;
    private String banner_link;

    public int getImage_banner() {
        return image_banner;
    }

    public void setImage_banner(int image_banner) {
        this.image_banner = image_banner;
    }

    public BannerEntity(int image_banner) {
        this.image_banner = image_banner;
    }

    public String getBanner_link() {
        return banner_link;
    }

    public void setBanner_link(String banner_link) {
        this.banner_link = banner_link;
    }

    public BannerEntity(int image_banner, String banner_link) {
        this.image_banner = image_banner;
        this.banner_link = banner_link;
    }
}
