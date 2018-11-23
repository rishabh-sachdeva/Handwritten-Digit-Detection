# Handwritten-Digit-Detection

Digit-8-Detection_LogisticRegression:
A binary classification model to detect whether the digit is 8 or not. Stochastic Logistic regression algorithm is used to train MNIST dataset.
Regularization term is introduced to avoid overfitting.The Ideal Value of regularization term depends on data, so some tuning is required to 
get the correct balance between generalization and training data. The model should not learn too much peculiarities of training data, and
perform well with unseen data. With regularization, model achieves 95+ percent accuracy. 
The final achieved 28*28 weight vectors is visualized, and appears like a rough 8 like figure.he largest weight should map to dark colored pixels, 
the smallest weight to lighter ones, and the other weights should linearly interpolate between those extremes.
Given any feature vector, the more importance is given to those boundry pixels (darker ones in below image), and less to the lighter ones. Given any feature vector, 
hypthosis is calculated by multiplying a bigger factor to pixels of features which are darker, and smaller factor to other lighter ones. 

K-Means Clustering Using Hadoop:

K-means clustering is a classical clustering algorithm that uses an expectation maximization like technique to partition a number of data points into k clusters. 
K-means clustering is commonly used for a number of classification applications.  Because k-means is run on such large data sets, and because of certain characteristics of the algorithm, it is a good candidate for parallelization.


Termination Condition:
When the clusters converge,i.e. centroids doesnt change in two consecutive steps. 
	
Final Clusters thus recieved are visualized in jupyter notebook. The final clusters achieved by training on full MNIST data set
was 10, as expected. The final clusters are converted into 28*28 pixel format, and forms digit like figures. The visualization
shows the accuracy of model, and hence the model is capable of detecting any handwritten digit with high accuracy(96+ percent, as tested).

Project Specific notes:

1.This is a maven project.
To build and install project, run command in folder where pom.xml is located:
mvn clean install

2.Command to run program: 

General:
hadoop-3.1.1/bin/hadoop jar <jar with path> <package.className> <num_clusters> <dimenstion> <input_dir> <output_dir>

Specific to my program:
hadoop-3.1.1/bin/hadoop jar hadoop-workspace/mapReduce/target/mapReduce-0.0.1-SNAPSHOT.jar kmeans_hadoop.mapReduce.KMeans 5 784 hadoop-workspace/mapReduce/input/ hadoop-workspace/mapReduce/output/


3.JAVA-Files-Kmeans-with-Input-Output folder contains plain java files to refer. Also, it contains input.txt (mnist data), and output clusters
