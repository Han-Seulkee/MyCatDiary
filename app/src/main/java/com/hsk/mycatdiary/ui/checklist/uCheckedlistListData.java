package com.hsk.mycatdiary.ui.checklist;

public class uCheckedlistListData {
    private String todo;
    private String idx;

    public uCheckedlistListData(String todo) {
        this.todo = todo;
    }

    public uCheckedlistListData(String todo, String idx) {
        this.todo = todo;
        this.idx = idx;
    }

    public String getTodo() {
        return this.todo;
    }

    public String getIdx() {
        return this.idx;
    }

}
