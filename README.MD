# Welcome Screen Views

A customizable welcome screen library for Android applications using traditional Views.

## Overview

This library provides a ready-to-use welcome screen implementation that can be easily integrated
into any Android application using traditional Android Views. It offers a clean, modern design with
customizable elements to create an engaging first-time user experience.

## Features

- Fully customizable welcome screen layout
- Built with traditional Android Views (ViewPager2, custom Views)
- Material Design 3 components
- Support for multiple welcome pages
- Customizable animations and transitions
- Light and dark theme support
- Multiple pre-built configurations
- Support for both Activity and Fragment integration
- Image loading with Glide
- Comprehensive page indicator with animations

## Installation

1. Add the library module to your project:

```kotlin
// In your app's build.gradle.kts
dependencies {
    implementation(project(":welcome_screen"))
}
```

2. Make sure your project includes the required dependencies (already included in the library):

```kotlin
// These are included in the library module
implementation("com.github.bumptech.glide:glide:4.16.0")
implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
implementation("androidx.viewpager2:viewpager2:1.1.0")
```

## Usage

### Basic Usage (Activity)

1. Create welcome screen data:

```kotlin
val welcomeScreens = listOf(
    welcomeScreenData {
        title("Welcome to Our App")
        description("Discover amazing features and possibilities")
        imageRes(R.drawable.welcome_image)
    },
    welcomeScreenData {
        title("Easy to Use")
        description("Simple and intuitive interface")
        imageRes(R.drawable.feature_image)
    }
)
```

2. Launch the welcome screens:

```kotlin
WelcomeScreensFactory.launch(
    context = this,
    screens = welcomeScreens,
    onFinish = { /* Handle completion */ }
)
```

### Fragment Usage

```kotlin
val fragment = WelcomeScreensFactory.createFragment(
    screens = welcomeScreens,
    config = WelcomeScreenConfigFactory.createModernConfig(),
    onSkip = { /* Handle skip */ },
    onFinish = { /* Handle finish */ }
)

supportFragmentManager.beginTransaction()
    .replace(R.id.container, fragment)
    .commit()
```

### Manual Activity Setup

For more control, you can extend `WelcomeScreensActivity`:

```kotlin
class MyWelcomeActivity : WelcomeScreensActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screens = createWelcomeScreens()
        val config = WelcomeScreenConfigFactory.createModernConfig()

        setupWelcomeScreens(
            screens = screens,
            config = config,
            onSkip = { finish() },
            onFinish = {
                // Start main activity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        )
    }
}
```

## Configuration

The library provides extensive customization options through `WelcomeScreenConfig`:

### Basic Configuration

```kotlin
val config = welcomeScreenConfig {
    // Background
    backgroundColor = 0xFFFFFFFF.toInt()
    useGradientBackground = true
    backgroundGradientColor = 0xFFF5F5F5.toInt()

    // Content padding
    contentPadding = 48 // pixels
}
```

### Button Configuration

```kotlin
val config = welcomeScreenConfig {
    // Skip button
    skipButton = ButtonConfig(
        isVisible = true,
        text = "Skip",
        placement = ButtonPlacement.TOP_RIGHT,
        style = ButtonStyle(
            backgroundColor = 0x00000000, // Transparent
            textColor = 0xFF9E9E9E.toInt(),
            textSize = 42f,
            cornerRadius = 60f
        )
    )

    // Next/Previous buttons
    nextButton = ButtonConfig(
        text = "Next",
        style = ButtonStyle(
            backgroundColor = 0xFF2196F3.toInt(),
            textColor = 0xFFFFFFFF.toInt(),
            cornerRadius = 72f,
            paddingHorizontal = 72,
            paddingVertical = 36
        )
    )

    // Finish button
    finishButton = ButtonConfig(
        text = "Get Started",
        style = ButtonStyle(
            backgroundColor = 0xFF4CAF50.toInt(),
            textColor = 0xFFFFFFFF.toInt(),
            cornerRadius = 84f,
            fontWeight = 600
        )
    )
}
```

### Page Indicator Configuration

```kotlin
val config = welcomeScreenConfig {
    pageIndicator = PageIndicatorConfig(
        isVisible = true,
        activeColor = 0xFF2196F3.toInt(),
        inactiveColor = 0x4D9E9E9E,
        size = 24, // pixels
        spacing = 36, // pixels
        animationType = IndicatorAnimation.SLIDE,
        animationDuration = 300
    )
}
```

Available animation types:

