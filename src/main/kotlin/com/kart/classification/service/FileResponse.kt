package com.kart.classification.service

import com.kart.classification.domain.Lap
import com.kart.classification.domain.Pilot
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class FileResponse (var pilots:List<Pilot>, var finalPosition:List<Lap>, var beginRaceTime:LocalDateTime?,var finalRaceTime:LocalDateTime?){

    init{
        println("Posição Chegada\tCódigo Piloto\tNome Piloto\tQtde Voltas Completadas")
        var i = 1
        finalPosition.forEach {
            println("$i\t${it.pilotId}\t${pilots.find { pilot -> pilot.id == it.pilotId }?.name}\t${it.lapNumber}")
            i++
        }
        val minutes = ChronoUnit.MINUTES.between(beginRaceTime, finalRaceTime)
        val seconds = ChronoUnit.SECONDS.between(beginRaceTime, finalRaceTime) % 60
        println("Tempo total de prova: $minutes minutos e $seconds segundos")
    }

}