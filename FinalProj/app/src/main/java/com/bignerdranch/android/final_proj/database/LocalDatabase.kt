package com.bignerdranch.android.final_proj.database
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bignerdranch.android.final_proj.Score

@Database(entities = [Score::class], version = 3, exportSchema = false)
@TypeConverters(ScoreTypeConverters::class)
abstract class leaderboard : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
}

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE leaderboard ADD COLUMN user TEXT NOT NULL DEFAULT ''"
        )
    }
}

val migration_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE leaderboard ADD COLUMN score TEXT NOT NULL DEFAULT ''"
        )
    }
}