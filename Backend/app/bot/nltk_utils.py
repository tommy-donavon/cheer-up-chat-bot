import nltk
import numpy
nltk.download('punkt')
from nltk.stem.lancaster import LancasterStemmer
stemmer = LancasterStemmer()

def tokenize(sentence: str):
    return nltk.word_tokenize(sentence)

def stem(word: str):
    return stemmer.stem(word.lower())

def word_bag(token_sentence, words):
    token_sentence = [stem(w) for w in token_sentence]
    bag = [0 for _ in range(len(words))]
    for word in token_sentence:
        for index, w in enumerate(words):
            if w == word:
                bag[index] = 1
    return numpy.array(bag)