package com.kart.classification.service

import com.kart.classification.domain.Lap
import com.kart.classification.domain.Pilot
import java.io.File
import java.io.IOException
import java.time.temporal.ChronoUnit

open class FileService {
    private var laps = mutableListOf<Lap>()
    private var pilots = arrayListOf<Pilot>()
    private val raceService = RaceService()

    fun fileInitialize(filename: String){

        val fileExists = File(filename).exists()
        if (!fileExists)
            throw IOException("file not found")
        File(filename).forEachLine {
            if (it.startsWith("Hora").not())
                raceService.mountDomainByLine(it)
        }

        raceService.mountRaceData()
    }
}