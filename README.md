# Movie Analyser
Project created to explore and use data involving top250 movies and its ratings
## How it was conducted
1. **Data extraction:** \
   using java and selenium I scraped data from 3 websites https://www.imdb.com/, https://www.rottentomatoes.com/ and https://www.metacritic.com/.
2. **Database creation:**\
   using SQL server I created local database and combined all data into one table:
   
  ![db](https://github.com/user-attachments/assets/6e9867d0-cf03-4a48-bafd-5b6b0d9a3ade)
3. **Data analysis:** \
   SQL: 
   - top 5 directors by average rating \
     ![query1](https://github.com/user-attachments/assets/b4178c81-dc4b-497d-8a77-3a39d982f1df)
     ![top5directors](https://github.com/user-attachments/assets/c83ebbda-1dcd-44a7-a35a-00889fd96a8e)
   - top 10 movies \
     ![query2](https://github.com/user-attachments/assets/4689f069-7187-4880-83d0-537ce5dcbe40)
     ![top10byRating](https://github.com/user-attachments/assets/8591e437-1a94-4d65-8138-66c261c7b06c)
  matplotlib:
  - average rating by decade: \
    ![decade_rating](https://github.com/user-attachments/assets/a0e29567-53da-4a34-a8ed-68d6fb1dd640)
  - correlation of features: \
    ![corr](https://github.com/user-attachments/assets/02a0c63a-9757-45f8-9dff-62fb42117a9f)
    **we can see that rtMetaRating is strongly correlated with average rating so we can assume that it is the most objective one** \
    **also meta ratings tend to be higher for older films**
4. **Data usage:**\
    I created gui based search engine using java, swing:
    

![java_gui](https://github.com/user-attachments/assets/b380804d-b3c5-4314-b19c-620253233f3a)


