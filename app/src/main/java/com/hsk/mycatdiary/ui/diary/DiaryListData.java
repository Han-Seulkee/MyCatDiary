package com.hsk.mycatdiary.ui.diary;

public class DiaryListData {
    private String day;
    private String title;
    private String date;

    public DiaryListData (String day, String title, String date){
        this.day = day;
        this.title = title;
        this.date = date;
    }

    public String getDay () {return this.day;}

    public String getTitle () {return this.title;}

    public String getDate () {return this.date;}
}
