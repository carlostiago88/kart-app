package com.kart.classification.service

import com.kart.classification.domain.Lap
import com.kart.classification.domain.Pilot
import java.time.LocalDateTime

class RaceService {

    private var laps = mutableListOf<Lap>()
    private var pilots = arrayListOf<Pilot>()

    fun mountDomains(line: String){

        var cleanedLine = line
                .replace("\t","|")
                .replace(" ","|")
                .replace("–","|")
                .split("|").toMutableList()
                //.replace(Regex("""[$|]"""), "")

        var normalizedLine = ""
        for (item in cleanedLine){
            if(item!="")
                normalizedLine += "$item|"
        }

        val absoluteHour = normalizedLine.substringBefore("|")
        val pilotId = normalizedLine.removePrefix("$absoluteHour|").substringBefore("|")
        val pilotName = normalizedLine.removePrefix("$absoluteHour|$pilotId|").substringBefore("|")
        val lapNumber = normalizedLine.removePrefix("$absoluteHour|$pilotId|$pilotName|").substringBefore("|")
        val lapTime = normalizedLine.removePrefix("$absoluteHour|$pilotId|$pilotName|$lapNumber|").substringBefore("|")
        val avgVelocity = normalizedLine.removePrefix("$absoluteHour|$pilotId|$pilotName|$lapNumber|$lapTime|").substringBefore("|")

        println(absoluteHour)

        val lap = Lap(getAbsoluteTimeBy(absoluteHour),pilotId.toLong(),lapNumber.toInt(),lapTime,avgVelocity.toLong())
        laps.add(lap)

       val pilotExists = pilots.find { it.id == pilotId }

        if(pilotExists == null){
            pilots.add(Pilot(pilotId,pilotName))
        }
    }

    fun mountRaceData(){
        //Montar informações do resultado.
        //1.Posição Chegada (Ordenar o MAX de número de voltas seguido do absoluteHour menor

        val beginRaceTime = laps.filter{ (it.lapNumber == 1 )}

        //println(beginRaceTime)
        laps.forEach { println(it) }


        val finalPosition = laps
                .sortedWith(compareBy(Lap::lapNumber))
                .asReversed()
                .distinctBy { it.pilotId }
                .sortedBy{ it.absoluteHour }


        //finalPosition.forEach { println(it) }

    }

    private fun getAbsoluteTimeBy(time:String):LocalDateTime{
        return LocalDateTime.parse("2018-07-22T$time")
    }

    private fun getAbsolute

}