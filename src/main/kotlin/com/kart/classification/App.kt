package com.kart.classification

import com.kart.classification.service.RaceService
import java.io.File

fun main(args: Array<String>) {

    val raceService = RaceService()
    val filename = "src/main/resources/kart_log.txt"

    File(filename).forEachLine {
        if(it.startsWith("Hora").not())
            raceService.mountDomains(it)
    }
    raceService.mountRaceData()
}


