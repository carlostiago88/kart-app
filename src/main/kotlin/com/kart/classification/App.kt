package com.kart.classification

import com.kart.classification.domain.Lap
import com.kart.classification.domain.Pilot
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun main(args: Array<String>) {
    //println(getGreeting())
    readFileLineByLineUsingForEachLine("src/main/resources/kart_log.txt")
}

fun readFileLineByLineUsingForEachLine(fileName: String){
    var laps = mutableListOf<Lap>()
    var pilots = HashMap<String,String>()

    val raceService = RaceService()

    File(fileName).forEachLine {
        if(it.startsWith("Hora").not())
            raceService.mountDomains(it)
    }

    raceService.getLaps()
}

open class RaceService {

    private var laps = mutableListOf<Lap>()
    private var pilots = arrayListOf<Pilot>()

    fun getPilots(){
        println(pilots)
    }

    fun getLaps(){
        println(laps)
    }

    fun mountDomains(line: String){

        val absoluteHour = line.substring(0,12)
        val pilotId = line.substring(18,21)
        val pilotName = line.substring(24, 50)
        val lapNumber = line.substring(58,59)
        val lapTime = line.substring(61,69)

        val lap = Lap(absoluteHour,pilotId,lapNumber,lapTime)
        laps.add(lap)

        getTime(absoluteHour)

        val pilotExists = pilots.find { it.id == pilotId }

        if(pilotExists == null){
            pilots.add(Pilot(pilotId,pilotName))
        }
    }


    //TO DO: corrigir
    private fun getTime(time:String):String{
        val sdf = SimpleDateFormat("HH:mm:ss.SSS")
        val netDate = Date(time.toLong())
        return sdf.format(netDate)
    }
}