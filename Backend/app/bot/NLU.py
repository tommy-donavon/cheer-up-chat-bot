import nltk
nltk.download('punkt')
from nltk.stem.lancaster import LancasterStemmer
stemmer = LancasterStemmer()

def tokenize(sentence: str):
    """
        Breaks a pattern of words down into an array of individual words 
    """
    return nltk.word_tokenize(sentence)
    
def word_bag(token_sentence, words):
    """
        proccesses text and comapres it to existing word data and prepares a list to show bot
    """
    token_sentence = [stem(w) for w in token_sentence]
    bag = [0 for _ in range(len(words))]
    for i, word in enumerate(words):
        if word in token_sentence:
            bag[i] = 1

    return bag

def stem(word: str):
    """
        Casts a word to lowercase and breaks it down to root.
    """
    return stemmer.stem(word.lower())
