package com.bignerdranch.android.final_proj
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity
@Parcelize
data class Score(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var date: Date = Date(),
    var user: String = "",
    var score: Int = 0
) : Parcelable