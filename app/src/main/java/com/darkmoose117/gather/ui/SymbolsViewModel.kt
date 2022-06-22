package com.darkmoose117.gather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkmoose117.gather.R
import com.darkmoose117.gather.data.android.RawResourceRepository
import com.darkmoose117.scryfall.data.CardSymbol
import com.darkmoose117.scryfall.data.DataList
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

@HiltViewModel
class SymbolsViewModel @Inject constructor(
    rawResourceRepository: RawResourceRepository,
    moshi: Moshi
) : ViewModel() {

    private val responseType: ParameterizedType = Types
        .newParameterizedType(DataList::class.java, CardSymbol::class.java)
    private val adapter: JsonAdapter<DataList<CardSymbol>> = moshi.adapter(responseType)

    private val _symbols = MutableStateFlow<Map<String, CardSymbol>>(emptyMap())
    val symbols: StateFlow<Map<String, CardSymbol>> = _symbols.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val fileString = rawResourceRepository.openRawResource(R.raw.symbols)
                .bufferedReader().use { it.readText() }
            val dataList = withContext(Dispatchers.IO) { adapter.fromJson(fileString) }
            val symbolMap = mutableMapOf<String, CardSymbol>()
            dataList!!.data.forEach {
                symbolMap[it.symbol] = it
            }

            _symbols.value = symbolMap.toMap()
        }
    }
}