package com.tvarkarastis.jbgschedule;

/**
 * Created by Titas on 8/26/2014.
 */
public class StudentBean {

    String key_file_name, key_user_name;

    public StudentBean(String file_name, String user_name) {
        key_file_name = file_name;
        key_user_name = user_name;

    }

    public String getUserName() {
        return key_user_name;
    }

    public String getFileName() {
        return key_file_name;
    }

}
