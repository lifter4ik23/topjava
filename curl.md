##curl commands

####get all meals
`curl -s http://localhost:8080/topjava/rest/meals`

####get filtered
`curl -s "http://localhost:8080/topjava/rest/meals/filter?startDate=2015-05-30&startTime=13:00&endDate=2015-05-31&endTime=18:00"`

####get by id
`curl -s http://localhost:8080/topjava/rest/meals/100004`

####delete by id
`curl -s -X DELETE http://localhost:8080/topjava/rest/meals/100004`

####update meal
`curl -s -X PUT -d '{"dateTime":"2018-11-25T20:10", "description":"updated meal","calories":1200}' -H 'Content-Type:application/json' http://localhost:8080/topjava/rest/meals/100005`

####create meal
`curl -s -X POST -d '{"dateTime":"2018-11-25T20:00","description":"new meal","calories":1200}' -H 'Content-Type:application/json' http://localhost:8080/topjava/rest/meals`