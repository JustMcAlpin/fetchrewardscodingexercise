package com.example.fetchrewardscodingexercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemViewModel : ViewModel() {
    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> get() = _items

    fun fetchItems() {
        RetrofitInstance.api.getItems().enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    _items.value = response.body()
                        ?.filter { it.name?.isNotBlank() == true } // Remove blank/null names
                        ?.sortedWith(compareBy<Item> { it.listId }
                            .thenBy { extractNumber(it.name) }) // Sort by extracted number
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                // Handle failure (e.g., show an error message)
            }
        })
    }

    private fun extractNumber(name: String?): Int {
        return name?.filter { it.isDigit() }?.toIntOrNull() ?: Int.MAX_VALUE
    }

}
