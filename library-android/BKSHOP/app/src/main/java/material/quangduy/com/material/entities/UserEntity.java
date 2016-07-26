package material.quangduy.com.material.entities;

import java.io.Serializable;

/**
 * Created by Quang Duy on 25-Nov-15.
 */
public class UserEntity implements Serializable{
    String userEmail;
    String password;
    int userId;
    String userFullName;
    int userRole;
    int userSex;
    String userAddress;
    public UserEntity(String userEmail, String password, int userId, String userFullName, int userRole, int userSex, String userPhone, String userAddress) {
        this.userEmail = userEmail;
        this.password = password;
        this.userId = userId;
        this.userFullName = userFullName;
        this.userRole = userRole;
        this.userSex = userSex;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
    }


    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    String userPhone;
    public UserEntity() {
    }

    public UserEntity(String userName, String password, int userId) {
        this.userEmail = userName;
        this.password = password;
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
