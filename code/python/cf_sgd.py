
# coding: utf-8

# In[ ]:

import numpy as np
import pandas as pd
from numpy import zeros
import operator

interactions = pd.read_csv('/home/ama/sidana/recommendersystemchallenge/data/data/interactions.csv',header = 0,sep='\t')
valid_interactions = interactions[interactions['interaction_type'].isin([1,2,3])]


# In[ ]:

aggregated = valid_interactions[['user_id','item_id']].groupby(['user_id','item_id'],as_index = False).size()
aggregated.name = 'count'
rating_df = valid_interactions[['user_id','item_id']].join(aggregated,on=['user_id','item_id'])
rating_df = rating_df.drop_duplicates()

aggregated_users = valid_interactions[['user_id']].groupby(['user_id',],as_index = False).size()
aggregated_users.name = 'count'
rating_df_users = valid_interactions[['user_id']].join(aggregated_users,on=['user_id'])
rating_df_users = rating_df_users.drop_duplicates()

aggregated_items = valid_interactions[['item_id']].groupby(['item_id'],as_index = False).size()
aggregated_items.name = 'count'
rating_df_items = valid_interactions[['item_id']].join(aggregated_items,on=['item_id'])
rating_df_items = rating_df_items.drop_duplicates()


# In[ ]:

unique_users = pd.unique(rating_df.user_id.ravel()).size
unique_items = pd.unique(rating_df.item_id.ravel()).size
print(unique_users)
print(unique_items)


# In[ ]:

userList = rating_df_users['user_id'].tolist()
itemList = rating_df_items['item_id'].tolist()


# In[ ]:

from collections import defaultdict
userIndex = {}
indexUser = {}
i = 0
for user in userList:
        indexUser[i] = user
        userIndex[user] = i
        i = i + 1 


# In[ ]:

userValue = np.zeros((unique_users, 10))
for key1, row in indexUser.items():
    for i in range(0,10):
        userValue[key1, i] = 0.1


# In[ ]:

itemIndex = {}
indexItem = {}
i = 0
for item in itemList:
    indexItem[i] = item
    itemIndex[item] = i
    i = i + 1


# In[ ]:

itemValue = np.zeros((unique_items, 10))
for key1, row in indexItem.items():
    for i in range(0,10):
        itemValue[key1,i] = 0.1


# In[ ]:

lrate = 0.0005
for index,row in rating_df.iterrows():
    actualUser = row['user_id']
    actualItem = row['item_id']
    rating = row['count']
    
    item = itemIndex[actualItem]
    user = userIndex[actualUser]
    
    userVector = userValue[user,:]
    itemVector = itemValue[item,:]

    predictedRating = np.dot(userVector,itemVector)
    error = lrate*(rating - predictedRating)
    uv = np.add(userVector,error*itemVector)
    iv = np.add(itemVector,error*userVector)
    userValue[user,:] = uv
    itemValue[item,:] = iv
    
    


# In[ ]:

target_users = pd.read_csv('/home/ama/sidana/recommendersystemchallenge/data/target_users.csv')
target_items = pd.read_csv('/home/ama/sidana/recommendersystemchallenge/data/data/items.csv',delimiter='\t')

allItemsList = target_items['id'].tolist()

itemStatus = {}
for index, row in target_items.iterrows():
    actualItem = row['id']
    active_during_test = row['active_during_test']
    itemStatus[actualItem] = active_during_test
    
t_u = target_users['user_id'].tolist()

userScoresList = {}
unique_items = rating_df_items['item_id'].drop_duplicates().tolist()
for actualUser in t_u:
    if actualUser in userIndex:
        user = userIndex[actualUser]
        scoresList = {}
        for actualItem in unique_items:
            item = itemIndex[actualItem]
            if itemStatus.get(actualItem) == 1:
                score  = np.dot(userValue[user,:],itemValue[item,:])
                scoresList[item] = score
        sorted_x = sorted(scoresList.items(), key=operator.itemgetter(1),reverse=True)
        i = 0
        userScoresList[actualUser] = {}
        for item_score in sorted_x:
            if i == 0:
                userScoresList[actualUser] = item_score
            else:
                obj2 = list(userScoresList[actualUser])
                obj2.append(item_score)
                userScoresList[actualUser] = tuple(obj2)
            i = i + 1
            if i>29:
                break
    else:
        userScoresList[actualUser] = {1053452,2268722,1007923,2778525,1244196,1729618,657183,581327,2791339,1386412,2663343,1805804,1078303,536047,1053542,2432923,278589,79531,1928254,2446769,1984327,1583705,1133414,2242152,1742926,1201171,2532610,784737,1233470,830073}

out = open('/home/ama/sidana/recommendersystemchallenge/output/sgd' ,'w')
for key, value in userScoresList.items():
    out.write(str(key)+'\t')
    for item in value:
           out.write(str(item)+",")
    out.write('\n')
out.close()
        
            
            
        


# In[ ]:




# In[ ]:



