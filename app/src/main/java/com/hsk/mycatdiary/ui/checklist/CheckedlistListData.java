package com.hsk.mycatdiary.ui.checklist;

public class CheckedlistListData {
    private String todo;
    private String idx;

    public CheckedlistListData (String todo){
        this.todo = todo;
    }

    public CheckedlistListData (String todo, String idx){
        this.todo = todo;
        this.idx = idx;
    }

    public String getTodo () {  return this.todo;}

    public String getIdx () {   return this.idx; }

}
