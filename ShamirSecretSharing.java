import java.io.*;
import java.util.*;

public class ShamirSecretSharing 
{
    public static void main(String[] args) 
	{
        try 
		{
            // reading json file which has input
            String jsonFilePath = "input.json"; 
            String jsonString = new String(Files.readAllBytes(new File(jsonFilePath).toPath()));

            Map<String, Object> inputData = parseJSON(jsonString);
            Map<String, Integer> keys = (Map<String, Integer>) inputData.get("keys");

            int n = keys.get("n");
            int k = keys.get("k");
            List<Point> points = new ArrayList<>();
            for (int i = 1; i <= n; i++) 
			{
                if (!inputData.containsKey(String.valueOf(i))) continue;
                Map<String, String> pointData = (Map<String, String>) inputData.get(String.valueOf(i));

                int base = Integer.parseInt(pointData.get("base"));
                int value = Integer.parseInt(pointData.get("value"), base);
                points.add(new Point(i, value));
            }
            if (points.size() < k) 
			{
                throw new IllegalArgumentException("Not enough points to compute the polynomial.");
            }

            // Finding constant term c 
            int constantTerm = findConstantTerm(points, k);
            System.out.println("The constant term (secret): " + constantTerm);

        } 
		catch (Exception e) 
		{
            e.printStackTrace();
        }
    }

    private static int findConstantTerm(List<Point> points, int k) 
	{
     double result = 0;
        for (int i = 0; i < k; i++) 
		{
            double term = points.get(i).y;
            for (int j = 0; j < k; j++) 
			{
                if (i != j) 
				{
                    term *= (0 - points.get(j).x) / (double) (points.get(i).x - points.get(j).x);
                }
            }
            result += term;
        }
        return (int) Math.round(result);
    }
    private static Map<String, Object> parseJSON(String json) 
	{
        return new HashMap<>();
    }

    static class Point 
	{
        int x, y;
        Point(int x, int y) 
		{
            this.x = x;
            this.y = y;
        }
    }
}
