package com.hsk.mycatdiary;

import android.provider.BaseColumns;

public class DataBases {

    public static final class createCat implements BaseColumns {
        public static final String CATNAME = "catName";
        public static final String CATAGE = "catAge";
        public static final String CATBIRTH = "catBirth";
        public static final String HOSPITAL = "hos_tel";
        public static final String _TABLECAT = "Cat";
        public static final String _CREATECAT = "CREATE TABLE IF NOT EXISTS "+_TABLECAT+"("
                +_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +CATNAME+" TEXT NOT NULL, "
                +CATAGE+" INTEGER NOT NULL, "
                +CATBIRTH+" TEXT NOT NULL, "
                +HOSPITAL+" TEXT NOT NULL);";
    }

    public static final class createDiary implements BaseColumns {
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String CONTENT = "content";
        public static final String CATSTATE = "catState";
        public static final String _TABLEDIARY = "Diary";
        public static final String _CREATEDIARY = "CREATE TABLE IF NOT EXISTS "+_TABLEDIARY+"("
                +_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TITLE+" TEXT NOT NULL, "
                +DATE+" TEXT NOT NULL, "
                +CONTENT+" TEXT, "
                +CATSTATE+" TEXT NOT NULL);";
    }

    public static final class createTodo implements BaseColumns {
        public static final String TODO = "todo"; //내용
        public static final String STATE = "state";
        public static final String TIME = "time";
        public static final String _TABLETODO = "Todo";
        public static final String _CREATETODO = "CREATE TABLE IF NOT EXISTS "+_TABLETODO+"("
                +_ID+" INTEGER PRIMARY KEY, "
                +TODO+" TEXT NOT NULL, "
                +STATE+" INTEGER NOT NULL, "
                +TIME+" TEXT NOT NULL);";
    }

    public static final class createBucket implements BaseColumns {
        public static final String GOODS = "goods";
        public static final String TAG = "tag";
        public static final String LINK = "link";
        public static final String _TABLEBUCKET = "Bucket";
        public static final String _CREATEBUCKET = "CREATE TABLE IF NOT EXISTS "+_TABLEBUCKET+"("
                +_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +GOODS+" TEXT NOT NULL, "
                +TAG+" TEXT NOT NULL, "
                +LINK+" TEXT NOT NULL);";
    }
}
