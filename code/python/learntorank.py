from sklearn.externals.joblib import Memory
from sklearn.datasets import load_svmlight_file
mem = Memory("./mycache")

@mem.cache
def get_data():
    data = load_svmlight_file("./inputFile_Ranking_libsvm")
    return data[0], data[1]

X, y = get_data()
similarity = X.dot(X.T).todense()

