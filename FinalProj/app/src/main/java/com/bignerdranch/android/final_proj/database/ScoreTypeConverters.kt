package com.bignerdranch.android.final_proj.database
import androidx.room.TypeConverter
import java.util.*

class ScoreTypeConverters {
    @TypeConverter
    fun date(date: Date?): Long? {
        return date?.time
    }
}