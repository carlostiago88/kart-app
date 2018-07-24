package com.kart.classification.service

import com.kart.classification.domain.Lap
import com.kart.classification.domain.Pilot
import java.time.LocalDateTime
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

open class RaceService {
    private var laps = mutableListOf<Lap>()
    private var pilots = arrayListOf<Pilot>()

    fun mountDomainByLine(line: String) {
        try {
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

            val absoluteHourToDate = LocalDateTime.parse("2018-07-22T$absoluteHour")

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

        } catch (e: IndexOutOfBoundsException) {
            throw IndexOutOfBoundsException("File is wrong $e")
        }catch(e: DateTimeParseException){
            throw e
        }

    }

    fun mountRaceData(): FileResponse {
        val beginRaceTime = laps.filter { (it.lapNumber == 1) }.minBy { it.lapBeginTime }
        val finalRaceTime = laps.filter { (it.lapNumber == 4) }.minBy { it.lapBeginTime }
        val finalPosition = laps
                .sortedWith(compareBy(Lap::lapNumber))
                .asReversed()
                .distinctBy { it.pilotId }
                .sortedBy { it.absoluteHour }

        return FileResponse(pilots,finalPosition,beginRaceTime?.lapBeginTime,finalRaceTime?.lapBeginTime)

    }

    private fun getBeginLapTime(lapTime: String, absoluteHour: LocalDateTime): LocalDateTime {
        val minutes = lapTime.substringBefore(":")
        val seconds = lapTime.removePrefix("$minutes:").substringBefore(".")
        val millis = lapTime.substringAfter(".")

        return absoluteHour.minusMinutes(minutes.toLong()).minusSeconds(seconds.toLong()).minus(millis.toLong(), ChronoUnit.MILLIS)
    }



}