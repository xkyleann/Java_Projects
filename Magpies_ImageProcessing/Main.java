
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Arrays;


// P1 --> To this end, prepare the private method LoadImage that loads the image from the file, you may use the command img = ImageIO.read(new File("file_name.tif"));
public class Image 
{
    private static int number_of_magpies = 0;
    private static String[] coords_of_magpies = new String[0];
	public static void main (String[] args) throws IOException
    {
        BufferedImage ref_image = LoadImage("./ref_image.tif");
        BufferedImage test_image = LoadImage("./test_image.tif");
        if (ref_image != null && test_image != null)
        {
            int[][] ref_image_array = ImageToArray(ref_image);
            int[][] test_image_array = ImageToArray(test_image);
            GetMagpies(ref_image_array, test_image_array);
            BufferedImage clear_image = ClearImage(ref_image_array, test_image_array);
            ImageIO.write(clear_image, "TIF", new File("./clear_image.tif"));
        }
	}

    private static BufferedImage LoadImage(String filename) throws IOException
    {
        try
        {
            BufferedImage image;
            image = ImageIO.read(new File(filename));
            return image;
        } 
        catch (IOException error)
        {
            System.out.println(error.getLocalizedMessage());
            return null;
        }
    }

    private static int[][] ImageToArray(BufferedImage image)
    {
        int[][] image_array = new int[image.getHeight()][image.getWidth()];
        for (int m = 0; m < image.getHeight(); m++)
        {
            for (int n = 0; n < image.getWidth(); n++)
            {
                Color color = new Color(image.getRGB(n, m));
                if (color.getRed() == 0 && color.getGreen() == 0 && color.getBlue() == 0) //for the pixels 
                {
                    image_array[m][n] = 0;
                }
                else
                {
                    image_array[m][n] = 1;
                }
            }
        }
        return image_array;
    }

    public static void GetMagpies(int[][] ref_image_array, int[][] test_image_array)
    {
        boolean controlFlag = true; boolean magpieFound = false;
        for (int m = 0; m < test_image_array.length - ref_image_array.length; m++)
        {
            for (int n = 0; n < test_image_array[0].length - ref_image_array[0].length; n++)
            {
                if (magpieFound == true)
                {
                    magpieFound = false;
                    number_of_magpies++;
                    coords_of_magpies = Arrays.copyOf(coords_of_magpies, coords_of_magpies.length + 1);
                    coords_of_magpies[coords_of_magpies.length - 1] = m + "," + (n - 1);
                }
                controlFlag = true;
                for (int b = 0; b < ref_image_array.length; b++)
                {
                    if (controlFlag == false)
                    {
                        magpieFound = false;
                        break;
                    }
                    for (int v = 0; v < ref_image_array[0].length; v++)
                    {
                        if (ref_image_array[b][v] == 1)
                        {
                            if (test_image_array[m + b][n + v] != ref_image_array[b][v])
                            {
                                controlFlag = false;
                                break;
                            }
                            else
                            {
                                magpieFound = true;
                            }
                        }
                 }
            }
        }
     }
        System.out.println("Number of Magpies that found: " + number_of_magpies);
}
// P7 -->  Prepare the public method DisplayImage that creates and saves an array as an image.      
    public static BufferedImage ClearImage(int[][] ref_image_array, int[][] image_array)
    {
        BufferedImage clear_image = new BufferedImage(image_array[0].length, image_array.length, BufferedImage.TYPE_BYTE_GRAY);
        int[][] clear_image_array = ImageToArray(clear_image);
        for (int m = 0; m < number_of_magpies; m++)
        {
            int width = Integer.parseInt(coords_of_magpies[m].split(",")[0]);
            int height = Integer.parseInt(coords_of_magpies[m].split(",")[1]);
            for (int n = width; n < width + ref_image_array.length; n++)
            {
                for (int b = height; b < height + ref_image_array[0].length; b++)
                {
                    if (ref_image_array[n - width][b - height] == 1)
                    {
                        clear_image_array[n][b] = 1;
                    }
                }
            }
        }
        clear_image = DisplayImage(clear_image_array);
        return clear_image;
    }

    // Writing image  
    public static BufferedImage DisplayImage(int[][] image_array)
    {
        BufferedImage image = new BufferedImage(image_array[0].length, image_array.length, BufferedImage.TYPE_BYTE_GRAY);
        for (int m = 0; m < image_array[0].length; m++)
        {
            for (int n = 0; n < image_array.length; n++)
            {
                int value = 0;
                if (image_array[n][m] == 1)
                {
                    value = 255;
                }
                Color color = new Color(value, value, value);
                int RGB = color.getRGB();
                image.setRGB(m, n, RGB);
            }
        }
        return image;
    }
} 

// END