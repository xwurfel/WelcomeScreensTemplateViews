# Welcome Screen Views Library

A highly customizable welcome screen library for Android applications using traditional Views.

## Overview

This library provides a ready-to-use welcome screen implementation that can be easily integrated into any Android application using traditional Android Views. It offers a clean, modern design with extensive customization options to create an engaging first-time user experience.

## Features

- **Fully Customizable** - Every aspect can be configured: colors, fonts, animations, spacing, and more
- **Traditional Android Views** - Built with ViewPager2 and custom Views for optimal performance
- **Multiple Pre-built Themes** - Modern, Dark, Material 3, Neon, Monochrome, Playful, and Corporate themes
- **Rich Animations** - 8 different page indicator animations (Scale, Fade, Slide, Morphing, Bounce, Pulse, Rotate)
- **Flexible Button Placement** - Configure Skip, Next, Previous, and Finish buttons anywhere on screen
- **Versatile Image Support** - Resources, URLs, Drawables with multiple sizing modes
- **Accessibility Ready** - Comprehensive accessibility support with proper content descriptions
- **Light & Dark Themes** - Built-in support for both light and dark themes
- **Fragment & Activity Support** - Easy integration in both Fragments and Activities

## Installation

1. Add the library module to your project:

```kotlin
// In your app's build.gradle.kts
dependencies {
    implementation(project(":welcome_screen"))
}
```

2. The library includes all required dependencies:

```kotlin
// Already included in the library
implementation("com.github.bumptech.glide:glide:4.16.0")
implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
implementation("com.google.android.material:material:1.12.0")
```

## Quick Start

### Basic Usage

```kotlin
// Create welcome screen data
val welcomeScreens = listOf(
    welcomeScreenData {
        title("Welcome to Our App")
        description("Discover amazing features and endless possibilities")
        imageRes(R.drawable.welcome_image)
    },
    welcomeScreenData {
        title("Easy to Use") 
        description("Simple and intuitive interface designed for everyone")
        imageRes(R.drawable.feature_image)
    }
)

// Launch with modern theme
val fragment = WelcomeScreensFactory.createModernFragment(
    screens = welcomeScreens,
    onSkip = { /* Handle skip */ },
    onFinish = { /* Handle completion */ }
)

supportFragmentManager.beginTransaction()
    .replace(R.id.container, fragment)
    .commit()
```

### Activity Integration

```kotlin
class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val screens = createWelcomeScreens()
        val fragment = WelcomeScreensFactory.createModernFragment(
            screens = screens,
            onSkip = { finish() },
            onFinish = {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        )
        
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .commit()
    }
}
```

## Pre-built Themes

The library includes 8 carefully designed themes:

```kotlin
// Modern theme with clean design
WelcomeScreensFactory.createModernFragment(screens, onSkip, onFinish)

// Minimal theme with essential elements only
WelcomeScreensFactory.createMinimalFragment(screens, onFinish)

// Dark theme for night-friendly experience
WelcomeScreensFactory.createDarkFragment(screens, onSkip, onFinish)

// Material 3 theme following latest guidelines
WelcomeScreenConfigFactory.createMaterial3Theme()

// Neon dark theme with glowing effects
WelcomeScreenConfigFactory.createNeonDarkTheme()

// Monochrome theme for elegant simplicity
WelcomeScreenConfigFactory.createMonochromeTheme()

// Playful theme with vibrant colors
WelcomeScreenConfigFactory.createPlayfulTheme()

// Corporate theme for professional apps
WelcomeScreenConfigFactory.createCorporateTheme()
```

## Configuration

### Basic Configuration

```kotlin
val config = welcomeScreenConfig {
    // Background styling
    backgroundColor = 0xFFFFFFFF.toInt()
    useGradientBackground = true
    backgroundGradientColor = 0xFFF5F5F5.toInt()
    
    // Content spacing
    contentPadding = 48
    
    // Custom spacing between elements
    customSpacing = WelcomeScreenSpacing(
        imageToTitleSpacing = 120,
        titleToDescriptionSpacing = 48,
        descriptionToIndicatorSpacing = 72
    )
}
```

### Button Configuration

