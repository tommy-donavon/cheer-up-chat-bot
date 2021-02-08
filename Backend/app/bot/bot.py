import tflearn

class Bot():
    def __init__(self, training, output):
        self.net = tflearn.input_data(shape=[None, len(training[0])])
        self.net = tflearn.fully_connected(self.net, 8)
        self.net = tflearn.fully_connected(self.net, 8)
        self.net = tflearn.fully_connected(self.net, len(output[0]), activation="softmax")
        self.net = tflearn.regression(self.net)
        self.model = tflearn.DNN(self.net)