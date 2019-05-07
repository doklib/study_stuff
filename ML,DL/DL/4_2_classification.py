import tensorflow as tf
import numpy as np

# [털, 날개]
x_data = np.array([
    [0, 0], [1, 0], [1, 1], [0, 0], [0, 0], [0, 1]
])


# 기타 = [1, 0, 0]
# 포유류 = [0, 1, 0]
# 조류 = [0, 0, 1]

y_data = np.array([
    [1, 0, 0],
    [0, 1, 0],
    [0, 0, 1], 
    [1, 0, 0],
    [1, 0, 0],
    [0, 0, 1]
])

# [털, 날개] -> [기타, 포유류, 조류]
#[0, 0] -> [1, 0, 0] # 기타
#[1, 0] -> [0, 1, 0] # 포유류
#[1, 1] -> [0, 0, 1] # 조류
#[0, 0] -> [1, 0, 0] # 기타
#[0, 0] -> [1, 0, 0] # 기타
#[0, 1] -> [0, 0, 1] # 조류

X = tf.placeholder(tf.float32)
Y = tf.placeholder(tf.float32)

W = tf.Variable(tf.random_uniform([2, 3], -1., 1.))
b = tf.Variable(tf.zeros([3]))

L = tf.add(tf.matmul(X, W), b)
L = tf.nn.relu(L)

model = tf.nn.softmax(L)

cost = tf.reduce_mean(-tf.reduce_sum(Y * tf.log(model), axis=1))

#Gradient Descent
optimizer = tf.train.GradientDescentOptimizer(learning_rate=0.01)
train_op = optimizer.minimize(cost)

# session
init = tf.global_variables_initializer()
sess = tf.Session()
sess.run(init)

# train100
for step in range(100):
    sess.run(train_op, feed_dict={X: x_data, Y: y_data})

    #cost 10 by 1
    if (step + 1) % 10 == 0:
        print(step + 1, sess.run(cost, feed_dict={X: x_data, Y: y_data}))

prediction = tf.argmax(model, axis=1)
target = tf.argmax(Y, axis=1)
print('예측값:', sess.run(prediction, feed_dict={X: x_data}))
print('실제값:', sess.run(target, feed_dict={Y: y_data}))

is_correct = tf.equal(prediction, target)
accuracy = tf.reduce_mean(tf.cast(is_correct, tf.float32))
print('정확도: %.2f' % sess.run(accuracy * 100, feed_dict={X: x_data, Y: y_data}))

##
W1 = tf.Variable(tf.random_uniform([2, 10], -1., 1.))
W2 = tf.Variable(tf.random_uniform([10, 3], -1., 1.))

b1 = tf.Variable(tf.zeros([10]))
b2 = tf.Variable(tf.zeros([3]))

L1 = tf.add(tf.matmul(X, W1), b1)
L1 = tf.nn.relu(L1)

model = tf.add(tf.matmul(L1, W2), b2)

cost = tf.reduce_mean(
    tf.nn.softmax_cross_entropy_with_logits(labels=Y, logits=model))

optimizer = tf.train.AdamOptimizer(learning_rate=0.01)
train_op = optimizer.minimize(cost)

##
init = tf.global_variables_initializer()
sess = tf.Session()
sess.run(init)

# train100
for step in range(100):
    sess.run(train_op, feed_dict={X: x_data, Y: y_data})

    #cost 10 by 1
    if (step + 1) % 10 == 0:
        print(step + 1, sess.run(cost, feed_dict={X: x_data, Y: y_data}))

prediction = tf.argmax(model, axis=1)
target = tf.argmax(Y, axis=1)
print('예측값:', sess.run(prediction, feed_dict={X: x_data}))
print('실제값:', sess.run(target, feed_dict={Y: y_data}))

is_correct = tf.equal(prediction, target)
accuracy = tf.reduce_mean(tf.cast(is_correct, tf.float32))
print('정확도: %.2f' % sess.run(accuracy * 100, feed_dict={X: x_data, Y: y_data}))