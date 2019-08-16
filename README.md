#INFO

The project was done in Spring boot. The source code is a intellij project and can be opened in it. To run the application, just run App.java


#PROBLEM STATEMENT

# Weather Server

Using the list of URLs and API keys below, create a REST endpoint to fetch and
aggregate weather information for a given city. The endpoint parameter is a
city name, and the output should be a JSON object with the average, standard
deviation and a list with all temperatures.

## Notes

- Your code should be extensible and performant for a bigger (~100) list of
  APIs.
- Consider constraints and possible improvements to this problem. Feel free to
  add comments to the relevant code parts where these come into play.

## Example

```
$ curl 'http://localhost:port/api/weather?city=San+Francisco'
{
  temp: 20.3,
  std: 0.2,
  all: [
    20.1,
    20.5
  ]
}
```

## URLs

```
URLS = ((
    'http://api.openweathermap.org/data/2.5/weather?q={city}&APPID={key}&units=imperial',
    '40e80daa66514dfaab21c98a003d08e7',
), (
    'http://api.wunderground.com/api/{key}/conditions/q/CA/{city}.json',
    '1e4ac434bb8e4f75',
))
```

