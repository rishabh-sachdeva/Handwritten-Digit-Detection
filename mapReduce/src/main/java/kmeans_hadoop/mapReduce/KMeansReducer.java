package kmeans_hadoop.mapReduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class KMeansReducer extends Reducer<IntWritable,Text,IntWritable,Text> {
	private final static String space = " ";
	int num_clusters= KMeans.getNum_clusters();
	int dimension = KMeans.getDimensionality();
	private String[] result = new String[dimension];
	public void reduce(IntWritable centroidKey, Iterable<Text> points,Context context) throws IOException, InterruptedException {
		int count=0;
		for(int i=0;i<result.length;i++) {
			result[i]= new String("0");
		}
		for (Text pixels : points) {
			count++;
			String[] indPixels = pixels.toString().split(" ");
			int i=0;
			for(String indPixel : indPixels) {
				result[i] = Float.toString(Float.parseFloat(result[i]) + Float.parseFloat(indPixel));
				i++;
			}
		}
		for(int i=0;i<result.length;i++) {
			float x=Float.parseFloat(result[i])/count;
			KMeans.centroids[Integer.parseInt(centroidKey.toString())][i] = x;
			result[i]= Float.toString(x);
		}
		Text centroidPixelsInText = new Text();
		prepareCentroidPixelsInText(centroidPixelsInText);
		context.write(centroidKey, centroidPixelsInText);
	}
	
	private void prepareCentroidPixelsInText(Text centroidPixelsInText) {
		for(int k =0;k<result.length;k++) {
			centroidPixelsInText.append(result[k].getBytes(), 0, result[k].getBytes().length);
			centroidPixelsInText.append(space.getBytes(), 0, 1);
		}
	}
}

