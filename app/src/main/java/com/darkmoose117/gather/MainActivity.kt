package com.darkmoose117.gather

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.darkmoose117.gather.ui.GatherUI
import com.darkmoose117.gather.ui.SymbolsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val symbolsViewModel: SymbolsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val symbolMap by symbolsViewModel.symbols.collectAsState()
            GatherUI(symbolMap = symbolMap)
        }
    }
}
