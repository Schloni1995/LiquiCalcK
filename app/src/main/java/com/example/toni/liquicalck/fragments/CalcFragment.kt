package com.example.toni.liquicalck.fragments

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.toni.liquicalck.activities.ResultActivity
import com.example.toni.liquicalck.calculations.Calculator
import com.example.toni.liquicalck.failHandling.FailHandler
import com.example.toni.liquidcalccompatible.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CalcFragment : Fragment() {
    private val calculator = Calculator()
    private var zielMengeET: EditText? = null
    private var zielKonzET: EditText? = null
    private var konzShotET: EditText? = null
    private var konzAromaET: EditText? = null
    private var resultAromaTV: TextView? = null
    private var resultShotTV: TextView? = null
    private var aromaMengetextViewText: String? = null
    private var shotMengetextViewText: String? = null
    private var errorColor: Int = 0
    private var edittextColor: Int = 0
    private var resultColor: Int = 0
    private var shotMenge: Double = 0.0
    private var aromaMenge: Double = 0.0
    private var nonFail: Boolean = false
    private var liquidFail: Boolean = false
    private var nicFail: Boolean = false
    private var shotFail: Boolean = false
    private var aromaFail: Boolean = false
    private var thisView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        thisView = inflater.inflate(R.layout.fragment_calc, container, false)
        firstInit()
        // Inflate the layout for this fragment
        return thisView
    }


    private fun firstInit() {
        setHasOptionsMenu(true)
        errorColor = ContextCompat.getColor(context!!, R.color.colorError)
        edittextColor = ContextCompat.getColor(context!!, R.color.white)
        resultColor = ContextCompat.getColor(context!!, R.color.colorResult)

        zielMengeET = thisView!!.findViewById(R.id.zielmenge)
        zielKonzET = thisView!!.findViewById(R.id.zielkonz)
        konzShotET = thisView!!.findViewById(R.id.shotkonz)
        konzAromaET = thisView!!.findViewById(R.id.aromakonz)

        val calcButton = thisView!!.findViewById<Button>(R.id.calcButton)
        calcButton.setOnClickListener(onClickBtn())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val zielmengeTV = thisView!!.findViewById<TextView>(R.id.zielmengeTV)
            val zielKonzTV = thisView!!.findViewById<TextView>(R.id.zielKonzTV)
            val shotKonzTV = thisView!!.findViewById<TextView>(R.id.shotKonzTV)
            val aromaKonzTV = thisView!!.findViewById<TextView>(R.id.aromaKonzTV)
            zielmengeTV.tooltipText = resources.getString(R.string.tooltipMenge)
            zielKonzTV.tooltipText = resources.getString(R.string.tooltipKonz)
            shotKonzTV.tooltipText = resources.getString(R.string.tooltipShotKonz)
            aromaKonzTV.tooltipText = resources.getString(R.string.tooltipAroma)
        }

        resultAromaTV = thisView!!.findViewById(R.id.aromaMengetextView)
        resultShotTV = thisView!!.findViewById(R.id.shotMengetextView)
        resultAromaTV!!.setOnClickListener(onClickResult())
        resultShotTV!!.setOnClickListener(onClickResult())

        aromaMengetextViewText = resultAromaTV!!.text.toString()
        shotMengetextViewText = resultShotTV!!.text.toString()

        resetFails()

        zielMengeET!!.setBackgroundColor(edittextColor)
        zielKonzET!!.setBackgroundColor(edittextColor)
        konzShotET!!.setBackgroundColor(edittextColor)
        konzAromaET!!.setBackgroundColor(edittextColor)

        resultAromaTV!!.visibility = View.INVISIBLE
        resultShotTV!!.visibility = View.INVISIBLE

        konzAromaET!!.setOnKeyListener { _, keyCode, _ ->
            if (KeyEvent.KEYCODE_ENTER == keyCode) {
                val calcButtonTemp = thisView!!.findViewById<Button>(R.id.calcButton)
                calcButtonTemp.performClick()
                closeVirtualKeyboard()
                true
            } else
                false
        }
    }

    private fun closeVirtualKeyboard() {
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(activity?.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun onClickBtn(): View.OnClickListener {
        return View.OnClickListener {
            closeVirtualKeyboard()

            val liquidMenge = zielMengeET!!.text.toString()
            val liquidKonz = zielKonzET!!.text.toString()
            val shotKonz = konzShotET!!.text.toString()
            val aromaKonz = konzAromaET!!.text.toString()

            resetFails()
            checkTextValidation(liquidMenge, liquidKonz, shotKonz, aromaKonz)

            if (nonFail || !liquidFail && !shotFail && !nicFail)
                shotMenge = calculator.calcShotMenge(liquidMenge, liquidKonz, shotKonz)
            if (nonFail || !liquidFail && !aromaFail)
                aromaMenge = calculator.calcAromaMenge(liquidMenge, aromaKonz)

            FailHandler().handleGui(nonFail, aromaFail, activity, konzAromaET, errorColor, resultColor, resultShotTV, shotMengetextViewText, shotMenge, nicFail, zielKonzET, resultAromaTV, liquidFail, aromaMengetextViewText, aromaMenge, zielMengeET, shotFail, konzShotET)
        }

    }

    private fun checkTextValidation(liquidMenge: String, liquidKonz: String, shotKonz: String, aromaKonz: String) {
        if (inputNotValid(liquidMenge)) setLiquidFail()
        if (inputNotValid(liquidKonz)) setNicFail()
        if (inputNotValid(shotKonz)) setShotFail()
        if (inputNotValid(aromaKonz)) setAromaFail()

        nonFail = !(liquidFail || nicFail || shotFail || aromaFail)
    }

    private fun inputNotValid(input: String): Boolean = input.isEmpty()

//    private fun handleFails() {
//        if (!nonFail) {
//            if (aromaFail) {
//                Toast.makeText(activity, "Fehler bei der Aromakonzentration", Toast.LENGTH_SHORT).show()
//                konzAromaET!!.setBackgroundColor(errorColor)
//                resultShotTV!!.setBackgroundColor(resultColor)
//                resultShotTV!!.text = String.format(Locale.GERMANY, "%s %.2f ml", shotMengetextViewText, shotMenge)
//                resultShotTV!!.visibility = View.VISIBLE
//            }
//            if (nicFail) {
//                Toast.makeText(activity, "Fehler bei der Zielkonzentration.", Toast.LENGTH_SHORT).show()
//                zielKonzET!!.setBackgroundColor(errorColor)
//
//                resultAromaTV!!.setBackgroundColor(resultColor)
//                resultAromaTV!!.text = String.format(Locale.GERMANY, "%s %.2f ml", aromaMengetextViewText, aromaMenge)
//                resultAromaTV!!.visibility = View.VISIBLE
//            }
//            if (liquidFail) {
//                Toast.makeText(activity, "Fehler bei der Zielliquidmenge", Toast.LENGTH_SHORT).show()
//                zielMengeET!!.setBackgroundColor(errorColor)
//
//                resultShotTV!!.setBackgroundColor(resultColor)
//                resultShotTV!!.text = String.format(Locale.GERMANY, "%s %.2f ml", shotMengetextViewText, shotMenge)
//                resultShotTV!!.visibility = View.VISIBLE
//            }
//            if (shotFail) {
//                Toast.makeText(activity, "Fehler bei der Shotkonzentration", Toast.LENGTH_SHORT).show()
//                konzShotET!!.setBackgroundColor(errorColor)
//
//                resultAromaTV!!.setBackgroundColor(resultColor)
//                resultAromaTV!!.text = String.format(Locale.GERMANY, "%s %.2f ml", aromaMengetextViewText, aromaMenge)
//                resultAromaTV!!.visibility = View.VISIBLE
//            }
//        } else {
//            resultAromaTV!!.setBackgroundColor(resultColor)
//            resultShotTV!!.setBackgroundColor(resultColor)
//
//            resultAromaTV!!.text = String.format(Locale.GERMANY, "%s %.2f ml", aromaMengetextViewText, aromaMenge)
//            resultShotTV!!.text = String.format(Locale.GERMANY, "%s %.2f ml", shotMengetextViewText, shotMenge)
//
//            resultAromaTV!!.visibility = View.VISIBLE
//            resultShotTV!!.visibility = View.VISIBLE
//            Toast.makeText(activity, "Berechnung erfolgreich", Toast.LENGTH_SHORT).show()
//        }
//    }

    private fun onClickResult(): View.OnClickListener {
        return View.OnClickListener {
            val intent = Intent(activity, ResultActivity::class.java)
            intent.putExtra("shotMenge", String.format(Locale.GERMANY, "%.2f ml", shotMenge))
            intent.putExtra("aromaMenge", String.format(Locale.GERMANY, "%.2f ml", aromaMenge))
            startActivity(intent)
        }
    }

    private fun setLiquidFail() {
        nonFail = false
        this.liquidFail = true
    }

    private fun setNicFail() {
        nonFail = false
        this.nicFail = true
    }

    private fun setShotFail() {
        nonFail = false
        this.shotFail = true
    }

    private fun setAromaFail() {
        nonFail = false
        this.aromaFail = true
    }

    private fun clearText() {
        zielMengeET!!.setText("")
        zielKonzET!!.setText("")
        konzShotET!!.setText("${resources.getInteger(R.integer.shotDefault)}")
        konzAromaET!!.setText("")
        resetFails()
    }

    private fun resetFails() {
        liquidFail = false
        nicFail = false
        aromaFail = false
        shotFail = false
        nonFail = true

        zielMengeET!!.setBackgroundColor(edittextColor)
        zielKonzET!!.setBackgroundColor(edittextColor)
        konzShotET!!.setBackgroundColor(edittextColor)
        konzAromaET!!.setBackgroundColor(edittextColor)

        resultAromaTV!!.text = ""
        resultShotTV!!.text = ""
        resultAromaTV!!.visibility = View.INVISIBLE
        resultShotTV!!.visibility = View.INVISIBLE

        aromaMenge = 0.0
        shotMenge = 0.0
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId


        if (id == R.id.action_clear) {
            clearText()
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater!!.inflate(R.menu.option_menu_calc, menu)
    }

    companion object {
        //        private const val DIGIT = "[0-9]+(\\.[0-9]+)?" // für reg-ex
    }

}// Required empty public constructor
