Contenu de l’archive:

DocumentExplicatif.pdf
GravityRocket.jar
src
├── main
│   └── fr
│       └── insa
│           └── gravityrocket
│               ├── controller
│               │   ├── KeyboardHandler.java
│               │   └── MouseHandler.java
│               ├── graphics
│               │   ├── GamePanel.java
│               │   ├── GravityRocketView.java
│               │   ├── interfaces
│               │   │   ├── HomePanel.java
│               │   │   ├── LevelMarkerButton.java
│               │   │   ├── LevelSelectionPanel.java
│               │   │   ├── MainWindow.java
│               │   │   ├── RulesPanel.java
│               │   │   └── TransparentButton.java
│               │   └── renderer
│               │       ├── collision
│               │       │   ├── CircularCollisionBoxRenderer.java
│               │       │   └── RectangularCollisionBoxRenderer.java
│               │       ├── entity
│               │       │   ├── AlienRenderer.java
│               │       │   ├── AsteroidRenderer.java
│               │       │   ├── ExplosionRenderer.java
│               │       │   ├── ItemFuelRenderer.java
│               │       │   ├── LaserRenderer.java
│               │       │   ├── PlanetRenderer.java
│               │       │   └── RocketRenderer.java
│               │       ├── IRenderer.java
│               │       ├── level
│               │       │   ├── LandingLevelRenderer.java
│               │       │   ├── Level5Renderer.java
│               │       │   ├── Level6Renderer.java
│               │       │   ├── Level7Renderer.java
│               │       │   ├── Level8Renderer.java
│               │       │   ├── LevelRenderer.java
│               │       │   └── ReachingZoneLevelRenderer.java
│               │       └── RenderManager.java
│               ├── GravityRocket.java
│               └── logic
│                   ├── collision
│                   │   ├── CircularCollisionBox.java
│                   │   ├── CollisionBox.java
│                   │   └── RectangularCollisionBox.java
│                   ├── entity
│                   │   ├── alien
│                   │   │   ├── Alien.java
│                   │   │   ├── OrbitingAlien.java
│                   │   │   └── WanderingAlien.java
│                   │   ├── Asteroid.java
│                   │   ├── Entity.java
│                   │   ├── EnumAsteroidVariant.java
│                   │   ├── item
│                   │   │   └── ItemFuel.java
│                   │   ├── particle
│                   │   │   ├── Explosion.java
│                   │   │   ├── Laser.java
│                   │   │   └── Particle.java
│                   │   ├── Planet.java
│                   │   ├── rocket
│                   │   │   ├── FuelTank.java
│                   │   │   ├── Reactor.java
│                   │   │   └── Rocket.java
│                   │   └── Satellite.java
│                   ├── EnumLevelState.java
│                   ├── GravityRocketModel.java
│                   ├── level
│                   │   ├── LandingLevel.java
│                   │   ├── Level1.java
│                   │   ├── Level2.java
│                   │   ├── Level3.java
│                   │   ├── Level4.java
│                   │   ├── Level5.java
│                   │   ├── Level6.java
│                   │   ├── Level7.java
│                   │   ├── Level8.java
│                   │   ├── Level.java
│                   │   └── ReachingZoneLevel.java
│                   └── SoundHandler.java
└── resources
    ├── fonts
    │   └── BebasNeue-Regular.ttf
    ├── rules.txt
    ├── sounds
    │   ├── alarm.wav
    │   ├── alien_speech.wav
    │   ├── explosion.wav
    │   ├── music
    │   │   ├── batman.wav
    │   │   ├── faster_than_light.wav
    │   │   ├── guardians_of_the_galaxy.wav
    │   │   ├── interstellar_0.wav
    │   │   ├── interstellar_1.wav
    │   │   ├── kerbal_space_program.wav
    │   │   ├── mass_effect.wav
    │   │   ├── star_wars.wav
    │   │   └── the_martian.wav
    │   ├── pew_pew.wav
    │   ├── rocket_booster.wav
    │   └── success.wav
    └── textures
        ├── background_0.jpg
        ├── background_1.jpg
        ├── background_2.jpg
        ├── background_3.jpg
        ├── background_4.jpg
        ├── background_5.jpg
        ├── background_6.jpg
        ├── background_7.jpg
        ├── background_main.png
        ├── background_rules.jpg
        ├── constellation
        │   ├── aquarius.png
        │   ├── candy.png
        │   ├── rhinoceros.png
        │   └── taurus.png
        ├── danger.png
        ├── entity
        │   ├── alien.png
        │   ├── asteroid
        │   │   ├── asteroid_0.png
        │   │   ├── asteroid_1.png
        │   │   └── asteroid_2.png
        │   ├── fuel.png
        │   └── rocket
        │       ├── flame.png
        │       ├── gas.png
        │       └── rocket.png
        ├── particle
        │   ├── explosion.png
        │   └── laser.png
        └── star
            ├── asteroid.png
            ├── earth_2.png
            ├── earth.png
            ├── jupiter.png
            ├── mars.png
            ├── mercury.png
            ├── moon.png
            ├── neptun.png
            ├── star_0.png
            ├── star_1.png
            ├── sun.png
            ├── uranus.png
            └── venus.png


