package com.promanage.app.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.promanage.app.service.Labor
import com.promanage.app.service.Material
import java.util.*

@Entity(tableName = "project_table")
data class Project(@PrimaryKey val projectId: Long, val projectName: String, val clientName: String, val startDate: Date, val CompletionDate: Date,
                   val notes: String, val address: String, val materials: List<Material>,
                   val labor: List<Labor>, val overheadCosts: Map<String, Int>, val isAccepted: Boolean)