Configure Skip, Next, Previous, and Finish buttons:

```kotlin
val config = welcomeScreenConfig {
    // Skip button (top-right by default)
    skipButton {
        isVisible = true
        text = "Skip"
        placement = ButtonPlacement.TOP_RIGHT
        style {
            backgroundColor = 0x00000000
            textColor = 0xFF9E9E9E.toInt()
            textSize = 42f
            cornerRadius = 60f
        }
    }
    
    // Next button (bottom-right by default)
    nextButton {
        text = "Next"
        placement = ButtonPlacement.BOTTOM_RIGHT
        style {
            backgroundColor = 0xFF2196F3.toInt()
            textColor = 0xFFFFFFFF.toInt()
            cornerRadius = 72f
            paddingHorizontal = 72
            paddingVertical = 36
            fontWeight = 600
        }
    }
    
    // Previous button (bottom-left by default)
    previousButton {
        text = "Back"
        placement = ButtonPlacement.BOTTOM_LEFT
        style {
            backgroundColor = 0x00000000
            textColor = 0xFF9E9E9E.toInt()
            borderWidth = 3
            borderColor = 0xFF9E9E9E.toInt()
            cornerRadius = 72f
        }
    }
    
    // Finish button (bottom-center on last page)
    finishButton {
        text = "Get Started"
        placement = ButtonPlacement.BOTTOM_CENTER
        style {
            backgroundColor = 0xFF4CAF50.toInt()
            textColor = 0xFFFFFFFF.toInt()
            cornerRadius = 84f
            paddingHorizontal = 96
            paddingVertical = 48
            fontWeight = 600
        }
    }
}
```

#### Button Placement Options

```kotlin
enum class ButtonPlacement {
    TOP_LEFT, TOP_RIGHT, TOP_CENTER,
    BOTTOM_LEFT, BOTTOM_RIGHT, BOTTOM_CENTER,
    CENTER_LEFT, CENTER_RIGHT
}
```

### Page Indicator Configuration

```kotlin
val config = welcomeScreenConfig {
    pageIndicator {
        isVisible = true
        activeColor = 0xFF2196F3.toInt()
        inactiveColor = 0x4D9E9E9E
        size = 24 // pixels
        spacing = 36 // pixels
        placement = IndicatorPlacement.CENTER_BELOW_DESCRIPTION
        animationType = IndicatorAnimation.SLIDE
        animationDuration = 300
        
        shape = IndicatorShape.CIRCLE
        useProgressiveSizing = false
        enableGlow = false
        glowColor = 0xFFFFFFFF.toInt()
        glowRadius = 12f
    }
}
```

#### Available Animations

```kotlin
enum class IndicatorAnimation {
    SCALE,      // Scale animation for active indicator
    FADE,       // Fade in/out animation  
    SLIDE,      // Slide and expand animation
    MORPHING,   // Morphing shape animation
    BOUNCE,     // Bouncing animation
    PULSE,      // Pulsing animation
    ROTATE,     // Rotating animation
    NONE        // No animation
}
```

#### Indicator Shapes

```kotlin
enum class IndicatorShape {
    CIRCLE, ROUNDED_RECTANGLE, RECTANGLE, DIAMOND
}
```

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

#### Available Typefaces

```kotlin
enum class WelcomeTypeface {
    DEFAULT, DEFAULT_BOLD, SANS_SERIF, SERIF, MONOSPACE
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

```kotlin
enum class ImageSizeMode {
    ASPECT_RATIO,     // Maintains aspect ratio
    FIXED_SIZE,       // Fixed width and height
    FILL_WIDTH,       // Fills available width
    PERCENTAGE_WIDTH, // Percentage of available width
    CUSTOM           // Custom constraints
}

