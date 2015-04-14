package com.example.vishnuchelle.mydairy;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishnu Chelle on 4/14/2015.
 */
public class Group {

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    String groupID;
    String groupName;
    List<Status> statusList = new ArrayList<Status>();

    public Group(String groupID, String groupName) {
        this.groupID = groupID;
        this.groupName = groupName;
    }

    public Group(String groupID, String groupName, List<Status> statusList) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.statusList = statusList;
    }
}
