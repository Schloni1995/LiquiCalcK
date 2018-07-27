package com.example.toni.liquidcalccompatible.calculations

/**
 * Created by Toni on 07.03.2018.
 */

class Calculator {
    fun calcShotMenge(zielLiquidMengeString: String, zielLiquidKonzString: String, konzShotString: String): Double {
        val zielLiquidMenge = java.lang.Double.parseDouble(zielLiquidMengeString.replace(",", "."))
        val zielLiquidKonz = java.lang.Double.parseDouble(zielLiquidKonzString.replace(",", "."))
        val konzShot = java.lang.Double.parseDouble(konzShotString.replace(",", "."))
        return zielLiquidMenge * zielLiquidKonz / konzShot
    }

    fun calcAromaMenge(zielLiquidMengeString: String, konzAromaString: String): Double {
        val zielLiquidMenge: Double = java.lang.Double.parseDouble(zielLiquidMengeString.replace(",", "."))
        val konzAroma: Double = java.lang.Double.parseDouble(konzAromaString.replace(",", "."))
        return zielLiquidMenge * konzAroma / 100.0
    }
}
