package com.ruchanokal.foodlistmvvm

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.IOException


class FoodViewModel : ViewModel() {

    val foods = MutableLiveData<List<FoodModel>>()

    fun createData(context: Context) {

        val food1 = FoodModel("Adana Kebap","Adana","https://travelfoodatlas.com/wp-content/uploads/2021/11/turkish-adana-kebab.jpg")
        val food2 = FoodModel("Hamsili Pilav","Rize","https://i4.hurimg.com/i/hurriyet/75/750x422/5bcd7218c03c0e17180076cc.jpg")
        val food3 = FoodModel("Çiğköfte","Adıyaman","https://cdn.getiryemek.com/restaurants/1611135566259_1125x522.jpeg")
        val food4 = FoodModel("Perde Pilavı","Siirt","https://cdn.yemek.com/mncrop/940/625/uploads/2020/05/perde-pilavi-tarifi.jpeg")
        val food5 = FoodModel("İskender Kebap","Bursa","https://cdn.getiryemek.com/products/1608798312957_500x375.jpeg")
        val food6 = FoodModel("Mantı","Kayseri","https://cdn.yemek.com/mncrop/940/625/uploads/2017/02/kayserimantisi-yeni-1.jpg")
        val food7 = FoodModel("Tepsi Kebabı","Antakya","https://cdn.yemek.com/mncrop/313/280/uploads/2017/10/tepsi-kebabi-tarifi.jpg")

        val foodList = arrayListOf<FoodModel>(food1,food2,food3,food4,food5,food6,food7)

        val prefs: SharedPreferences = context.getSharedPreferences("com.ruchanokal.foodlistmvvm", Context.MODE_PRIVATE)

        var foodModelList = arrayListOf<FoodModel>()

        try {
            foodModelList = ObjectSerializer.deserialize(prefs.getString("foodModelList",
                ObjectSerializer.serialize(ArrayList<FoodModel>()))) as ArrayList<FoodModel>

            Log.i("FoodViewModel", "foodModelList: " + foodModelList )

            Log.i("FoodViewModel", "foodModelList shared: " + prefs.getString("foodModelList",
                ObjectSerializer.serialize(ArrayList<FoodModel>())) )



            if (foodModelList.size > 0)
                foods.value = foodModelList
            else
                foods.value = foodList

        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("ERROR", "ERROR -->> " + e.localizedMessage);
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            Log.e("ERROR", "ERROR 2 -->> " + e.localizedMessage);
        }

        //foods.value = foodList

    }

    fun addNewValue(foodModel: FoodModel,context: Context) {

        var newFoodList  = arrayListOf<FoodModel>()
        newFoodList = foods.value as ArrayList<FoodModel>
        newFoodList.add(foodModel)
        foods.postValue(newFoodList)

        val prefs: SharedPreferences = context.getSharedPreferences("com.ruchanokal.foodlistmvvm", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        try {
            editor.putString("foodModelList", ObjectSerializer.serialize(newFoodList))
        } catch (e: IOException) {

            Log.e("FoodViewModel","ERROR: " + e.localizedMessage)
            e.printStackTrace()
        }
        editor.apply()


    }

    fun saveFood(foodModel: FoodModel, context: Context) {

        var foodModelList = arrayListOf<FoodModel>()

        if (foods.value != null) {
            foodModelList = foods.value as ArrayList<FoodModel>
            foodModelList.add(foodModel)
            foods.postValue(foodModelList)
        }

        // save the task list to preference
        val prefs: SharedPreferences = context.getSharedPreferences("com.ruchanokal.foodlistmvvm", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        try {
            editor.putString("foodModelList", ObjectSerializer.serialize(foodModelList))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        editor.apply()

    }


}