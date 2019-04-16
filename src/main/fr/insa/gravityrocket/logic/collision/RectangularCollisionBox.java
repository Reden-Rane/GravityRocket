package fr.insa.gravityrocket.logic.collision;

/**
 * Boite de collision rectangulaire
 */
public class RectangularCollisionBox extends CollisionBox
{

    private final double[][] points = new double[4][2];
    private final double     area;

    public RectangularCollisionBox(double centerX, double centerY, double width, double height, double rotation) {
        double cos = Math.cos(rotation);
        double sin = Math.sin(rotation);

        //On calcule les coordonnées des points à l'aide d'une matrice de rotation

        this.points[0][0] = centerX + (-width / 2) * cos - (-height / 2) * sin;
        this.points[0][1] = centerY + (-width / 2) * sin + (-height / 2) * cos;

        this.points[1][0] = centerX + (+width / 2) * cos - (-height / 2) * sin;
        this.points[1][1] = centerY + (+width / 2) * sin + (-height / 2) * cos;

        this.points[2][0] = centerX + (+width / 2) * cos - (+height / 2) * sin;
        this.points[2][1] = centerY + (+width / 2) * sin + (+height / 2) * cos;

        this.points[3][0] = centerX + (-width / 2) * cos - (+height / 2) * sin;
        this.points[3][1] = centerY + (-width / 2) * sin + (+height / 2) * cos;

        this.area = computeArea();
    }

    private double computeArea() {
        double dx1 = this.points[1][0] - this.points[0][0];
        double dy1 = this.points[1][1] - this.points[0][1];
        double dx2 = this.points[2][0] - this.points[1][0];
        double dy2 = this.points[2][1] - this.points[1][1];
        double l   = Math.sqrt(dx1 * dx1 + dy1 * dy1);
        double L   = Math.sqrt(dx2 * dx2 + dy2 * dy2);
        return L * l;
    }

    @Override
    public boolean collidesWith(CollisionBox otherCollisionBox) {
        if (otherCollisionBox instanceof RectangularCollisionBox) {
            return collidesWithRectangular((RectangularCollisionBox) otherCollisionBox);
        } else if (otherCollisionBox instanceof CircularCollisionBox) {
            return collidesWithCircular((CircularCollisionBox) otherCollisionBox);
        } else {
            return false;
        }
    }

    private boolean collidesWithRectangular(RectangularCollisionBox r) {

        for (double[] point : points) {
            if (r.containsPoint(point[0], point[1])) {
                return true;
            }
        }

        for (double[] point : r.points) {
            if (containsPoint(point[0], point[1])) {
                return true;
            }
        }

        return false;
    }

    public boolean collidesWithCircular(CircularCollisionBox c) {

        boolean intersectsSegment = false;

        for (int i = 0; i < 4; i++) {
            intersectsSegment = intersectsSegment || segmentIntersectsCircle(points[i][0], points[i][1], points[(i + 1) % 4][0], points[(i + 1) % 4][1], c.getCenterX(), c.getCenterY(), c.getRadius());
        }

        return this.containsPoint(c.getCenterX(), c.getCenterY()) || intersectsSegment;
    }

    /**
     * On calcule la somme des aires des triangles formés par le point dans le rectangle, si elle est supérieur à l'aire
     * du rectangle, alors le point est en dehors.
     *
     * @return Vrai si le point (x; y) est contenu dans le rectangle
     */
    private boolean containsPoint(double x, double y) {

        double areaSum = 0;

        for (int i = 0; i < 4; i++) {
            double dxa = points[i][0] - points[(i + 1) % 4][0];
            double dya = points[i][1] - points[(i + 1) % 4][1];
            double dxb = points[i][0] - x;
            double dyb = points[i][1] - y;
            double dxc = points[(i + 1) % 4][0] - x;
            double dyc = points[(i + 1) % 4][1] - y;
            double a   = Math.sqrt(dxa * dxa + dya * dya);
            double b   = Math.sqrt(dxb * dxb + dyb * dyb);
            double c   = Math.sqrt(dxc * dxc + dyc * dyc);

            //On utilise la formule de Héron pour calculer l'aire des triangles
            double p = (a + b + c) / 2;
            areaSum += Math.sqrt(p * (p - a) * (p - b) * (p - c));
        }

        return areaSum <= this.area;
    }

    private boolean segmentIntersectsCircle(double xA, double yA, double xB, double yB, double centerX, double centerY, double radius) {
        double xAB = xB - xA;
        double yAB = yB - yA;
        double dAB = xAB * xAB + yAB * yAB;
        double u   = ((centerX - xA) * xAB + (centerY - yA) * yAB) / dAB;
        //Le point issu de la projection du centre du cercle sur le segment
        double xC = xA + u * xAB;
        double yC = yA + u * yAB;

        double dx       = xC - centerX;
        double dy       = yC - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > radius) {
            //Le segment ne peut pas rentrer en collision avec le cercle car trop éloigné
            return false;
        } else {
            double AC = Math.sqrt((centerX - xA) * (centerX - xA) + (centerY - yA) * (centerY - yA));
            double BC = Math.sqrt((centerX - xB) * (centerX - xB) + (centerY - yB) * (centerY - yB));

            if (AC <= radius || BC <= radius) {
                return true;
            } else {
                double xAC = xC - xA;
                double yAC = yC - yA;
                //Produit scalaire
                double kAC = xAB * xAC + yAB * yAC;
                double kAB = xAB * xAB + yAB * yAB;
                //Si le produit scalaire est négatif ou supérieur à celui du segment avec lui même, le point C est en dehors du segment
                return kAC >= 0 && kAC <= kAB;
            }
        }
    }

    public double[][] getPoints() {
        return points;
    }

    public double getArea() {
        return area;
    }

}
