import pandas as pd
import numpy as py
import matplotlib.pyplot as plt
from sklearn.naive_bayes import MultinomialNB
from sklearn.linear_model import LogisticRegression
from sklearn.neural_network import MLPClassifier
from sklearn.model_selection import cross_validate

# Reads csv and parses through the data
training = pd.read_csv("../Data/train.csv")
x_test = pd.read_csv("../Data/test.csv")
y_test = pd.read_csv("../Data/sample_predictions.csv")
y_test.loc[y_test['Choice'] >= .50, 'Choice'] = 1
y_test.loc[y_test['Choice'] < .50, 'Choice'] = 0
y_test = y_test['Choice']
#y_test = list(y_test)
#print(y_test)
#print(y_test.head())
#print(y_test.tail())

#Break training data into x (training features) and y (labels)
x_train = training.drop("Choice", axis=1)
y_train = training["Choice"]

# Model set up
NBmodel = MultinomialNB()
MLPmodel = MLPClassifier(solver='lbfgs', alpha=1e-5, hidden_layer_sizes=(5, 2), random_state=1)
LRmodel = LogisticRegression(random_state=0, solver='lbfgs', max_iter=1000, multi_class='multinomial')

#Model training and prediction

#Naive Bayes

NBcv = cross_validate(NBmodel, x_train, y_train, cv=5)
print("Naive Bayes: Analytics on training data: ")
print(NBcv)
NBpred = NBmodel.fit(x_train, y_train).predict(x_test)
NBAccuracy = 1 - ((y_test != NBpred).sum() / float(y_test.count()))
print("Naive Bayes: Number of mislabeled points out of a total %d points : %d"
      % (y_test.count(),(y_test != NBpred).sum()))
print("Naive Bayes: Accuracy on test data: " + str(NBAccuracy))

# PerceptronThe ability to detect who the influencers are is vital for
# advertising agencies. The reason this information is so important for advertisers is because
# influencers have a large following of dedicated fans that view their content on a daily basis.
# Being able to get an influencer to display some type of product or clothing item allows the item
# to be shown to thousands and or millions of people. The bigger the influencer, the larger the
# exposure of the item.
MLPcv = cross_validate(MLPmodel, x_train, y_train, cv=5)
print("Perceptron: Analytics on training data: ")
print(MLPcv)
MLPpred = MLPmodel.fit(x_train, y_train).predict(x_test)
MLPAccuracy = 1 - ((y_test != MLPpred).sum() / float(y_test.count()))
print("Perceptron: Number of mislabeled points out of a total %d points : %d"
      % (y_test.count(),(y_test != MLPpred).sum()))
print("Perceptron: Accuracy on test data: " + str(MLPAccuracy))


# Logistic Regression
LRcv = cross_validate(LRmodel, x_train, y_train, cv=5)
print("Logistic Regression: Analytics on training data: ")
print(LRcv)
LRpred = LRmodel.fit(x_train, y_train).predict(x_test)
LRAccuracy = 1 - ((y_test != LRpred).sum() / float(y_test.count()))
print("Logistic Regression: Number of mislabeled points out of a total %d points : %d"
      % (y_test.count(),(y_test != LRpred).sum()))
print("Logistic Regression: Accuracy on test data: " + str(LRAccuracy))
