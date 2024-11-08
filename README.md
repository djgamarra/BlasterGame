# Blaster Game 🚀

## Overview

This is a simple yet exciting Space Invaders-style game built using Kotlin
and the `Graphics2D` class. It serves as a demonstration of concepts like
game loops, graphics rendering and multithreading.

The game runs with no external libraries or frameworks, relying solely on
Kotlin, `JFrame`, `Canvas` and the `Graphics2D` class.

## Features

- **Simple controls:** You just have to use your mouse to move the player.
- **Multi-scene game:** This is a simple example on how to create games
  with multiple "views".
- **Hand-made animations:** Smooth movements thanks to a custom
  implementation of animations.
- **Multithreading:** This game uses three different threads:
    - The main thread: For Swing.
    - Drawer thread: Which renders to the `Canvas` at a fixed rate (FPS).
    - Game thread: Which emits a signal each 10 ms so that the different
      scenes and view components know when to dispatch actions like
      shooting a new projectile or checking collisions.

## Purpose

The primary goal of this project is to provide a practical example for:

- Kotlin basics.
- `Graphics2D` (basic rendering and simple animation techniques).
- Multithreading in `Swing` environments.

## Screenshots

![Start screen](/screenshots/start.png?raw=true "Start screen")
![Settings screen](/screenshots/settings.png?raw=true "Settings screen")
![Game](/screenshots/game.png?raw=true "Game")
![Game over screen](/screenshots/game_over.png?raw=true "Game over screen")
![Demo](/screenshots/demo.gif?raw=true "Demo")

## Requirements

- **Java version**: 21. Use **Oracle JDK** or **GraalVM** for optimal
  performance. (**Note:**  The game may experience lag with IBM Semeru JDK)

## Setup & Running

```bash
# Clone the repo
git clone git@github.com:djgamarra/BlasterGame.git

# Enter in project folder:
cd BlasterGame

# chmod +x gradlew

# Run using gradle
./gradlew run
```

## Performance Notes

**This game was not built with performance in mind.** I made it to perform
as good as it can, but it has not been well-tested.

_This is my local setup:_
- _Acer Nitro ANV15-51_
- _i5-13420H processor_
- _40GB of RAM_
- _Windows 11 Home_

## License

MIT
