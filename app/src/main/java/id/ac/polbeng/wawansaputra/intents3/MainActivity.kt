package id.ac.polbeng.wawansaputra.intents3

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.ac.polbeng.wawansaputra.intents3.databinding.ActivityMainBinding

const val SECOND_ACTIVITY = 1000

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.inputWeight.setHint("weight (lbs)")
        binding.inputHeight.setHint("height (inches)")

        binding.btnSendData.setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            val bundle = Bundle()
            bundle.putDouble("weight", binding.inputWeight.text.toString().toDouble())
            bundle.putDouble("height", binding.inputHeight.text.toString().toDouble())
            intent.putExtra("main_activity_data", bundle)
            startActivityForResult(intent, SECOND_ACTIVITY)
        }
    }

    override fun onResume() {
        super.onResume()
        clearInputs()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SECOND_ACTIVITY && resultCode == Activity.RESULT_OK) {
            val bmi = data!!.getDoubleExtra("second_activity_data", 0.0)
            val bmiString = "%.2f".format(bmi)
            binding.inputHeight.setText("")
            binding.inputWeight.setText("")
            binding.txtBMI.setText("BMI : $bmiString ${getBMIDescription(bmi)}")
        }
    }

    private fun getBMIDescription(bmi: Double): String {
        return when (bmi) {
            in 1.0..18.5 -> "Underweight"
            in 18.6..24.9 -> "Normal weight"
            in 25.0..29.9 -> "Overweight"
            else -> "Obese"
        }
    }

    private fun clearInputs() {
        binding.inputWeight.setText("")
        binding.inputHeight.setText("")
    }
}
