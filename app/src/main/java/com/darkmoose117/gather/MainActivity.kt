package com.darkmoose117.gather

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darkmoose117.gather.ui.GatherUI
import com.darkmoose117.gather.ui.SymbolsViewModel

class MainActivity : AppCompatActivity() {

    private val symbolsViewModel by viewModels<SymbolsViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SymbolsViewModel::class.java)) {
                    return SymbolsViewModel(application as GatherApp) as T
                }
                throw IllegalArgumentException("unknown modelClass $modelClass")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val symbolMap by symbolsViewModel.symbols.observeAsState(emptyMap())
            GatherUI(
                symbolMap = symbolMap
            )
        }
    }
}
