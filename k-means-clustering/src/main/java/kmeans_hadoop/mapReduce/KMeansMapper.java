package kmeans_hadoop.mapReduce;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class KMeansMapper extends Mapper<Object, Text, IntWritable, Text>{
	int num_clusters= KMeans.getNum_clusters();
	int dimensionality = KMeans.getDimensionality();
	private final static String SPACE = " ";
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		float min_distance = Float.MAX_VALUE;
		StringTokenizer itr = new StringTokenizer(value.toString());
		String pixelElements[] = new String[dimensionality];
		for(int i =0;i<dimensionality;i++) {
			pixelElements[i]=itr.nextToken();
		}
		int centroid_index=-1;	
		for(int i =0;i<num_clusters;i++) {
			float distance=-1;
			for(int j=0;j<dimensionality;j++) {
				distance += Math.pow(KMeans.centroids[i][j]-Float.parseFloat(pixelElements[j]),2);
			}
			distance = (float) Math.sqrt(distance);//calculate euclidean distance
			if(distance<min_distance) {
				min_distance=distance;
				centroid_index=i;
			}
		}
		IntWritable winner_centroid = new IntWritable(centroid_index);
		Text pixels = new Text();
		
		for(int k =0;k<pixelElements.length;k++) {
			pixels.append(pixelElements[k].getBytes(), 0, pixelElements[k].getBytes().length);
			pixels.append(SPACE.getBytes(), 0, 1);
		}
		context.write(winner_centroid, pixels);
	}
}
