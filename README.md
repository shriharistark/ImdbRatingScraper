# ImdbRatingScraper

Returns the IMDB rating for a movie.
Will return most relevant ratings.

Input : [Movie name]
| Output : Ratings of all matched movies


More info .. 
 * Parsejson obtains JSON for the input movie and parses 'Ids' from that corresponding JSON
 * Using the ids an url is built and same is requested
 * The Rating value is scraped from the DOCUMENT
