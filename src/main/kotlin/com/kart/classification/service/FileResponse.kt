package com.kart.classification.service

import com.kart.classification.domain.Lap
import com.kart.classification.domain.Pilot
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class FileResponse (pilots:List<Pilot>,
                    finalPosition:List<Lap>,
                    beginRaceTime:LocalDateTime?,
                    finalRaceTime:LocalDateTime?,
                    bestLap: Lap){

    init{
        println("Position\tID\tPilotName\tFinished Laps")
        var i = 1
        finalPosition.forEach {
            println(i.toString().padEnd(8)+"\t${it.pilotId.toString().padEnd(2)}\t${pilots.find { pilot -> pilot.id == it.pilotId }?.name?.padEnd(20)}\t${it.lapNumber}")
            i++
        }
        val minutes = ChronoUnit.MINUTES.between(beginRaceTime, finalRaceTime)
        val seconds = ChronoUnit.SECONDS.between(beginRaceTime, finalRaceTime) % 60
        println("Total Time: $minutes minutos e $seconds segundos")
        println("")
        println("Record Lap ${bestLap.lapTime} by ${pilots.find { pilot -> pilot.id == bestLap.pilotId }?.name} ")
    }

}