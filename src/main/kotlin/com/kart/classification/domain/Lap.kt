package com.kart.classification.domain

import java.time.LocalDateTime

data class Lap (val absoluteHour: LocalDateTime, val pilotId: Long, val lapNumber: Int, val lapTime: String, val avgVelocity: Long){
}