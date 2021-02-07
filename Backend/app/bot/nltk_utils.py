import nltk
nltk.download('punkt')
from nltk.stem.lancaster import LancasterStemmer
stemmer = LancasterStemmer()

def tokenize(sentence: str):
    return nltk.word_tokenize(sentence)

def stem(word: str):
    return stemmer.stem(word.lower())

def word_bag(token_sentence: str, words):
    token_sentence = [stem(w) for w in words]
    bag = [0 for _ in words]
    for index, word in enumerate(words):
        if word in token_sentence:
            bag[index] = 1
    return bag