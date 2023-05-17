## OOP with Physics Tasks
- Combining JAVA with Physics

1. **_Java class representing a system of N point electric charges, statically distributed in 3D space._**

	```Java
	public class ElectricChargeSystem {
    private PointCharge[] charges; // Array to store the charges

    public ElectricChargeSystem(int numCharges) {
        charges = new PointCharge[numCharges];
    }

    public void addCharge(PointCharge charge, int index) {
        if (index >= 0 && index < charges.length) {
            charges[index] = charge;
        } else {
            throw new IllegalArgumentException("Invalid index for adding charge");
        }
    }

    public double calculateElectricFieldAt(Point3D point) {
        Vector3D electricField = new Vector3D();

        for (PointCharge charge : charges) {
            Vector3D chargePosition = charge.getPosition();
            double chargeValue = charge.getValue();

            Vector3D displacement = chargePosition.subtract(point);
            double distanceSquared = displacement.magnitudeSquared();

            double electricFieldMagnitude = (chargeValue / (4 * Math.PI * Constants.EPSILON_0 * distanceSquared))
                    * displacement.magnitude();

            Vector3D electricFieldComponent = displacement.normalize().multiply(electricFieldMagnitude);
            electricField = electricField.add(electricFieldComponent);
        }

        return electricField.magnitude(); } 
       }
  
  1. **_Based on the documentation of Graphics and Graphics2D classes and tutorial 2D Graphics add to your class a method which creates a PNG file illustrating the position of all the charges in the x-y plane, the charge size should be proportional to its value, and the color should indicate if it is positive or negative._**
  ```Java
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ElectricChargeSystem {
    // Rest of the class implementation...

    public void createChargeVisualization(String filename, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Calculate maximum charge value for proportional sizing
        double maxChargeValue = 0.0;
        for (PointCharge charge : charges) {
            maxChargeValue = Math.max(maxChargeValue, Math.abs(charge.getValue()));
        }

        // Iterate over charges and draw them on the image
        for (PointCharge charge : charges) {
            double chargeValue = charge.getValue();
            Point3D position = charge.getPosition();

            // Calculate size and color based on charge value
            int size = (int) Math.round(Math.abs(chargeValue) * 50 / maxChargeValue); // Adjust the scaling factor as needed
            Color chargeColor = chargeValue > 0 ? Color.RED : Color.BLUE;

            // Draw the charge as a filled circle
            int x = (int) Math.round(position.getX());
            int y = (int) Math.round(position.getY());
            int xCenter = x - size / 2;
            int yCenter = y - size / 2;

            g2d.setColor(chargeColor);
            g2d.fillOval(xCenter, yCenter, size, size);
        }

        // Save the image as a PNG file
        try {
            ImageIO.write(image, "png", new File(filename));
            System.out.println("Charge visualization saved as: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving charge visualization: " + e.getMessage());
        }

        // Dispose of the graphics context
        g2d.dispose();
    }
}
  
3. **_Print the values of the charges and the total charge in the system. Print the forces acting on each charge. Create an image showing the charges._**
```java
public class Main {
    public static void main(String[] args) {
        ElectricChargeSystem system = new ElectricChargeSystem(3);

        // Add charges to the system
        system.addCharge(new PointCharge(1.5, new Point3D(0, 0, 0)), 0);
        system.addCharge(new PointCharge(-2.0, new Point3D(1, 1, 0)), 1);
        system.addCharge(new PointCharge(0.5, new Point3D(-1, 2, 0)), 2);

        // Print charge values and total charge
        system.printChargeValues();
        System.out.println("Total Charge: " + system.calculateTotalCharge());

        // Print forces
        system.printForces();

        // Create charge visualization image
        system.createChargeVisualization("charge_visualization.png", 800, 600);
    }
}
```
