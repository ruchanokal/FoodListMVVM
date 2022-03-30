package com.ruchanokal.foodlistmvvm

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruchanokal.foodlistmvvm.databinding.ActivityMainBinding
import com.ruchanokal.foodlistmvvm.databinding.YeniYemekEkleDialogBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : FoodViewModel
    private lateinit var binding: ActivityMainBinding
    private val foodRecyclerAdapter = FoodRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        viewModel = ViewModelProviders.of(this).get(FoodViewModel::class.java)
        viewModel.createData(this)

        binding.foodRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.foodRecyclerView.adapter = foodRecyclerAdapter

        observeDatas()

        binding.floatingActionButton.setOnClickListener {

            val dialog = Dialog(this)

            if (dialog != null) {

                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                val binding2 : YeniYemekEkleDialogBinding = YeniYemekEkleDialogBinding.inflate(layoutInflater)
                dialog.setContentView(binding2.root)

                binding2.ekle.setOnClickListener {

                    val yemekName = binding2.yemekNameInputEditText.text.toString()
                    val yemekYoresi = binding2.yemekYoresiInputEditText.text.toString()
                    val yemekImageUrl = binding2.yemekURLInputEditText.text.toString()

                    if (!yemekName.isEmpty() &&
                        !yemekYoresi.isEmpty() &&
                        !yemekImageUrl.isEmpty()) {

                        viewModel.addNewValue(FoodModel(yemekName,yemekYoresi,yemekImageUrl),this)

                        dialog.dismiss()

                    } else if (yemekName.isNullOrEmpty()) {

                        Toast.makeText(this,"Lütfen yemek ismini boş bırakmayınız.",Toast.LENGTH_SHORT).show()

                    } else if (yemekYoresi.isNullOrEmpty()) {

                        Toast.makeText(this,"Lütfen yemek yöresini boş bırakmayınız.",Toast.LENGTH_SHORT).show()

                    } else if (yemekImageUrl.isNullOrEmpty()) {

                        Toast.makeText(this,"Lütfen yemek URL'sini boş bırakmayınız.",Toast.LENGTH_SHORT).show()

                    }



                }

                binding2.iptal.setOnClickListener {

                    dialog.dismiss()

                }


                dialog.show()
            }



        }


    }

    private fun observeDatas() {

        viewModel.foods.observe(this, Observer { foodList ->

            foodList?.let {

                foodRecyclerAdapter.updateList(foodList)

            }

        })


    }
}