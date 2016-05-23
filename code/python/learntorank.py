from sklearn.externals.joblib import Memory
from sklearn.datasets import load_svmlight_file
mem = Memory("./mycache")

@mem.cache
def get_data():
    data = load_svmlight_file("/home/sumit/recommendersystemchallenge/data/output/input.similarity")
    return data[0], data[1]

X, y = get_data()

for i,row in enumerate(X):
        for j,row in enumerate(X):
                dotproduct = X[i].dot(X[j].T)
                norm1 = X[i].dot(X[i].T)
                norm2 = X[j].dot(X[j].T)
                sqrtNorms = np.sqrt(norm1.dot(norm2))
                print(dotproduct)
                print(norm1)
                print(norm2)
                print(np.sqrt(norm1.dot(norm2)))
                print(dotproduct/sqrtNorms)

for i,row in enumerate(X):
	for j,row in enumerate(X):
		intersectV = np.intersect(X[i],X[j])
		print(intersect)
