package com.example.toni.liquicalck.failHandling

import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class FailHandler {
    fun handleGui(nonFail: Boolean, aromaFail: Boolean, activity: FragmentActivity?, konzAromaET: EditText?, errorColor: Int, resultColor: Int,
                  resultShotTV: TextView?, shotMengetextViewText: String?,
                  shotMenge: Double, nicFail: Boolean,
                  zielKonzET: EditText?, resultAromaTV: TextView?, liquidFail: Boolean, aromaMengetextViewText: String?, aromaMenge: Double,
                  zielMengeET: EditText?, shotFail: Boolean, konzShotET: EditText?) {
        if (!nonFail) {
            if (aromaFail) {
                Toast.makeText(activity, "Fehler bei der Aromakonzentration", Toast.LENGTH_SHORT).show()
                konzAromaET!!.setBackgroundColor(errorColor)
                resultShotTV!!.setBackgroundColor(resultColor)
                resultShotTV.text = String.format(Locale.GERMANY, "%s %.2f ml", shotMengetextViewText, shotMenge)
                resultShotTV.visibility = View.VISIBLE
            }
            if (nicFail) {
                Toast.makeText(activity, "Fehler bei der Zielkonzentration.", Toast.LENGTH_SHORT).show()
                zielKonzET!!.setBackgroundColor(errorColor)

                resultAromaTV!!.setBackgroundColor(resultColor)
                resultAromaTV.text = String.format(Locale.GERMANY, "%s %.2f ml", aromaMengetextViewText, aromaMenge)
                resultAromaTV.visibility = View.VISIBLE
            }
            if (liquidFail) {
                Toast.makeText(activity, "Fehler bei der Zielliquidmenge", Toast.LENGTH_SHORT).show()
                zielMengeET!!.setBackgroundColor(errorColor)

                resultShotTV!!.setBackgroundColor(resultColor)
                resultShotTV.text = String.format(Locale.GERMANY, "%s %.2f ml", shotMengetextViewText, shotMenge)
                resultShotTV.visibility = View.VISIBLE
            }
            if (shotFail) {
                Toast.makeText(activity, "Fehler bei der Shotkonzentration", Toast.LENGTH_SHORT).show()
                konzShotET!!.setBackgroundColor(errorColor)

                resultAromaTV!!.setBackgroundColor(resultColor)
                resultAromaTV.text = String.format(Locale.GERMANY, "%s %.2f ml", aromaMengetextViewText, aromaMenge)
                resultAromaTV.visibility = View.VISIBLE
            }
        } else {
            resultAromaTV!!.setBackgroundColor(resultColor)
            resultShotTV!!.setBackgroundColor(resultColor)

            resultAromaTV.text = String.format(Locale.GERMANY, "%s %.2f ml", aromaMengetextViewText, aromaMenge)
            resultShotTV.text = String.format(Locale.GERMANY, "%s %.2f ml", shotMengetextViewText, shotMenge)

            resultAromaTV.visibility = View.VISIBLE
            resultShotTV.visibility = View.VISIBLE
            Toast.makeText(activity, "Berechnung erfolgreich", Toast.LENGTH_SHORT).show()
        }
    }
}