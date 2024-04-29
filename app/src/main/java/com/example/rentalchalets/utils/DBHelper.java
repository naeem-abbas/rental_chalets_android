package com.example.rentalchalets.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RentalChaletsDB";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Bookings (" +
                "id INTEGER PRIMARY KEY, " +
                "room_type TEXT, " +
                "start_date TEXT, " +
                "end_date TEXT, " +
                "is_special INTEGER, " +
                "activity_buffet TEXT, " +
                "activity_massage TEXT, " +
                "activity_sauna TEXT, " +
                "beauty_treatment TEXT, " +
                "total_fee REAL" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Bookings");
        onCreate(db);
    }

    public boolean insertBooking(String roomType, String startDate, String endDate, Boolean isSpecial, String activityBuffet, String activityMassage, String activitySauna, String beautyTreatment, double totalFee) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO Bookings (room_type, start_date, end_date, is_special, activity_buffet, activity_massage, activity_sauna, beauty_treatment, total_fee) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, roomType);
        statement.bindString(2, startDate);
        statement.bindString(3, endDate);
        statement.bindString(4, String.valueOf(isSpecial));
        statement.bindString(5, activityBuffet);
        statement.bindString(6, activityMassage);
        statement.bindString(7, activitySauna);
        statement.bindString(8, beautyTreatment);
        statement.bindString(9, String.valueOf(totalFee));
        long rowCount = statement.executeInsert();
        db.close();
        return rowCount > 0; // Return true if record is inserted successfully
    }
}