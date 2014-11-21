package com.tvarkarastis.jbgschedule;


import java.io.Serializable;


public class LessonBean implements Serializable {
    String lessonName, classNumber;

    public LessonBean() {
    }

    public LessonBean(String lesson, String number) {
        lessonName = lesson;
        classNumber = number;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

}
