package com.steff.painpatternanalyzer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.steff.painpatternanalyzer.data.local.PainDatabase
import com.steff.painpatternanalyzer.data.repository.PainRepository
import com.steff.painpatternanalyzer.ui.AppNavigation
import com.steff.painpatternanalyzer.ui.theme.PainPatternAnalyzerTheme
import com.steff.painpatternanalyzer.viewmodel.*

class MainActivity : ComponentActivity() {

    private val repository by lazy {
        PainRepository(PainDatabase.getInstance(applicationContext).painEntryDao())
    }

    private val dashboardViewModel by lazy { makeViewModel { DashboardViewModel(repository) } }
    private val addEntryViewModel  by lazy { makeViewModel { AddEntryViewModel(repository) } }
    private val timelineViewModel  by lazy { makeViewModel { TimelineViewModel(repository) } }
    private val insightsViewModel  by lazy { makeViewModel { InsightsViewModel(repository) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PainPatternAnalyzerTheme {
                AppNavigation(
                    dashboardViewModel = dashboardViewModel,
                    addEntryViewModel  = addEntryViewModel,
                    timelineViewModel  = timelineViewModel,
                    insightsViewModel  = insightsViewModel
                )
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : ViewModel> makeViewModel(create: () -> T): T {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <V : ViewModel> create(modelClass: Class<V>) = create() as V
        })[create()::class.java]
    }
}