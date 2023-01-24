# Evolution 1: more validations to do

## Outline

The application is proving to be a success, that is great! We have seen one more problem with the sensors though. It
seems that some combinations of firmware and configuration are creating weird behaviour in the digital input and output
of the sensor.

The values of input and output are sent in messages to an endpoint for further processing. Before installation and
before we apply a customer specific configuration, the firmware is programmed to periodically send all the different
combinations of the inputs. So every 30 seconds, a different combination is being sent. There are 4 different
combinations:

|Digital input |Digital output|
|--------------|--------------|
|0|0 |
|0|1 |
|1|1 |
|1|0 |

So that means that every 4 minutes, all of the combinations should have been sent in a message. These messages are stored
in the "messages" tables. The structure of the table is as following:

```markdown
message_id: UUID, this is also the primary key
sender_id: UUID
message_time: timestamp
din: int
dout: int
```

These messages end up in our database. In order to see if the digital input and output is working correctly, you can
query the database to see and check if all combinations are there for a certain period of time. 

**Here is the challenge of the first evolution**: Adjust the application so that it can also validate the digital input and
digital output of the sensors.


## Summary of the challenge
* For each device, fetch enough messages to see if all digital input/digital output combinations has been formed.
* If not all the combinations have been formed, the sensor can be marked as 'invalid'.
* Add the results to the already existing results. 

## Hints: 
* you might think that you need an actual database to do this. But you don't. Anything that resembles a database will do. In other words, you can use any datastructure and pretend it be a database. 


