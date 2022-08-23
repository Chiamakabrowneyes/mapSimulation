public class inp {
    public static void inputLatLong() throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("LatLong.txt"));
        writer.write(String.valueOf(new double[] {37.785191918157246, -122.41031518470686}));
        System.out.println(writer);

    }
}
