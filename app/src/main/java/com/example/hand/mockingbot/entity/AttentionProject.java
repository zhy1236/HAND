package com.example.hand.mockingbot.entity;

/**
 * Created by zhy on 2017/5/11.
 */

public class AttentionProject {

    private String ProjectName;
    private int ProjectImage;

    public void setProjectImage(int projectImage) {
        ProjectImage = projectImage;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public int getProjectImage() {
        return ProjectImage;
    }

    public String getProjectName() {
        return ProjectName;
    }
}
