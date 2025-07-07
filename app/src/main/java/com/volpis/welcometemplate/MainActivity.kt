package com.volpis.welcometemplate

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.volpis.welcome_screen.WelcomeScreenData
import com.volpis.welcome_screen.config.WelcomeScreenConfigFactory
import com.volpis.welcome_screen.welcomeScreenData

class MainActivity : AppCompatActivity() {

    private var currentDemo = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        showWelcomeScreens()
    }

    private fun showWelcomeScreens() {
        when (currentDemo % 4) {
            0 -> showModernWelcomeDemo()
            1 -> showMinimalWelcomeDemo()
            2 -> showDarkThemeDemo()
            3 -> showCustomThemeDemo()
        }
    }

    private fun showModernWelcomeDemo() {
        val screens = listOf(
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Welcome to Our Platform")
                description("Discover a world of possibilities with our comprehensive suite of tools designed to enhance your productivity and streamline your workflow.")
                contentDescription("Welcome screen with platform introduction")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Connect & Collaborate")
                description("Join a thriving community of professionals and innovators. Share ideas, collaborate on projects, and grow together in an environment built for success.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Secure & Reliable")
                description("Your data is protected with enterprise-grade security. Enjoy peace of mind while focusing on what matters most - achieving your goals.")
            }
        )

        val config = WelcomeScreenConfigFactory.createModernConfig()

        WelcomeScreensFactory.launch(
            context = this,
            screens = screens,
            config = config,
            onSkip = { incrementDemo() },
            onFinish = { incrementDemo() }
        )
    }

    private fun showMinimalWelcomeDemo() {
        val screens = listOf(
            WelcomeScreenData(
                imageRes = R.mipmap.ic_launcher,
                title = "Material Design",
                description = "Experience the latest Material Design principles with dynamic theming and beautiful animations.",
                backgroundColor = 0xFFF7F2FA.toInt()
            ),
            WelcomeScreenData(
                imageRes = R.mipmap.ic_launcher,
                title = "Adaptive Interface",
                description = "Our interface adapts to your preferences and system settings for the best possible experience.",
                backgroundColor = 0xFFF1F3F4.toInt()
            ),
            WelcomeScreenData(
                imageRes = R.mipmap.ic_launcher,
                title = "Ready to Begin",
                description = "Everything is set up and ready. Let's start your journey with our powerful tools and features.",
                backgroundColor = 0xFFE8F5E8.toInt()
            )
        )

        val config = WelcomeScreenConfigFactory.createMinimalConfig()

        WelcomeScreensFactory.launch(
            context = this,
            screens = screens,
            config = config,
            onSkip = { incrementDemo() },
            onFinish = { incrementDemo() }
        )
    }

    private fun showDarkThemeDemo() {
        val screens = listOf(
            WelcomeScreenData(
                imageRes = R.mipmap.ic_launcher,
                title = "Dark Mode Excellence",
                description = "Designed for optimal viewing in any lighting condition. Easy on the eyes, beautiful to behold.",
                textColor = 0xFFFFFFFF.toInt()
            ),
            WelcomeScreenData(
                imageRes = R.mipmap.ic_launcher,
                title = "Night-Friendly",
                description = "Perfect for late-night sessions and low-light environments. Your eyes will thank you.",
                textColor = 0xFFFFFFFF.toInt()
            ),
            WelcomeScreenData(
                imageRes = R.mipmap.ic_launcher,
                title = "Let's Get Started",
                description = "You're all set to explore our platform. Welcome to a better way of working.",
                textColor = 0xFFFFFFFF.toInt()
            )
        )

        val config = WelcomeScreenConfigFactory.createDarkThemeConfig()

        WelcomeScreensFactory.launch(
            context = this,
            screens = screens,
            config = config,
            onSkip = { incrementDemo() },
            onFinish = { incrementDemo() }
        )
    }

    private fun showCustomThemeDemo() {
        val screens = listOf(
            WelcomeScreenData(
                imageRes = R.mipmap.ic_launcher,
                title = "Custom Experience",
                description = "Fully customizable welcome screens with your brand colors and styling preferences."
            ),
            WelcomeScreenData(
                imageRes = R.mipmap.ic_launcher,
                title = "Brand Integration",
                description = "Seamlessly integrate your brand identity with customizable themes and color schemes."
            ),
            WelcomeScreenData(
                imageRes = R.mipmap.ic_launcher,
                title = "Your Journey Begins",
                description = "Start exploring with a welcome experience tailored specifically for your application."
            )
        )

        val config = WelcomeScreenConfigFactory.createCustomThemeConfig(
            primaryColor = 0xFF9C27B0.toInt(), // Purple
            backgroundColor = 0xFFF3E5F5.toInt(), // Light purple
            textColor = 0xFF4A148C.toInt(), // Dark purple
            accentColor = 0xFFE91E63.toInt() // Pink accent
        )

        WelcomeScreensFactory.launch(
            context = this,
            screens = screens,
            config = config,
            onSkip = { incrementDemo() },
            onFinish = { incrementDemo() }
        )
    }

    private fun incrementDemo() {
        currentDemo++
        // Delay to show transition between demos
        findViewById<android.view.View>(R.id.main).postDelayed({
            showWelcomeScreens()
        }, 500)
    }

    /**
     * Example of using as a Fragment instead of Activity
     */
    private fun showWelcomeAsFragment() {
        val screens = listOf(
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Fragment Example")
                description("This shows how to use welcome screens as a fragment within your existing activity.")
            }
        )

        val fragment = WelcomeScreensFactory.createFragment(
            screens = screens,
            config = WelcomeScreenConfigFactory.createModernConfig(),
            onSkip = { /* Handle skip */ },
            onFinish = { /* Handle finish */ }
        )

        supportFragmentManager.beginTransaction()
            .replace(R.id.main, fragment)
            .commit()
    }
}