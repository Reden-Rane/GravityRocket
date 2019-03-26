package fr.insa.gravityrocket.logic.entity;

import fr.insa.gravityrocket.logic.Level;
import fr.insa.gravityrocket.logic.collision.CollisionBox;
import fr.insa.gravityrocket.logic.collision.RectangularCollisionBox;

/**
 * Classe abstraite d'une entité: objet positionnable dans l'espace, possédant une vitesse, une accélération, une
 * masse.
 */
public abstract class Entity
{

    private final Level level;

    /**
     * La position selon l'axe x en mètres
     */
    private       double xPos;
    /**
     * La position selon l'axe y en mètres
     */
    private       double yPos;
    /**
     * La largeur en mètres
     */
    private final double width;
    /**
     * La hauteur en mètres
     */
    private final double height;
    /**
     * La rotation en radians
     */
    private       double rotation;
    /**
     * La vitesse selon l'axe x en m/s
     */
    private       double xSpeed;
    /**
     * La vitesse selon l'axe y en m/s
     */
    private       double ySpeed;
    /**
     * La vitesse de rotation en rad/s
     */
    private       double rotationSpeed;
    /**
     * L'accélération selon l'axe x en m/s²
     */
    private       double xAcceleration;
    /**
     * L'accélération selon l'axe y en m/s²
     */
    private       double yAcceleration;
    /**
     * L'accélération de rotation en rad/s²
     */
    private       double rotationAcceleration;

    private CollisionBox collisionBox;

    public Entity(Level level, double width, double height) {
        this(level, 0, 0, width, height, 0);
    }

    public Entity(Level level, double xPos, double yPos, double width, double height, double rotation) {
        this.level = level;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.collisionBox = computeCollisionBox();
    }

    public CollisionBox computeCollisionBox() {
        return new RectangularCollisionBox(getXPos(), getYPos(), getWidth(), getHeight(), getRotation());
    }

    /**
     * Méthode de mise à jour de l'entité, elle est appelée 20 fois par seconde
     *
     * @param dt La petite variation de temps entre l'instant de la dernière mise à jour et le temps actuel
     */
    public void update(double dt) {

        for (Entity otherEntity : getLevel().getEntitySet()) {

            if (otherEntity == this) {
                continue;
            }

            if (collidesWith(otherEntity)) {
                onCollisionWith(otherEntity);
            }

            if (isAttractedBy(otherEntity)) {
                applyGravitationalAcceleration(otherEntity);
            }
        }

        this.xSpeed += this.xAcceleration * dt;
        this.ySpeed += this.yAcceleration * dt;
        this.rotationSpeed += this.rotationAcceleration * dt;
        this.xPos += this.xSpeed * dt;
        this.yPos += this.ySpeed * dt;
        this.rotation += this.rotationSpeed * dt;
        this.updateCollisionBox();
    }

    public Level getLevel() {
        return level;
    }

    public boolean collidesWith(Entity otherEntity) {
        return getCollisionBox().collidesWith(otherEntity.getCollisionBox());
    }

    /**
     * Méthode appelée quand l'entité rentre en collision avec une autre
     *
     * @param entity L'entité qui entre en collision avec l'entité actuelle
     */
    public void onCollisionWith(Entity entity) {

    }

    /**
     * Calcule la norme de la force gravitationnelle entre l'entité actuelle et une autre
     *
     * @param entity L'autre entité a l'origine de la force gravitationnelle
     *
     * @return La norme de la force gravitationnelle
     */
    public double gravitationalForce(Entity entity) {

        //Contante gravitationnelle
        double G               = 6.67e-11;
        double squaredDistance = squaredDistance(entity);

        if (squaredDistance == 0) {
            return 0;
        }

        /*
         * On applique la formule de Newton de la force gravitationnelle
         * Cf. https://www.wikiwand.com/fr/Loi_universelle_de_la_gravitation
         */
        return G * (entity.getMass() * this.getMass()) / squaredDistance;
    }

    /**
     * @param entity L'autre entité qui pourrait attirer l'entité actuelle
     *
     * @return Vrai si l'entité peut être attirée par l'entité donnée
     */
    public boolean isAttractedBy(Entity entity) {
        return true;
    }

    private void applyGravitationalAcceleration(Entity otherEntity) {

        double forceX = 0;
        double forceY = 0;

        double distanceToOther = this.distanceTo(otherEntity);

        if (distanceToOther != 0) {
            forceX += this.gravitationalForce(otherEntity) * (otherEntity.getXPos() - this.getXPos()) / distanceToOther;
            forceY += this.gravitationalForce(otherEntity) * (otherEntity.getYPos() - this.getYPos()) / distanceToOther;
        }

        //Application du principe fondamental de la dynamique: somme des forces = masse * acceleration => acceleration = somme des forces / masse
        this.accelerate(forceX / this.getMass(), forceY / this.getMass());
    }

    /**
     * @return Retourne la masse de l'entité
     */
    public abstract double getMass();

    public void updateCollisionBox() {
        this.collisionBox = computeCollisionBox();
    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    /**
     * @param entity L'autre entité depuis laquelle on mesure la ditance avec l'entité actuelle
     *
     * @return Retourne la distanceTo entre l'entité courante et une autre entité
     */
    public double distanceTo(Entity entity) {
        return Math.sqrt(squaredDistance(entity));
    }

    public double getXPos() {
        return xPos;
    }

    public void accelerate(double xAcceleration, double yAcceleration) {
        setXAcceleration(getXAcceleration() + xAcceleration);
        setYAcceleration(getYAcceleration() + yAcceleration);
    }

    public double getYPos() {
        return yPos;
    }

    /**
     * @param entity L'autre entité depuis laquelle on mesure la ditance avec l'entité actuelle
     *
     * @return Retourne le carré de la distanceTo entre l'entité courante et une autre entité, permet d'alléger les
     * calculs car Math.sqrt est gourmand en ressource CPU
     */
    public double squaredDistance(Entity entity) {
        return (entity.xPos - this.xPos) * (entity.xPos - this.xPos) + (entity.yPos - this.yPos) * (entity.yPos - this.yPos);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public double getXAcceleration() {
        return xAcceleration;
    }

    public void setXAcceleration(double xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

    public double getYAcceleration() {
        return yAcceleration;
    }

    public void setYAcceleration(double yAcceleration) {
        this.yAcceleration = yAcceleration;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
        updateCollisionBox();
    }

    public double getRotationAcceleration() {
        return rotationAcceleration;
    }

    public void setRotationAcceleration(double rotationAcceleration) {
        this.rotationAcceleration = rotationAcceleration;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
        updateCollisionBox();
    }

}