package fr.insa.gravityrocket;

import fr.insa.gravityrocket.entity.Entity;
import fr.insa.gravityrocket.entity.IDestroyable;
import fr.insa.gravityrocket.entity.Planet;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Level
{

    /**
     * L'ensemble des entités présentes dans le niveau
     */
    private final Set<Entity> entitySet = new HashSet<>();

    /**
     * Ajoute une entité dans le niveau
     *
     * @param entity L'entité à ajouter
     */
    public void addEntity(Entity entity) {
        this.entitySet.add(entity);
    }

    /**
     * Méthode de mise à jour du niveau, elle est appelée 20 fois par seconde et met à jour toutes les entités du
     * niveau.
     *
     * @param dt La petite variation de temps entre l'instant de la dernière mise à jour et le temps actuel
     */
    public void update(double dt) {

        Iterator<Entity> iterator = this.entitySet.iterator();

        while (iterator.hasNext()) {
            Entity entity = iterator.next();

            //Si une entité est destructible et est détruite, on la retire du niveau
            if (entity instanceof IDestroyable && ((IDestroyable) entity).isDestroyed()) {
                iterator.remove();
                continue;
            }

            if (!(entity instanceof Planet)) {
                applyGravitationalAcceleration(entity);
            }

            entity.update(dt);
        }
    }

    private void applyGravitationalAcceleration(Entity entity) {

        double forceX = 0;
        double forceY = 0;

        for (Entity otherEntity : this.entitySet) {

            if (otherEntity == entity) {
                continue;
            }

            double distanceToOther = otherEntity.distance(entity);

            if (distanceToOther != 0) {
                forceX += entity.gravitationalForce(otherEntity) * (otherEntity.getXPos() - entity.getXPos()) / distanceToOther;
                forceY += entity.gravitationalForce(otherEntity) * (otherEntity.getYPos() - entity.getYPos()) / distanceToOther;
            }
        }

        //Application du principe fondamental de la dynamique: somme des forces = masse * acceleration => acceleration = somme des forces / masse
        entity.setXAcceleration(forceX / entity.getMass());
        entity.setYAcceleration(forceY / entity.getMass());
    }

    /**
     * Dessine le niveau à l'écran
     *
     * @param g2d L'instance de Graphics2D permettant de réaliser le rendu
     */
    public void render(Graphics2D g2d) {
        for (Entity entity : entitySet) {

            AffineTransform prevTransform = g2d.getTransform();

            AffineTransform transformation = new AffineTransform();
            int             renderX        = (int) (entity.getXPos() - entity.getWidth() / 2);
            int             renderY        = (int) (entity.getYPos() - entity.getHeight() / 2);

            //On translate le rendu à la position de l'entité
            transformation.translate(renderX, renderY);

            //On applique la rotation
            transformation.translate(entity.getWidth() / 2, entity.getHeight() / 2);
            transformation.rotate(entity.getRotation());
            transformation.translate(-entity.getWidth() / 2, -entity.getHeight() / 2);

            g2d.transform(transformation);

            entity.render(g2d);

            g2d.setTransform(prevTransform);
        }
    }

}