package com.example.tiptime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    /**
     * Creates the top-level variable binding object.
     * It's declared at this evel because it will be used across multiple methods in MainActivity class.
     * lateinit means that the code will initialize the variable before using it,
     * otherwise the app will crash
     */
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initializes the binding object which will be used to access Views in the activity_main.xml layout.
        binding = ActivityMainBinding.inflate(layoutInflater)
        /**
         * Set the content view of the activity. Instead of passing the resource ID of the layout,
         * R.layout.activity_main, this specifies the root of the hierarchy of views in your app, binding.root.
         * You may recall the idea of parent views and child views; the root connects to all of them.
         */
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }
    }
    fun calculateTip() {

        /** Creates variable to hold cost of service as string.
         *  Need .toString() because the text attribute of an EditText is an Editable (not String)
         *  because it represents text that can be changed.
         */
        val stringInTextField: String = binding.costOfService.text.toString()
        /**
         * Creates variable cost: Double to store the cost as double
         * by calling toDouble on stringInTextField,
         * because its a String
         */
        val cost: Double = stringInTextField.toDouble()

        /**
         * Stores the selected button ID int selectedId val
         */
        val selectedId = binding.tipOptions.checkedRadioButtonId

        /**
         * Creates tipPercentage val, which is a Double, and stores the corresponding
         * value of tip based on which RadioButtonId was selected.
         */
        val tipPercentage: Double = when (selectedId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        /**
         * Calculates the tip amount. Must be var because
         * it may need to be rounded up
         */
        var tip = tipPercentage * cost
        /**
         *  For a `Switch` element, you can check the `isChecked`
         *  attribute to see if the switch is "on".
         */
        val roundUp = binding.roundUpSwitch.isChecked
        /**
         * If the val roundUp returned true (isChecked),
         * tip will be rounded up
         */
        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }
        /**
         * Android Framework provides methods for formatting numbers as currency,
         * so you don't need to know all the possibilities. NumberFormat.getCurrencyInstance()
         * gives a number formatter that can be used to format numbers as currency.
         * Then, format(tip) is called to format the tip with the getCurrencyInstance(),
         * after, the result if stored in formattedTip
         */
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

        /**
         *  binding.tipResult.text is the text attribute of tip_result TextView. So, the = will
         *  provide
         *  the value to it. The value will be te result of
         *  getString(R.string.tip_amount, formattedTip)
         */
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)


    }
}