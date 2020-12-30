package com.skyman.weighttrainingpro.management.project.database;


public class ProjectDbManager {

    // instance variable
    private ProjectDbConnector projectDbConnector;

    // constructor
    public ProjectDbManager(ProjectDbConnector projectDbConnector) {
        this.projectDbConnector = projectDbConnector;
    }

    /**
     * [method] [getter] this.projectDbConnector
     */
    public ProjectDbConnector getProjectDbConnector() {
        return this.projectDbConnector;
    }
}
