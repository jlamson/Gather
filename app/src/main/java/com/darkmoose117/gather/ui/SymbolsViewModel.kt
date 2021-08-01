package com.darkmoose117.gather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkmoose117.gather.GatherApp
import com.darkmoose117.gather.R
import com.darkmoose117.scryfall.customizeForScryfall
import com.darkmoose117.scryfall.data.CardSymbol
import com.darkmoose117.scryfall.data.DataList
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.ParameterizedType

class SymbolsViewModel(
    application: GatherApp,
    moshi: Moshi = Moshi.Builder().customizeForScryfall().build()
) : ViewModel() {

    private val responseType: ParameterizedType = Types.newParameterizedType(DataList::class.java, CardSymbol::class.java)
    private val adapter: JsonAdapter<DataList<CardSymbol>> = moshi.adapter(responseType)

    private val _symbols: MutableLiveData<Map<String, CardSymbol>> = MutableLiveData(emptyMap())
    val symbols: LiveData<Map<String, CardSymbol>> = Transformations.distinctUntilChanged(_symbols)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val fileString = application.resources.openRawResource(R.raw.symbols)
                .bufferedReader().use { it.readText() }
            val dataList = withContext(Dispatchers.IO) { adapter.fromJson(fileString) }
            val symbolMap = mutableMapOf<String, CardSymbol>()
            dataList!!.data.forEach {
                symbolMap[it.symbol] = it
            }

            _symbols.postValue(symbolMap.toMap())
        }
    }
}