- `SCALE` - Scale animation for active indicator
- `FADE` - Fade in/out animation
- `SLIDE` - Slide and expand animation
- `MORPHING` - Morphing shape animation
- `BOUNCE` - Bouncing animation
- `PULSE` - Pulsing animation
- `ROTATE` - Rotating animation
- `NONE` - No animation

### Text Styling

```kotlin
val config = welcomeScreenConfig {
    titleTextColor = 0xFF000000.toInt()
    titleTextSize = 84f // pixels
    titleTypeface = WelcomeTypeface.DEFAULT_BOLD

    descriptionTextColor = 0xFF9E9E9E.toInt()
    descriptionTextSize = 48f // pixels
    descriptionTypeface = WelcomeTypeface.DEFAULT
}
```

### Image Configuration

```kotlin
val config = welcomeScreenConfig {
    imageCornerRadius = 48f
    imageElevation = 12f
    imageScaleType = ImageScaleType.FIT
    imageSizeConfig = ImageSizeConfig(
        sizeMode = ImageSizeMode.ASPECT_RATIO,
        aspectRatio = 1.2f,
        horizontalPadding = 96
    )
}
```

#### Image Size Modes

- `ASPECT_RATIO` - Maintains aspect ratio
- `FIXED_SIZE` - Fixed width and height
- `FILL_WIDTH` - Fills available width
- `PERCENTAGE_WIDTH` - Percentage of available width
- `CUSTOM` - Custom constraints

### Pre-built Configurations

The library includes several pre-built configurations:

```kotlin
// Minimal configuration
val minimalConfig = WelcomeScreenConfigFactory.createMinimalConfig()

// Modern configuration
val modernConfig = WelcomeScreenConfigFactory.createModernConfig()

// Dark theme configuration
val darkConfig = WelcomeScreenConfigFactory.createDarkThemeConfig()

// Custom theme configuration
val customConfig = WelcomeScreenConfigFactory.createCustomThemeConfig(
    primaryColor = 0xFF2196F3.toInt(),
    backgroundColor = 0xFFFFFFFF.toInt(),
    textColor = 0xFF000000.toInt(),
    accentColor = 0xFF4CAF50.toInt()
)
```

## Advanced Usage

### Custom Welcome Screen Data

```kotlin
val screenData = WelcomeScreenData(
    imageRes = R.drawable.welcome_image,
    imageUrl = "https://example.com/image.jpg", // Alternative to imageRes
    imageDrawable = ContextCompat.getDrawable(this, R.drawable.icon), // Alternative
    title = "Welcome",
    description = "Get started with our app",
    backgroundColor = 0xFFF5F5F5.toInt(),
    textColor = 0xFF333333.toInt(),
    contentDescription = "Welcome screen illustration"
)
```

### Using Builder Pattern

```kotlin
val screenData = welcomeScreenData {
    imageRes(R.drawable.welcome_image)
    title("Welcome to Our App")
    description("Start your journey with us")
    backgroundColor(0xFFF5F5F5.toInt())
    textColor(0xFF333333.toInt())
    contentDescription("Welcome illustration")
}
```

### Custom Spacing

```kotlin
val config = welcomeScreenConfig {
    customSpacing = WelcomeScreenSpacing(
        imageToTitleSpacing = 120,
        titleToDescriptionSpacing = 48,
        descriptionToIndicatorSpacing = 72,
        indicatorToButtonSpacing = 96,
        horizontalPadding = 72,
        verticalPadding = 96
    )
}
```

## Image Loading

The library uses Glide for image loading, supporting:

- **Resource images**: `imageRes(R.drawable.image)`
- **URL images**: `imageUrl("https://example.com/image.jpg")`
- **Drawable objects**: `imageDrawable(drawable)`

Images are automatically rounded according to the `imageCornerRadius` configuration.

## Accessibility

The library includes comprehensive accessibility support:

- Content descriptions for images
- Semantic labels for navigation
- Proper focus handling
- Screen reader compatibility

```kotlin
val screenData = welcomeScreenData {
    imageRes(R.drawable.welcome)
    title("Welcome")
    description("Get started")
    contentDescription("Welcome screen showing app features")
}
```

## Theming

### Color Values

All colors should be provided as integer values:

```kotlin
// Using color resources
val color = ContextCompat.getColor(context, R.color.primary)

// Using hex values
val color = 0xFF2196F3.toInt() // Blue

// Using alpha
val transparentColor = 0x802196F3.toInt() // Semi-transparent blue
```

### Typography

Available typeface options:

- `WelcomeTypeface.DEFAULT`
- `WelcomeTypeface.DEFAULT_BOLD`
- `WelcomeTypeface.SANS_SERIF`
- `WelcomeTypeface.SERIF`
- `WelcomeTypeface.MONOSPACE`
