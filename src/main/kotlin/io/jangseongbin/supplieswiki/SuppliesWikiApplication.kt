package io.jangseongbin.supplieswiki

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class SuppliesWikiApplication

fun main(args: Array<String>) {
    runApplication<SuppliesWikiApplication>(*args)
}
