package com.kart.classification

import com.kart.classification.service.FileService


    fun main(args: Array<String>) {
        val fileService = FileService()
        fileService.fileInitialize("src/main/resources/kart_log.txt")
    }


