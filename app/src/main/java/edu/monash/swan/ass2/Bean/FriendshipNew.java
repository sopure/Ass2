package edu.monash.swan.ass2.Bean;

import org.json.JSONObject;

public class FriendshipNew {


    private static final long serialVersionUID = 1L;
    private static Integer fsId = 1000;
    //  protected FriendshipPK friendshipPK;
    private Integer id;
    private String startingDate;
    private String endingDate;
    private Integer sid;
    private Integer fid;

    public FriendshipNew( String mStartDate, String mEndDate, Integer mStudents, Integer mStudents1) {
        id=++fsId;;
        startingDate = mStartDate;
        endingDate = mEndDate;
        sid = mStudents;
        fid = mStudents1;
    }
    public JSONObject convert(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.id);
            jsonObject.put("fid", this.fid);
            jsonObject.put("sid", this.sid);
            jsonObject.put("startingDate", this.startingDate);
            jsonObject.put("endingDate", this.endingDate);

        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

}
