from matplotlib import pyplot as plt
import pandas as pd
import numpy as np

from connector import Connector

class Plotter():
    def __init__(self) -> None:
        self.connector = Connector()

        query = "SELECT * FROM MOVIES;"
        self.data = pd.read_sql(query, self.connector.connection)
        self.data["year"] = pd.to_numeric(self.data["year"])

    def plot_avg_decade_rating(self):
        bins = [x for x in range(1920, 2040, 10)]

        self.data["decade"] = pd.cut(self.data["year"], bins)

        average_ratings = self.data.groupby('decade')['avgRating'].mean()
        plt.figure()
        average_ratings.plot(kind='bar', edgecolor='black')
        plt.xlabel('Decade')
        plt.ylabel('Average Rating')
        plt.title('Average Rating Across Decades')
        plt.xticks(rotation=45)
        plt.show()
    
    def plot_genre_popularity(self):
        genre_list = self.data["genre"].to_list()
        flattened_genre_list = [genre for sublist in genre_list for genre in sublist.split()]
        
        frequency_dict = {}
        for genre in flattened_genre_list:
            if genre not in frequency_dict.keys():
                frequency_dict[genre] = 1
            else:
                frequency_dict[genre] += 1
        
        genres = frequency_dict.keys()
        freqs = frequency_dict.values()

        plt.figure()
        plt.title("genre frequency plot")
        plt.bar(genres, freqs)
        plt.xticks(rotation=45)
        plt.show()
    
    def display_correlation(self):
        corr = self.data.select_dtypes(include=np.number).corr()
        ticks = [i for i in range(len(self.data.select_dtypes(include=np.number).columns))]
        
        plt.figure()
        plt.title("column correlation")
        plt.matshow(corr)        
        plt.xticks(ticks=ticks, labels=self.data.select_dtypes(include=np.number).columns.to_list(), rotation=315)
        plt.yticks(ticks=ticks, labels=self.data.select_dtypes(include=np.number).columns.to_list())
        plt.show()

    def plot_imdbMetaRating_vs_year(self):
        ratings_dict = self.data.groupby('year')['imdbMetaRating'].mean().to_dict()
        ratings_dict.pop(1924)
        ratings_dict.pop(1925)
        ratings_dict.pop(1926)
        ratings_dict.pop(1927)

        years = ratings_dict.keys()
        ratings = ratings_dict.values()

        plt.figure()
        plt.title("imdbMetaRating vs year")
        plt.plot(years, ratings)
        plt.show()