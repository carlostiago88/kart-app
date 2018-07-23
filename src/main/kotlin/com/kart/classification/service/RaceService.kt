package com.kart.classification.service

import com.kart.classification.domain.Lap
import com.kart.classification.domain.Pilot
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class RaceService {

    private var laps = mutableListOf<Lap>()
    private var pilots = arrayListOf<Pilot>()

    fun mountDomains(line: String) {

        val cleanedLine = line
                .replace("\t", "|")
                .replace(" ", "|")
                .replace("–", "|")
                .split("|").toMutableList()
        //.replace(Regex("""[$|]"""), "")

        var normalizedLine = ""
        for (item in cleanedLine) {
            if (item != "")
                normalizedLine += "$item|"
        }

        val absoluteHour = normalizedLine.substringBefore("|")
        val pilotId = normalizedLine.removePrefix("$absoluteHour|").substringBefore("|")
        val pilotName = normalizedLine.removePrefix("$absoluteHour|$pilotId|").substringBefore("|")
        val lapNumber = normalizedLine.removePrefix("$absoluteHour|$pilotId|$pilotName|").substringBefore("|")
        val lapTime = normalizedLine.removePrefix("$absoluteHour|$pilotId|$pilotName|$lapNumber|").substringBefore("|")
        val avgVelocity = normalizedLine.removePrefix("$absoluteHour|$pilotId|$pilotName|$lapNumber|$lapTime|").substringBefore("|").replace(",", ".")

        var absoluteHourToDate = getAbsoluteTimeBy(absoluteHour)

        val lap = Lap(absoluteHourToDate, pilotId.toLong(),
                lapNumber.toInt(),
                lapTime,
                getBeginLapTime(lapTime, absoluteHourToDate),
                avgVelocity.toDouble())
        laps.add(lap)

        val pilotExists = pilots.find { it.id == pilotId.toLong() }
        if (pilotExists == null) {
            pilots.add(Pilot(pilotId.toLong(), pilotName))
        }
    }

    fun mountRaceData() {
        val beginRaceTime = laps.filter { (it.lapNumber == 1) }.minBy { it.lapBeginTime }
        val finalRaceTime = laps.filter { (it.lapNumber == 4) }.minBy { it.lapBeginTime }

        val finalPosition = laps
                .sortedWith(compareBy(Lap::lapNumber))
                .asReversed()
                .distinctBy { it.pilotId }
                .sortedBy { it.absoluteHour }
        mountResponse(finalPosition,beginRaceTime,finalRaceTime)
    }

    private fun getAbsoluteTimeBy(time: String): LocalDateTime {
        return LocalDateTime.parse("2018-07-22T$time")
    }

    private fun getBeginLapTime(lapTime: String, absoluteHour: LocalDateTime): LocalDateTime {
        val minutes = lapTime.substringBefore(":")
        val seconds = lapTime.removePrefix("$minutes:").substringBefore(".")
        val millis = lapTime.substringAfter(".")

        return absoluteHour.minusMinutes(minutes.toLong()).minusSeconds(seconds.toLong()).minus(millis.toLong(), ChronoUnit.MILLIS)
    }

    private fun mountResponse(finalPosition: List<Lap>, beginRaceTime:Lap?,finalRaceTime:Lap?) {
        println("Posição Chegada\tCódigo Piloto\tNome Piloto\tQtde Voltas Completadas")
        var i = 1
        finalPosition.forEach {
            println("$i\t${it.pilotId}\t${pilots.find { pilot -> pilot.id == it.pilotId }?.name}\t${it.lapNumber}")
            i++
        }
        val minutes = ChronoUnit.MINUTES.between(beginRaceTime?.lapBeginTime, finalRaceTime?.lapBeginTime)
        val seconds = ChronoUnit.SECONDS.between(beginRaceTime?.lapBeginTime, finalRaceTime?.lapBeginTime) % 60
        println("Tempo total de prova: $minutes minutos e $seconds segundos")
    }

}