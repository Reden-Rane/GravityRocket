package fr.insa.gravityrocket.logic.entity;

import fr.insa.gravityrocket.logic.collision.CollisionBox;
import fr.insa.gravityrocket.logic.collision.RectangularCollisionBox;

import java.awt.*;

/**
 * Classe abstraite d'une entité: objet positionnable dans l'espace, possédant une vitesse, une accélération, une
 * masse.
 */
public abstract class Entity
{

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

    private final CollisionBox collisionBox;

    public Entity(double width, double height) {
        this(new RectangularCollisionBox(width, height), width, height);
    }

    public Entity(double xPos, double yPos, double width, double height, double rotation) {
        this(new RectangularCollisionBox(width, height), xPos, yPos, width, height, rotation);
    }

    public Entity(CollisionBox collisionBox, double width, double height) {
        this(collisionBox, 0, 0, width, height, 0);
    }

    public Entity(CollisionBox collisionBox, double xPos, double yPos, double width, double height, double rotation) {
        this.collisionBox = collisionBox;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }

    /**
     * Méthode de mise à jour de l'entité, elle est appelée 20 fois par seconde
     *
     * @param dt La petite variation de temps entre l'instant de la dernière mise à jour et le temps actuel
     */
    public void update(double dt) {
        this.xSpeed += this.xAcceleration * dt;
        this.ySpeed += this.yAcceleration * dt;
        this.rotationSpeed += this.rotationAcceleration * dt;
        this.xPos += this.xSpeed * dt;
        this.yPos += this.ySpeed * dt;
        this.rotation += this.rotationSpeed * dt;
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
     * @param entity L'autre entité depuis laquelle on mesure la ditance avec l'entité actuelle
     *
     * @return Retourne le carré de la distance entre l'entité courante et une autre entité, permet d'alléger les
     * calculs car Math.sqrt est gourmand en ressource CPU
     */
    public double squaredDistance(Entity entity) {
        return (entity.xPos - this.xPos) * (entity.xPos - this.xPos) + (entity.yPos - this.yPos) * (entity.yPos - this.yPos);
    }

    /**
     * @param entity L'autre entité depuis laquelle on mesure la ditance avec l'entité actuelle
     *
     * @return Retourne la distance entre l'entité courante et une autre entité
     */
    public double distance(Entity entity) {
        return Math.sqrt(squaredDistance(entity));
    }

    /**
     * Dessine l'entité à l'écran
     *
     * @param g2d L'instance de Graphics2D permettant de réaliser le rendu
     */
    public abstract void render(Graphics2D g2d);

    /**
     * @return Retourne la masse de l'entité
     */
    public abstract double getMass();

    public double getXPos() {
        return xPos;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
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

    public double getRotationAcceleration() {
        return rotationAcceleration;
    }

    public void setRotationAcceleration(double rotationAcceleration) {
        this.rotationAcceleration = rotationAcceleration;
    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

}