ImageSizeConfig.small()      // Small centered image
ImageSizeConfig.banner()     // Wide banner-style image
ImageSizeConfig.portrait()   // Tall portrait image
ImageSizeConfig.fillWidth(aspectRatio = 1.5f)
```

## Advanced Usage

### Custom Theme Creation

```kotlin
val customConfig = WelcomeScreenConfigFactory.createCustomThemeConfig(
    primaryColor = 0xFF9C27B0.toInt(),
    backgroundColor = 0xFFF3E5F5.toInt(),
    textColor = 0xFF4A148C.toInt(),
    accentColor = 0xFFE91E63.toInt()
)
```

### Welcome Screen Data Options

```kotlin
val screenData = welcomeScreenData {
    // Image options (use one)
    imageRes(R.drawable.welcome_image)                           // Resource
    imageUrl("https://example.com/image.jpg")                   // URL
    imageDrawable(ContextCompat.getDrawable(context, R.drawable.icon)) // Drawable
    
    title("Welcome to Our App")
    description("Start your journey with us today")
    
    backgroundColor(0xFFF5F5F5.toInt())
    textColor(0xFF333333.toInt())
    contentDescription("Welcome screen illustration")
}
```

### Advanced Configuration Example

```kotlin
val advancedConfig = welcomeScreenConfig {
    backgroundColor = 0xFF1C1C1E.toInt()
    useGradientBackground = true
    backgroundGradientColor = 0xFF2C2C2E.toInt()
    
    customSpacing {
        imageToTitleSpacing = 120
        titleToDescriptionSpacing = 48
        descriptionToIndicatorSpacing = 72
        horizontalPadding = 96
        verticalPadding = 144
    }
    
    pageIndicator {
        animationType = IndicatorAnimation.PULSE
        shape = IndicatorShape.CIRCLE
        enableGlow = true
        glowColor = 0xFF10B981.toInt()
        glowRadius = 12f
        useProgressiveSizing = true
        maxSize = 36
        minSize = 18
    }
    
    imageSizeConfig = ImageSizeConfig(
        sizeMode = ImageSizeMode.PERCENTAGE_WIDTH,
        widthFraction = 0.8f,
        aspectRatio = 1.2f,
        horizontalPadding = 48
    )
}
```

## Demo Application

The included demo app (`MainActivity.kt`) showcases all available themes and configurations:

## Image Loading

The library uses Glide for efficient image loading with support for:

- **Resource images**: `imageRes(R.drawable.image)`
- **Network images**: `imageUrl("https://example.com/image.jpg")`
- **Drawable objects**: `imageDrawable(drawable)`

Images are automatically rounded according to `imageCornerRadius` configuration.

## Accessibility

Comprehensive accessibility features included:

```kotlin
val screenData = welcomeScreenData {
    imageRes(R.drawable.welcome)
    title("Welcome")
    description("Get started with our amazing app")
    contentDescription("Welcome screen showing app features and benefits")
}
```

- Content descriptions for images and screens
- Semantic labels for navigation buttons
- Screen reader compatibility
- Proper focus handling
- Keyboard navigation support

## Best Practices

### 1. Theme Selection
```kotlin
WelcomeScreensFactory.createPlayfulFragment(screens, onSkip, onFinish)

WelcomeScreenConfigFactory.createCorporateTheme()

WelcomeScreensFactory.createMinimalFragment(screens, onFinish)
```

### 2. Button Configuration
```kotlin
skipButton { placement = ButtonPlacement.TOP_RIGHT }
nextButton { placement = ButtonPlacement.BOTTOM_RIGHT }
previousButton { placement = ButtonPlacement.BOTTOM_LEFT }
finishButton { placement = ButtonPlacement.BOTTOM_CENTER }
```

### 3. Image Optimization
```kotlin
imageSizeConfig = ImageSizeConfig.small() // For icon-style images
imageSizeConfig = ImageSizeConfig.banner() // For wide illustrations
```

### 4. Color Usage
```kotlin
// Use consistent color schemes
val primaryColor = 0xFF2196F3.toInt()
val backgroundColor = 0xFFFFFFFF.toInt() 
val textColor = 0xFF000000.toInt()

// Apply alpha for subtle variations
val fadedTextColor = primaryColor.withAlpha(0.7f)
```

## Requirements

- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)
- **Kotlin**: 2.1.21+
- **AndroidX**: Required

## Dependencies

```kotlin
// Core dependencies (included automatically)
implementation("com.github.bumptech.glide:glide:4.16.0") 
implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
implementation("com.google.android.material:material:1.12.0")
```
