public class ScientificCalculator {
    // true = Rad, false = Deg
    private boolean radiansMode = true; 

    // false = normal, true = Inv
    private boolean inverseMode = false; 

    public void setRadiansMode(boolean radiansMode) {
        this.radiansMode = radiansMode;
    }

    public boolean isRadiansMode() {
        return radiansMode;
    }

    public void setInverseMode(boolean inverseMode) {
        this.inverseMode = inverseMode;
    }

    public boolean isInverseMode() {
        return inverseMode;
    }

    public boolean toggleInverseMode() {
        inverseMode = !inverseMode;
        return inverseMode;
    }

    public double pi() {
        return Math.PI;
    }

    // Operations based on current Inv state
    public double trigSin(double value) {
        if (inverseMode) {
            return asin(value);
        }
        return sin(value);
    }

    public double trigCos(double value) {
        if (inverseMode) {
            return acos(value);
        }
        return cos(value);
    }

    public double trigTan(double value) {
        if (inverseMode) {
            return atan(value);
        }
        return tan(value);
    }

    public double rootOrSquare(double value) {
        if (inverseMode) {
            return sqr(value);
        }
        return sqrt(value);
    }

    // Operations is radians or degrees based on mode
    public double sin(double angle) {
        double rad;
        if (radiansMode) {
            rad = angle;
        } else {
            rad = Math.toRadians(angle);
        }
        return cleanNearZero(Math.sin(rad));
    }

    public double cos(double angle) {
        double rad;
        if (radiansMode) {
            rad = angle;
        } else {
            rad = Math.toRadians(angle);
        }
        return cleanNearZero(Math.cos(rad));
    }

    public double tan(double angle) {
        if (!radiansMode && Math.abs(angle % 180.0) == 90.0) {
            return Double.NaN;
        }

        double rad;
        if (radiansMode) {
            rad = angle;
        } else {
            rad = Math.toRadians(angle);
        }

        double result = Math.tan(rad);
        if (Math.abs(result) > 1e12) {
            return Double.NaN;
        }
        return cleanNearZero(result);
    }

    // Inverse operations radians or degrees based on mode
    public double asin(double x) {
        if (x < -1.0 || x > 1.0 || Double.isNaN(x)) {
            return Double.NaN;
        }

        double rad = Math.asin(x);
        double output;
        if (radiansMode) {
            output = rad;
        } else {
            output = Math.toDegrees(rad);
        }
        return cleanNearZero(output);
    }

    public double acos(double x) {
        if (x < -1.0 || x > 1.0 || Double.isNaN(x)) {
            return Double.NaN;
        }

        double rad = Math.acos(x);
        double output;
        if (radiansMode) {
            output = rad;
        } else {
            output = Math.toDegrees(rad);
        }
        return cleanNearZero(output);
    }

    public double atan(double x) {
        if (Double.isNaN(x)) {
            return Double.NaN;
        }

        double rad = Math.atan(x);
        double output;
        if (radiansMode) {
            output = rad;
        } else {
            output = Math.toDegrees(rad);
        }
        return cleanNearZero(output);
    }

    public double sqrt(double x) {
        if (x < 0.0 || Double.isNaN(x)) {
            return Double.NaN;
        }
        return Math.sqrt(x);
    }

    public double sqr(double x) {
        if (Double.isNaN(x)) {
            return Double.NaN;
        }
        return x * x;
    }

    // Prevents floating point errors for sin(PI) or cos(90 deg)
    private double cleanNearZero(double value) {
        if (Math.abs(value) < 1e-12) {
            return 0.0;
        }
        return value;
    }
}