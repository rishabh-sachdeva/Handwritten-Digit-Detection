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
