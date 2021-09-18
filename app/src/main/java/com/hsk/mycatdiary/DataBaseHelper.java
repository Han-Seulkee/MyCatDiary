package com.hsk.mycatdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "myDb.db";
    SQLiteDatabase db;

    String[] todo = {"아침밥", "오전간식", "점심밥", "물 갈기", "화장실!", "오후간식", "저녁밥", "화장실!!"};
    int[] state = {0, 0, 0, 0, 0, 0, 0, 0};
    String[] time = {"1st", "2nd", "3rd", "3rd", "3rd", "4th", "5th", "5th"};

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    //테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBases.createCat._CREATECAT);
        db.execSQL(DataBases.createDiary._CREATEDIARY);
        db.execSQL(DataBases.createTodo._CREATETODO);
        db.execSQL(DataBases.createBucket._CREATEBUCKET);

        for (int i = 0; i < todo.length; i++) {
            db.execSQL("INSERT INTO Todo VALUES ( "+i+", '"+todo[i]+"', "+state[i]+", '"+time[i]+"');");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataBases.createCat._TABLECAT);
        db.execSQL("DROP TABLE IF EXISTS " + DataBases.createDiary._TABLEDIARY);
        db.execSQL("DROP TABLE IF EXISTS " + DataBases.createTodo._TABLETODO);
        db.execSQL("DROP TABLE IF EXISTS " + DataBases.createBucket._TABLEBUCKET);
        onCreate(db);
    }

    //고양이 정보 저장
    public long insertCat(String catName, int catAge, String catBirth,String hos_tel) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBases.createCat.CATNAME, catName);
        values.put(DataBases.createCat.CATAGE, catAge);
        values.put(DataBases.createCat.CATBIRTH, catBirth);
        values.put(DataBases.createCat.HOSPITAL, hos_tel);
        return db.insert(DataBases.createCat._TABLECAT, null, values);
    }

    //일지 저장
    public long insertDiary(String title, String date, String content, String catState) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBases.createDiary.TITLE, title);
        values.put(DataBases.createDiary.DATE, date);
        values.put(DataBases.createDiary.CONTENT, content);
        values.put(DataBases.createDiary.CATSTATE, catState);
        return db.insert(DataBases.createDiary._TABLEDIARY, null, values);
    }

    //장바구니 항목 저장
    public long insertBucket(String goods, String tag, String link) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBases.createBucket.GOODS, goods);
        values.put(DataBases.createBucket.TAG, tag);
        values.put(DataBases.createBucket.LINK, link);
        return db.insert(DataBases.createBucket._TABLEBUCKET,null,values);
    }

    //일지 수정 (id로 구분)
    public boolean updateDiary(long id, String title, String date, String content, String catState) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBases.createDiary.TITLE, title);
        values.put(DataBases.createDiary.DATE, date);
        values.put(DataBases.createDiary.CONTENT, content);
        values.put(DataBases.createDiary.CATSTATE, catState);
        return db.update(DataBases.createDiary._TABLEDIARY, values, "_id=" + id, null) > 0;
    }

    //체크리스트 상태 변경시
    public boolean updateTodoState(String id, int state) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBases.createTodo.STATE, state);
        return db.update(DataBases.createTodo._TABLETODO, values, "_id=" + id, null) > 0;
    }

    //일지 삭제
    public boolean deleteDiary(long id) {
        db = this.getWritableDatabase();
        return db.delete(DataBases.createDiary._TABLEDIARY, "_id=" + id, null) > 0;
    }

    public Cursor selectCat() {
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Cat;", null);
        return c;
    }

    //다이어리 검색(목록)
    public Cursor selectDiary() {
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Diary ORDER BY _id desc;", null);
        return c;
    }

    //특정 다이어리정보
    public Cursor showDiary(long id) {
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Diary WHERE _id=" + id + ";", null);
        return c;
    }

    //해당시간에 선택이 안되어있는 리스트
    public Cursor showTodo(String time) {
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Todo WHERE state=0 AND time='" + time + "';", null);
        return c;
    }

    //완료한 체크리스트
    public Cursor showComplete() {
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Todo WHERE state=1", null);
        return c;
    }

    //(시간대)완료안한 체크리스트
    public Cursor showDonot(/*String time*/) {
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Todo WHERE state=0/* AND time='"+time+"'*/;", null);
        return c;
    }

    //체크리스트 초기화
    public boolean resetTodo() {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBases.createTodo.STATE, 0);
        return db.update(DataBases.createTodo._TABLETODO, values, "state=1", null) > 0;
    }

    public Cursor showBucket() {
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Bucket;", null);
        return c;

    }
}
