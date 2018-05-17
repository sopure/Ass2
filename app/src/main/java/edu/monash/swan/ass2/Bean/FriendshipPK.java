package edu.monash.swan.ass2.Bean;

public class FriendshipPK {

    private String myMonashEmail;
    private String friendMonashEmail;

    public FriendshipPK() {
    }

    public FriendshipPK(String mMyMonashEmail, String mFriendMonashEmail) {
        this.myMonashEmail = mMyMonashEmail;
        this.friendMonashEmail = mFriendMonashEmail;
    }

    public String getMyMonashEmail() {
        return myMonashEmail;
    }

    public void setMyMonashEmail(String mMyMonashEmail) {
        this.myMonashEmail = mMyMonashEmail;
    }

    public String getFriendMonashEmail() {
        return friendMonashEmail;
    }

    public void setFriendMonashEmail(String mFriendMonashEmail) {
        this.friendMonashEmail = mFriendMonashEmail;
    }
}
