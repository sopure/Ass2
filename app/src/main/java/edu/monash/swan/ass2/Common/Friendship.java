package edu.monash.swan.ass2.Common;

/**
 * Created by owliz on 2017/5/10.
 */

public class Friendship {
    private static final long serialVersionUID = 1L;
    protected FriendshipPK friendshipPK;
    private String startDate;
    private String endDate;
    private Student students;
    private Student students1;

    public Friendship(FriendshipPK mFriendshipPK, String mStartDate, String mEndDate, Student mStudents, Student mStudents1) {
        friendshipPK = mFriendshipPK;
        startDate = mStartDate;
        endDate = mEndDate;
        students = mStudents;
        students1 = mStudents1;
    }

    public Friendship() {
    }
    public Friendship(String myMonashEmail, String friendMonashEmail) {
        this.friendshipPK = new FriendshipPK(myMonashEmail, friendMonashEmail);
    }

    public FriendshipPK getFriendshipPK() {
        return friendshipPK;
    }

    public void setFriendshipPK(FriendshipPK friendshipPK) {
        this.friendshipPK = friendshipPK;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Student getStudents() {
        return students;
    }

    public void setStudents(Student students) {
        this.students = students;
    }

    public Student getStudents1() {
        return students1;
    }

    public void setStudents1(Student students1) {
        this.students1 = students1;
    }

}
