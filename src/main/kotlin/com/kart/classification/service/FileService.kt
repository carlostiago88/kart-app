package com.kart.classification.service

import java.io.File
import java.io.IOException

open class FileService {
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