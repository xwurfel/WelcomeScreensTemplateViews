package com.volpis.welcometemplate

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.volpis.welcome_screen.WelcomeScreenData
import com.volpis.welcome_screen.WelcomeScreensFactory
import com.volpis.welcome_screen.config.WelcomeScreenConfigFactory
import com.volpis.welcome_screen.utils.setRoundedCorners
import com.volpis.welcome_screen.welcomeScreenData

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupEnhancedDemoLayout()
    }

    private fun setupEnhancedDemoLayout() {
        val scrollView = ScrollView(this)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 48, 48, 48)
        }

        val titleView = android.widget.TextView(this).apply {
            text = "Welcome Screen Themes"
            textSize = 24f
            setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.black))
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setPadding(0, 0, 0, 48)
        }
        layout.addView(titleView)

        val demos = listOf(
            "Basic Welcome" to ::showBasicWelcome,
            "Modern Welcome" to ::showModernWelcome,
            "Minimal Welcome" to ::showMinimalWelcome,
            "Dark Theme Welcome" to ::showDarkWelcome,
            "Material 3 Theme" to ::showMaterial3Theme,
            "Neon Dark Theme" to ::showNeonDarkTheme,
            "Monochrome Theme" to ::showMonochromeTheme,
            "Playful Theme" to ::showPlayfulTheme,
            "Corporate Theme" to ::showCorporateTheme,
            "Custom Brand Theme" to ::showCustomWelcome,
            "Advanced Configuration" to ::showAdvancedWelcome
        )

        demos.forEach { (title, action) ->
            layout.addView(createEnhancedDemoButton(title, action))
        }

        scrollView.addView(layout)
        setContentView(scrollView)
    }

    private fun createEnhancedDemoButton(title: String, action: () -> Unit): Button {
        return Button(this).apply {
            text = title

            setBackgroundColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    android.R.color.holo_blue_light
                )
            )
            setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.white))
            setRoundedCorners(24f)

            elevation = 8f
            isAllCaps = false
            textSize = 16f

            setOnClickListener {
                // Add subtle animation
                animate()
                    .scaleX(0.95f)
                    .scaleY(0.95f)
                    .setDuration(100)
                    .withEndAction {
                        animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .withEndAction { action() }
                            .start()
                    }
                    .start()
            }

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 32)
            }

            minHeight = 120
        }
    }

    // Original theme implementations
    private fun showBasicWelcome() {
        val screens = createBasicScreens()
        val fragment = WelcomeScreensFactory.createFragment(
            screens = screens,
            onSkip = ::handleWelcomeSkipped,
            onFinish = ::handleWelcomeCompleted
        )
        showWelcomeFragment(fragment)
    }

    private fun showModernWelcome() {
        val screens = createModernScreens()
        val fragment = WelcomeScreensFactory.createModernFragment(
            screens = screens,
            onSkip = ::handleWelcomeSkipped,
            onFinish = ::handleWelcomeCompleted
        )
        showWelcomeFragment(fragment)
    }

    private fun showMinimalWelcome() {
        val screens = createMinimalScreens()
        val fragment = WelcomeScreensFactory.createMinimalFragment(
            screens = screens,
            onFinish = ::handleWelcomeCompleted
        )
        showWelcomeFragment(fragment)
    }

    private fun showDarkWelcome() {
        val screens = createDarkScreens()
        val fragment = WelcomeScreensFactory.createDarkFragment(
            screens = screens,
            onSkip = ::handleWelcomeSkipped,
            onFinish = ::handleWelcomeCompleted
        )
        showWelcomeFragment(fragment)
    }

    private fun showMaterial3Theme() {
        val screens = createMaterial3Screens()
        val fragment = WelcomeScreensFactory.createFragment(
            screens = screens,
            config = WelcomeScreenConfigFactory.createMaterial3Theme(),
            onSkip = { handleWelcomeSkipped("Material 3 welcome skipped") },
            onFinish = { handleWelcomeCompleted("Welcome to Material You!") }
        )
        showWelcomeFragment(fragment)
    }

    private fun showNeonDarkTheme() {
        val screens = createNeonDarkScreens()
        val fragment = WelcomeScreensFactory.createFragment(
            screens = screens,
            config = WelcomeScreenConfigFactory.createNeonDarkTheme(),
            onSkip = { handleWelcomeSkipped("Dark mode activated") },
            onFinish = { handleWelcomeCompleted("Welcome to the dark side! 🌙") }
        )
        showWelcomeFragment(fragment)
    }

    private fun showMonochromeTheme() {
        val screens = createMonochromeScreens()
        val fragment = WelcomeScreensFactory.createFragment(
            screens = screens,
            config = WelcomeScreenConfigFactory.createMonochromeTheme(),
            onSkip = { handleWelcomeSkipped("Minimalism wins") },
            onFinish = { handleWelcomeCompleted("Less is more. Welcome!") }
        )
        showWelcomeFragment(fragment)
    }

    private fun showPlayfulTheme() {
        val screens = createPlayfulScreens()
        val fragment = WelcomeScreensFactory.createFragment(
            screens = screens,
            config = WelcomeScreenConfigFactory.createPlayfulTheme(),
            onSkip = { handleWelcomeSkipped("Fun skipped 😢") },
            onFinish = { handleWelcomeCompleted("Let's have fun! 🎉") }
        )
        showWelcomeFragment(fragment)
    }

    private fun showCorporateTheme() {
        val screens = createCorporateScreens()
        val fragment = WelcomeScreensFactory.createFragment(
            screens = screens,
            config = WelcomeScreenConfigFactory.createCorporateTheme(),
            onSkip = { handleWelcomeSkipped("Professional welcome skipped") },
            onFinish = { handleWelcomeCompleted("Welcome to your professional journey") }
        )
        showWelcomeFragment(fragment)
    }

    private fun showCustomWelcome() {
        val screens = createCustomScreens()
        val customConfig = WelcomeScreenConfigFactory.createCustomThemeConfig(
            primaryColor = 0xFF9C27B0.toInt(), // Purple
            backgroundColor = 0xFFF3E5F5.toInt(), // Light purple
            textColor = 0xFF4A148C.toInt(), // Dark purple
            accentColor = 0xFFE91E63.toInt() // Pink accent
        )

        val fragment = WelcomeScreensFactory.createFragment(
            screens = screens,
            config = customConfig,
            onSkip = ::handleWelcomeSkipped,
            onFinish = ::handleWelcomeCompleted
        )
        showWelcomeFragment(fragment)
    }

    private fun showAdvancedWelcome() {
        val screens = createAdvancedScreens()
        val config = createAdvancedConfiguration()

        val fragment = WelcomeScreensFactory.createFragment(
            screens = screens,
            config = config,
            onSkip = { handleWelcomeSkipped("Advanced configuration skipped") },
            onFinish = { handleWelcomeCompleted("Advanced welcome completed!") }
        )
        showWelcomeFragment(fragment)
    }

    private fun createMaterial3Screens(): List<WelcomeScreenData> {
        return listOf(
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Material Design 3")
                description("Experience the latest Material Design principles with dynamic colors and expressive interfaces.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Dynamic Theming")
                description("Colors that adapt to your preferences and content that responds to your interactions.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Personal Expression")
                description("Make it yours with customizable themes and personalized experiences.")
            }
        )
    }

    private fun createNeonDarkScreens(): List<WelcomeScreenData> {
        return listOf(
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Enter the Neon Era")
                description("Experience the future with glowing accents and dark aesthetics that protect your eyes.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Cyberpunk Vibes")
                description("Immerse yourself in a digital world where neon lights guide your way through the darkness.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Power Up")
                description("Ready to jack into the matrix? Your neon adventure awaits.")
            }
        )
    }

    private fun createMonochromeScreens(): List<WelcomeScreenData> {
        return listOf(
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Pure Simplicity")
                description("Sometimes the most powerful experiences come from the simplest designs.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Focus on Content")
                description("No distractions, no clutter. Just you and what matters most.")
            }
        )
    }

    private fun createPlayfulScreens(): List<WelcomeScreenData> {
        return listOf(
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Let's Play! 🎮")
                description("Get ready for a fun and colorful experience that will brighten your day!")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Bright & Beautiful 🌈")
                description("Vibrant colors and playful animations make everything more enjoyable.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Adventure Awaits! 🚀")
                description("Your colorful journey starts now. Let's make something amazing together!")
            }
        )
    }

    private fun createCorporateScreens(): List<WelcomeScreenData> {
        return listOf(
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Professional Excellence")
                description("Streamline your workflow with enterprise-grade tools designed for productivity and efficiency.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Secure & Reliable")
                description("Built with security in mind, ensuring your business data remains protected at all times.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Scale with Confidence")
                description("From startup to enterprise, our platform grows with your business needs.")
            }
        )
    }

    // Original screen creators (keeping for compatibility)
    private fun createBasicScreens(): List<WelcomeScreenData> {
        return listOf(
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Welcome!")
                description("Thank you for downloading our app. Let's get you started!")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Features")
                description("Discover all the amazing features we have prepared for you.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Let's Begin")
                description("You're all set! Tap the button below to start using the app.")
            }
        )
    }

    private fun createModernScreens(): List<WelcomeScreenData> {
        return listOf(
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Welcome to Our Platform")
                description("Discover a world of possibilities with our comprehensive suite of tools.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Connect & Collaborate")
                description("Join a thriving community of professionals and innovators.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Secure & Reliable")
                description("Your data is protected with enterprise-grade security.")
            }
        )
    }

    private fun createMinimalScreens(): List<WelcomeScreenData> {
        return listOf(
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Material Design")
                description("Experience the latest Material Design principles with beautiful animations.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Adaptive Interface")
                description("Our interface adapts to your preferences for the best experience.")
            }
        )
    }

    private fun createDarkScreens(): List<WelcomeScreenData> {
        return listOf(
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Dark Mode Excellence")
                description("Designed for optimal viewing in any lighting condition.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Night-Friendly")
                description("Perfect for late-night sessions and low-light environments.")
            }
        )
    }

    private fun createCustomScreens(): List<WelcomeScreenData> {
        return listOf(
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Custom Experience")
                description("Fully customizable welcome screens with your brand colors.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Brand Integration")
                description("Seamlessly integrate your brand identity with custom themes.")
            }
        )
    }

    private fun createAdvancedScreens(): List<WelcomeScreenData> {
        return listOf(
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Advanced Configuration")
                description("This example shows all the customization options available.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Fully Customizable")
                description("Every aspect can be customized: colors, fonts, animations, and more.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Production Ready")
                description("Built with performance and accessibility in mind.")
            }
        )
    }

    private fun createAdvancedConfiguration() = WelcomeScreenConfigFactory.createDarkThemeConfig()

    private fun showWelcomeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            .replace(android.R.id.content, fragment)
            .addToBackStack("welcome")
            .commit()
    }

    private fun goToMainContent() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        }
    }

    private fun handleWelcomeSkipped(message: String = "Welcome skipped") {
        showMessage(message)
        goToMainContent()
    }

    private fun handleWelcomeCompleted(message: String = "Welcome completed") {
        showMessage(message)
        goToMainContent()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}