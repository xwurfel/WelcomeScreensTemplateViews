package com.volpis.welcometemplate

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.volpis.welcome_screen.WelcomeScreenData
import com.volpis.welcome_screen.WelcomeScreensFactory
import com.volpis.welcome_screen.config.ButtonPlacement
import com.volpis.welcome_screen.config.ImageScaleType
import com.volpis.welcome_screen.config.ImageSizeConfig
import com.volpis.welcome_screen.config.ImageSizeMode
import com.volpis.welcome_screen.config.IndicatorAnimation
import com.volpis.welcome_screen.config.IndicatorShape
import com.volpis.welcome_screen.config.WelcomeScreenConfigFactory
import com.volpis.welcome_screen.config.WelcomeTypeface
import com.volpis.welcome_screen.config.welcomeScreenConfig
import com.volpis.welcome_screen.welcomeScreenData

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDemoLayout()
    }

    private fun setupDemoLayout() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 48, 48, 48)
        }

        val demos = listOf(
            "Basic Welcome" to ::showBasicWelcome,
            "Modern Welcome" to ::showModernWelcome,
            "Minimal Welcome" to ::showMinimalWelcome,
            "Dark Theme Welcome" to ::showDarkWelcome,
            "Custom Brand Welcome" to ::showCustomWelcome,
            "Advanced Configuration" to ::showAdvancedWelcome
        )

        demos.forEach { (title, action) ->
            layout.addView(createDemoButton(title, action))
        }

        setContentView(layout)
    }

    private fun createDemoButton(title: String, action: () -> Unit): Button {
        return Button(this).apply {
            text = title
            setOnClickListener { action() }
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 24)
            }
        }
    }


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
            onSkip = { handleWelcomeSkipped("Welcome tutorial skipped!") },
            onFinish = { handleWelcomeCompleted("Welcome tutorial completed!") }
        )
        showWelcomeFragment(fragment)
    }


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
                description("Discover a world of possibilities with our comprehensive suite of tools designed to enhance your productivity.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Connect & Collaborate")
                description("Join a thriving community of professionals and innovators. Share ideas and grow together.")
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Secure & Reliable")
                description("Your data is protected with enterprise-grade security. Focus on what matters most.")
            }
        )
    }

    private fun createMinimalScreens(): List<WelcomeScreenData> {
        return listOf(
            WelcomeScreenData(
                imageRes = R.mipmap.ic_launcher,
                title = "Material Design",
                description = "Experience the latest Material Design principles with beautiful animations.",
                backgroundColor = 0xFFF7F2FA.toInt()
            ),
            WelcomeScreenData(
                imageRes = R.mipmap.ic_launcher,
                title = "Adaptive Interface",
                description = "Our interface adapts to your preferences for the best experience.",
                backgroundColor = 0xFFF1F3F4.toInt()
            )
        )
    }

    private fun createDarkScreens(): List<WelcomeScreenData> {
        return listOf(
            WelcomeScreenData(
                imageRes = R.mipmap.ic_launcher,
                title = "Dark Mode Excellence",
                description = "Designed for optimal viewing in any lighting condition.",
                textColor = 0xFFFFFFFF.toInt()
            ),
            WelcomeScreenData(
                imageRes = R.mipmap.ic_launcher,
                title = "Night-Friendly",
                description = "Perfect for late-night sessions and low-light environments.",
                textColor = 0xFFFFFFFF.toInt()
            )
        )
    }

    private fun createCustomScreens(): List<WelcomeScreenData> {
        return listOf(
            WelcomeScreenData(
                imageRes = R.mipmap.ic_launcher,
                title = "Custom Experience",
                description = "Fully customizable welcome screens with your brand colors."
            ),
            WelcomeScreenData(
                imageRes = R.mipmap.ic_launcher,
                title = "Brand Integration",
                description = "Seamlessly integrate your brand identity with custom themes."
            )
        )
    }

    private fun createAdvancedScreens(): List<WelcomeScreenData> {
        return listOf(
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Advanced Configuration")
                description("This example shows all the customization options available in the welcome screen library.")
                backgroundColor(0xFFF8F9FA.toInt())
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Fully Customizable")
                description("Every aspect can be customized: colors, fonts, animations, button placement, and more.")
                backgroundColor(0xFFE3F2FD.toInt())
            },
            welcomeScreenData {
                imageRes(R.mipmap.ic_launcher)
                title("Production Ready")
                description("Built with performance and accessibility in mind. Ready for production use.")
                backgroundColor(0xFFE8F5E8.toInt())
            }
        )
    }


    private fun createAdvancedConfiguration() = welcomeScreenConfig {
        skipButton {
            isVisible(true)
            text("Skip Tutorial")
            placement(ButtonPlacement.TOP_RIGHT)
            style {
                backgroundColor(0x00000000)
                textColor(0xFF6C757D.toInt())
                textSize(42f)
                isUnderlined(true)
                paddingHorizontal(48)
                paddingVertical(24)
            }
        }

        nextButton {
            isVisible(true)
            text("Continue")
            placement(ButtonPlacement.BOTTOM_RIGHT)
            style {
                backgroundColor(0xFF007BFF.toInt())
                textColor(0xFFFFFFFF.toInt())
                cornerRadius(72f)
                paddingHorizontal(96)
                paddingVertical(36)
                fontWeight(600)
            }
        }

        previousButton {
            isVisible(true)
            text("Back")
            placement(ButtonPlacement.BOTTOM_LEFT)
            style {
                backgroundColor(0x00000000)
                textColor(0xFF6C757D.toInt())
                borderWidth(3)
                borderColor(0xFF6C757D.toInt())
                cornerRadius(72f)
                paddingHorizontal(72)
                paddingVertical(36)
            }
        }

        finishButton {
            isVisible(true)
            text("Get Started Now!")
            placement(ButtonPlacement.BOTTOM_CENTER)
            style {
                backgroundColor(0xFF28A745.toInt())
                textColor(0xFFFFFFFF.toInt())
                cornerRadius(96f)
                paddingHorizontal(120)
                paddingVertical(48)
                textSize(54f)
                fontWeight(700)
                isBold(true)
            }
        }

        pageIndicator {
            isVisible(true)
            activeColor(0xFF007BFF.toInt())
            inactiveColor(0x4D6C757D)
            size(30)
            spacing(45)
            animationType(IndicatorAnimation.MORPHING)
            animationDuration(500)
            shape(IndicatorShape.ROUNDED_RECTANGLE)
            customCornerRadius(15f)
        }

        titleTextColor(0xFF212529.toInt())
        titleTextSize(96f)
        titleTypeface(WelcomeTypeface.DEFAULT_BOLD)

        descriptionTextColor(0xFF6C757D.toInt())
        descriptionTextSize(54f)
        descriptionTypeface(WelcomeTypeface.DEFAULT)

        backgroundColor(0xFFFFFFFF.toInt())
        useGradientBackground(true)
        backgroundGradientColor(0xFFF8F9FA.toInt())

        imageCornerRadius(72f)
        imageElevation(24f)
        imageScaleType(ImageScaleType.FIT)
        imageSizeConfig(
            ImageSizeConfig(
                sizeMode = ImageSizeMode.PERCENTAGE_WIDTH,
                aspectRatio = 1.2f,
                widthFraction = 0.8f,
                horizontalPadding = 72,
                verticalPadding = 24
            )
        )

        customSpacing {
            imageToTitleSpacing(144)
            titleToDescriptionSpacing(72)
            descriptionToIndicatorSpacing(96)
            indicatorToButtonSpacing(120)
            horizontalPadding(96)
            verticalPadding(120)
        }
    }

    private fun showWelcomeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .addToBackStack("welcome")
            .commit()
    }

    private fun goToMainContent() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
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