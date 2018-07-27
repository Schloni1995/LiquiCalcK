package com.example.toni.liquidcalccompatible.calculations

import com.example.toni.liquidcalccompatible.logging.MyLogger

/**
 * Created by Toni on 07.03.2018.
 */

class Calculator {
    private val LOG = MyLogger()
    private val LOG_TAG = this@Calculator.toString()

    fun calcShotMenge(zielLiquidMengeString: String, zielLiquidKonzString: String, konzShotString: String): Double {
        val zielLiquidMenge = java.lang.Double.parseDouble(zielLiquidMengeString.replace(",", "."))
        val zielLiquidKonz = java.lang.Double.parseDouble(zielLiquidKonzString.replace(",", "."))
        val konzShot = java.lang.Double.parseDouble(konzShotString.replace(",", "."))

        val messages = arrayOf("Zielmenge Liquid:$zielLiquidMenge", "Zielkonzentration Liquid:$zielLiquidKonz", "Konzentration Shot:$konzShot")
        //        LOG.outInfo(LOG_TAG + "_calcShotmenge", messages);

        return zielLiquidMenge * zielLiquidKonz / konzShot
    }

    fun calcAromaMenge(zielLiquidMengeString: String, konzAromaString: String): Double {
        val zielLiquidMenge: Double
        val konzAroma: Double
        zielLiquidMenge = java.lang.Double.parseDouble(zielLiquidMengeString.replace(",", "."))
        konzAroma = java.lang.Double.parseDouble(konzAromaString.replace(",", "."))
        return zielLiquidMenge * konzAroma / 100.0
    }
}
