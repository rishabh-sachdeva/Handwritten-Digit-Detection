package kmeans_hadoop.mapReduce;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class KMeans {
	private static int num_clusters;
	private static int dimensionality;
	public static float[][] centroids ;
	public static void main(String[] args) throws Exception {
		int num_clusters = Integer.parseInt(args[0]);
		int dim = Integer.parseInt(args[1]);
		String input_path = args[2];
		String output_path = args[3];
		KMeans.setDimensionality(dim);
		KMeans.setNum_clusters(num_clusters);
		float[][] old_centroids = new float[num_clusters][dimensionality];
		initializeCentroids();
		while(!ifCentroidConverge(old_centroids,centroids)) {
			copyCentroidElements(old_centroids,centroids);
			Configuration conf = new Configuration();	
			Job job = Job.getInstance(conf, "k-means-mapReduce");
			job.setJarByClass(KMeans.class);
			job.setMapperClass(KMeansMapper.class);
			job.setCombinerClass(KMeansReducer.class);
			job.setReducerClass(KMeansReducer.class);
			job.setMapOutputKeyClass(IntWritable.class);
			job.setMapOutputValueClass(Text.class);
			job.setOutputKeyClass(IntWritable.class);
			job.setOutputValueClass(Text.class);
			deleteOutputDirectoryIfExist(conf,output_path);
			//FileInputFormat.addInputPath(job, new Path("/home/rishabh/Desktop/map-reduceinput"));
			FileInputFormat.addInputPath(job, new Path(input_path));
			FileOutputFormat.setOutputPath(job, new Path(output_path));
			//FileOutputFormat.setOutputPath(job, new Path("/home/rishabh/hadoop-workspace/mapReduce/output"));
			job.waitForCompletion(true);
		}
	}
	
	private static void copyCentroidElements(float[][] old_centroids, float[][] centroids) {
		for(int i =0;i<num_clusters;i++) {
			for(int j=0;j<dimensionality;j++) {
				old_centroids[i][j]=centroids[i][j];
			}
		}
	}
	public static void initializeCentroids() throws IOException{
			//initiate centroids for the first iteration only
			KMeans.centroids = new float[num_clusters][dimensionality];
			for(int i=0;i<num_clusters;i++) { 
				for(int j=0;j<dimensionality;j++) {
					KMeans.centroids[i][j]=i+1;
				}
		}
	}
	private static void deleteOutputDirectoryIfExist(Configuration conf, String output_path) throws IOException {

		Path outDir = new Path(output_path);
		FileSystem outFs = outDir.getFileSystem(conf);
		outFs.delete(outDir, true);
	}
	
	private static boolean ifCentroidConverge(float[][] old_centroids, float[][] centroids) {
		if(old_centroids==null ) {
			return false;
		}
		if(old_centroids.length != centroids.length) {
			return false;
		}
		for(int i =0;i<old_centroids.length;i++) {
			for(int j=0;j<centroids[0].length;j++) {
				if(old_centroids[i][j]!=centroids[i][j]) {
					//change detected, return false;
					return false;
				}
			}
		}

		return true;
	}
	public static void setNum_clusters(int num_cluster) {
		num_clusters = num_cluster;
	}
	public static int getNum_clusters() {
		return num_clusters;
	}
	public static void setDimensionality(int dimension) {
		dimensionality = dimension;
	}
	public static int getDimensionality() {
		return dimensionality;
	}
}
