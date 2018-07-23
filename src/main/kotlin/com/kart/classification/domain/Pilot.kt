package com.kart.classification.domain

data class Pilot (
        val id : Long,
        val name : String
){
    fun getNameById(id: Long) = name
}

