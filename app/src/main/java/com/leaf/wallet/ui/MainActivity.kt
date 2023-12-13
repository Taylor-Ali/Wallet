package com.leaf.wallet.ui
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.leaf.wallet.R
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel : MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment)


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.welcomeFragment,
                R.id.walletFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        val currentNightMode: Int =
            this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                // Night mode is not active, we're in day time
            }

            Configuration.UI_MODE_NIGHT_YES -> {
                // Night mode is not active, we're in day time
                supportActionBar?.setBackgroundDrawable(
                    ColorDrawable(resources.getColor(R.color.purple_200, null))
                )
            }

            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                // We don't know what mode we're in, assume notnight
            }
        }


        viewModel.isInitialSetupComplete()
        viewModel.setupCompleteLiveData.observe(this){
            if(it)
                navController.navigate(R.id.walletFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

    override fun onBackPressed() {
        val navController = this.findNavController(R.id.nav_host_fragment)
        if (navController.currentDestination?.id == R.id.walletFragment) {
            exitProcess(0)
        }
        super.onBackPressed()

    }
}