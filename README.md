# Sensor validation

## Outline

You are part of an IoT company shipping sensors to a vast amount of clients. These sensors, which are of type TS50X,
have a firmware from their original manufacturer, which is pretty trustworthy. Your company, meaning you actually, you
have to configure these sensors so that they send valuable information which is of interest to your vast amount of
clients.

While testing the sensors with the latest configuration, we noticed a compatibility problem with older firmware. There
are new features which can be configured but some older firmware doesn't know how to deal with those new features. So
the sensors only do a part of what they should be doing. We can’t have that, our company stands for high quality and
happy customers.

**Here is the challenge**: You need to write some software to validate all the sensors of an incoming shipment. The
application takes a list of sensor id's and shows which sensors are ok and which are not ok. The application also shows
what is not ok if the sensor is not ready to be shipped.

The application needs to verify if these sensors have a firmware version compatible with the latest configuration
options. If the firmware is not compatible, you need to schedule a task to install the latest firmware over the
internet. The shipments of new sensors do not have the latest configuration either. So you need to also schedule a task
to install this configuration.

You have to realise that these sensors have 4G connections. They can have temporarily bad connections and so tasks can
be scheduled for firmware updates for a while. Before you schedule a new task, check if there isn’t one already. If
there is one and if it is older than 2 hours, you can reschedule.

The manufacturer of the sensors has an API which you can use to get information about the sensors and any ongoing tasks.
So you need to use his API to get the job done.

Endpoint to get sensor status:
GET # www.mysensor.io/api/sensors/{id}

Endpoint to get tasks by sensor:
GET # www.mysensor.io/api/sensors/{id}/tasks

Endpoint to get a specific task:
GET # www.mysensor.io/api/tasks/{id}

Endpoint to add a new task:
POST # www.mysensor.io/api/tasks

Endpoint to delete/stop a task:
DELETE # www.mysensor.io/api/tasks/{id}

## Summary of the challenge:

* The application takes in a list of unique sensor id's. These id's are numbers of with a length of 17. You can choose
  how the input will happen: a csv file that is uploaded, etc etc.
* The application is something that keeps on running until you give it a new list. So it keeps on looping to return the
  most up to date status when requested.
* The application returns the latest update of the status of the list of sensors. So it shows which sensors are ok and
  which are not ok and why they are not ok. How this is returned is up to you: it can be a rest api, a webservice, it
  doesn't matter.
* A sensor is ok if the firmware is compatible with the latest configuration and if the latest configuration has been
  installed.
* There is a time cost to updating the firmware: This can easily take up to an hour. So if the firmware isn't the latest
  firmware, but is compatible with the configuration, you don't have to schedule an update.
* Check for tasks that have been running for more than 2 hours. These need to be cancelled.
* The application checks for every sensor if the firmware version is compatible with the latest configuration. The
  firmware version always looks has the following structure: FW:DD-MM-YYYY#XX. So it is basically the letters FW, a
  colon, then the date of the firmware starting with day, then month, then year. There is '#'in between and then follows
  a number of 2 digits. Because the firmware can get many updates over a day. The firmware is compatible starting from
  version FW:23-09-2022:03.
