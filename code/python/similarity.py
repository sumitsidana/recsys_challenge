from sklearn.externals.joblib import Memory
from sklearn.datasets import load_svmlight_file
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.datasets import dump_svmlight_file
import numpy as np
import math
mem = Memory("./mycache")

@mem.cache
def get_data():
    data = load_svmlight_file("/home/ama/sidana/recommendersystemchallenge//output/input.similarity")
    return data[0], data[1]

X, y = get_data()

nrows = X.shape[0]

step_sizeDouble = nrows/75#convert to int
step_size = math.floor(step_sizeDouble)

count = 0
while (count < nrows):
    if count + step_size>nrows:
        submatrix = X[count:nrows:1]
        b = y[count:nrows:1]
        similarity = cosine_similarity(submatrix,X)
        print("File corresponding to row number: "+str(nrows)+"being written")
        print(similarity.shape)
        dump_svmlight_file(similarity, b,
                       '/home/ama/sidana/recommendersystemchallenge/output/user_similarity_'
                       +str(nrows), zero_based=False)
        break
    submatrix = X[count:count+step_size:1]
    b = y[count:count+step_size:1]
    similarity = cosine_similarity(submatrix,X)
    print("File corresponding to row number: "+str(count)+"being written")
    print(similarity.shape)
    #b = np.zeros(similarity.shape[0])
    dump_svmlight_file(similarity, b,
                       '/home/ama/sidana/recommendersystemchallenge/output/user_similarity_'
                       +str(count), zero_based=False)
    count = count + step_size
b = y[nrows,:]
submatrix = X[nrows,:]
similarity = cosine_similarity(submatrix,X)
print("File corresponding to row number: "+str(nrows+1)+"being written")
print(similarity.shape)
dump_svmlight_file(similarity, b,
                       '/home/ama/sidana/recommendersystemchallenge/output/user_similarity_'
                       +str(nrows+1), zero_based=False)
print("done")
