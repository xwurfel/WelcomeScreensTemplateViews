package com.volpis.welcometemplate

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.volpis.welcome_screen.WelcomeScreenData
import com.volpis.welcome_screen.WelcomeScreensFactory
import com.volpis.welcome_screen.config.WelcomeScreenConfigFactory
import com.volpis.welcome_screen.welcomeScreenData

class WelcomeOnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome_onboarding)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPrefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val hasSeenWelcome = sharedPrefs.getBoolean("has_seen_welcome", false)

        if (!hasSeenWelcome) {
            showWelcomeScreens()
        } else {
            goToMainApp()
        }
    }

    private fun showWelcomeScreens() {
        val screens = createOnboardingScreens()
        val config = WelcomeScreenConfigFactory.createModernConfig()

        val fragment = WelcomeScreensFactory.createFragment(
            screens = screens,
            config = config,
            onSkip = {
                markWelcomeAsSeen()
                goToMainApp()
            },
            onFinish = {
                markWelcomeAsSeen()
                goToMainApp()
            }
        )

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .commit()
    }

    private fun createOnboardingScreens(): List<WelcomeScreenData> {
        return listOf(
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Welcome to ${getString(R.string.app_name)}")
                description("Thank you for choosing our app. Let's get you started with a quick tour.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Key Features")
                description("Discover the powerful features that will help you achieve your goals more efficiently.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Privacy & Security")
                description("Your privacy is our priority. Learn how we protect your data and ensure your security.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Ready to Start")
                description("You're all set! Start exploring and make the most of your new app experience.")
            }
        )
    }

    private fun markWelcomeAsSeen() {
        getSharedPreferences("app_prefs", MODE_PRIVATE)
            .edit {
                putBoolean("has_seen_welcome", true)
            }
    }

    private fun goToMainApp() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}