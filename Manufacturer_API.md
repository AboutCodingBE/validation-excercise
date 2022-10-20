# Manufacturer API

This is a made up API, slightly based on a real API. The domain 'www.mysensor.io' did not exist at the time of creating 
this challenge. The URL is made up. So there is no point in trying to reach this API. In case this changes, it is purely 
accidental and it should be reported to me (nicholas@aboutcoding.be)

## Sensors

#### GET # www.mysensor.io/api/sensors/{id}

API endpoint to get information about a single sensor. The response looks like this:

```json
{
  "serial": 123456789098789,
  "status_id": 1,
  "current_configuration": "some_oonfiguration.cfg",
  "current_firmware": "FW:23-09-2022:03",
  "created_at": "2022-03-31 11:26:08",
  "updated_at": "2022-10-18 17:53:48",
  "status_name": "Idle",
  "next_task": null,
  "task_count": 5,
  "activity_status": "Online",
  "task_queue": "Empty"
}
```

##### Status

A sensor can be in one of the next statuses:

|Status name |Status id| Description|
|------------|---------|------------|
|Idle|1 | The sensor doesn't have any tasks|
|Awaiting|2 | A task has been scheduled, the device is waiting for execution|
|Updating|3 | The device is doing an update|

##### Activity status

A sensor can have the following activity statuses:

|Status name |Status id| Description|
|------------|---------|------------|
|Online|1 | The sensor is online and capable of sending information|
|Disconnectd|2 | The sensor is not online and cannot send information|
|Error|3 | The sensor is in an error state and cannot function normally|

## Tasks

#### GET # www.mysensor.io/api/tasks/{id}

API endpoint to get information about a single task. The response looks like this: 

```json
{
  "id": 16624171,
  "sensor_serial": 12345678909789,
  "type": "update_firmware",
  "status_id": 2,
  "attempt_count": 3,
  "created_at": "2022-10-17 16:00:07",
  "updated_at": "2022-10-17 16:49:39",
  "file_id": "a3e4aed2-b091-41a6-8265-2185040e2c32",
  "status_name": "Completed"
}
```

##### Task type

A task can have be one of 2 types: 

|Type | Description|
|------------|------------|
|update_firmware| A task to update the firmware|
|update_configuration| A task to update the configuration|

Both types of tasks actually need a file (either the firmware installation file or the configuration file) to be able
to do their job. That is why the API is showing a `file_id`, which is a reference to an existing file. 

##### Status

A task can be in one of the next statuses:

|Status name |Status id| Description|
|------------|---------|------------|
|Pending|1 | The task is waiting to start|
|Completed|2 | The task has successfully completed|
|Error|3 | The task was either interrupted by an error or it completed causing an error|
|Ongoing|4 | The task is ongoing|

#### GET # www.mysensor.io/api/tasks?sensor_serial=124345654567876

API endpoint to get the tasks linked to a specific sensor. A response looks like this: 

```json
{
  "current_page": 1,
  "from": 1,
  "to": 10,
  "total": 26,
  "first_page": 1,
  "last_page": 3,
  "per_page": 10,
  "data": [
    {
      "id": 16624171,
      "sensor_serial": 12345678909789,
      "type": "update_firwmare",
      "status_id": 2,
      "attempt_count": 3,
      "created_at": "2022-10-17 16:00:07",
      "updated_at": "2022-10-17 16:49:39",
      "file_id": "a3e4aed2-b091-41a6-8265-2185040e2c32",
      "status_name": "Completed"
    },
    {
      "id": 16624170,
      "sensor_serial": 12345678909789,
      "type": "update_firwmare",
      "status_id": 2,
      "attempt_count": 3,
      "created_at": "2022-10-17 16:00:07",
      "updated_at": "2022-10-17 16:49:39",
      "file_id": "a3e4aed2-b091-41a6-8265-2185040e2c32",
      "status_name": "Completed"
    }
  ]
}
```

#### PUT # www.mysensor.io/api/tasks

API endpoint to create a task for a specific sensor.

```json
{
  "sensor_serial": 234545767890987,
  "file_id": "a3e4aed2-b091-41a6-8265-2185040e2c32",
  "type": "update_configuration"
}

```

#### DELETE # www.mysensor.io/api/tasks/{id}

