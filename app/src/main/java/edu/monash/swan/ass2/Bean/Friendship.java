package edu.monash.swan.ass2.Bean;

import java.util.Date;

/**
 * Created by owliz on 2017/5/10.
 */

public class Friendship {
    private static final long serialVersionUID = 1L;
    private static Integer fsId = 1000;
    protected FriendshipPK friendshipPK;
    private Integer id;
    private String startingDate;
    private String endingDate;
    private Student sid;
    private Student fid;

    public Friendship(FriendshipPK mFriendshipPK, String mStartDate, String mEndDate, Student mStudents, Student mStudents1) {
        id=++fsId;
        friendshipPK = mFriendshipPK;
        startingDate = mStartDate;
        endingDate = mEndDate;
        sid = mStudents;
        fid = mStudents1;
    }

    public Friendship() {
    }
    public Friendship(String myMonashEmail, String friendMonashEmail) {
        this.friendshipPK = new FriendshipPK(myMonashEmail, friendMonashEmail);
        this.id = ++fsId;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FriendshipPK getFriendshipPK() {
        return friendshipPK;
    }

    public void setFriendshipPK(FriendshipPK friendshipPK) {
        this.friendshipPK = friendshipPK;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public Student getSid() {
        return sid;
    }

    public void setSid(Student sid) {
        this.sid = sid;
    }

    public Student getFid() {
        return fid;
    }

    public void setFid(Student fid) {
        this.fid = fid;
    }

}
