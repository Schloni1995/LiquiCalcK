package com.example.toni.liquidcalccompatible.fragments

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.toni.liquidcalccompatible.R
import com.example.toni.liquidcalccompatible.activities.ResultActivity
import com.example.toni.liquidcalccompatible.calculations.Calculator
import com.example.toni.liquidcalccompatible.logging.MyLogger
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CalcFragment : Fragment() {
    private val calculator = Calculator()
    private var view: View? = null
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
    private var shotMenge: Double = 0.toDouble()
    private var aromaMenge: Double = 0.toDouble()
    private var nonFail: Boolean = false
    private var liquidFail: Boolean = false
    private var nicFail: Boolean = false
    private var shotFail: Boolean = false
    private var aromaFail: Boolean = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        view = inflater!!.inflate(R.layout.fragment_calc, container, false)
        firstInit()
        // Inflate the layout for this fragment
        return view
    }


    private fun firstInit() {
        setHasOptionsMenu(true)
        errorColor = ContextCompat.getColor(activity, R.color.colorError)
        edittextColor = ContextCompat.getColor(activity, R.color.white)
        resultColor = ContextCompat.getColor(activity, R.color.colorResult)

        zielMengeET = view!!.findViewById(R.id.zielmenge)
        zielKonzET = view!!.findViewById(R.id.zielkonz)
        konzShotET = view!!.findViewById(R.id.shotkonz)
        konzAromaET = view!!.findViewById(R.id.aromakonz)

        val calcButton = view!!.findViewById<Button>(R.id.calcButton)
        calcButton.setOnClickListener(onClickBtn())

        LOG.outInfo("Installed SDK", "SDK: " + Build.VERSION.SDK_INT)
        LOG.outInfo("Needed SDK", "Version: " + Build.VERSION_CODES.O)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val zielmengeTV = view!!.findViewById<TextView>(R.id.zielmengeTV)
            val zielKonzTV = view!!.findViewById<TextView>(R.id.zielKonzTV)
            val shotKonzTV = view!!.findViewById<TextView>(R.id.shotKonzTV)
            val aromaKonzTV = view!!.findViewById<TextView>(R.id.aromaKonzTV)
            zielmengeTV.tooltipText = resources.getString(R.string.tooltipMenge)
            zielKonzTV.tooltipText = resources.getString(R.string.tooltipKonz)
            shotKonzTV.tooltipText = resources.getString(R.string.tooltipShotKonz)
            aromaKonzTV.tooltipText = resources.getString(R.string.tooltipAroma)
        }

        resultAromaTV = view!!.findViewById(R.id.aromaMengetextView)
        resultShotTV = view!!.findViewById(R.id.shotMengetextView)
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

        konzAromaET!!.setOnKeyListener { v, keyCode, event ->
            if (KeyEvent.KEYCODE_ENTER == keyCode) {
                val calcButton = view!!.findViewById<Button>(R.id.calcButton)
                calcButton.performClick()
                closeVirtualKeyboard()
                true
            } else
                false
        }
    }

    private fun closeVirtualKeyboard() {
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (activity.currentFocus != null) {
            assert(inputManager != null) //Asserts are intended for debug code, and not for release time code.
            inputManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun onClickBtn(): View.OnClickListener {
        return View.OnClickListener {
            closeVirtualKeyboard()

            val liquidMenge = zielMengeET!!.text.toString().trim { it <= ' ' }
            val liquidKonz = zielKonzET!!.text.toString().trim { it <= ' ' }
            val shotKonz = konzShotET!!.text.toString().trim { it <= ' ' }
            val aromaKonz = konzAromaET!!.text.toString().trim { it <= ' ' }

            resetFails()
            checkTextValidation(liquidMenge, liquidKonz, shotKonz, aromaKonz)

            //TODO Hiier  Kommas druch Punktersetzen
            //TODO Doppelkonn / - punkt verhindern

            if (nonFail || !liquidFail && !shotFail && !nicFail)
                shotMenge = calculator.calcShotMenge(liquidMenge, liquidKonz, shotKonz)
            if (nonFail || !liquidFail && !aromaFail)
                aromaMenge = calculator.calcAromaMenge(liquidMenge, aromaKonz)

            handleFails()
        }

    }

    private fun checkTextValidation(liquidMenge: String, liquidKonz: String, shotKonz: String, aromaKonz: String) {
        if (inputNotValid(liquidMenge)) setLiquidFail()
        if (inputNotValid(liquidKonz)) setNicFail()
        if (inputNotValid(shotKonz)) setShotFail()
        if (inputNotValid(aromaKonz)) setAromaFail()

        Log.d("checkTextValidation", java.lang.Boolean.toString(liquidFail) + "(liq), " +
                java.lang.Boolean.toString(nicFail) + "(nic), " +
                java.lang.Boolean.toString(shotFail) + "(sho), " +
                java.lang.Boolean.toString(aromaFail) + "(aro)")

        nonFail = !(liquidFail || nicFail || shotFail || aromaFail)
    }

    private fun inputNotValid(input: String): Boolean {
        return input.isEmpty()
    }

    private fun handleFails() {
        if (!nonFail) {
            if (aromaFail) {
                Toast.makeText(activity, "Fehler bei der Aromakonzentration", Toast.LENGTH_SHORT).show()
                konzAromaET!!.setBackgroundColor(errorColor)
                resultShotTV!!.setBackgroundColor(resultColor)
                resultShotTV!!.text = String.format(Locale.GERMANY, "%s %.2f ml", shotMengetextViewText, shotMenge)
                resultShotTV!!.visibility = View.VISIBLE
            }
            if (nicFail) {
                Toast.makeText(activity, "Fehler bei der Zielkonzentration.", Toast.LENGTH_SHORT).show()
                zielKonzET!!.setBackgroundColor(errorColor)

                resultAromaTV!!.setBackgroundColor(resultColor)
                resultAromaTV!!.text = String.format(Locale.GERMANY, "%s %.2f ml", aromaMengetextViewText, aromaMenge)
                resultAromaTV!!.visibility = View.VISIBLE
            }
            if (liquidFail) {
                Toast.makeText(activity, "Fehler bei der Zielliquidmenge", Toast.LENGTH_SHORT).show()
                zielMengeET!!.setBackgroundColor(errorColor)

                resultShotTV!!.setBackgroundColor(resultColor)
                resultShotTV!!.text = String.format(Locale.GERMANY, "%s %.2f ml", shotMengetextViewText, shotMenge)
                resultShotTV!!.visibility = View.VISIBLE
            }
            if (shotFail) {
                Toast.makeText(activity, "Fehler bei der Shotkonzentration", Toast.LENGTH_SHORT).show()
                konzShotET!!.setBackgroundColor(errorColor)

                resultAromaTV!!.setBackgroundColor(resultColor)
                resultAromaTV!!.text = String.format(Locale.GERMANY, "%s %.2f ml", aromaMengetextViewText, aromaMenge)
                resultAromaTV!!.visibility = View.VISIBLE
            }
        } else {
            resultAromaTV!!.setBackgroundColor(resultColor)
            resultShotTV!!.setBackgroundColor(resultColor)

            resultAromaTV!!.text = String.format(Locale.GERMANY, "%s %.2f ml", aromaMengetextViewText, aromaMenge)
            resultShotTV!!.text = String.format(Locale.GERMANY, "%s %.2f ml", shotMengetextViewText, shotMenge)

            resultAromaTV!!.visibility = View.VISIBLE
            resultShotTV!!.visibility = View.VISIBLE
            Toast.makeText(activity, "Berechnung erfolgreich", Toast.LENGTH_SHORT).show()
        }
        /*
        TODO NUR Aromaausgabe oder nur Shotausgabe erlauben??
        */
    }

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
        konzShotET!!.setText(String.format("%s", resources.getInteger(R.integer.shotDefault).toString()))
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
        private val LOG = MyLogger()
        private val DIGIT = "[0-9]+(\\.[0-9]+)?"
    }

}// Required empty public constructor
