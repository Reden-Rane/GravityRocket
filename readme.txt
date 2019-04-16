Contenu de l’archive:

DocumentExplicatif.pdf
GravityRocket.jar
src/
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
│                   ├── Polygon2D.java
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
    │   │   └── music01.wav
    │   ├── pew_pew.wav
    │   ├── rocket_booster.wav
    │   └── success.wav
    └── textures
        ├── background_0.png
        ├── background_1.png
        ├── background_2.png
        ├── background_3.png
        ├── background_main.png
        ├── background_rules.jpg
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
